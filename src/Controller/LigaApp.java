package Controller;

import Entity.Equipo;
import Entity.Jugador;
import Entity.Liga;
import Entity.Partido;
import Repository.JugadorRepository;
import Repository.PartidoRepository;
import Service.EquipoService;
import Service.JugadorService;
import Service.LigaService;
import Service.MenuService;
import View.Lector;
import View.Mensaje;
import com.mysql.cj.util.EscapeTokenizer;

public class LigaApp {

    private final MenuService menuService;
    private final LigaService ligaService;
    private final EquipoService equipoService;
    private final JugadorService jugadorservice;
    private Liga liga;

    public LigaApp(){
        liga = null;

        this.menuService = new MenuService();
        this.ligaService = new LigaService();
        this.equipoService = new EquipoService();
        this.jugadorservice = new JugadorService();
    }


    public void run() {

        boolean salirMenuPrincipal = false;

        do {
            switch (menuService.mostrarLigas()) {
                case 1:
                    liga = ligaService.getLigaById(1);
                    break;
                case 2:
                    //liga = Lector.getNewLiga();
                    //ligaservice.save(liga);
                    break;
                case 3:
                    salirMenuPrincipal = true;
                    Mensaje.shutDownMessage();
                    continue;
                default:
                    continue;
            }
            boolean salirMenuInterno = false;

            do {
                int periodo = ligaService.getPeriodoActual(liga);
                if (periodo == 1) {
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
                } else {
                    switch (menuService.menuInLiga()){
                        case 1:
                            mostrarClasificacion();
                            break;
                        case 2:
                            break;
                        case 3:
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


}
