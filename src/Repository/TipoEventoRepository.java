package Repository;
import Entity.Evento;
import Entity.Jugador;
import Entity.Partido;
import Entity.TipoEvento;
import Util.DBC;
import View.Mensaje;
import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<TipoEvento> findAll() {
        List<TipoEvento> tiposEvento = new ArrayList<>();

        try{

            Connection con = getConnection();

            String query = "SELECT idtable2, nombre from tipoevento";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idTipoEv = rs.getInt("idtable2");
                String nombre = rs.getString("nombre");

                TipoEvento tipoEv = new TipoEvento(idTipoEv, nombre);

                tiposEvento.add(tipoEv);
            }

            for (TipoEvento tv : tiposEvento)
            {
                List<Evento> eventos = new ArrayList<>();

               query = "SELECT e.idEvento, " +
                                "e.minuto, " +
                                "e.jugador_idJugador as idJugador, " +
                                "e.partido_id, " +
                                "e.TipoEvento_idTipoEvento " +
                        "FROM tipoevento tv " +
                        "JOIN evento e " +
                        "on tv.idtable2 = e.tipoevento_idtipoevento " +
                        "WHERE tv.idtable2 = ?";

               pstmt = con.prepareStatement(query);
               pstmt.setInt(1, tv.getId());
               rs = pstmt.executeQuery();

               while(rs.next())
               {
                   int idEvento = rs.getInt("idEvento");
                   int minuto = rs.getInt("minuto");
                   Jugador jug = new Jugador(rs.getInt("idJugador"), null, null, null, null, 0, null);
                   Partido p = new Partido(rs.getInt("partido_id"), 0, 0, null, null, null, null);

                   Evento event = new Evento(idEvento, minuto, jug, p, tv);

                   tv.addEvento(event);
                   eventos.add(event);

               }

               tv.setEventos(eventos);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tiposEvento;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public TipoEvento findById(int id) {

        TipoEvento tipEv = null;

        try
        {
            Connection con = getConnection();

            String query = "SELECT idtable2, nombre from tipoevento where idtable2 = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                int idTipoEv = rs.getInt("idtable2");
                String nombre = rs.getString("nombre");

                tipEv = new TipoEvento(idTipoEv, nombre);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tipEv;

    }

    /**
     * @param object
     */
    @Override
    public void save(TipoEvento tipoEvento) {
        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(tipoEvento.getId() == 0){
                query = "INSERT INTO TipoEvento(nombre) " +
                        "VALUES(?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, tipoEvento.getNombre());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    tipoEvento.setId(generatedKeys.getInt(1));
                }

                Mensaje.saveMessage();

            }else{
                query = "UPDATE TipoEvento SET nombre = ? " +
                        "WHERE idtable2 = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setString(1, tipoEvento.getNombre());
                pstmt.setInt(2, tipoEvento.getId());
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
            String query = "DELETE FROM TipoEvento WHERE idtable2 = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Mensaje.deleteMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
