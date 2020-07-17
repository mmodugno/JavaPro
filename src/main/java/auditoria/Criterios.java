package auditoria;

import egreso.CriterioSeleccion;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class Criterios implements CondicionValidacion {
	
	String nombre = "Criterio de Selección de Presupuesto";

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		
		CriterioSeleccion criterioDeSeleccion;
		Presupuesto presupuestoCriterio;
		Presupuesto presupuestoOrdenDeCompra;
		
		if(ordenDeCompra.getPresupuestos().size() > 1) {
			
			criterioDeSeleccion = ordenDeCompra.getCriterioSeleccion();
			
			presupuestoCriterio = criterioDeSeleccion.seleccionar(ordenDeCompra);
			
			presupuestoOrdenDeCompra = ordenDeCompra.presupuestoAceptado();
			
			return presupuestoCriterio.valorTotal() == presupuestoOrdenDeCompra.valorTotal();
			
		} else {
			
			return true;
			
		}
		
	}
	
	public String getNombre() {
		return nombre;
	}

}
