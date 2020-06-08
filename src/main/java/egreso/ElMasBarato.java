package egreso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ElMasBarato implements CriterioSeleccion {

	@Override
	public Presupuesto seleccionar(OrdenDeCompra ordenDeCompra) {

		List<Double> lista = ordenDeCompra.getPresupuestos().stream().map(Presupuesto::valorTotal).collect(toList());
		Double menorPrecio = Collections.min(lista);
		Presupuesto presu = ordenDeCompra.getPresupuestos().stream().filter(m -> m.valorTotal().equals(menorPrecio)).collect(toList()).get(0);
		return presu;
		
	}

	
	
}
