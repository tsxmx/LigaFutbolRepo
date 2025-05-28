package Service;

import Entity.Equipo;
import Entity.Jornada;
import Entity.Liga;
import Repository.EquipoRepository;
import Repository.LigaRepository;

import java.time.LocalDate;
import java.util.List;

public class LigaService {

    private final LigaRepository ligaRepository;
    private final EquipoService equipoService;

    public LigaService()
    {
        this.ligaRepository = new LigaRepository();
        this.equipoService = new EquipoService();
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

    public Liga getClasificacion(Liga liga){
        List<Equipo> equiposClasi = equipoService.getEquiposByLigaId(liga.getId());

        for(Equipo equipo : equiposClasi){

            int idEquipo = equipo.getId();

            equipo.setPuntos(equipoService.calculaPuntos(idEquipo));

            int golesAFavor = equipoService.getGetGolesAFavor(idEquipo);
            equipo.setGolesFavor(golesAFavor);

            int golesEnContra = equipoService.getGetGolesEnContra(idEquipo);
            equipo.setGolesContra(golesEnContra);

            equipo.setDiferenciaGoles(golesAFavor - golesEnContra);

            liga.addEquipo(equipo);
        }
        return liga;
    }




}
