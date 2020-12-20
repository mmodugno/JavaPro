package auditoria;

import egreso.Egreso;
import egreso.OrdenDeCompra;

public class CantidadPresupuestos extends CondicionValidacion {

	/*ATRIBUTOS*/
	private int cantidadPresupuestos;
	private int presupuestosRequeridos;

	/*GETTERS*/
	public String getNombre() {
		return nombre;
	}
	public int getCantidadPresupuestos() {
		return cantidadPresupuestos;
	}
	public int getPresupuestosRequeridos() {
		return presupuestosRequeridos;
	}
	/*SETTERS*/

	public void setCantidadPresupuestos(int cantidadPresupuestos) {
		this.cantidadPresupuestos = cantidadPresupuestos;
	}
	public void setPresupuestosRequeridos(int presupuestosRequeridos) {
		this.presupuestosRequeridos = presupuestosRequeridos;
	}

/*	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		nombre = "Validación de Cantidad de Presupuestos Requeridos";
		boolean validacion;

		this.cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        this.presupuestosRequeridos = ordenDeCompra.getNecesitaPresupuesto();
        		
		validacion = presupuestosRequeridos <= cantidadPresupuestos;
		
		reporte.resultadoValidacionPresupuestos(this, validacion);
		
        return validacion;
	}*/
	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte, Egreso egreso) {
		nombre = "Validación de Cantidad de Presupuestos Requeridos";
		boolean validacion;

		this.cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        this.presupuestosRequeridos = ordenDeCompra.getNecesitaPresupuesto();
        		
		validacion = presupuestosRequeridos <= cantidadPresupuestos;
		
		reporte.resultadoValidacionPresupuestos(this, validacion);
		
        return validacion;	
       }

}
