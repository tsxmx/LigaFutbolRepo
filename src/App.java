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



        EquipoRepository ER = new EquipoRepository();
        List<Equipo> equipos   = new ArrayList<>();

        equipos.addAll(ER.findAll());

        for(Equipo e : equipos)
        {
            System.out.println(e.getNombre());
            System.out.println(e.getPlantilla().getFirst().getNombre());
        }



    }
}