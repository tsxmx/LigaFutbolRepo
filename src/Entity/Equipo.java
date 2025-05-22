package Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Comparable<Equipo> {

    private int id;
    private String nombre;
    private String estadio;
    private Color primario;
    private Color secundario;
    private double cuota;
    private LocalDate fechaCreacion;
    private Liga liga;

    private List<Jugador> plantilla = new ArrayList<>();

    // Constructor


    public Equipo(int id, String nombre, String estadio, Color primario, Color secundario, double cuota, LocalDate fechaCreacion, Liga liga) {

        this.id = id;
        this.nombre = nombre;
        this.estadio = estadio;
        this.primario = primario;
        this.secundario = secundario;
        this.cuota = cuota;
        this.fechaCreacion = fechaCreacion;
        this.liga = liga;
        this.plantilla = new ArrayList<>();
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

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public Color getPrimario() {
        return primario;
    }

    public void setPrimario(Color primario) {
        this.primario = primario;
    }

    public Color getSecundario() {
        return secundario;
    }

    public void setSecundario(Color secundario) {
        this.secundario = secundario;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public List<Jugador> getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(List<Jugador> plantilla) {
        this.plantilla = plantilla;
    }

    public void addJugador(Jugador jug)
    {
        if(jug.getEquipo() != null)
        {
            jug.getEquipo().removeJugador(jug);
        }

        this.plantilla.add(jug);
        jug.setEquipo(this);
    }

    public void removeJugador(Jugador jug)
    {
        plantilla.remove(jug);
    }


    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Equipo e) {
        return Integer.compare(this.id, e.getId()); // comparacion de IDs
    }
}
