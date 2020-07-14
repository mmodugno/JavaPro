package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimeroEgreso extends CriterioDeVinculacion {
    public PrimeroEgreso(List<CondicionObligatoria> condicionesObligatorias) {
        super(condicionesObligatorias);
    }

    @Override
    void ordenar(List<Ingreso> ingresos, List<Egreso> egresos) {
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

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {
        ordenar(ingresos, egresos);
        for(int i = 0; i<ingresos.size(); i++){
            List<Integer> indexDestruir = new ArrayList<Integer>();
            for(int j = 0; j<egresos.size(); j++){
                if(pasaCondiciones(ingresos.get(i),egresos.get(j))){

                    ingresos.get(i).asociarEgreso(egresos.get(j));

                     egresos.get(j).setIngresoAsociado(ingresos.get(i));//AGREGO INGRESO A EGRESO

                    indexDestruir.add(j);
                }
                for(int z = 0; z<indexDestruir.size(); z++)
                {
                    ingresos.remove(indexDestruir.get(z)-z);
                }
            }
        }

    }
}
