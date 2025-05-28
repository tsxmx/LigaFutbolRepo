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

    public int getVictorias(int idEquipo){
        return equipoRepo.getVictoriasEquipo(idEquipo);
    }

    public int getDerrotas(int idEquipo){
        return equipoRepo.getDerrotasEquipo(idEquipo);
    }

    public int getEmpates(int idEquipo){
        return equipoRepo.getEmpatesEquipo(idEquipo);
    }

    public int getGetGolesAFavor(int idEquipo){
        return equipoRepo.getGolesAFavor(idEquipo);
    }

    public int getGetGolesEnContra(int idEquipo){
        return equipoRepo.getGolesEnContra(idEquipo);
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

    public int calculaPuntos(int idEquipo){
        int victorias = getVictorias(idEquipo);
        int empates = getEmpates(idEquipo);
        return (victorias * 3) + (empates * 1);
    }

    public int calculaDiferencia(int idEquipo){
        return getGetGolesAFavor(idEquipo) - getGetGolesEnContra(idEquipo);
    }



}
