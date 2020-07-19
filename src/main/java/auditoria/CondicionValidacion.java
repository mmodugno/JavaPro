package auditoria;

import egreso.OrdenDeCompra;

public abstract class CondicionValidacion {
	
	protected String nombre;
	
	public abstract boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte);

	public String getNombre() {
		return nombre;
	}
}