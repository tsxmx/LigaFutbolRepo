package View;

import Entity.*;
import Util.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import Exception.*;

import javax.swing.*;

public class Lector {

    private static Scanner scan = null;

    /**
     *
     * Clase que crea un lector
     *
     * @return si es la primera vez que se usa, Si no es la primera usa el ya creado y lo devuelve por getLector()
     */

    public static Scanner getLector()
    {
        if(scan == null)
        {
            scan = new Scanner(System.in);
        }
        return scan;
    }

    public static void closeLector()
    {
        scan = null;
    }

    public static int getInt(String mensaje)
    {
        Scanner lector = Lector.getLector();
        boolean entradaValida = true;
        String numero;

        do {
            numero = lector.nextLine();
            System.out.println(mensaje);

            try{
                Validator.esNumero(numero);
            }
            catch (CadenaVacia | NumeroNoValido e){
                e.printStackTrace();
                entradaValida = false;
            }
        }while(!entradaValida);

        return Integer.parseInt(numero);

    }

    public static double getDouble(String mensaje)
    {
        Scanner lector = Lector.getLector();
        boolean entradaValida = true;
        String numero;

        do {
            System.out.println(mensaje);
            numero = lector.nextLine();

            try{
                Validator.esDouble(numero);
            }
            catch (CadenaVacia | NumeroNoValido e){
                e.printStackTrace();
                entradaValida = false;
            }
        }while(!entradaValida);

        return Double.parseDouble(numero);

    }



    public static int getNumeroEnRango(int minimo, int maximo)
    {
        boolean numeroEnRango = true;
        int numero = 0;

        do {
            numero = getInt("");

            try {
                Validator.numeroEnRango(minimo,maximo, numero);
                numeroEnRango = true;
            }
            catch (NumeroFueraDeRango e){
                e.printStackTrace();
                numeroEnRango = false;
            }

        }while(!numeroEnRango);

        return numero;

    }


    /**
     * Pide una palabra (por ejemplo, nombre o apellido) al usuario y la valida usando Validator.validaNombreApe.
     * Se repite la solicitud hasta que se introduce un valor válido.
     *
     * @param msj_entrada Mensaje que se muestra para pedir la palabra.
     * @return La palabra ingresada válida.
     */
    public static String getNombre(String msj_entrada) {
        Scanner lector = Lector.getLector();
        String palabra;
        boolean entradaValida;

        do {
            System.out.println(msj_entrada);
            palabra = lector.nextLine();

            try {
                Validator.validaStringSpaces(palabra);
                entradaValida = true;
            }
            catch (PalabraNoValida e)
            {
                e.printStackTrace();
                entradaValida = false;
            }
        } while (!entradaValida); // condicion de salida del bucle

        return palabra; // devuelve la palabta formada
    }


    /**
     * Metodo que en base a un formato devuelve un local date con la fecha introducida por teclado
     * @param msj_entrada mensaje de entrada del metodo
     * @param formato formato a introducir: yyyy-MM-dd / dd-MM-yy ...etc
     * @return devuelve un LocalDate construido por los datos previos
     */
    public static LocalDate getFecha(String msj_entrada, String formato) {
        Scanner lector = Lector.getLector();
        String fecha;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato); // se crea este objeto para asegurar que el formato introducido es aplicado a la hora de validar

        do {
            System.out.println(msj_entrada);
            fecha = lector.nextLine(); // repite la introduccion de la fecha hasta que cumpla la validacion
        } while (!Validator.validarFecha(fecha, formato));

        return LocalDate.parse(fecha, formatter);// devuelve el objeto Local date formado con la fecha String y su formato deseado
    }

    public static Color getColor(String msj_entrada) {
        Scanner lector = Lector.getLector();
        String color;
        boolean entradaValida;
        Color colorFinal = null;

        do {
            System.out.println(msj_entrada);
            color = lector.nextLine();

            try {
                colorFinal = Color.valueOf(color.toUpperCase());
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                entradaValida = false;
            }
        } while (!entradaValida); // condicion de salida del bucle

        return colorFinal;
    }

    public static Nacionalidad getNacionalidad(String msj_entrada) {
        Scanner lector = Lector.getLector();
        String nacionalidad;
        boolean entradaValida;
        Nacionalidad nacionalidadFin = null;

        do {
            System.out.println(msj_entrada);
            nacionalidad = lector.nextLine();

            try {
                nacionalidadFin = Nacionalidad.valueOf(nacionalidad.toUpperCase());
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                entradaValida = false;
            }
        } while (!entradaValida); // condicion de salida del bucle

        return nacionalidadFin;
    }

    public static Posicion getPosicion(String msj_entrada) {
        Scanner lector = Lector.getLector();
        String posicion;
        boolean entradaValida;
        Posicion posicionFin = null;

        do {
            System.out.println(msj_entrada);
            posicion = lector.nextLine();

            try {
                posicionFin = Posicion.valueOf(posicion.toUpperCase());
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                entradaValida = false;
            }
        } while (!entradaValida); // condicion de salida del bucle

        return posicionFin;
    }

    public static Equipo createEquipo(){

        Scanner lector = getLector();

        String nombreEquipo = getNombre("NOMBRE DEL EQUIPO: ");
        String estadio = getNombre("NOMBRE DEL ESTADIO: ");
        Color colorPri = getColor("INTRODUCE EL COLOR PRIMARIO: ");
        Color colorSec = getColor("INTRODUCE EL COLOR SECUNDARIO: ");
        double cuota = getDouble("INTRODUCE LA CUOTA: ");
        LocalDate fechaCreacion = getFecha("INTRODUCE LA FECHA DE CREACION: ", "yyyy-MM-dd");

        return new Equipo(0, nombreEquipo, estadio, colorPri, colorSec, cuota, fechaCreacion, null);
    }

    public static JugadorPayable createJugadorPro(){

        String nombreJugador = getNombre("NOMBRE DEL JUGADOR: ");
        LocalDate fechaNacimiento = getFecha("INTRODUCE LA FECHA DE NACIMIENTO: ", "yyyy-MM-dd");
        Nacionalidad nacionalidad = getNacionalidad("INTRODUCE LA NACIONALIDAD: ");
        Posicion posicion = getPosicion("INTRODUCE LA POSICION: ");
        System.out.println("INTRODUCE EL DORSAL");
        int dorsal = getNumeroEnRango(1, 25);

        return new JugadorPayable(0, nombreJugador, fechaNacimiento, nacionalidad, posicion, dorsal, null);

    }

    public static Jugador createJugadorAma(){

        String nombreJugador = getNombre("NOMBRE DEL JUGADOR: ");
        LocalDate fechaNacimiento = getFecha("INTRODUCE LA FECHA DE NACIMIENTO: ", "yyyy-MM-dd");
        Nacionalidad nacionalidad = getNacionalidad("INTRODUCE LA NACIONALIDAD: ");
        Posicion posicion = getPosicion("INTRODUCE LA POSICION: ");
        System.out.println("INTRODUCE EL DORSAL");
        int dorsal = getNumeroEnRango(1, 25);

        return new Jugador(0, nombreJugador, fechaNacimiento, nacionalidad, posicion, dorsal, null);

    }



}
