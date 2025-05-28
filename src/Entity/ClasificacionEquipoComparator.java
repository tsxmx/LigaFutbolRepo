package Entity; // O Entity.comparators; si quieres un subpaquete

import java.util.Comparator;

public class ClasificacionEquipoComparator implements Comparator<Equipo> {

    @Override
    public int compare(Equipo e1, Equipo e2) {
        int resultadoComparacion = 0;

        resultadoComparacion = Integer.compare(e2.getPuntos(), e1.getPuntos());

        if (resultadoComparacion == 0) {
            resultadoComparacion = Integer.compare(e2.getDiferenciaGoles(), e1.getDiferenciaGoles());
            if (resultadoComparacion == 0) {
                resultadoComparacion = Integer.compare(e2.getGolesFavor(), e1.getGolesFavor());
                if (resultadoComparacion == 0) {
                    resultadoComparacion = Integer.compare(e1.getGolesContra(), e2.getGolesContra());
                    if (resultadoComparacion == 0) {
                        resultadoComparacion = Integer.compare(e1.getId(), e2.getId());
                    }
                }
            }
        }

        return resultadoComparacion;
    }
}