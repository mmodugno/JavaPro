package auditoria;

import egreso.OrdenDeCompra;

public class MontoPresupuesto implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		return ordenDeCompra.presupuestoAceptado().valorTotal() == ordenDeCompra.valorTotal();
	}

}
