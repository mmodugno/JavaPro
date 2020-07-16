package Vinculador;

import egreso.*;

import java.util.List;


public abstract class CriterioDeVinculacion {

    private List<CondicionObligatoria> condicionesObligatorias;

    public CriterioDeVinculacion(List<CondicionObligatoria> condicionesObligatorias) {
        this.condicionesObligatorias = condicionesObligatorias;
    }


    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {}

    //TODO LA EXEPCION SI LAS LISTAS ESTAN VACIAS. ASÍ NO TIRA ERROR MAS ADELANTE SI UNA LISTA QUEDO VACIA.
    void ordenarValor(List<Ingreso> ingresos, List<Egreso> egresos) {
        ingresos.sort((Ingreso unIngreso, Ingreso otroIngreso) -> {
            if (unIngreso.getMonto() > otroIngreso.getMonto()) return 1;
            if (unIngreso.getMonto() < otroIngreso.getMonto()) return -1;
            return 0;
        });
        egresos.sort((Egreso unEgreso, Egreso otroEgreso) -> {
            if (unEgreso.valorTotal() > otroEgreso.valorTotal()) return 1;
            if (unEgreso.valorTotal() < otroEgreso.valorTotal()) return -1;
            return 0;
        });
    }

    boolean pasaCondiciones(Ingreso ingreso, Egreso egreso){

        return condicionesObligatorias.stream().allMatch(c -> c.cumpleCondicion(ingreso, egreso));
    }
/*
    int ordenarDouble(double primero,double segundo){
        if (primero > segundo) return 1;
        if (primero < segundo) return -1;
        return 0;

    }
*/

}
