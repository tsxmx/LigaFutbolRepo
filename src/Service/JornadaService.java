package Service;

import Entity.Jornada;
import Entity.Partido;
import Repository.JornadaRepository;

import java.util.List;

public class JornadaService {

    private final JornadaRepository jornadaRepo;

    public JornadaService(){
        this.jornadaRepo = new JornadaRepository();
    }

    public List<Partido> getPartidosByJornadaId(int jornadaId){
        return jornadaRepo.findPartidosByJornadaId(jornadaId);
    }
}
