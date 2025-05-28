package Helper;

import Entity.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {

    public static final String ANSI_RESET = "\u001B[0m";

    public static LocalDate datetimteToLocalDate(String dateTime)
    {
        String formato = "yyyy-MM-dd";
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(formato);

        return LocalDate.parse(dateTime, formater);
    }

    public static String colorToAANSI(Color c)
    {
        String color = null;
        switch (c) {
            case NEGRO:
                color = "\u001B[30m";
                break;
            case ROJO:
                color = "\u001B[31m";
                break;
            case VERDE:
                color = "\u001B[32m";
                break;
            case AMARILLO:
                color = "\u001B[33m";
                break;
            case AZUL:
                color = "\u001B[34m";
                break;
            case MORADO:
                color = "\u001B[35m";
                break;
            case CELESTE:
                color = "\u001B[36m";
                break;
            case BLANCO:
                color = "\u001B[37m";
                break;
            case GRIS:
                color = "\u001B[90m";
                break;
            case NARANJA:
                color =  "\u001B[33m";
                break;
            case ROSA:
                color =  "\u001B[35m";
                break;
            case MARRÓN:
                color =  "\u001B[33m";
                break;
            case DORADO:
                color =  "\u001B[33m";
                break;
            case PLATEADO:
                color =  "\u001B[37m";
                break;
        }
        return color;
    }
}

