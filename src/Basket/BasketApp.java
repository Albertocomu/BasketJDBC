package Basket;

import model.Team;
import model.Player;
import persistence.BasketJDBC;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by albertocomu on 18/1/17.
 */
public class BasketApp {

    public static void main(String[] args) {
        BasketJDBC gestor = new BasketJDBC();
        try {
            System.out.println("Estableciendo conexión con la bbdd...");
            gestor.conectar();
            System.out.println("Conectado a la bbdd Basket");
            System.out.println("Estableciendo conexión con la bbdd...");
            gestor.conectar();
            System.out.println("Conexión establecida.");
            Team e1 = new Team("Team 1", "Madrid", LocalDate.of(1980, 10, 10));
            Team e2 = new Team("Team 2", "Barça", LocalDate.of(1970, 10, 10));
            Team e3 = new Team("Team 3", "Celta", LocalDate.of(1990, 10, 10));
            Player j1 = new Player("Sergio", LocalDate.of(1996, 8, 8), "base", 100, 100, 100, e1);
            Player j2 = new Player("Fernando", LocalDate.of(1996, 8, 8), "pivot", 200, 200, 200, e3);
            Player j3 = new Player("Victor", LocalDate.of(1990, 8, 8), "alero", 300, 300, 300, e2);
            Player j4 = new Player("Alberto", LocalDate.of(1988, 8, 8), "alero", 400, 300, 300, e1);
            gestor.insertarEquipo(e2);
            System.out.println("Team e2 insertado en la bdd.");
            gestor.insertarEquipo(e3);
            System.out.println("Team e3 insertado en la bdd.");
            gestor.insertJugador(j1);
            System.out.println("Player j1 insertado en la bdd.");
            gestor.insertJugador(j2);
            System.out.println("Player j2 insertado en la bdd.");
            gestor.insertJugador(j3);
            System.out.println("Player j3 insertado en la bdd.");
            gestor.insertJugador(j4);
            System.out.println("Player j4 insertado en la bdd.");
            j1.setNbaskets(234); j1.setNassists(234); j1.setNrebounds(234);
            gestor.modificarCAR(j1);
            System.out.println("Puntos de j1 modificados.");
            j2.setTeam(e3);
            gestor.modificarEquipo(j2);
            System.out.println("Team de j2 modificado.");
            gestor.borrarJugador(j2);
            System.out.println("Obtener Player por nombre: Alberto.");
            System.out.println(gestor.selectJugadorByNombre("Alberto"));
            System.out.println("Obtener Jugadores por nombre: er");
            List<Player> jugadores = gestor.selectJugadoresByNombre("er");
            for(Player x: jugadores){
                System.out.println(x);
            }
            System.out.println("Obtener Jugadores con mas nbaskets: 200");
            System.out.println(gestor.selectJugadoresByCanastas(200));
            System.out.println("Obtener Jugadores con nassists entre 200 y 240");
            System.out.println(gestor.selectJugadoresByAsistencias(200, 240));
            System.out.println("Obtener Jugadores que jueguen en base");
            System.out.println(gestor.selectJugadoresByPosicion("base"));
            System.out.println("Obtener Jugadores con fecha anterior a 8/8/1990");
            System.out.println(gestor.selectJugadoresByBirthday(LocalDate.of(1990, 8, 8)));
            System.out.println("Obtener lista de jugadores por position y estadisticas");
            System.out.println("NOPE...");
            System.out.println("Obtener jugadores por ranking de anotación");
            System.out.println(gestor.selectPlayersRanking());
            System.out.println("Obtener Equipos de Madrid");
            System.out.println(gestor.selectEquiposByCiudad("Madrid"));
            System.out.println("Obtener Jugadores del Team 2");
            System.out.println(gestor.selectPlayersByEquipo("Team 2"));
            System.out.println("Obtener Jugadores del Team 1 que juegon en alero");
            System.out.println(gestor.selectPlayersTeamPosition("Team 1", "alero"));
            System.out.println("Obtener Player con MAX nbaskets del Team 1");
            System.out.println(gestor.selectPlayersTeamMaxBaskets("Team 1"));
            System.out.println("Ranking de Jugadores (Canastas)");
            System.out.println("NOPE...");

            gestor.desconectar();
            System.out.println("Cerrada la conexión con la bbdd.");
        } catch (SQLException ex) {
            System.out.println("Error con la BBDD: " + ex.getMessage());
        }
    }
}
