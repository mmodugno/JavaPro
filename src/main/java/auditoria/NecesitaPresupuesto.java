package auditoria;

import egreso.OrdenDeCompra;

class NecesitaPresupuesto implements CondicionValidacion {
	
	public boolean validar(OrdenDeCompra ordenDeCompra){
		int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
	    
	    return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
	}
}