package Repository;

import Entity.Jornada;
import Util.DBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JornadaRepository implements Repository<Jornada>{


    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Jornada> findAll() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Jornada findById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Jornada object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Jornada object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }

}
