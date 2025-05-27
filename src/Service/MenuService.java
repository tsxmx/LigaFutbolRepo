package Service;
import Entity.*;
import Helper.Converter;
import View.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static View.Menu.mostrarMenu;

public class MenuService {

    private final LigaService ligaService;
    private final JornadaService jornadaService;
    private final EquipoService equipoService;

    public MenuService()
    {
        this.ligaService = new LigaService();
        this.jornadaService = new JornadaService();
        this.equipoService = new EquipoService();
    }

    public int menuPreLiga(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Crear Equipo");
        listaOpciones.add("2. Eliminar Empleado");
        listaOpciones.add("3. Añadir Jugador");
        listaOpciones.add("4. Eliminar jugador");
        listaOpciones.add("5. Ver Equipos");
        listaOpciones.add("6. Ver Jugadores");
        listaOpciones.add("7. Datos Liga");
        listaOpciones.add("8. Salir del programa");

        return mostrarMenu(listaOpciones, "Menú Principal"); // return de la opcion
    }

    public int menuInLiga(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Clasificacion");
        listaOpciones.add("2. Narrar Partido (Registro)");
        listaOpciones.add("3. Jornadas totales");
        listaOpciones.add("4. Detalles partido");
        listaOpciones.add("5. Salir del programa");

        return mostrarMenu(listaOpciones, "Menú Principal"); // return de la opcion
    }

    public int menuAddJugador1(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Jugador Profesional");
        listaOpciones.add("2. Jugador Amateur");
        listaOpciones.add("3. Salir del programa");

        return mostrarMenu(listaOpciones, "Creacion de Jugador"); // return de la opcion
    }

    public int menuNarrarPartido(Partido partido){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Añadir gol Local");
        listaOpciones.add("3. Eliminar gol Local");
        listaOpciones.add("2. Añador gol visitante");
        listaOpciones.add("3. Eliminar gol Visitante");
        listaOpciones.add("3. añadir Evento");
        listaOpciones.add("3. eliminar Evento");
        listaOpciones.add("5. volver al Menu principal");

        return mostrarMenu(listaOpciones,
                "Partido: L-"+Converter.colorToAANSI(partido.getLocal().getPrimario()) + partido.getLocal().getNombre() + Converter.ANSI_RESET+
                        " / V-"+ Converter.colorToAANSI(partido.getVisitante().getSecundario()) + partido.getVisitante().getNombre() + Converter.ANSI_RESET); // return de la opcion
    }



    /**
     * Impresion de las ligas de este programa para elegir cual usar.
     * @return
     */
    public int mostrarLigas(){
        ArrayList<Liga> ligas = new ArrayList<>(this.ligaService.getLigas());

        ArrayList<String> ligasMenu = new ArrayList<>(); // array para las opciones del menu
        for(Liga l : ligas)
        {
            ligasMenu.add(l.getId() + ". " + l.getNombre());
        }
        ligasMenu.add(ligasMenu.size()+1 + ". salir del programa");

        return mostrarMenu(ligasMenu, "Selecciona la Liga");
    }

    public int mostrarEquiposByLiga(int idLiga){
        ArrayList<Equipo> equiposLiga = new ArrayList<>(this.equipoService.getEquiposByLigaId(idLiga));

        ArrayList<String> equiposMenu = new ArrayList<>();
        for(Equipo e : equiposLiga)
        {
            equiposMenu.add(e.getId() + ". " + e.getNombre());
        }
        equiposMenu.add(equiposMenu.size()+1 + ". volver atrás");

        return mostrarMenu(equiposMenu, "Seleccione el Equipo");

    }

    public int mostrarJugadoresByEquipoId(int idEquipo){
        ArrayList<Jugador> jugadoresEquipo = new ArrayList<>(this.equipoService.getJugadoresByEquipoId(idEquipo));

        ArrayList<String> jugadoresMenu = new ArrayList<>();
        for(Jugador j : jugadoresEquipo)
        {
            int indice = jugadoresEquipo.indexOf(j) + 1;
            jugadoresMenu.add(indice + ". " + j.getNombre());
        }
        jugadoresMenu.add(jugadoresMenu.size()+1 + ". volver atrás");

        return mostrarMenu(jugadoresMenu, "Seleccione el Jugador");
    }

    public int mostrarJornadasByLigaId(int idLiga)
    {
        ArrayList<Jornada> jornadasLiga = new ArrayList<>(this.ligaService.getJornadasByLigaId(idLiga));

        ArrayList<String> jornadasMenu = new ArrayList<>();
        for(Jornada j : jornadasLiga)
        {
            jornadasMenu.add(j.getId() + ". JORNADA - " + j.getFechaInicio());
        }
        jornadasMenu.add(jornadasMenu.size()+1 + ". volver atrás");

        return mostrarMenu(jornadasMenu, "Seleccione la Jornada");
    }

    public int mostrarPartidosByJornadaId(int jornadaId)
    {
        ArrayList<Partido> partidosJornada = new ArrayList<>(this.jornadaService.getPartidosByJornadaId(jornadaId));

        ArrayList<String> partidosMenu = new ArrayList<>();
        for(Partido p : partidosJornada)
        {
            int indice = partidosJornada.indexOf(p) + 1;
            partidosMenu.add(indice + " - " +
                    Converter.colorToAANSI(p.getLocal().getPrimario()) + p.getLocal().getNombre() + Converter.ANSI_RESET +
                    " VS " +
                    Converter.colorToAANSI(p.getVisitante().getSecundario()) + p.getVisitante().getNombre() + Converter.ANSI_RESET);
        }
        partidosMenu.add(partidosMenu.size()+1 + ". volver atrás");

        return mostrarMenu(partidosMenu, "Seleccione un partido");
    }

}
