package egreso;

import java.util.List;
import producto.*;

public class Presupuesto {

	public Presupuesto(Egreso egresoAsociado, List<Item> items, Proveedor proveedor, MedioDePago medioDePago) {
		super();
		this.egresoAsociado = egresoAsociado;
		this.items = items;
		this.proveedor = proveedor;
		this.medioDePago = medioDePago;
	}
	private Egreso egresoAsociado;
	private List<Item> items;
	private Proveedor proveedor;
	private MedioDePago medioDePago;
	
	public Egreso getEgresoAsociado() {
		return egresoAsociado;
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
			return items.stream().mapToDouble(Item::getPrecio).sum();
		}

	
	
}
