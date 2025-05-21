package Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Jornada {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Liga liga;

    private List<Partido> partidos = new ArrayList<>();

    // Constructor


    public Jornada(int id, LocalDate fechaInicio, LocalDate fechaFin, Liga liga) {

        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.liga = liga;
    }

    // Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public void addPartido (Partido p)
    {
        if(p.getJornada() != null)
        {
            p.getJornada().removePartido(p);
        }
        this.partidos.add(p);
        p.setJornada(this);
    }

    public void removePartido (Partido p)
    {
        this.partidos.remove(p);
    }
}
