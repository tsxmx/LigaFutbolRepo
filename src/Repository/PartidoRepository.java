package Repository;

import Entity.Partido;
import Util.DBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PartidoRepository implements Repository<Partido> {

    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Partido> findAll() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Partido findById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Partido object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Partido object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
