package Repository;

import Entity.Evento;
import Util.DBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventoRepository implements Repository<Evento>{


    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Evento> findAllBasic() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Evento findBasicById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Evento object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Evento object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
