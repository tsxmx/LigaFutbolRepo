package Service;

import Repository.JugadorRepository;

public class JugadorService {

    private final JugadorRepository jugadorRepo;

    public JugadorService(){
        this.jugadorRepo = new JugadorRepository();
    }



}
