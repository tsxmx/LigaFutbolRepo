package Service;

import Entity.Evento;
import Entity.TipoEvento;
import Repository.EventoRepository;
import Repository.TipoEventoRepository;
import View.Lector;

public class EventoService {

    private final EventoRepository eventoRepo;
    private final TipoEventoService tipoSer;
    private final MenuService menuService;

    public EventoService(){
        this.eventoRepo = new EventoRepository();
        this.tipoSer = new TipoEventoService();
        this.menuService = new MenuService();
    }

    // Metodos del repositorio

    public void saveUpdate(Evento event){
        eventoRepo.save(event);
    }

    public void delete(Evento event){
        eventoRepo.delete(event.getId());
    }

    // Metodos propios de clase

    public Evento createEvento(){
        Evento event = null;

        int tipo = menuService.mostrarTiposEvento();
        event = Lector.createEvent();
        event.setTipo(tipoSer.getTipoEventoById(tipo));
        return event;
    }

    public Evento inciaPartido(){
        return new Evento(0, 0, null, null, tipoSer.getTipoEventoById(3));
    }



}
