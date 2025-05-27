package View;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public static int mostrarMenu(ArrayList<String> opciones, String mensaje) { // añadir array de opc validas

        Scanner lector = Lector.getLector();
        String opc = "";

        System.out.println("╔" + "═".repeat(17) + "═".repeat(17) + "╗"); // formato de los bordes del menu
        System.out.println("║" + "    " + mensaje);
        System.out.println("╠" + "═".repeat(17) + "═".repeat(17) + "╣");

        for (String opcion : opciones) {
            System.out.println("║ " + opcion); // for each para imprimir cada opcion del menu
        }
        System.out.println("╚" + "═".repeat(34) + "╝");
        return Lector.getNumeroEnRango(1, opciones.size());
    }

}
