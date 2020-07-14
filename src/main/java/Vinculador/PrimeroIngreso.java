package Vinculador;

import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.Ingreso;

public class PrimeroIngreso extends CriterioDeVinculacion {

    public PrimeroIngreso(List<CondicionObligatoria> condicionesObligatorias) {
        super(condicionesObligatorias);
    }
    
    @Override
    void ordenar(List<Ingreso> ingresos, List<Egreso> egresos) {
        ingresos.sort((Ingreso unIngreso, Ingreso otroIngreso) -> unIngreso.getMonto().compareTo(otroIngreso.getMonto()));
        egresos.sort((Egreso unEgreso, Egreso otroEgreso) -> {
        	if (unEgreso.valorTotal() > otroEgreso.valorTotal()) return 1;
        	if (unEgreso.valorTotal() < otroEgreso.valorTotal()) return -1;
        	return 0;
        });
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {
        ordenar(ingresos, egresos);
        for(int i = 0; i<egresos.size(); i++){
            List<Integer> indexDestruir = new ArrayList<Integer>();
            for(int j = 0; j<ingresos.size(); j++){
                if(pasaCondiciones(ingresos.get(j),egresos.get(i))){
                    ingresos.get(j).asociarEgreso(egresos.get(i));
                    indexDestruir.add(i);
                }
                for(int z = 0; z<indexDestruir.size(); z++)
                {
                    ingresos.remove(indexDestruir.get(z)-z);
                }
            }
        }

    }
}
