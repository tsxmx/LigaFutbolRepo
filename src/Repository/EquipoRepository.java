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

public class EquipoRepository implements Repository<Equipo> {


    private LigaRepository LR = new LigaRepository();

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

            String query = "SELECT  e.idEquipo, " +
                                    "e.nombre as nombreEquipo, " +
                                    "e.estadio, " +
                                    "e.color_primario, " +
                                    "e.color_secundario, " +
                                    "e.cuota, " +
                                    "DATE(e.fecha_creacion) as fecha_creacion, " +
                                    "e.liga_id " +
                            "FROM equipo e";


            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int idEquipo = rs.getInt("idEquipo");
                String nombreEquipo = rs.getString("nombreEquipo");
                String estadio = rs.getString("estadio");
                String colorP = rs.getString("color_primario");
                String colorS = rs.getString("color_secundario");
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));
                Liga liga = LR.findById(rs.getInt("liga_id"));

                Equipo equipo = new Equipo(idEquipo, nombreEquipo, estadio, Color.valueOf(colorP), Color.valueOf(colorS), cuota, fechaCreacion, liga);
                equiposReturn.add(equipo);
            }


            for(Equipo e : equiposReturn)
            {
                List<Jugador> jugadores = new ArrayList<>();
                con = getConnection();

                query = "SELECT  j.idJugador, " +
                                "j.nombre, " +
                                "DATE(j.fecha_nacimiento) as fechaNac, " +
                                "j.nacionalidad, " +
                                "j.dorsal, " +
                                "j.pro, " +
                                //"j.equipo_idEquipo, " +
                                "j.posicion " +
                        "FROM equipo e " +
                        "JOIN jugador j " +
                        "on e.idEquipo = j.equipo_idequipo " +
                        "WHERE e.idEquipo = ?";


                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, e.getId());
                rs = pstmt.executeQuery();

                while(rs.next())
                {
                    Jugador jug;
                    int pro = rs.getInt("pro");

                    int idjugador = rs.getInt("idJugador");
                    String nombre = rs.getString("nombre");
                    LocalDate fechaNaci = Converter.datetimteToLocalDate(rs.getString("fechaNac"));
                    Nacionalidad nacionalidad = Nacionalidad.valueOf(rs.getString("nacionalidad"));
                    int dorsal = rs.getInt("dorsal");
                    Posicion pos = Posicion.valueOf(rs.getString("posicion"));


                    if(pro == 1)
                    {
                        jug = new JugadorPayable(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, e);
                    }
                    else
                    {
                        jug = new Jugador(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, e);
                    }
                    e.addJugador(jug);
                    jugadores.add(jug);

                }

                e.setPlantilla(jugadores);

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
                Color colorP = Color.valueOf(rs.getString("color_primario"));
                Color colorS = Color.valueOf(rs.getString("color_secundario"));
                double cuota = rs.getDouble("cuota");
                LocalDate fechaCreacion = Converter.datetimteToLocalDate(rs.getString("fecha_creacion"));
                Liga liga = LR.findById(rs.getInt("liga_id"));

                equipoReturn = new Equipo(idEquipo, nombreEquipo, estadio, colorP, colorS, cuota, fechaCreacion, liga);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipoReturn;
    }

    public List<Jugador> findJugadoresByEquipoId(int id)
    {
        List<Jugador> jugadores = new ArrayList<>();

        try{
            Connection con = getConnection();

            String query = "SELECT  j.idJugador, " +
                                    "j.nombre, " +
                                    "DATE(j.fecha_nacimiento) as fechaNac, " +
                                    "j.nacionalidad, " +
                                    "j.dorsal, " +
                                    "j.pro, " +
                                    "j.equipo_idEquipo, " +
                                    "j.posicion " +
                            "FROM equipo e " +
                            "JOIN jugador j " +
                                "on e.idEquipo = j.equipo_idequipo " +
                            "WHERE e.idEquipo = ?";


            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                Jugador jug;
                int pro = rs.getInt("pro");

                int idjugador = rs.getInt("idJugador");
                String nombre = rs.getString("nombre");
                LocalDate fechaNaci = Converter.datetimteToLocalDate(rs.getString("fechaNac"));
                Nacionalidad nacionalidad = Nacionalidad.valueOf(rs.getString("nacionalidad"));
                int dorsal = rs.getInt("dorsal");
                Posicion pos = Posicion.valueOf(rs.getString("posicion"));
                Equipo equipo = findById(rs.getInt("equipo_idEquipo"));

                if(pro == 1)
                {
                    jug = new JugadorPayable(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }
                else
                {
                    jug = new Jugador(idjugador, nombre, fechaNaci, nacionalidad, pos, dorsal, equipo);
                }
                equipo.addJugador(jug);
                jugadores.add(jug);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return jugadores;
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
                            "WHERE liga_id = ? " +
                            "order by 1";

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

                Equipo equipo = new Equipo(idEquipo, nombreEquipo, estadio, Color.valueOf(colorP), Color.valueOf(colorS), cuota, fechaCreacion,
                                            LR.findById(rs.getInt("liga_id")));
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
