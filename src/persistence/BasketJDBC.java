/*
 * Clase encargada de la gestión con la BBDD
 */
package persistence;


import model.Team;
import model.Player;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BasketJDBC {

    private Connection conexion;

    /**
     * @return lista de todos los jugadores.
     * @throws SQLException
     */
    public List<Player> selectAllJugadores() throws SQLException {
        List<Player> jugadores;
        String query = "select * from player";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    public void insertJugador(Player j) throws SQLException {
        String insert = "insert into player values (?, ?, ?, ?, ?, ?,?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, j.getName());
        ps.setDate(2, java.sql.Date.valueOf(j.getBirth()));
        ps.setInt(3, j.getNbaskets());
        ps.setInt(4, j.getNassists());
        ps.setInt(5, j.getNrebounds());
        ps.setString(6, j.getPosition());
        ps.setString(7, j.getTeam().getName());
        ps.executeUpdate();
        ps.close();
    }

    public void insertarEquipo(Team e) throws SQLException {
        String insert = "insert into team values (?,?,?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, e.getName());
        ps.setString(2, e.getCity());
        ps.setDate(3, java.sql.Date.valueOf(e.getCreation()));
        ps.executeUpdate();
        ps.close();
    }

    //nbaskets, nassists y nrebounds
    public void modificarCAR(Player j) throws SQLException {
        String update = "update player set nbaskets=?, nassists=?, nrebounds=? where name=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setInt(1, j.getNbaskets());
        ps.setInt(2, j.getNassists());
        ps.setInt(3, j.getNrebounds());
        ps.setString(4, j.getName());
        ps.executeUpdate();
        ps.close();
    }

    //modificar team de un player determinado
    public void modificarEquipo(Player j) throws SQLException {
        String update = "update player set team=? where name=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, j.getTeam().getName());
        ps.setString(2, j.getName());
        ps.executeUpdate();
        ps.close();
    }

    //borrar un player de la base de datos
    public void borrarJugador(Player j) throws SQLException {
        String delete = "delete from player where name=?";
        PreparedStatement ps = conexion.prepareStatement(delete);
        ps.setString(1, j.getName());
        ps.executeUpdate();
        ps.close();
    }

    //buscar player por name
    public Player selectJugadorByNombre(String name) throws SQLException {
        Player j = new Player();
        String query = "select * from player where name=?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            j.setName(name);
            j.setBirth(rs.getDate("birth").toLocalDate());
            j.setPosition(rs.getString("position"));
            j.setNbaskets(rs.getInt("nbaskets"));
            j.setNassists(rs.getInt("nassists"));
            j.setNrebounds(rs.getInt("nrebounds"));
            j.setTeam(new Team(rs.getString("team")));
        }
        rs.close();
        ps.close();
        return j;
    }

    //devolver lista jugadores por cadena de caracteres
    public List<Player> selectJugadoresByNombre(String name) throws SQLException{
        List<Player> jugadores;
        String query = "select * from player where name LIKE '%"+name+"%';";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //jugadores por nbaskets superiores
    public List<Player> selectJugadoresByCanastas(int canastas) throws SQLException {
        List<Player> jugadores;
        String query = "select * from player where nbaskets >="+canastas+";";
        Statement st = conexion.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //jugadores por rango de nassists
    public List<Player> selectJugadoresByAsistencias(int nassists, int asistencias2) throws SQLException {
        List<Player> jugadores;
        String query = "select * from player where nassists between "+nassists+" and "+asistencias2+";";
        Statement st = conexion.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //jugadores por position
    public List<Player> selectJugadoresByPosicion(String position) throws SQLException {
        List<Player> jugadores;
        String query = "select * from player where position = '"+position+"';";
        Statement st = conexion.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //jugadores por fecha de birth
    public List<Player> selectJugadoresByBirthday(LocalDate date) throws SQLException {
        List<Player> jugadores;
        String query = "select * from player where birth <'"+java.sql.Date.valueOf(date)+"';";
        Statement st = conexion.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //agrupar jugadores por position
    //????

    //Ranking por nbaskets
    public List<String> selectPlayersRanking() throws SQLException {
        List<String> ranking = new ArrayList<>();
        String query = "select name, nbaskets from player order by nbaskets desc;";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        int num = 1;
        while (rs.next()) {
            ranking.add(num + " " + rs.getString("name") + " " + rs.getInt("nbaskets"));
            num++;
        }
        rs.close();
        st.close();
        return ranking;
    }

    //obtener position
    //????

    //Lista de equipos por city
    public List<Team> selectEquiposByCiudad(String city) throws SQLException{
        List<Team> teams = new ArrayList<>();
        String query = "select * from team where city='"+city+"';";
        Statement st = conexion.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            Team e = new Team();
            e.setName(rs.getString("name"));
            e.setCity(rs.getString("city"));
            e.setCreation(rs.getDate("creation").toLocalDate());
            teams.add(e);
        }
        rs.close();
        st.close();
        return teams;
    }

    //Jugadores por team
    public List<Player> selectPlayersByEquipo(String name) throws SQLException{
        List<Player> jugadores;
        String select = "select * from player where team='"+name+"';";
        PreparedStatement st = conexion.prepareStatement(select);
        ResultSet rs = st.executeQuery();
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //Jugadores team y position
    public List<Player> selectPlayersTeamPosition(String equipo, String posicion) throws SQLException{
        List<Player> jugadores;
        String query = "select * from player where team='"+equipo+"' and position='"+posicion+"';";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        jugadores = rellenoLista(rs);
        rs.close();
        st.close();
        return jugadores;
    }

    //Jugadores por nbaskets y team
    public Player selectPlayersTeamMaxBaskets(String equipo) throws SQLException{
        Player j = new Player();
        String query = "select * from player where team='"+equipo+"' and nbaskets=(select max(nbaskets) from player where team='"+equipo+"');";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            Team e = new Team();
            j.setName(rs.getString("name"));
            j.setBirth(rs.getDate("birth").toLocalDate());
            j.setNbaskets(rs.getInt("nbaskets"));
            j.setNassists(rs.getInt("nassists"));
            j.setNrebounds(rs.getInt("nrebounds"));
            j.setPosition(rs.getString("position"));
            e.setName(rs.getString("team"));
            j.setTeam(e);
        }
        rs.close();
        st.close();
        return j;
    }


    // Función que conecta con la bbdd
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/basket";
        String usr = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, usr, pass);
    }

    // Función que desconecta de la bbdd
    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    /**
     * @param rs
     * @return recorre la rs y rellena un array con los jugadores
     * @throws SQLException
     */
    public ArrayList<Player> rellenoLista(ResultSet rs) throws SQLException {
        ArrayList<Player> jugadores = new ArrayList<>();
        while (rs.next()) {
            Player j = new Player();
            j.setName(rs.getString("name"));
            j.setBirth(rs.getDate("birth").toLocalDate());
            j.setPosition(rs.getString("position"));
            j.setNbaskets(rs.getInt("nbaskets"));
            j.setNassists(rs.getInt("nassists"));
            j.setNrebounds(rs.getInt("nrebounds"));
            j.setTeam(new Team(rs.getString("team")));
            jugadores.add(j);
        }
        return jugadores;
    }

}
