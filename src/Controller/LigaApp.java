package Controller;

import Entity.Partido;
import Repository.PartidoRepository;
import Service.MenuService;

public class LigaApp {

    public void run(){

        MenuService menuService = new MenuService();

        int idLiga = menuService.mostrarLigas();

        int idJornada = menuService.mostrarJornadasByLigaId(idLiga);

        PartidoRepository P = new PartidoRepository();

        Partido p = P.findById(menuService.mostrarPartidosByJornadaId(idJornada));

        menuService.menuNarrarPartido(p);
    }

}
