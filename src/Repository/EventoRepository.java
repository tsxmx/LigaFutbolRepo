package Repository;

import Entity.Evento;
import Entity.Jugador;
import Entity.Partido;
import Entity.TipoEvento;
import Util.DBC;

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
    @Override
    public void save(Evento object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Evento object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
