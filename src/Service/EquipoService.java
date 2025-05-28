package Service;

import Entity.Equipo;
import Entity.Jugador;
import Repository.EquipoRepository;
import View.Lector;

import java.util.List;

public class EquipoService {

    private final EquipoRepository equipoRepo;

    public EquipoService(){
        this.equipoRepo = new EquipoRepository();
    }

    // Metodos propios del repositorio

    public List<Equipo> getEquiposByLigaId(int idLiga){
        return equipoRepo.findEquiposByLigaId(idLiga);
    }

    public List<Jugador> getJugadoresByEquipoId(int idEquipo){
        return equipoRepo.findJugadoresByEquipoId(idEquipo);
    }

    public Equipo getEquipoById(int idEquipo){
        return equipoRepo.findById(idEquipo);
    }

    public void saveUpdateEquipo(Equipo e){
        equipoRepo.save(e);
    }

    public void deleteEquipo(Equipo e){
        equipoRepo.delete(e.getId());
    }

    //Metodos propios de clase

    public Equipo createEquipo()
    {
        Equipo equipo = Lector.createEquipo();
        return equipo;
    }



}
