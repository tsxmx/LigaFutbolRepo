package Service;

import Entity.Jornada;
import Entity.Liga;
import Repository.LigaRepository;

import java.time.LocalDate;
import java.util.List;

public class LigaService {

    private final LigaRepository ligaRepository;

    public LigaService()
    {
        this.ligaRepository = new LigaRepository();
    }


    // Metodos del repositorio
    public List<Liga> getLigas()
    {
        return ligaRepository.findAll();
    }

    public Liga getLigaById(int ligaId){
        return ligaRepository.findById(ligaId);
    }

    public List<Jornada> getJornadasByLigaId(int idLiga)
    {
        return ligaRepository.findJornadasByLigaId(idLiga);
    }

    // Metodos propios de la clase liga

    public int getPeriodoActual(Liga liga)
    {
        if(liga == null) {
            throw new NullPointerException("ERROR :: El objeto liga no puede ser nulo!");
        }

        LocalDate hoy = LocalDate.now();

        return liga.getFechaInicio().isAfter(hoy) ? 1 : 2;
    }



}
