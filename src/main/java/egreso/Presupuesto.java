package egreso;

import java.util.List;
import producto.*;

public class Presupuesto {

	public Presupuesto(OrdenDeCompra ordenAsociada, Proveedor proveedor, MedioDePago medioDePago) {
		super();
		this.ordenAsociada = ordenAsociada;
		this.items = ordenAsociada.getItems();
		this.proveedor = proveedor;
		this.medioDePago = medioDePago;
	}
	private OrdenDeCompra ordenAsociada;
	private List<Item> items;
	private Proveedor proveedor;
	private MedioDePago medioDePago;
	
	
	
	public OrdenDeCompra getOrdenAsociada() {
		return ordenAsociada;
	}
	public List<Item> getItems() {
		return items;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}


	public Double valorTotal() throws SinItemsExcepcion{
			if(items.isEmpty()){
				throw new SinItemsExcepcion();
			}
			return items.stream().mapToDouble(Item::obtenerPrecio).sum();
		}



	
}
