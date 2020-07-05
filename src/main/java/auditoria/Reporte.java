package auditoria;

import java.util.List;

import producto.Item;

public class Reporte {
	
	private int nroPresupuestosNecesarios;
	private int nroPresupuestos;
	private boolean validacionNroPresupuestos;
	
	private double montoCompra;
	private double montoPresupuesto;
	private boolean validacionMontos;
	
	private int cantidadItemsCompra;
	private int cantidadItemsPresupuesto;
	private boolean validacionItems;
	private List<Item> itemsFaltantesEnPresupuesto;
	private List<Item> itemsFaltantesCompra;

	public void setPresupuestos(int nroPresupuestos, int nroPresupuestosNecesarios) {
		this.nroPresupuestos = nroPresupuestos;
		this.nroPresupuestosNecesarios = nroPresupuestosNecesarios;
	}
	
	public void validacionCantidadDePresupuestos(boolean validacion) { 
		this.validacionNroPresupuestos = validacion;
	}
	
	public void setMontos(double montoCompra, double montoPresupuesto) {
		this.montoCompra = montoCompra;
		this.montoPresupuesto = montoPresupuesto;
	}
	
	public void validacionMontosCompraPresupuesto(boolean validacion) { 
		this.validacionNroPresupuestos = validacion;
	}
	
	public void setCantidadItems(int cantidadItemsCompra, int cantidadItemsPresupuesto) {
		this.cantidadItemsCompra = cantidadItemsCompra;
		this.cantidadItemsPresupuesto = cantidadItemsPresupuesto;
	}
	
	public void validacionCantidadItems(boolean validacion) { 
		this.validacionItems = validacion;
	}
	
	public void itemsFaltantesPresupuesto(List<Item> items) {
		this.itemsFaltantesEnPresupuesto = items;
	}
	
	public void itemsFaltantesCompra(List<Item> items) {
		this.itemsFaltantesCompra = items;
	}
	

}