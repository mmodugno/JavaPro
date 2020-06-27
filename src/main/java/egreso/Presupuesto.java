package egreso;

import java.util.ArrayList;
import java.util.List;
import producto.*;

public class Presupuesto {

	public Presupuesto(List<Item> itemsoriginal, Proveedor proveedor, MedioDePago medioDePago) throws CloneNotSupportedException {
		super();
		this.items = new ArrayList<Item>();
		this.proveedor = proveedor;
		this.medioDePago = medioDePago;

		llenarItems(itemsoriginal);
	}
	private void llenarItems(List<Item> itemsoriginales) throws CloneNotSupportedException {
		for(int i = 0;i<itemsoriginales.size();i++){
			items.add(itemsoriginales.get(i).clone());
		}

	}



	private List<Item> items;
	private Proveedor proveedor;
	private MedioDePago medioDePago;
	
	
	
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
