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
        switch (c) {
            case NEGRO:
                return "\u001B[30m";
            case ROJO:
                return "\u001B[31m";
            case VERDE:
                return "\u001B[32m";
            case AMARILLO:
                return "\u001B[33m";
            case AZUL:
                return "\u001B[34m";
            case MORADO:
                return "\u001B[35m";
            case CELESTE:
                return "\u001B[36m";
            case BLANCO:
                return "\u001B[37m";
            case GRIS:
                return "\u001B[90m";
            case NARANJA:
                return "\u001B[33m";
            case ROSA:
                return "\u001B[35m";
            case MARRÓN:
                return "\u001B[33m";
            case DORADO:
                return "\u001B[33m";
            case PLATEADO:
                return "\u001B[37m";
            default:
                return ANSI_RESET;
        }
    }
}

