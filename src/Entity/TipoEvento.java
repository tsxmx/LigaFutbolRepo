package Entity;

import java.util.List;

public class TipoEvento {

    private int id;
    private String nombre;
    private List<Evento> eventos;

    // Constructor

    public TipoEvento(int id, String nombre, List<Evento> eventos) {

        this.id = id;
        this.nombre = nombre;
        this.eventos = eventos;
    }

    // Getter y Setter


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

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
