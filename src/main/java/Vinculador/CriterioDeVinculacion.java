package Vinculador;

import egreso.*;

import java.util.List;


public abstract class CriterioDeVinculacion {

    private List<CondicionObligatoria> condicionesObligatorias;

    public CriterioDeVinculacion(List<CondicionObligatoria> condicionesObligatorias) {
        this.condicionesObligatorias = condicionesObligatorias;
    }


    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {}

    void ordenar(List<Ingreso> ingresos, List<Egreso> egresos){}

    boolean pasaCondiciones(Ingreso ingreso, Egreso egreso){

        return condicionesObligatorias.stream().allMatch(c -> c.cumpleCondicion(ingreso, egreso));
    }
}
