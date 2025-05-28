package Service;

import Entity.Partido;
import Repository.PartidoRepository;

public class PartidoService {

    private final PartidoRepository partidoRepo;

    public PartidoService(){
        this.partidoRepo = new PartidoRepository();
    }

    public void saveUpdate(Partido partido){
        partidoRepo.save(partido);
    }
}
