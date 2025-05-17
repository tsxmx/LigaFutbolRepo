package Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Liga {

    private int id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Set<Equipo> equiposSorted = new TreeSet<>();
    private Map<Integer, Equipo> equiposfind = new HashMap();

    private List<Jornada> jornadas = new ArrayList<>();

    // Constructor de copia


    public Liga(int id, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {

        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters


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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    // Metodos override


    @Override
    public String toString() {
        return "Liga{" +
                "nombre='" + nombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
