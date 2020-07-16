package Vinculador;

import egreso.*;

import java.util.List;


public abstract class CriterioDeVinculacion {

    private List<CondicionObligatoria> condicionesObligatorias;

    public CriterioDeVinculacion(List<CondicionObligatoria> condicionesObligatorias) {
        this.condicionesObligatorias = condicionesObligatorias;
    }


    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {}

    void ordenarValor(List<Ingreso> ingresos, List<Egreso> egresos) {
        ingresos.sort((Ingreso unIngreso, Ingreso otroIngreso) -> {
            return ordenarDouble(unIngreso.getMonto(),otroIngreso.getMonto());
        });
        egresos.sort((Egreso unEgreso, Egreso otroEgreso) -> {
            return ordenarDouble(unEgreso.valorTotal(), otroEgreso.valorTotal());
        });
    }

    boolean pasaCondiciones(Ingreso ingreso, Egreso egreso){

        return condicionesObligatorias.stream().allMatch(c -> c.cumpleCondicion(ingreso, egreso));
    }

    int ordenarDouble(double primero,double segundo){
        if (primero > segundo) return 1;
        if (primero < segundo) return -1;
        return 0;

    }
}
