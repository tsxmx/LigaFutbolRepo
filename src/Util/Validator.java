package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import Exception.*;

public class Validator {

    /**
     * Metodo para comprobar que una cadena esta formada por numeros
     *
     * Reg ex: '\d+' significa directamente 1 o mas numeros
     *
     * @param cad
     * @return
     */
    public static boolean esNumero(String cad) {

        if (cad == null || cad.isEmpty()) {
            throw new CadenaVacia("ERROR :: La cadena introducida esta Vacía");
        }

        if (!cad.matches("^\\d+$")) { // \d+ significa uno o más dígitos
            throw new NumeroNoValido("ERROR :: La cadena introducida no es un numero");
        };

        return true;
    }

    public static boolean numeroEnRango(int min, int max, int numero)
    {
        if(numero >= min && numero <= max){
            throw new NumeroFueraDeRango("ERROR :: El numero se encuentra fuera del rango establecido");
        }

        return true;
    }

    /**
     * Metodo que verifica que una cadena esta formada solo por letras
     * RegExp: '[a-zA-Z]+' 1 o mas letras mayus y minusc
     * @param cad
     * @return
     */
    public static boolean esPalabra(String cad)
    {
        if (cad == null || cad.isEmpty()) {
            throw new CadenaVacia("ERROR :: La cadena introducida esta Vacía");
        }
        // [a-zA-Z]+ significa una o más letras (mayúsculas o minúsculas)
        if(!cad.matches("^[a-zA-Z]+$")){
            throw new PalabraNoValida("ERROR :: La cadena introducida no es una palabra");
        };
        return true;
    }

    /**
     * Metodo que verifica que una cadena esta formada solo por 1 letra
     * RegExp: '[a-zA-Z]' 1 letras mayus y minusc
     * @param cad
     * @return
     */
    public static boolean esLetra(String cad)
    {

        if (cad == null || cad.isEmpty()) {
            throw new CadenaVacia("ERROR :: La cadena introducida esta Vacía");
        }
        // [a-zA-Z]+ significa una o más letras (mayúsculas o minúsculas)
        if(!cad.matches("[a-zA-Z]")){
            throw new PalabraNoValida("ERROR :: La cadena introducida no es una palabra");
        };

        return true;
    }

    public static boolean validaStringSpaces(String cad)
    {
        boolean cadValida = true;
        if (cad == null || cad.isEmpty()) {
            throw new CadenaVacia("ERROR :: La cadena introducida esta Vacía");
        }
        // [a-zA-Z]+ significa una o más letras (mayúsculas o minúsculas)
        if(!cad.matches("^[a-zA-Z\\s]+$")){
            throw new PalabraNoValida("ERROR :: La palabra introducida no es valida, Caracteres no permitidos");
        };
        return cadValida;
    }


    /**
     * Valida si la cadena de texto es una fecha válida según el formato especificado.
     *
     * @param fechaStr La cadena con la fecha.
     * @param formato  El formato esperado, por ejemplo "dd/MM/yyyy".
     * @return true si la fecha es válida, false si no lo es.
     */
    public static boolean validarFecha(String fechaStr, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato); // comprueba que la fecha introducida esta en el formato (dd/mm/yyyy ...)
        try {
            LocalDate.parse(fechaStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false; // devuelve falso en caso de que salte la excepcion (lo que significa que la fecha no es valida)
        }
    }





}
