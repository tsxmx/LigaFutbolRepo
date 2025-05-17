package Entity;

import java.time.LocalDate;
import java.util.List;

public class JugadorPayable extends Jugador implements PayAble{


    public JugadorPayable(int id, String nombre, LocalDate fechaNacimiento, Nacionalidad nacionalidad, Posicion pos, int dorsal, Equipo equipo) {
        super(id, nombre, fechaNacimiento, nacionalidad, pos, dorsal, equipo);
    }

    /**
     * @return
     */
    @Override
    public double getSueldo() {
        return 0; // #TODO
    }
}
