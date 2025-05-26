package Entity;

import java.time.LocalDateTime;

public class Evento {

    private int id;
    int minuto;
    private Jugador jugador;
    private Partido partido;
    private TipoEvento tipo;

    // Constructor


    public Evento(int id, int minuto, Jugador jugador, Partido partido, TipoEvento tipo) {

        this.id = id;
        this.minuto = minuto;
        this.jugador = jugador;
        this.partido = partido;
        this.tipo = tipo;
    }

    // Getter & Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }


}
