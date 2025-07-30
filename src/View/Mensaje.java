package View;

public class Mensaje {

    public static void shutDownMessage(){
        System.out.println("\n" +  "=".repeat(25) + "\n|| PROGRAMA FINALIZADO ||\n" + "=".repeat(25));
    }

    public static void saveMessage(){
        System.out.println("GUARDADO COMPLETADO SIN PROBLEMAS");
    }

    public static void updateMessage(){
        System.out.println("ACTUALIZADO REALIZADO CORRECTAMENTE");
    }

    public static void deleteMessage(){
        System.out.println("ELIMINADO REALIZADO CORRECTAMENTE");
    }

    public static void genericToString(Object o){
        System.out.println(o);
    }

}
