package Repository;

import Entity.Jornada;
import Entity.Liga;
import Entity.Partido;
import Helper.Converter;
import Util.DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JornadaRepository implements Repository<Jornada>{

    private LigaRepository LR = new LigaRepository();
    private EquipoRepository ER = new EquipoRepository();

    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Jornada> findAll() {

        List<Jornada> jornadas = new ArrayList<>();

        try
        {
            Connection con = getConnection();

            String query = "SELECT ID, DATE(fecha_inicio) as fecha_inicio, DATE(fecha_fin) as fecha_fin, liga_id FROM jornada";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idJornada = rs.getInt("ID");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicio"));
                LocalDate fechaFin = Converter.datetimteToLocalDate(rs.getString("fecha_fin"));
                Liga liga = LR.findById(rs.getInt("liga_id"));

                Jornada j = new Jornada(idJornada, fechaInicio, fechaFin, liga);
                liga.addJornada(j);
                jornadas.add(j);

            }

            for(Jornada j : jornadas)
            {
                List<Partido> partidosJornada = new ArrayList<>();
                 query = "SELECT p.id as partidoID, " +
                                    "p.goles_Local, " +
                                    "p.goles_Visitante, " +
                                    "DATE(p.fecha_inicio) as fecha_inicioP, " +
                                    "p.Equipo_idVisitante as idVisitante, " +
                                    "p.Equipo_idLocal as idLocal " +
                        "FROM jornada j " +
                        "JOIN partido p " +
                        "on j.id = p.jornada_id " +
                        "WHERE p.jornada_id = ?";
                 pstmt = con.prepareStatement(query);
                 pstmt.setInt(1, j.getId());
                 rs = pstmt.executeQuery();

                while(rs.next())
                {
                    int idPartido = rs.getInt("partidoID");
                    int golesLocal = rs.getInt("goles_local");
                    int golesVisitante = rs.getInt("goles_visitante");
                    LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicioP"));
                    // Jornada = j;
                    //Equipo eLocal = ER.findById(rs.getInt(idLocal);
                    //Equipo eVisitante = ER.findById(rs.getInt(idVisitante);

                    Partido p = new Partido(idPartido, golesLocal, golesVisitante, fechaInicio, j, null, null); // hacer en cuanto este hecho el ER
                    j.addPartido(p);
                    partidosJornada.add(p);
                }

                j.setPartidos(partidosJornada);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jornadas;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Jornada findById(int id) {
        Jornada jornada = null;

        try
        {
            Connection con = getConnection();

            String query = "SELECT ID, DATE(fecha_inicio) as fecha_inicio, DATE(fecha_fin) as fecha_fin, liga_id FROM jornada";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                int idJornada = rs.getInt("ID");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicio"));
                LocalDate fechaFin = Converter.datetimteToLocalDate(rs.getString("fecha_fin"));
                Liga liga = LR.findById(rs.getInt("liga_id"));

                jornada = new Jornada(idJornada, fechaInicio, fechaFin, liga);
                liga.addJornada(jornada);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jornada;
    }

    public List<Partido> findPartidosByJornadaId(int id)
    {
        List<Partido> partidos = new ArrayList<>();

        try
        {
            Connection con = getConnection();

            String query = "SELECT p.id as partidoID, " +
                                    "p.goles_Local, " +
                                    "p.goles_Visitante, " +
                                    "DATE(p.fecha_inicio) as fecha_inicioP, " +
                                    "p.jornada_id, " +
                                    "p.Equipo_idVisitante as idVisitante, " +
                                    "p.Equipo_idLocal as idLocal " +
                                    "FROM jornada j " +
                            "JOIN partido p " +
                            "on j.id = p.jornada_id " +
                            "WHERE p.jornada_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idPartido = rs.getInt("partidoID");
                int golesLocal = rs.getInt("goles_local");
                int golesVisitante = rs.getInt("goles_visitante");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicioP"));
                Jornada j = findById(rs.getInt("jornada_id"));
                //Equipo eLocal = ER.findById(rs.getInt(idLocal);
                //Equipo eVisitante = ER.findById(rs.getInt(idVisitante);

                Partido p = new Partido(idPartido, golesLocal, golesVisitante, fechaInicio, j, null, null); // hacer en cuanto este hecho el ER
                partidos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return partidos;
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
