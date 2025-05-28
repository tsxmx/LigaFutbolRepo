package Repository;

import Entity.*;
import Helper.Converter;
import Util.DBC;
import View.Mensaje;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadorRepository implements Repository<Jugador> {

    EquipoRepository ER = new EquipoRepository();
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
    public List<Jugador> findAll() {

        List<Jugador> jugadores = new ArrayList<>();
        try
        {
            Connection con = getConnection();

            String query = "SELECT idJugador, " +
                                    "nombre, " +
                                    "DATE(fecha_nacimiento) as fechaNac, " +
                                    "nacionalidad, " +
                                    "dorsal, " +
                                    "pro, " +
                                    "Equipo_idEquipo, " +
                                    "posicion " +
                            "FROM jugador";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                Jugador jug;

                int idjugador = rs.getInt("idJugador");
                String nombre = rs.getString("nombre");
                LocalDate fechaNaci = Converter.datetimteToLocalDate(rs.getString("fechaNac"));
                Nacionalidad nacionalidad = Nacionalidad.valueOf(rs.getString("nacionalidad"));
                int dorsal = rs.getInt("dorsal");
                Posicion pos = Posicion.valueOf(rs.getString("posicion"));
                Equipo equipo = ER.findById(rs.getInt("equipo_idEquipo"));

                int pro = rs.getInt("pro");

                if(pro == 1)
                {
                    jug = new JugadorPayable(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }
                else
                {
                    jug = new Jugador(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }

                jugadores.add(jug);
            }

            for(Jugador j : jugadores)
            {
                List<Evento> eventos = new ArrayList<>();

                con = getConnection();

                query = "SELECT j.idJugador, " +
                                "e.idevento, " +
                                "e.minuto, " +
                                "e.jugador_idJugador, " +
                                "e.partido_id, " +
                                "e.TipoEvento_idTipoEvento " +
                        "FROM jugador j " +
                        "JOIN evento e " +
                        "on j.idJugador = e.Jugador_idJugador " +
                        "WHERE j.idJugador = ?";

                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, j.getId());

                while(rs.next())
                {
                    int idEvento = rs.getInt("idevento");
                    int minuto = rs.getInt("minuto");
                    Jugador jugador = findById(rs.getInt("idJugador"));
                    Partido partido = PR.findById(rs.getInt("partido_id"));
                    //TipoEvento tipoEv = TR.findbyId();

                    Evento event = new Evento(idEvento, minuto, jugador, partido, null);

                    j.addEvento(event);
                    eventos.add(event);
                }

                j.setEventos(eventos);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jugadores;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Jugador findById(int id) {

        Jugador jug = null;

        try {
            Connection con = getConnection();

            String query = "SELECT idJugador, " +
                                    "nombre, " +
                                    "DATE(fecha_nacimiento) as fechaNac, " +
                                    "nacionalidad, " +
                                    "dorsal, " +
                                    "pro, " +
                                    "Equipo_idEquipo, " +
                                    "posicion " +
                            "FROM jugador " +
                            "WHERE idJugador = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {

                int idjugador = rs.getInt("idJugador");
                String nombre = rs.getString("nombre");
                LocalDate fechaNaci = Converter.datetimteToLocalDate(rs.getString("fechaNac"));
                Nacionalidad nacionalidad = Nacionalidad.valueOf(rs.getString("nacionalidad"));
                int dorsal = rs.getInt("dorsal");
                Posicion pos = Posicion.valueOf(rs.getString("posicion"));
                Equipo equipo = ER.findById(rs.getInt("equipo_idEquipo"));

                int pro = rs.getInt("pro");

                if(pro == 1)
                {
                    jug = new JugadorPayable(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }
                else
                {
                    jug = new Jugador(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jug;
    }

    public List<Evento> findEventosByJugadorId(int id)
    {
        List<Evento> eventos = new ArrayList<>();

        try{
            Connection con = getConnection();

            String query = "SELECT j.idJugador, " +
                                    "e.idevento, " +
                                    "e.minuto, " +
                                    "e.jugador_idJugador, " +
                                    "e.partido_id, " +
                                    "e.TipoEvento_idTipoEvento " +
                    "FROM jugador j " +
                    "JOIN evento e " +
                    "on j.idJugador = e.Jugador_idJugador " +
                    "WHERE j.idJugador = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEvento = rs.getInt("idevento");
                int minuto = rs.getInt("minuto");
                Jugador jugador = findById(rs.getInt("idJugador"));
                Partido partido = PR.findById(rs.getInt("partido_id"));
                TipoEvento tipoEv = TR.findById(rs.getInt("tipoEvento_idTipoEvento"));

                Evento event = new Evento(idEvento, minuto, jugador, partido, tipoEv);

                eventos.add(event);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return eventos;
    }

    /**
     * @param object
     */
    @Override
    public void save(Jugador jugador) { // Cambiamos 'object' por 'jugador' para mayor claridad
        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(jugador.getId() == 0){ // Si el ID es 0, es un nuevo registro (INSERT)
                query = "INSERT INTO Jugador(nombre, fecha_nacimiento, nacionalidad, dorsal, pro, Equipo_idEquipo, posicion) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, jugador.getNombre());
                pstmt.setObject(2, jugador.getFechaNacimiento());
                pstmt.setString(3, jugador.getNacionalidad().toString()); // Asumiendo que Nacionalidad es un Enum
                pstmt.setInt(4, jugador.getDorsal());
                pstmt.setInt(5, (jugador instanceof JugadorPayable) ? 1 : 0); // Guardamos 1 si es JugadorPayable, 0 si es Jugador normal
                pstmt.setInt(6, jugador.getEquipo().getId()); // Asumiendo que Equipo tiene un getId()
                pstmt.setString(7, jugador.getPosicion().toString()); // Asumiendo que Posicion es un Enum
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    jugador.setId(generatedKeys.getInt(1)); // Asignamos el ID generado al objeto Jugador
                }

                Mensaje.saveMessage();

                generatedKeys.close();
                pstmt.close();
                con.close();

            }else{ // Si el ID no es 0, es un registro existente (UPDATE)
                query = "UPDATE Jugador SET nombre = ?, fecha_nacimiento = ?, nacionalidad = ?, dorsal = ?, pro = ?, Equipo_idEquipo = ?, posicion = ? " +
                        "WHERE idJugador = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setString(1, jugador.getNombre());
                pstmt.setObject(2, jugador.getFechaNacimiento());
                pstmt.setString(3, jugador.getNacionalidad().toString());
                pstmt.setInt(4, jugador.getDorsal());
                pstmt.setInt(5, (jugador instanceof JugadorPayable) ? 1 : 0);
                pstmt.setInt(6, jugador.getEquipo().getId());
                pstmt.setString(7, jugador.getPosicion().toString());
                pstmt.setInt(8, jugador.getId());
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
            String query = "DELETE FROM Jugador WHERE idJugador = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Mensaje.deleteMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
