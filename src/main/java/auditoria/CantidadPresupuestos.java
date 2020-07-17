package auditoria;

import egreso.OrdenDeCompra;

public class CantidadPresupuestos implements CondicionValidacion {

	String nombre = "Cantidad de Presupuestos";
	
	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        
        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
	}
	
	public String getNombre() {
		return nombre;
	}

}
