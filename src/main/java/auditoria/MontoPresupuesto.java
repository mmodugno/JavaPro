package auditoria;

import egreso.OrdenDeCompra;

public class MontoPresupuesto extends CondicionValidacion {

	private double montoCompra;
	private double montoPresupuestoAceptado;
	
	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		nombre = "Validación de Monto Compra y Presupuesto";
		boolean validacion;
		this.montoCompra = ordenDeCompra.valorTotal();
		this.montoPresupuestoAceptado = ordenDeCompra.presupuestoAceptado().valorTotal();
		validacion = (montoCompra == montoPresupuestoAceptado);
		
		reporte.resultadoValidacionMontoPresupuestos(this, validacion);
		
		return validacion;
	}

	public double getMontoCompra() {
		return montoCompra;
	}

	public double getMontoPresupuestoAceptado() {
		return montoPresupuestoAceptado;
	}

}
