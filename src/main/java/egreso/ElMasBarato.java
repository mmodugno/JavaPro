package egreso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ElMasBarato implements CriterioSeleccion {

	public Double elMenor(List<Double> t) {
	
    double min = t.get(0);
    for (int i = 0; i < t.size(); i++) {
        if (t.get(i) < min) {
            min = t.get(i);
        }
    }
    
	return min;
    
	}

	@Override
	public Presupuesto seleccionar(OrdenDeCompra ordenDeCompra) {

		List<Double> lista = ordenDeCompra.getPresupuestos().stream().map(Presupuesto::valorTotal).collect(toList());
		Double menorPrecio = Collections.min(lista);
		Presupuesto presu = ordenDeCompra.getPresupuestos().stream().filter(m -> m.valorTotal() == menorPrecio).findFirst().get();
		return presu;
		
	}

	
	
}
