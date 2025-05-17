package Repository;

import Entity.Equipo;
import Entity.Liga;
import Helper.Converter;
import Util.DBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LigaRepository implements Repository<Liga>{

    private Connection getConnection() throws SQLException
        {
            return DBC.getConnection();
        }


    /**
     * @return
     */
    @Override
    public List<Liga> findAllBasic() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Liga findBasicById(int id) {
        return null;
    }

    /**
     * @param object
     */
    @Override
    public void save(Liga object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Liga object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
