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

    public Set<Equipo> getEquiposSorted() {
        return equiposSorted;
    }

    public void setEquiposSorted(Set<Equipo> equiposSorted) {
        this.equiposSorted = equiposSorted;
    }

    public Map<Integer, Equipo> getEquiposfind() {
        return equiposfind;
    }

    public void setEquiposfind(Map<Integer, Equipo> equiposfind) {
        this.equiposfind = equiposfind;
    }

    // Metodos propios de la clase

    public void addEquipo(Equipo equipo)
    {
        if(equipo.getLiga() != null) {
            equipo.getLiga().removeEquipo(equipo); // solo elimina el equipo si este ya trae una liga dentro (update)
         }
        this.equiposSorted.add(equipo);
        this.equiposfind.put(equipo.getId(), equipo);
        equipo.setLiga(this);
    }

    public void addAllEquipos(List<Equipo> equipos)
    {
        for(Equipo e : equipos)
        {
            if(e.getLiga() != null){
                e.getLiga().removeEquipo(e);
            }
            this.equiposSorted.add(e);
            this.equiposfind.put(e.getId(), e);
            e.setLiga(this);
        }
    }

    public void removeEquipo(Equipo equipo)
    {
        this.equiposSorted.remove(equipo);
        this.equiposfind.remove(equipo.getId());
    }

    public void addJornada(Jornada j)
    {
        if(j.getLiga() != null) {
            j.getLiga().removeJornada(j); // solo elimina el equipo si este ya trae una liga dentro (update)
        }

        this.jornadas.add(j);
        j.setLiga(this);
    }

    public void removeJornada(Jornada j)
    {
        this.jornadas.remove(j);
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
