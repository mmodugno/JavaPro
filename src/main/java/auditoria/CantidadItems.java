package auditoria;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class CantidadItems implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
	    return ordenDeCompra.getItems().size() == ordenDeCompra.presupuestoAceptado().getItems().size();
	}
}
