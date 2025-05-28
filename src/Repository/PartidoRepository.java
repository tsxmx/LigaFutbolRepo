package Repository;

import Entity.*;
import Helper.Converter;
import Util.DBC;
import View.Mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartidoRepository implements Repository<Partido> {

    JornadaRepository JR = new JornadaRepository();
    EquipoRepository ER = new EquipoRepository();
    TipoEventoRepository TR = new TipoEventoRepository();

    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Partido> findAll() {

        List<Partido> partidos = new ArrayList<>();

        try{
            Connection con = getConnection();

            String query = "SELECT id as partidoID, " +
                                    "goles_Local, " +
                                    "goles_Visitante, " +
                                    "DATE(fecha_inicio) as fecha_inicioP, " +
                                    "jornada_id, " +
                                    "Equipo_idVisitante as idVisitante, " +
                                    "Equipo_idLocal as idLocal " +
                            "FROM partido ";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idPartido = rs.getInt("partidoID");
                int golesLocal = rs.getInt("goles_local");
                int golesVisitante = rs.getInt("goles_visitante");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicioP"));
                Jornada jornada = JR.findById(rs.getInt("jornada_id"));
                Equipo eLocal = ER.findById(rs.getInt("idLocal"));
                Equipo eVisitante = ER.findById(rs.getInt("idVisitante"));

                Partido p = new Partido(idPartido, golesLocal, golesVisitante, fechaInicio, jornada, eLocal, eVisitante);
                partidos.add(p);
            }

            for (Partido p : partidos)
            {
                List<Evento> eventos = new ArrayList<>();
                con = getConnection();

                query = "SELECT e.idEvento, " +
                                "e.minuto, " +
                                "e.jugador_idJugador as idJugador, " +
                                "e.partido_id, " +
                                "e.TipoEvento_idTipoEvento " +
                        "FROM partido p " +
                        "JOIN evento e " +
                        "on p.id = e.partido_id " +
                        "WHERE p.id = ?";

                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, p.getId());

                rs = pstmt.executeQuery();

                while(rs.next())
                {
                    int idEvento = rs.getInt("idevento");
                    int minuto = rs.getInt("minuto");
                    Jugador jugador = new Jugador(rs.getInt("idJugador"), null, null,null,null,0,null);
                    Partido partido = findById(rs.getInt("partido_id"));
                    TipoEvento tipoEv = TR.findById(rs.getInt("TipoEvento_idTipoEvento"));

                    Evento event = new Evento(idEvento, minuto, jugador, p, tipoEv);

                    p.addEvento(event);
                    eventos.add(event);
                }

                p.setEventos(eventos);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return partidos;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Partido findById(int id) {

        Partido partido = null;

        try{
            Connection con = getConnection();

            String query = "SELECT  id as partidoID, " +
                                    "goles_Local, " +
                                    "goles_Visitante, " +
                                    "DATE(fecha_inicio) as fecha_inicioP, " +
                                    "jornada_id, " +
                                    "Equipo_idVisitante as idVisitante, " +
                                    "Equipo_idLocal as idLocal " +
                            "FROM partido " +
                            "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idPartido = rs.getInt("partidoID");
                int golesLocal = rs.getInt("goles_local");
                int golesVisitante = rs.getInt("goles_visitante");
                LocalDate fechaInicio = Converter.datetimteToLocalDate(rs.getString("fecha_inicioP"));
                Jornada jornada = JR.findById(rs.getInt("jornada_id"));
                Equipo eLocal = ER.findById(rs.getInt("idLocal"));
                Equipo eVisitante = ER.findById(rs.getInt("idVisitante"));

                partido = new Partido(idPartido, golesLocal, golesVisitante, fechaInicio, jornada, eLocal, eVisitante);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return partido;



    }

    /**
     * @param object
     */
    @Override
    public void save(Partido partido) { // Cambiamos 'object' por 'partido' para mayor claridad

        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(partido.getId() == 0){ // Si el ID es 0, es un nuevo registro (INSERT)
                query = "INSERT INTO Partido(goles_Local, goles_Visitante, fecha_inicio, jornada_id, Equipo_idVisitante, Equipo_idLocal) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, partido.getGolesLocal());
                pstmt.setInt(2, partido.getGolesVisitante());
                pstmt.setObject(3, partido.getFechaInicio());
                pstmt.setInt(4, partido.getJornada().getId()); // Asumiendo que Jornada tiene un getId()
                pstmt.setInt(5, partido.getVisitante().getId()); // Asumiendo que Equipo tiene un getId()
                pstmt.setInt(6, partido.getLocal().getId()); // Asumiendo que Equipo tiene un getId()
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    partido.setId(generatedKeys.getInt(1)); // Asignamos el ID generado al objeto Partido
                }

                Mensaje.saveMessage();

                generatedKeys.close();
                pstmt.close();
                con.close();

            }else{ // Si el ID no es 0, es un registro existente (UPDATE)
                query = "UPDATE Partido SET goles_Local = ?, goles_Visitante = ?, fecha_inicio = ?, jornada_id = ?, Equipo_idVisitante = ?, Equipo_idLocal = ? " +
                        "WHERE id = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setInt(1, partido.getGolesLocal());
                pstmt.setInt(2, partido.getGolesVisitante());
                pstmt.setObject(3, partido.getFechaInicio());
                pstmt.setInt(4, partido.getJornada().getId());
                pstmt.setInt(5, partido.getVisitante().getId());
                pstmt.setInt(6, partido.getLocal().getId());
                pstmt.setInt(7, partido.getId());
                pstmt.executeUpdate();

                Mensaje.updateMessage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {
        try {
            Connection con = getConnection();
            String query = "DELETE FROM Partido WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Mensaje.deleteMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
