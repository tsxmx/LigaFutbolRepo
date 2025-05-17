package Repository;

import Entity.Jugador;
import Util.DBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JugadorRepository implements Repository<Jugador> {


    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Jugador> findAll() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Jugador findById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Jugador object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Jugador object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
