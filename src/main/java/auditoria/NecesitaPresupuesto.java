package auditoria;

import egreso.OrdenDeCompra;

class NecesitaPresupuesto implements CondicionValidacion {
	
	String nombre = "Necesita Presupuesto";
	
	public boolean validar(OrdenDeCompra ordenDeCompra){
		int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
	    
	    return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
	}
	
	public String getNombre() {
		return nombre;
	}
}