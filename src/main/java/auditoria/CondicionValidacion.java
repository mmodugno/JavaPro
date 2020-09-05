package auditoria;

import egreso.OrdenDeCompra;

public abstract class CondicionValidacion {

	/*ATRIBUTOS*/
	protected String nombre;
	
	public abstract boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte);

	/*GETTERS*/
	public String getNombre() {
		return nombre;
	}

	/*SETTERS*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}