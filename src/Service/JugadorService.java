package Service;

import Entity.Evento;
import Entity.Jugador;
import Repository.JugadorRepository;
import View.Lector;

import java.util.List;

public class JugadorService {

    private final MenuService menuService;
    private final JugadorRepository jugadorRepo;

    public JugadorService(){
        this.jugadorRepo = new JugadorRepository();
        this.menuService = new MenuService();
    }

    // Metodos del repositorio

    public Jugador getJugadorById(int idJugador){
        return jugadorRepo.findById(idJugador);
    }


    public void saveUpdateJugador(Jugador jug){
        jugadorRepo.save(jug);
    }

    public void deleteJugador(Jugador jug){
        jugadorRepo.delete(jug.getId());
    }

    public List<Evento> getEventosByJugadorId(int idJug){
        return jugadorRepo.findEventosByJugadorId(idJug);
    }

    // Metodos de clase propios

    public Jugador crearJugador(){

        Jugador jugadorFinal = null;

        switch (menuService.menuAddJugador1()){
            case 1:
                jugadorFinal = Lector.createJugadorPro();
                break;
            case 2:
                jugadorFinal = Lector.createJugadorAma();
        }

        return jugadorFinal;
    }

    public double getSalario(){
        List<Evento> eventosJug = getEventosByJugadorId(1);
        return 0.0;
    }

}
