package auditoria;

import egreso.OrdenDeCompra;

public class CantidadItems implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
	    return ordenDeCompra.getItems().size() == ordenDeCompra.presupuestoAceptado().getItems().size();
	}
}
