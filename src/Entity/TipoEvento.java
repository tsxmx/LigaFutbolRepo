package Entity;

import java.util.ArrayList;
import java.util.List;

public class TipoEvento {

    private int id;
    private String nombre;
    private List<Evento> eventos = new ArrayList<>();

    // Constructor

    public TipoEvento(int id, String nombre) {

        this.id = id;
        this.nombre = nombre;
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

    public void addEvento(Evento event)
    {
        if(event.getTipo() != null)
        {
            event.getTipo().removeEvento(event);
        }

        this.eventos.add(event);
        event.setTipo(this);
    }

    public void removeEvento(Evento event)
    {
        eventos.remove(event);
    }
}
