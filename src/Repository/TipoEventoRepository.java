package Repository
import Entity.TipoEvento;
import Util.DBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TipoEventoRepository implements Repository<TipoEvento> {


    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<TipoEvento> findAllBasic() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TipoEvento findBasicById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(TipoEvento object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(TipoEvento object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
