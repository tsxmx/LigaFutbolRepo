package Repository;

import Entity.Evento;
import Entity.Jugador;
import Entity.Partido;
import Entity.TipoEvento;
import Util.DBC;
import View.Mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventoRepository implements Repository<Evento>{

    JugadorRepository JR = new JugadorRepository();
    PartidoRepository PR = new PartidoRepository();
    TipoEventoRepository TR = new TipoEventoRepository();


    private Connection getConnection() throws SQLException
    {
        return DBC.getConnection();
    }

    /**
     * @return
     */
    @Override
    public List<Evento> findAll() {

        List<Evento> eventos = new ArrayList<>();
        try
        {
            Connection con = getConnection();

            String query = "SELECT idEvento, " +
                                    "minuto, " +
                                    "jugador_idJugador, " +
                                    "Partido_id, " +
                                    "TipoEvento_idTipoEvento " +
                            "FROM evento";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEvent = rs.getInt("idEvento");
                int minuto = rs.getInt("minuto");
                Jugador jug = JR.findById(rs.getInt("jugador_idJugador"));
                Partido par =PR.findById(rs.getInt("partido_id"));
                TipoEvento TipEv =  TR.findById(rs.getInt("TipoEvento_idTipoEvento"));

                Evento event = new Evento(idEvent, minuto, jug, par, TipEv);

                jug.addEvento(event);
                par.addEvento(event);
                TipEv.addEvento(event);

                eventos.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return eventos;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Evento findById(int id) {

        return null;

    }

    /**
     * @param object
     */
    public void save(Evento event) { // Cambiamos 'object' por 'event' para mayor claridad

        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(event.getId() == 0){ // Si el ID es 0, es un nuevo registro (INSERT)
                query = "INSERT INTO Evento(minuto, jugador_idJugador, Partido_id, TipoEvento_idTipoEvento) " +
                        "VALUES(?, ?, ?, ?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, event.getMinuto());
                pstmt.setInt(2, event.getJugador().getId()); // Asumiendo que Jugador tiene un getId()
                pstmt.setInt(3, event.getPartido().getId()); // Asumiendo que Partido tiene un getId()
                pstmt.setInt(4, event.getTipo().getId()); // Asumiendo que TipoEvento tiene un getId()
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1)); // Asignamos el ID generado al objeto Evento
                }

                Mensaje.saveMessage();

                generatedKeys.close();
                pstmt.close();
                con.close();

            }else{ // Si el ID no es 0, es un registro existente (UPDATE)
                query = "UPDATE Evento SET minuto = ?, jugador_idJugador = ?, Partido_id = ?, TipoEvento_idTipoEvento = ? " +
                        "WHERE idEvento = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setInt(1, event.getMinuto());
                pstmt.setInt(2, event.getJugador().getId());
                pstmt.setInt(3, event.getPartido().getId());
                pstmt.setInt(4, event.getTipo().getId());
                pstmt.setInt(5, event.getId());
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

    }
}
