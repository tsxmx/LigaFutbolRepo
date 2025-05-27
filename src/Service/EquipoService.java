package Service;

import Entity.Equipo;
import Entity.Jugador;
import Repository.EquipoRepository;

import java.util.List;

public class EquipoService {

    private final EquipoRepository equipoRepo;

    public EquipoService(){
        this.equipoRepo = new EquipoRepository();
    }

    public List<Equipo> getEquiposByLigaId(int idLiga){
        return equipoRepo.findEquiposByLigaId(idLiga);
    }

    public List<Jugador> getJugadoresByEquipoId(int idEquipo){
        return equipoRepo.findJugadoresByEquipoId(idEquipo);
    }

}
