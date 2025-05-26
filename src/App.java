import Entity.*;
import Repository.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) {



        PartidoRepository PR = new PartidoRepository();
        Partido partido;

        partido = PR.findById(2);



            System.out.println(partido.getFechaInicio());




    }
}