package Repository;

import Entity.Equipo;
import Util.DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoRepository implements Repository<Equipo> {
    #a
    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }


    /**
     * @return
     */
    @Override
    public List<Equipo> findAllBasic() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Equipo findBasicById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Equipo object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Equipo object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
