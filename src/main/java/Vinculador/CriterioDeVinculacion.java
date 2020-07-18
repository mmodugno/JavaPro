package Vinculador;

import egreso.*;

import java.util.List;
import java.util.stream.Collectors;


public abstract class CriterioDeVinculacion {

    private List<CondicionObligatoria> condicionesObligatorias;

    public CriterioDeVinculacion(List<CondicionObligatoria> condicionesObligatorias) {
        this.condicionesObligatorias = condicionesObligatorias;
    }


    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {}

    //TODO LA EXEPCION SI LAS LISTAS ESTAN VACIAS. ASÍ NO TIRA ERROR MAS ADELANTE SI UNA LISTA QUEDO VACIA.
    void ordenarValor(List<Ingreso> ingresos, List<Egreso> egresos) {
        ingresos.sort((Ingreso unIngreso, Ingreso otroIngreso) -> {
                return ordenarDouble(unIngreso.getMonto(),otroIngreso.getMonto());
        });
        egresos.sort((Egreso unEgreso, Egreso otroEgreso) -> {
            return ordenarDouble(unEgreso.valorTotal(),otroEgreso.valorTotal());
        });
    }

    boolean pasaCondiciones(Ingreso ingreso, Egreso egreso){

        for(int i = 0;i<condicionesObligatorias.size();i++){
            if(!condicionesObligatorias.get(i).cumpleCondicion(ingreso, egreso)){
                return false;
            }

        }
        return true;
    }

    int ordenarDouble(double primero,double segundo){
        if (primero > segundo) return -1;
        if (primero < segundo) return 1;
        return 0;

    }


}
