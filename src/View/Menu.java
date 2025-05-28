package View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static int mostrarMenu(List<String> opciones, String mensaje) { // añadir array de opc validas

        Scanner lector = Lector.getLector();
        String opc = "";

        System.out.println("╔" + "═".repeat(30) + "═".repeat(30) + "╗"); // formato de los bordes del menu
        System.out.println("║" + "    " + mensaje);
        System.out.println("╠" + "═".repeat(30) + "═".repeat(30) + "╣");

        for (String opcion : opciones) {
            System.out.println("║ " + opcion); // for each para imprimir cada opcion del menu
        }
        System.out.println("╚" + "═".repeat(60) + "╝");
        return Lector.getNumeroEnRango(1, opciones.size());
    }

}
