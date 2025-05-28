package Repository;

import Entity.Color;
import Entity.Equipo;
import Entity.Jornada;
import Entity.Liga;
import Helper.Converter;
import Util.DBC;
import View.Mensaje;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LigaRepository implements Repository<Liga>{

    private  EquipoRepository ER;
    private  JornadaRepository JR;

    private Connection getConnection() throws SQLException
    {
            return DBC.getConnection();
    }

    // READ
    /**
     * Metodo que devuelve una lista de ligas basicas (sin equipos ni jornadas)
     * @return
     */
    @Override
    public List<Liga> findAll() {

        List<Liga> ligasReturn = new ArrayList<>();

        try{

            Connection con = getConnection();

            String query =  "select id, nombre, DATE(fecha_inicio) fecha_inicio, DATE(fecha_fin) as fecha_fin " +
                            "from liga order by 1";


            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idLiga = rs.getInt("id");
                String nombreLiga = rs.getString("nombre");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicio"));
                LocalDate fechaFin = Converter.datetimteToLocalDate(rs.getString("fecha_fin"));

                Liga liga = new Liga(idLiga, nombreLiga, fechaInicio, fechaFin);
                ligasReturn.add(liga);
            }

            for(Liga liga : ligasReturn)
            {
                query =  "select l.nombre, e.idEquipo, e.nombre as nombreEquipo, e.estadio, e.color_primario, e.color_secundario, e.cuota, DATE(e.fecha_creacion) as fecha_creacion " +
                        "from liga l " +
                        "join equipo e " +
                        "on l.id = e.liga_id " +
                        "where l.nombre like ?";

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, liga.getNombre());
                rs = pstmt.executeQuery();

                while(rs.next())
                {
                    LocalDate fCrea = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));

                    liga.addEquipo(new Equipo(rs.getInt("idEquipo"),
                            rs.getString("nombreEquipo"),
                            rs.getString("estadio"),
                            Color.valueOf(rs.getString("color_primario")),
                            Color.valueOf(rs.getString("color_secundario")),
                            rs.getDouble("cuota"),
                            fCrea,
                            liga));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ligasReturn;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Liga findById(int id) {

        Liga ligaReturn = null;

        try{

            Connection con = getConnection();

            String query =    "SELECT id, nombre, DATE(fecha_inicio) as fecha_inicio, DATE(fecha_fin) as fecha_fin " +
                              "FROM liga " +
                              "WHERE ID = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                int idLiga = rs.getInt("id");
                String nombreLiga = rs.getString("nombre");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicio"));
                LocalDate fechaFin = Converter.datetimteToLocalDate(rs.getString("fecha_fin"));

                ligaReturn = new Liga(idLiga, nombreLiga, fechaInicio, fechaFin);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ligaReturn;

    }

    public List<Equipo> findEquiposByLigaId(int idLiga){
        List<Equipo> equiposReturn = new ArrayList<>();

        try{
            Connection con = getConnection();

            String query = "SELECT e.idEquipo, " +
                                    "e.nombre, " +
                                    "e.estadio, " +
                                    "e.color_primario, " +
                                    "e.color_secundario, " +
                                    "e.cuota, " +
                                    "DATE(e.fecha_creacion) as fecha_creacion, " +
                                    "l.id as ligaID, " +
                                    "l.nombre as ligaNombre " +
                            "FROM equipo e " +
                            "JOIN liga l " +
                            "on e.Liga_ID = l.id " +
                            "WHERE l.id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idLiga);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEquipo = rs.getInt("idEquipo");
                String nombre = rs.getString("nombre");
                String estadio = rs.getString("estadio");
                Color colorP = Color.valueOf(rs.getString("color_primario"));
                Color colorS = Color.valueOf(rs.getString("color_secundario"));
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));
                Liga liga = new Liga(rs.getInt("ligaID"), rs.getString("ligaNombre"), null, null);

                Equipo e = new Equipo(idEquipo, nombre, estadio, colorP, colorS, cuota, fechaCreacion, liga);
                equiposReturn.add(e);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equiposReturn;
    }

    /**
     * Metodo que devuelve las jornadas (Basic) de una liga
     * @param idLiga
     * @return
     */
    public List<Jornada> findJornadasByLigaId(int idLiga)
    {
        List<Jornada> jornadasReturn = new ArrayList<>();

        try{
            Connection con = getConnection();

            String query = "SELECT  j.id as jornadaId, " +
                                    "DATE(j.fecha_inicio) as fecha_inicio, " +
                                    "DATE(j.fecha_fin) as fecha_fin, " +
                                    "j.liga_id,  " +
                                    "l.nombre as nombreLiga " +
                                    "FROM jornada j " +
                                    "JOIN liga l " +
                                    "ON j.liga_id = l.id " +
                                    "WHERE j.liga_id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idLiga);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt("jornadaId");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicio"));
                LocalDate fechaFin = Converter.datetimteToLocalDate(rs.getString("fecha_fin"));
                Liga liga = new Liga(rs.getInt("liga_id"), rs.getString("nombreLiga"), null, null);

                Jornada jornada = new Jornada(id, fechaInicio, fechaFin, liga);
                jornadasReturn.add(jornada);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jornadasReturn;
    }


    // CREATE
    /**
     * @param object
     */
    @Override
    public void save(Liga liga) { // Cambiamos 'object' por 'liga' para mayor claridad
        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(liga.getId() == 0){ // Si el ID es 0, es un nuevo registro (INSERT)
                query = "INSERT INTO Liga(nombre, fecha_inicio, fecha_fin) " +
                        "VALUES(?, ?, ?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, liga.getNombre());
                pstmt.setObject(2, liga.getFechaInicio());
                pstmt.setObject(3, liga.getFechaFin());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    liga.setId(generatedKeys.getInt(1)); // Asignamos el ID generado al objeto Liga
                }

                Mensaje.saveMessage();

                generatedKeys.close();
                pstmt.close();
                con.close();

            }else{ // Si el ID no es 0, es un registro existente (UPDATE)
                query = "UPDATE Liga SET nombre = ?, fecha_inicio = ?, fecha_fin = ? " +
                        "WHERE ID = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setString(1, liga.getNombre());
                pstmt.setObject(2, liga.getFechaInicio());
                pstmt.setObject(3, liga.getFechaFin());
                pstmt.setInt(4, liga.getId());
                pstmt.executeUpdate();

                Mensaje.updateMessage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE

    /**
     * @param id
     */
    @Override
    public void delete(int id) {
        try {
            Connection con = getConnection();
            String query = "DELETE FROM Liga WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Mensaje.deleteMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
