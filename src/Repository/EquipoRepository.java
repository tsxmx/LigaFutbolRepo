package Repository;

import Entity.Color;
import Entity.Equipo;
import Entity.Liga;
import Helper.Converter;
import Util.DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoRepository implements Repository<Equipo> {


    private LigaRepository LR;
    private JugadorRepository JR;

    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }


    /**
     * @return
     */
    @Override
    public List<Equipo> findAll() {

        List<Equipo> equiposReturn = new ArrayList<>();

        try{

            Connection con = getConnection();

            String query = "SELECT  idEquipo, " +
                                    "nombre, " +
                                    "estadio, " +
                                    "color_primario, " +
                                    "color_secundario, " +
                                    "cuota, " +
                                    "DATE(fecha_creacion) as fecha_creacion, " +
                                    "liga_id " +
                            "FROM Equipo";


            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEquipo = rs.getInt("idEquipo");
                String nombreEquipo = rs.getString("nombre");
                String estadio = rs.getString("estadio");
                String colorP = rs.getString("color_primario");
                String colorS = rs.getString("color_secundario");
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));

                //Equipo equipo = new Equipo(idEquipo, nombreEquipo, estadio, Color.valueOf(colorP), Color.valueOf(colorS), cuota, fechaCreacion);
                //equiposReturn.add(equipo);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equiposReturn;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Equipo findById(int id) {

        Equipo equipoReturn = null;

        try{

            Connection con = getConnection();

            String query = "SELECT  idEquipo, " +
                                    "nombre, " +
                                    "estadio, " +
                                    "color_primario, " +
                                    "color_secundario, " +
                                    "cuota, " +
                                    "DATE(fecha_creacion) as fecha_creacion, " +
                                    "liga_id " +
                            "FROM Equipo " +
                            "WHERE idEquipo = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                int idEquipo = rs.getInt("idEquipo");
                String nombreEquipo = rs.getString("nombre");
                String estadio = rs.getString("estadio");
                String colorP = rs.getString("color_primario");
                String colorS = rs.getString("color_secundario");
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));
                Liga liga = new Liga(rs.getInt("liga_id"), null, null, null); // Stub de la liga -> solo la Id

                equipoReturn = new Equipo(idEquipo, nombreEquipo, estadio, Color.valueOf(colorP), Color.valueOf(colorS), cuota, fechaCreacion, null);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipoReturn;
    }


    public List<Equipo> findEquiposByLigaId(int id)
    {
        List<Equipo> equiposReturn = new ArrayList<>();

        try{

            Connection con = getConnection();

            String query = "SELECT  idEquipo, " +
                                    "nombre, " +
                                    "estadio, " +
                                    "color_primario, " +
                                    "color_secundario, " +
                                    "cuota, " +
                                    "DATE(fecha_creacion) as fecha_creacion, " +
                                    "liga_id " +
                                    "FROM Equipo " +
                            "WHERE idEquipo = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEquipo = rs.getInt("idEquipo");
                String nombreEquipo = rs.getString("nombre");
                String estadio = rs.getString("estadio");
                String colorP = rs.getString("color_primario");
                String colorS = rs.getString("color_secundario");
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));

                Equipo equipo = new Equipo(idEquipo, nombreEquipo, estadio, Color.valueOf(colorP), Color.valueOf(colorS), cuota, fechaCreacion, null);
                equiposReturn.add(equipo);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equiposReturn;
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
