import Entity.Equipo;
import Entity.Liga;
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



        LigaRepository ligaRepo = new LigaRepository();
        List<Equipo> equipos = new ArrayList<>();

        equipos.addAll(ligaRepo.findEquiposByLigaId(1));



        for(Equipo e : equipos)
        {
            System.out.println(e.getNombre());
        }

    }
}