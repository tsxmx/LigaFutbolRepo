package Controller;

import Entity.Equipo;
import Entity.Liga;
import Entity.Partido;
import Repository.PartidoRepository;
import Service.EquipoService;
import Service.LigaService;
import Service.MenuService;
import View.Lector;
import View.Mensaje;
import com.mysql.cj.util.EscapeTokenizer;

public class LigaApp {

    private final MenuService menuService;
    private final LigaService ligaService;
    private final EquipoService equipoService;
    private Liga liga;

    public LigaApp(){
        liga = null;

        this.menuService = new MenuService();
        this.ligaService = new LigaService();
        this.equipoService = new EquipoService();
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
                    }
                } else {
                    menuService.menuInLiga();
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
        int idEquipo = menuService.mostrarEquiposByLiga(liga.getId());

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



}
