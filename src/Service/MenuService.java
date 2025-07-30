package Service;
import Entity.*;
import Helper.Converter;
import View.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static View.Menu.mostrarMenu;

public class MenuService {

    private final LigaService ligaService;
    private final JornadaService jornadaService;
    private final EquipoService equipoService;
    private final TipoEventoService tipoEventoService;

    public MenuService()
    {
        this.ligaService = new LigaService();
        this.jornadaService = new JornadaService();
        this.equipoService = new EquipoService();
        this.tipoEventoService = new TipoEventoService();
    }

    public void clear()
    {
        System.out.println("\n".repeat(35));
    }

    public void stop()
    {
        Scanner s = Lector.getLector();
        System.out.println("\n ** Pulse la tecla enter para continuar ** \n");
        s.nextLine();
    }

    public int menuPreLiga(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Crear Equipo"); // done
        listaOpciones.add("2. Eliminar Equipo"); // done
        listaOpciones.add("3. Añadir Jugador"); // done
        listaOpciones.add("4. Eliminar jugador"); // done
        listaOpciones.add("5. Ver Equipos");
        listaOpciones.add("6. Ver Jugadores");
        listaOpciones.add("7. Datos Liga");
        listaOpciones.add("8. Salir del programa");

        return mostrarMenu(listaOpciones, "Menú Principal"); // return de la opcion
    }

    public int menuInLiga(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Clasificacion"); // done
        listaOpciones.add("2. Calendario de Jornadas/Partidos");
        listaOpciones.add("3. Narrar Partido");
        listaOpciones.add("4. Detalles partido");
        listaOpciones.add("5. Maximos Goleadores");
        listaOpciones.add("6. Rendimiento de un Equipo");
        listaOpciones.add("7. Ganancias de los Jugadores");
        listaOpciones.add("8. Salir del programa");

        return mostrarMenu(listaOpciones, "Menú Principal"); // return de la opcion
    }

    public void mostrarDatosLiga(Liga l){
        clear();
        Mensaje.genericToString(l);
        stop();
    }

    public int menuAddJugador1(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Jugador Profesional");
        listaOpciones.add("2. Jugador Amateur");
        listaOpciones.add("3. Salir del programa");

        return mostrarMenu(listaOpciones, "Creacion de Jugador"); // return de la opcion
    }

    public int menuEliminar(){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. continuar");
        listaOpciones.add("2. Salir");

        return mostrarMenu(listaOpciones, "¿Seguro que desea Eliminar?"); // return de la opcion
    }

    public int menuOnceInicial(Jugador j){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. empieza el partido");
        listaOpciones.add("2. banquillo");

        return mostrarMenu(listaOpciones, "¿ " + j.getNombre() + " Participara en el once Inicial?"); // return de la opcion
    }

    public int menuNarrarPartido(Partido partido){
        ArrayList<String> listaOpciones = new ArrayList<>();

        listaOpciones.add("1. Añadir gol Local");
        listaOpciones.add("2. Eliminar gol Local");
        listaOpciones.add("3. Añador gol visitante");
        listaOpciones.add("4. Eliminar gol Visitante");
        listaOpciones.add("5. añadir Evento");
        listaOpciones.add("6. volver al Menu de Jornadas");

        return mostrarMenu(listaOpciones,
                "Partido: L-"+Converter.colorToAANSI(partido.getLocal().getPrimario()) + partido.getLocal().getNombre() + Converter.ANSI_RESET+
                        " / V-"+ Converter.colorToAANSI(partido.getVisitante().getSecundario()) + partido.getVisitante().getNombre() + Converter.ANSI_RESET); // return de la opcion
    }

    public int mostrarTiposEvento(){
        ArrayList<TipoEvento> tipos = new ArrayList<>(this.tipoEventoService.getAllTipos());

        ArrayList<String> tiposMenu = new ArrayList<>(); // array para las opciones del menu
        for(TipoEvento t : tipos)
        {
            int indice = tipos.indexOf(t) + 1;
            tiposMenu.add(indice + ". " + t.getNombre());
        }

        tiposMenu.add(tiposMenu.size()+1 + ". salir del programa");


        int opcTipo = mostrarMenu(tiposMenu, "Selecciona el tipo de Evento");
        int idTipo = 0;

        if (opcTipo < tipos.size() + 1) {
            idTipo = tipos.get(opcTipo - 1).getId();
        }else {
            idTipo = -1;
        }

        return idTipo;
    }



    /**
     * Impresion de las ligas de este programa para elegir cual usar.
     * @return
     */
    public int mostrarLigas(){
        ArrayList<Liga> ligas = new ArrayList<>(this.ligaService.getLigas());

        ArrayList<String> ligasMenu = new ArrayList<>(); // array para las opciones del menu
        for(Liga l : ligas)
        {
            ligasMenu.add(l.getId() + ". " + l.getNombre());
        }
        ligasMenu.add(ligasMenu.size()+1 + ". salir del programa");

        return mostrarMenu(ligasMenu, "Selecciona la Liga");
    }

