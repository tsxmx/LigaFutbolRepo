package Controller;

import Entity.*;
import Repository.JugadorRepository;
import Repository.PartidoRepository;
import Service.*;
import View.Lector;
import View.Mensaje;
import com.mysql.cj.util.EscapeTokenizer;

public class LigaApp {

    private final MenuService menuService;
    private final LigaService ligaService;
    private final EquipoService equipoService;
    private final JugadorService jugadorservice;
    private final PartidoService partidoService;
    private final EventoService eventoService;
    private Liga liga;

    public LigaApp(){
        liga = null;

        this.menuService = new MenuService();
        this.ligaService = new LigaService();
        this.equipoService = new EquipoService();
        this.jugadorservice = new JugadorService();
        this.partidoService = new PartidoService();
        this.eventoService = new EventoService();
    }


    public void run() {

        boolean salirMenuPrincipal = false;

        do {
            switch (menuService.mostrarLigas()) {
                case 1:
                    liga = ligaService.getLigaById(1);
                    break;
                case 2:
                    salirMenuPrincipal = true;
                    Mensaje.shutDownMessage();
                    continue;
                default:
                    continue;
            }
            boolean salirMenuInterno = false;

            do {
                switch (ligaService.getPeriodoActual(liga)) {
                    case 1:

                        switch (menuService.menuPreLiga()) {
                            case 1:
                                crearEquipo();
                                break;
                            case 2:
                                eliminaEquipo();
                                break;
                            case 3:
                                crearJugador();
                                break;
                            case 4:
                                eliminaJugador();
                                break;
                            case 5:
                                mostrarEquipos();
                                break;
                            case 6:
                                mostrarJugadores();
                                break;
                            case 7:
                                mostrarDatosLiga();
                                break;
                            case 8:
                                salirMenuInterno = true;
                                break;
                        }

                        break;
                    case 2:
                        ligaService.generarCalendarioDoubleRoundRobin(liga); // si la liga empieza hoy, generamos el calendario de jornadas
                        break;
                    case 3:
                        switch (menuService.menuInLiga()){
                            case 1:
                                mostrarClasificacion();
                                break;
                            case 2:
                                getCalendario();
                                break;
                            case 3:
                                narracionPartido();
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                salirMenuInterno = true;
                                break;
                        };
                        break;
                }
            } while (!salirMenuInterno);

        } while (!salirMenuPrincipal);
    }

    //Menu principal

    private void crearEquipo(){
        Equipo equipoNuevo = equipoService.createEquipo();
        liga.addEquipo(equipoNuevo);
        equipoService.saveUpdateEquipo(equipoNuevo);
    }

    private void eliminaEquipo(){
        int idEquipo = menuService.mostrarEquiposByLiga(liga.getId(), "Seleccione el Equipo");

        if(idEquipo != -1){
            if(menuService.menuEliminar() == 1){
                menuService.clear();
                Equipo equipoBorrar = equipoService.getEquipoById(idEquipo);
                equipoService.deleteEquipo(equipoBorrar);
                liga.removeEquipo(equipoBorrar);
                menuService.stop();
            }
        }
    }

    private void crearJugador(){
        Jugador jugadorNuevo = jugadorservice.crearJugador();

        if(jugadorNuevo != null){
            int idEquipo = menuService.mostrarEquiposByLiga(liga.getId(), "Seleccione el Equipo del Jugador");
            Equipo equipoJug = equipoService.getEquipoById(idEquipo);

            equipoJug.addJugador(jugadorNuevo);
            jugadorservice.saveUpdateJugador(jugadorNuevo);
        }
    }

    private void eliminaJugador(){

        int idEquipo = menuService.mostrarEquiposByLiga(liga.getId(), "Seleccione el Equipo del Jugador");
        if(idEquipo != -1) {
            Equipo equipoJug = equipoService.getEquipoById(idEquipo);
            int idJugador = menuService.mostrarJugadoresByEquipoId(equipoJug.getId());

            if (idJugador != -1) {
                if (menuService.menuEliminar() == 1) {

                    menuService.clear();
                    Jugador jugadorBorrar = jugadorservice.getJugadorById(idJugador);
                    jugadorservice.deleteJugador(jugadorBorrar);
                    equipoJug.removeJugador(jugadorBorrar);
                }
            }
        }
    }

