package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class Jornada {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Liga liga;

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
}
