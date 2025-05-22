import Entity.*;
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



        JugadorRepository JR = new JugadorRepository();
        List<Jugador> jugadores = new ArrayList<>();

        jugadores.addAll(JR.findAll());

        for(Jugador j : jugadores)
        {
            System.out.println(j.getNombre());
        }



    }
}