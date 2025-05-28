package Controller;

import Entity.Liga;
import Entity.Partido;
import Repository.PartidoRepository;
import Service.LigaService;
import Service.MenuService;
import View.Mensaje;

public class LigaApp {

    private final MenuService menuService;
    private final LigaService ligaService;

    public LigaApp(){
        this.menuService = new MenuService();
        this.ligaService = new LigaService();
    }


    public void run() {

        boolean salirMenuPrincipal = false;
        Liga liga = null;

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
                    System.out.println("Opción no válida, por favor, inténtalo de nuevo.");
                    continue;
            }
            boolean salirMenuInterno = false;
            try {
                do {
                    int periodo = ligaService.getPeriodoActual(liga);
                    if (periodo == 1) {
                        switch (menuService.menuPreLiga()) {
                            case 1:
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
                        }
                    } else {
                        menuService.menuInLiga();
                    }
                } while (!salirMenuInterno);
            } catch (NullPointerException e) {
                System.err.println("ERROR :: No se ha seleccionado una liga válida o la liga es nula. Por favor, selecciona una liga existente o crea una nueva.");
            }
        } while (!salirMenuPrincipal);
    }

}
