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
import java.time.LocalDateTime;
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


    public int getVictoriasEquipo(int idEquipo) {
        int totalVictorias = 0;
        try {
            Connection con = getConnection();

            String query = "SELECT COUNT(*) AS total_victorias " +
                            "FROM Partido p " +
                            "WHERE (p.Equipo_idLocal = ? AND p.GOLES_LOCAL > p.GOLES_VISITANTE) " +
                            "   OR (p.Equipo_idVisitante = ? AND p.GOLES_VISITANTE > p.GOLES_LOCAL);";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idEquipo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalVictorias = rs.getInt("total_victorias");
            }
        } catch (SQLException e) {
            // Envuelve la SQLException en una RuntimeException para una gestión más sencilla
            throw new RuntimeException("Error al calcular victorias para el equipo ID " + idEquipo + ": " + e.getMessage(), e);
        }
        return totalVictorias;
    }

    public int getDerrotasEquipo(int idEquipo) {
        int totalDerrotas = 0;
        try {
            Connection con = getConnection();

            String query = "SELECT COUNT(*) AS total_derrotas " +
                            "FROM Partido p " +
                            "WHERE (p.Equipo_idLocal = ? AND p.GOLES_LOCAL < p.GOLES_VISITANTE) " +
                            "   OR (p.Equipo_idVisitante = ? AND p.GOLES_VISITANTE < p.GOLES_LOCAL);";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idEquipo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalDerrotas = rs.getInt("total_derrotas");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular derrotas para el equipo ID " + idEquipo + ": " + e.getMessage(), e);
        }
        return totalDerrotas;
    }

    public int getEmpatesEquipo(int idEquipo) {
        int totalEmpates = 0;
        try {
            Connection con = getConnection();

            String query = "SELECT COUNT(*) AS total_empates " +
                            "FROM Partido p " +
                            "WHERE (p.Equipo_idLocal = ? OR p.Equipo_idVisitante = ?) " +
                            "  AND p.GOLES_LOCAL = p.GOLES_VISITANTE;";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idEquipo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalEmpates = rs.getInt("total_empates");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular empates para el equipo ID " + idEquipo + ": " + e.getMessage(), e);
        }
        return totalEmpates;
    }

    public int getGolesAFavor(int idEquipo) {
        int golesAFavor = 0;
        try {
            Connection con = getConnection();
            String query = "SELECT COALESCE(SUM(CASE WHEN p.Equipo_idLocal = ? THEN p.GOLES_LOCAL ELSE p.GOLES_VISITANTE END), 0) AS goles_a_favor " +
                            "FROM Partido p " +
                            "WHERE p.Equipo_idLocal = ? OR p.Equipo_idVisitante = ?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idEquipo);
            pstmt.setInt(3, idEquipo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                golesAFavor = rs.getInt("goles_a_favor");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular goles a favor para el equipo ID " + idEquipo + ": " + e.getMessage(), e);
        }
        return golesAFavor;
    }

    public int getGolesEnContra(int idEquipo) {
        int golesEnContra = 0;
        try {
            Connection con = getConnection();
            String query = "SELECT COALESCE(SUM(CASE WHEN p.Equipo_idLocal = ? THEN p.GOLES_VISITANTE ELSE p.GOLES_LOCAL END), 0) AS goles_en_contra " +
                            "FROM Partido p " +
                            "WHERE p.Equipo_idLocal = ? OR p.Equipo_idVisitante = ?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idEquipo);
            pstmt.setInt(3, idEquipo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                golesEnContra = rs.getInt("goles_en_contra");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular goles en contra para el equipo ID " + idEquipo + ": " + e.getMessage(), e);
        }
        return golesEnContra;
    }
    

    /**
     * @param object
     */
    @Override
    public void save(Equipo equipo) {

        try{
            Connection con = getConnection();
            String query;
            PreparedStatement pstmt = null;

            if(equipo.getId() == 0){
                query = "INSERT INTO Equipo(nombre, estadio, color_primario, color_secundario, cuota, fecha_creacion, liga_id) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";

                pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, equipo.getNombre());
                pstmt.setString(2, equipo.getEstadio());
                pstmt.setString(3, equipo.getPrimario().toString());
                pstmt.setString(4, equipo.getSecundario().toString());
                pstmt.setDouble(5, equipo.getCuota());
                pstmt.setObject(6, equipo.getFechaCreacion());
                pstmt.setInt(7, equipo.getLiga().getId());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    equipo.setId(generatedKeys.getInt(1));
                }

                Mensaje.saveMessage();

                generatedKeys.close();
                pstmt.close();
                con.close();

            }else{
                query = "UPDATE Equipo SET nombre = ?, estadio = ?, color_primario = ?, color_secundario = ?, cuota = ?, fecha_creacion = ?, liga_id = ? " +
                        "WHERE idEquipo = ?";
                pstmt = con.prepareStatement(query);

                pstmt.setString(1, equipo.getNombre());
                pstmt.setString(2, equipo.getEstadio());
                pstmt.setString(3, equipo.getPrimario().toString());
                pstmt.setString(4, equipo.getSecundario().toString());
                pstmt.setDouble(5, equipo.getCuota());
                pstmt.setObject(6, equipo.getFechaCreacion());
                pstmt.setInt(7, equipo.getLiga().getId());
                pstmt.setInt(8, equipo.getId());
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
            String query = "DELETE FROM Equipo WHERE idEquipo = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Mensaje.deleteMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
