package Service;

import Entity.Jornada;
import Entity.Liga;
import Repository.LigaRepository;

import java.util.List;

public class LigaService {

    private final LigaRepository ligaRepository;

    public LigaService()
    {
        this.ligaRepository = new LigaRepository();
    }

    public List<Liga> getLigas()
    {
        return ligaRepository.findAll();
    }

    public List<Jornada> getJornadasByLigaId(int idLiga)
    {
        return ligaRepository.findJornadasByLigaId(idLiga);
    }

}
