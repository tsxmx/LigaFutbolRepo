package Repository;

import Entity.Equipo;
import Entity.Jornada;
import Entity.Liga;
import Helper.Converter;
import Service.LigaService;
import Util.DBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LigaRepository implements Repository<Liga>{

    private final EquipoRepository ER;
    private final JornadaRepository JR;

    public LigaRepository(EquipoRepository equipoRepo, JornadaRepository jornadaRepo) {
        this.ER = equipoRepo;
        this.JR = jornadaRepo;
    }

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

            String query = "SELECT id, nombre, DATE(fecha_inicio) as fecha_inicio, DATE(fecha_fin) as fecha_fin " +
                         "FROM liga";


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

    public Liga findByIdWithTeams(int idLiga)
    {
        Liga liga = findById(idLiga);

        if(liga != null)
        {
            // List<Equipo> equipos = ER.findByLigaId(int id); #TODO
            // liga.setEquipos(equipos);
        }
        return liga;
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

    public Liga findByIdWithJornadas(int idLiga)
    {
        Liga liga = findById(idLiga);

        if(liga != null)
        {
            // List<Jornada> jornadas = JR.findByLigaId(int id); #TODO
            // liga.setJornadas(jornadas);
        }
        return liga;
    }


    // CREATE
    /**
     * @param object
     */
    @Override
    public void save(Liga object) {

    }

    // UPDATE

    /**
     * @param object
     */
    @Override
    public void update(Liga object) {

    }

    // DELETE

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