    private void mostrarEquipos(){
        menuService.clear();
        menuService.mostrarEquiposByLiga(liga.getId(), "Lista de equipos");
    }

    private void mostrarJugadores(){
        menuService.clear();

        int idEquipo = menuService.mostrarEquiposByLiga(liga.getId(), "Seleccione el Equipo");
        if(idEquipo != -1) {
            Equipo equipoJug = equipoService.getEquipoById(idEquipo);
            menuService.mostrarJugadoresByEquipoId(equipoJug.getId());
        }
    }


    private void mostrarDatosLiga(){
        menuService.mostrarDatosLiga(liga);
    }

    // METODOS DEL MENU PARA LA LIGA EMPEZADA

    private void mostrarClasificacion(){
        menuService.clear();
        menuService.mostrarClasificacion(liga);
    }

    private void getCalendario(){
        boolean salir = false;
        do {
            int opcJornada = menuService.mostrarJornadasByLigaId(liga.getId(), "Seleccione una Jornada");
            if(opcJornada != -1){
                menuService.mostrarPartidosByJornadaId(opcJornada, "Lista de partidos de la Jornada");
            }else{
                salir = true;
            }
        }while(!salir);
    }

    private void narracionPartido(){

        boolean salir = false;
        do {
            int opcJornada = menuService.mostrarJornadasByLigaId(liga.getId(), "Seleccione una Jornada");
            if(opcJornada != -1){
                int opcPartido = menuService.mostrarPartidosByJornadaId(opcJornada, "Seleccione el partido a Narrar");
                Partido partido = partidoService.getPartidoById(opcPartido);
                if(partido.getEventos().isEmpty()){
                    int once = 3;
                    for(Jugador j : equipoService.getJugadoresByEquipoId(partido.getLocal().getId())){
                        if(once > 0){
                            if(menuService.menuOnceInicial(j) == 1)
                            {
                                once --;
                              Evento event = eventoService.inciaPartido();
                                partido.addEvento(event);
                                j.addEvento(event);
                            }
                        }
                    }
                    once = 3;
                    for(Jugador j : equipoService.getJugadoresByEquipoId(partido.getVisitante().getId())){
                        if(once > 0){
                            if(menuService.menuOnceInicial(j) == 1)
                            {
                                once --;
                              Evento event = eventoService.inciaPartido();
                            partido.addEvento(event);
                            j.addEvento(event);
                            }
                        }
                    }
                }

                boolean salirNarracion = false;
                do {
                    int opcMenuPartido = menuService.menuNarrarPartido(partido);
                    if (opcMenuPartido != 6) {
                        switch (opcMenuPartido) {
                            case 1:
                                partido.addGoalLoc();
                                break;
                            case 2:
                                partido.subsGoalLoc();
                                break;
                            case 3:
                                partido.addGoalVis();
                                break;
                            case 4:
                                partido.subsGoalVis();
                                break;
                            case 5:
                                int equipo = menuService.getEquiposByPartido(partido);
                                if(equipo != 3){
                                    Jugador jugador;

                                    if(equipo == 1){
                                        jugador = jugadorservice.getJugadorById(menuService.mostrarJugadoresByEquipoId(partido.getLocal().getId()));
                                    }
                                    else{
                                        jugador = jugadorservice.getJugadorById(menuService.mostrarJugadoresByEquipoId(partido.getVisitante().getId()));
                                    }

                                    Evento event = eventoService.createEvento();
                                    partido.addEvento(event);
                                    jugador.addEvento(event);
                                    eventoService.saveUpdate(event);

                                    switch(event.getTipo().getId()){
                                        case 1:
                                            if(equipo == 1){
                                                partido.addGoalLoc();
                                            }else{
                                                partido.addGoalVis();
                                            }
                                            break;
                                        case 10:
                                            if(equipo == 1){
                                                partido.addGoalVis();
                                            }else{
                                                partido.addGoalLoc();
                                            }
                                            break;
                                    }
                                }
                                break;

                        }
                    } else {
                        salirNarracion = true;
                    }
                }while(!salirNarracion);

                partidoService.saveUpdate(partido);
            }else{
                salir = true;
            }
        }while(!salir);


    }


}
