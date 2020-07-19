package auditoria;

import egreso.OrdenDeCompra;

public class CantidadPresupuestos extends CondicionValidacion {

	private int cantidadPresupuestos;
	private int presupuestosRequeridos;
	
	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		nombre = "Validación de Cantidad de Presupuestos Requeridos";
		boolean validacion;

		this.cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        this.presupuestosRequeridos = ordenDeCompra.getNecesitaPresupuesto();
        		
		validacion = presupuestosRequeridos <= cantidadPresupuestos;
		
		reporte.resultadoValidacionPresupuestos(this, validacion);
		
        return validacion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getCantidadPresupuestos() {
		return cantidadPresupuestos;
	}

	public int getPresupuestosRequeridos() {
		return presupuestosRequeridos;
	}
	
	

}
