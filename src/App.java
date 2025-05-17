import Entity.Liga;
import Repository.EquipoRepository;
import Repository.JornadaRepository;
import Repository.LigaRepository;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) {



        LigaRepository ligaRepo = new LigaRepository(new EquipoRepository(), new JornadaRepository());
        Liga ligas = null;

        ligas = ligaRepo.findById(1);

        //for(Liga l : ligas)
        //{
            System.out.println(ligas.getNombre());
        //}

    }
}