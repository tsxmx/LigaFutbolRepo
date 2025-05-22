package Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Nacionalidad nacionalidad;
    private Posicion posicion;
    private int dorsal;
    private Equipo equipo;

    private List<Evento> eventos;

    // Constructor


    public Jugador(int id, String nombre, LocalDate fechaNacimiento, Nacionalidad nacionalidad, Posicion pos, int dorsal, Equipo equipo) {

        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.posicion = pos;
        this.dorsal = dorsal;
        this.equipo = equipo;
        this.eventos = new ArrayList<>();
    }

    // Getters & Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void addEvento(Evento event)
    {
        if(event.getJugador() != null)
        {
            event.getJugador().removeEvento(event);
        }

        this.eventos.add(event);
        event.setJugador(this);
    }

    public void removeEvento(Evento event)
    {
        eventos.remove(event);
    }
}
