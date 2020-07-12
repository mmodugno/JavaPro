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
        //TODO QUEDA ORDENAR
        //Collections.sort(egresos, (x,y) -> {return x.valorTotal() > y.valorTotal()});
        //Collections.sort(egresos);
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {
        ordenar(ingresos, egresos);
        for(int i = 0; i<ingresos.size(); i++){
            List<Integer> indexDestruir = new ArrayList<Integer>();
            for(int j = 0; j<egresos.size(); j++){
                if(pasaCondiciones(ingresos.get(i),egresos.get(j))){
                    ingresos.get(i).asociarEgreso(egresos.get(j));
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
