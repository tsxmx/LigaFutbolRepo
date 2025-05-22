package Repository;

import Entity.*;
import Helper.Converter;
import Util.DBC;

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
                    // Local DateTime
                    Jugador jugador = findById(rs.getInt("idJugador"));
                    //Partido partido = PR.findById();
                    //TipoEvento tipoEv = TR.findbyId();

                    Evento event = new Evento(idEvento, null, jugador, null, null);

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
                    "FROM jugador";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
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

    /**
     * @param object
     */
    @Override
    public void save(Jugador object) {

    }

    /**
     * @param object
     */
    @Override
    public void update(Jugador object) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
