package egreso;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

import producto.Producto;

public class ElMasBarato implements CriterioSeleccion {
	
	public Double elMenor(List<Double> t) {
	
    double min = 0;
    for (int i = 0; i < t.size(); i++) {
        if (t.get(i) < min) {
            min = t.get(i);
        }
    }
    
	return min;
    
	}
	
	@Override
	public Presupuesto seleccionar(OrdenDeCompra ordenDeCompra) {
		
	
		Double menorPrecio = elMenor(ordenDeCompra.getPresupuestos().stream().mapToDouble(presupuesto -> presupuesto.valorTotal()));

		
		return ordenDeCompra.getPresupuestos().stream().filter(m -> m.valorTotal() == menorPrecio).first();
		
		
	}

	
	
}
