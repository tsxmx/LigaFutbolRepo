package Service;

import Entity.TipoEvento;
import Repository.TipoEventoRepository;

import java.util.List;

public class TipoEventoService {

    private final TipoEventoRepository tipoRepo;

    public TipoEventoService() {
        this.tipoRepo = new TipoEventoRepository();
    }

    public List<TipoEvento> getAllTipos(){
        return tipoRepo.findAll();
    }

    public TipoEvento getTipoEventoById(int idTipo){
        return tipoRepo.findById(idTipo);
    }
}
