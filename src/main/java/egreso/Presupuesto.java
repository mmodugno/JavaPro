package egreso;

import java.util.List;
import producto.*;

public class Presupuesto {

	public Presupuesto(Egreso egresoAsociado, List<Item> items, Proveedor proveedor) {
		super();
		this.egresoAsociado = egresoAsociado;
		this.items = items;
		this.proveedor = proveedor;
	}
	private Egreso egresoAsociado;
	private List<Item> items;
	private Proveedor proveedor;
	
	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}
	public List<Item> getItems() {
		return items;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public double valorTotal() {
		return egresoAsociado.valorTotal();
	}
	
	
}
