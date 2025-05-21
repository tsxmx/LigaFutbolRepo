import Entity.Equipo;
import Entity.Jornada;
import Entity.Liga;
import Entity.Partido;
import Repository.EquipoRepository;
import Repository.JornadaRepository;
import Repository.JugadorRepository;
import Repository.LigaRepository;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) {



        JornadaRepository JornadaRepo = new JornadaRepository();
        List<Partido> partidos   = new ArrayList<>();

        partidos.addAll(JornadaRepo.findPartidosByJornadaId(1));

        System.out.println(partidos);

        /*
        for(Jornada j : jornadas)
        {
            System.out.println(j.getPartidos().getFirst().getJornada().getLiga().getNombre());
        }

         */

    }
}