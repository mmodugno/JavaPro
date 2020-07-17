package auditoria;

import egreso.OrdenDeCompra;

public class MontoPresupuesto implements CondicionValidacion {
	
	
	String nombre = "Monto Presupuesto";
	
	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		return ordenDeCompra.presupuestoAceptado().valorTotal() == ordenDeCompra.valorTotal();
	}
	
	public String getNombre() {
		return nombre;
	}

}
