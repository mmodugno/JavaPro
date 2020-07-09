package auditoria;

import egreso.CriterioSeleccion;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class Criterios implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		CriterioSeleccion criterioDeSeleccion;
		Presupuesto presupuestoCriterio;
		Presupuesto presupuestoOrdenDeCompra;
		criterioDeSeleccion = ordenDeCompra.getCriterioSeleccion();
		
		presupuestoCriterio = criterioDeSeleccion.seleccionar(ordenDeCompra);
		
		presupuestoOrdenDeCompra = ordenDeCompra.presupuestoAceptado();
		
		return presupuestoCriterio.valorTotal() == presupuestoOrdenDeCompra.valorTotal();
	}

}
