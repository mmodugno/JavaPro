package auditoria;

import egreso.OrdenDeCompra;

public class CantidadPresupuestos implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        
        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
	}

}
