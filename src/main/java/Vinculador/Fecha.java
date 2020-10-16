package Vinculador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import egreso.Egreso;
import egreso.Ingreso;
import organizacion.EntidadJuridica;

public class Fecha extends CriterioDeVinculacion {

	public Fecha(List<CondicionObligatoria> condicionesObligatorias) {

	}
	
	@Override
    public void vincular(List<Egreso> egresos, List<Ingreso> ingresos, Vinculador vinculador) {
        ordenarFecha(ingresos, egresos);
        for(int i = 0; i<ingresos.size(); i++){
            List<Integer> indexDestruir = new ArrayList<Integer>();
            for(int j = 0; j<egresos.size(); j++){
                if(pasaCondiciones(ingresos.get(i),egresos.get(j))){
                    ingresos.get(i).asociarEgreso(egresos.get(j).getValorTotal());
                    indexDestruir.add(j);
                }
                for(int z = 0; z<indexDestruir.size(); z++)
                {
                    ingresos.remove(indexDestruir.get(z)-z);
                }
            }
        }
    }
	

    public void ordenarFecha(List<Ingreso> ingresos, List<Egreso> egresos) {
		egresos.sort(Comparator.comparing(Egreso::getFecha));
		ingresos.sort(Comparator.comparing(Ingreso::getFecha));
    }
}
