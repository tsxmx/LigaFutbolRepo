package Service;

import Entity.Evento;
import Entity.Jugador;
import Entity.Partido;
import Entity.TipoEvento;
import Repository.PartidoRepository;

public class PartidoService {

    private final PartidoRepository partidoRepo;


    public PartidoService() {
        this.partidoRepo = new PartidoRepository();

    }

    public Partido getPartidoById(int idPartido) {
        return partidoRepo.findById(idPartido);
    }

    public void saveUpdate(Partido partido) {
        partidoRepo.save(partido);
    }
}


