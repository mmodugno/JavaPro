package auditoria;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public interface CondicionValidacion {
	
	public boolean validar(OrdenDeCompra ordenDeCompra);
}