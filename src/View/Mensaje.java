package View;

public class Mensaje {

    public static void shutDownMessage(){
        System.out.println("\n" +  "=".repeat(25) + "\n|| PROGRAMA FINALIZADO ||\n" + "=".repeat(25));
    }

    public static void saveMessage(){
        System.out.println("EQUIPO CREADO CORRECTAMENTE !");
    }

    public static void updateMessage(){
        System.out.println("EQUIPO ACTUALIZADO CORRECTAMENTE !");
    }

    public static void deleteMessage(){
        System.out.println("EQUIPO ELIMINADO CORRECTAMENTE !");
    }

}