    public int mostrarEquiposByLiga(int idLiga, String message) {
        ArrayList<Equipo> equiposLiga = new ArrayList<>(this.equipoService.getEquiposByLigaId(idLiga));
        ArrayList<String> equiposMenu = new ArrayList<>();


        for (Equipo e : equiposLiga) {
            int indice = equiposLiga.indexOf(e) + 1;
            equiposMenu.add(Converter.colorToAANSI(e.getPrimario()) + indice + ". " + e.getNombre() + Converter.ANSI_RESET);
        }
        equiposMenu.add((equiposLiga.size() + 1) + ". Volver atrás");

        int opcEquipo = mostrarMenu(equiposMenu, message);
        int idEquipo = 0;


        if (opcEquipo < equiposLiga.size() + 1) {
            idEquipo = equiposLiga.get(opcEquipo - 1).getId();
        }else {
            idEquipo = -1;
        }

        return idEquipo;
    }

    public int mostrarJugadoresByEquipoId(int idEquipo) {
        ArrayList<Jugador> jugadoresEquipo = new ArrayList<>(this.equipoService.getJugadoresByEquipoId(idEquipo));
        ArrayList<String> jugadoresMenu = new ArrayList<>();

        for (Jugador j : jugadoresEquipo) {
            int indice = jugadoresEquipo.indexOf(j) + 1;
            jugadoresMenu.add(indice + ". " + j.getNombre());
        }
        jugadoresMenu.add((jugadoresEquipo.size() + 1) + ". Volver atrás");

        int opcJugador = mostrarMenu(jugadoresMenu, "Seleccione el Jugador");
        int idJugador = 0;

        if (opcJugador < jugadoresEquipo.size() + 1) {
            idJugador = jugadoresEquipo.get(opcJugador - 1).getId();
        } else {
            idJugador = -1;
        }

        return idJugador;
    }

    public int mostrarJornadasByLigaId(int idLiga, String message) {
        ArrayList<Jornada> jornadasLiga = new ArrayList<>(this.ligaService.getJornadasByLigaId(idLiga));
        ArrayList<String> jornadasMenu = new ArrayList<>();

        for (int i = 0; i < jornadasLiga.size(); i++) {
            Jornada j = jornadasLiga.get(i);
            int indice = i + 1;
            jornadasMenu.add(indice + ". JORNADA - " + j.getFechaInicio());
        }

        jornadasMenu.add((jornadasLiga.size() + 1) + ". Volver atrás");

        int opcJornada = mostrarMenu(jornadasMenu, message);
        int idJornada = 0;

        if (opcJornada < jornadasLiga.size()+1) {
            idJornada = jornadasLiga.get(opcJornada - 1).getId();
        } else {
            idJornada = -1;
        }

        return idJornada;
    }

    public int mostrarPartidosByJornadaId(int jornadaId, String message) {
        ArrayList<Partido> partidosJornada = new ArrayList<>(this.jornadaService.getPartidosByJornadaId(jornadaId));
        ArrayList<String> partidosMenu = new ArrayList<>();

        for (int i = 0; i < partidosJornada.size(); i++) {
            Partido p = partidosJornada.get(i);
            int indice = i + 1;
            partidosMenu.add(indice + " - " +
                    Converter.colorToAANSI(p.getLocal().getPrimario()) + p.getLocal().getNombre() + Converter.ANSI_RESET +
                    " VS " +
                    Converter.colorToAANSI(p.getVisitante().getSecundario()) + p.getVisitante().getNombre() + Converter.ANSI_RESET);
        }

        partidosMenu.add((partidosJornada.size() + 1) + ". Volver atrás");

        int opcPartido = mostrarMenu(partidosMenu, message);
        int idPartido = 0;

        if (opcPartido < partidosJornada.size() +1) {
            idPartido = partidosJornada.get(opcPartido - 1).getId();
        } else {
            idPartido = -1;
        }

        return idPartido;
    }

    public int mostrarClasificacion(Liga liga){
        liga = ligaService.getClasificacion(liga);

        List<String> equiposClasificacion = new ArrayList<>();
        for (Equipo e : liga.getEquiposSorted()){
            equiposClasificacion.add(e.toString());
        }

        return mostrarMenu(equiposClasificacion, "CLASIFICACION");
    }

    public int getEquiposByPartido(Partido partido){

        List<String> equipos = new ArrayList<>();

        equipos.add("1. " + Converter.colorToAANSI(partido.getLocal().getPrimario()) + partido.getLocal().getNombre() + Converter.ANSI_RESET);
        equipos.add("2. " + Converter.colorToAANSI(partido.getVisitante().getSecundario()) + partido.getVisitante().getNombre() + Converter.ANSI_RESET);
        equipos.add("3. Salir");

        return mostrarMenu(equipos, "Selecione el Equipo");
    }

}
