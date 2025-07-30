package Service;

import Entity.Equipo;
import Entity.Jornada;
import Entity.Liga;
import Entity.Partido;
import Repository.EquipoRepository;
import Repository.LigaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LigaService {

    private final LigaRepository ligaRepository;
    private final EquipoService equipoService;
    private final JornadaService jornadaService;
    private final PartidoService partidoService;

    public LigaService() {
        this.ligaRepository = new LigaRepository();
        this.equipoService = new EquipoService();
        this.jornadaService = new JornadaService();
        this.partidoService = new PartidoService();
    }


    // Metodos del repositorio
    public List<Liga> getLigas()
    {
        return ligaRepository.findAll();
    }

    public Liga getLigaById(int ligaId){
        return ligaRepository.findById(ligaId);
    }

    public List<Jornada> getJornadasByLigaId(int idLiga)
    {
        return ligaRepository.findJornadasByLigaId(idLiga);
    }

    // Metodos propios de la clase liga

    public int getPeriodoActual(Liga liga) {
        if (liga == null) {
            throw new NullPointerException("ERROR :: El objeto liga no puede ser nulo!");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicioLiga = liga.getFechaInicio();

        if (fechaInicioLiga.isAfter(hoy)) {
            return 1;
        } else if (fechaInicioLiga.isEqual(hoy)) {
            return 2;
        } else {
            return 3;
        }
    }

    public Liga getClasificacion(Liga liga){
        List<Equipo> equiposClasi = equipoService.getEquiposByLigaId(liga.getId());

        for(Equipo equipo : equiposClasi){

            int idEquipo = equipo.getId();

            equipo.setPuntos(equipoService.calculaPuntos(idEquipo));

            int golesAFavor = equipoService.getGetGolesAFavor(idEquipo);
            equipo.setGolesFavor(golesAFavor);

            int golesEnContra = equipoService.getGetGolesEnContra(idEquipo);
            equipo.setGolesContra(golesEnContra);

            equipo.setDiferenciaGoles(golesAFavor - golesEnContra);

            liga.addEquipo(equipo);
        }
        return liga;
    }

    /**
     * Genera el calendario completo de la liga utilizando un algoritmo de double round-robin.
     * Los partidos se generan con goles a 0 y se guardan en la base de datos a través de los servicios.
     * Gestiona equipos impares añadiendo un equipo 'BYE' que simula los descansos.
     *
     * @param liga El objeto Liga para el cual se generará el calendario.
     * @throws RuntimeException Si no hay suficientes equipos o ocurre un error al guardar.
     */
    public void generarCalendarioDoubleRoundRobin(Liga liga) {
        List<Equipo> equiposReales = equipoService.getEquiposByLigaId(liga.getId());

        if (equiposReales.size() < 2) {
            throw new RuntimeException("Se necesitan al menos 2 equipos reales para generar un calendario de liga.");
        }

        int numEquiposOriginal = equiposReales.size();
        int numEquiposAlgoritmo = numEquiposOriginal;

        Equipo equipoFantasma = null;
        if (numEquiposOriginal % 2 != 0) {
            equipoFantasma = new Equipo(-1, "BYE_TEAM", "N/A", null, null, 0, null, null);
            equiposReales.add(equipoFantasma);
            numEquiposAlgoritmo++;
        }

        int numJornadasTotal = 2 * (numEquiposAlgoritmo - 1);

        LocalDate fechaActualJornada = liga.getFechaInicio().plusMonths(1).plusDays(15);
        LocalTime horaPartido = LocalTime.of(20, 0);

        // Crea una copia de la lista para el algoritmo, incluyendo el posible equipo fantasma
        // Esta lista será la que se manipule con rotaciones.
        List<Equipo> equiposEnCalendario = new ArrayList<>(equiposReales);

        // Algoritmo del Círculo para generar emparejamientos
        for (int j = 0; j < numJornadasTotal / 2; j++) { // Iterar para la primera mitad de las jornadas

            // Crear y guardar la Jornada para la primera vuelta
            Jornada jornadaPrimeraVuelta = new Jornada(0, fechaActualJornada, fechaActualJornada.plusDays(2), liga);
            jornadaService.saveUpdate(jornadaPrimeraVuelta);

            // Calcular la fecha para la jornada de la segunda vuelta
            LocalDate fechaSegundaVuelta = fechaActualJornada.plusWeeks(numJornadasTotal / 2);
            Jornada jornadaSegundaVuelta = new Jornada(0, fechaSegundaVuelta, fechaSegundaVuelta.plusDays(2), liga);
            jornadaService.saveUpdate(jornadaSegundaVuelta);

            // Generar TODOS los partidos para esta jornada
            // El bucle interno debe ir hasta numEquiposAlgoritmo / 2 para generar todos los emparejamientos
            for (int i = 0; i < numEquiposAlgoritmo / 2; i++) {
                Equipo localEquipo = equiposEnCalendario.get(i);
                Equipo visitanteEquipo = equiposEnCalendario.get(numEquiposAlgoritmo - 1 - i);

                // Asegurarse de que ninguno de los equipos es el fantasma antes de crear un partido real
                if (localEquipo.getId() != -1 && visitanteEquipo.getId() != -1) {
                    // Partido de la primera vuelta (roles normales)
                    Partido partidoVuelta1 = new Partido(0, 0, 0,
                            fechaActualJornada,
                            jornadaPrimeraVuelta,
                            localEquipo,
                            visitanteEquipo);
                    partidoService.saveUpdate(partidoVuelta1);

                    // Partido de la segunda vuelta (roles invertidos)
                    Partido partidoVuelta2 = new Partido(0, 0, 0,
                            fechaSegundaVuelta,
                            jornadaSegundaVuelta,
                            visitanteEquipo, // Roles invertidos
                            localEquipo); // Roles invertidos
                    partidoService.saveUpdate(partidoVuelta2);

                } else {
                    // Manejar el equipo que descansa si el fantasma está involucrado
                    Equipo descansaEquipo = (localEquipo.getId() == -1) ? visitanteEquipo : localEquipo;
                    System.out.println("Equipo " + descansaEquipo.getNombre() + " descansa en jornada " + (j + 1) + " y " + (j + 1 + numJornadasTotal / 2));
                }
            }

            // Rotar los equipos para la siguiente jornada
            // El primer equipo se mantiene fijo. El resto rota.
            // Guardamos el primer equipo temporalmente.
            Equipo primerEquipoFijo = equiposEnCalendario.get(0);
            // Creamos una nueva lista con los equipos que van a rotar (del segundo al último)
            List<Equipo> rotatorios = new ArrayList<>(equiposEnCalendario.subList(1, numEquiposAlgoritmo));
            // Movemos el último equipo rotatorio al principio de la lista de rotatorios
            if (!rotatorios.isEmpty()) {
                Equipo ultimoRotatorio = rotatorios.remove(rotatorios.size() - 1);
                rotatorios.add(0, ultimoRotatorio);
            }
            // Reconstruimos la lista principal 'equiposEnCalendario' con el fijo y los rotados
            equiposEnCalendario.clear();
            equiposEnCalendario.add(primerEquipoFijo);
            equiposEnCalendario.addAll(rotatorios);

            // Incrementar la fecha para la siguiente jornada de la primera vuelta
            fechaActualJornada = fechaActualJornada.plusWeeks(1);
        }

        // Eliminar el equipo fantasma de la lista de equipos reales si se añadió
        // (Esto solo afecta la lista en memoria, no la BD si el fantasma no se insertó)
        if (equipoFantasma != null) {
            equiposReales.remove(equipoFantasma);
        }

        System.out.println("Calendario de double round-robin con " + numJornadasTotal + " jornadas generado y guardado.");
    }
}





