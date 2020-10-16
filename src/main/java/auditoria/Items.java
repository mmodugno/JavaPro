package auditoria;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import egreso.OrdenDeCompra;
import producto.Item;

public class Items extends CondicionValidacion {

	/*ATRIBUTOS*/
	private int cantidadItemsCompra;
	private int cantidadItemsPresupuesto;
	List<Item> itemsNoValidadosOrdenCompra;
	List<Item> itemsFaltantesPresupuesto;

	/*GETTERS*/
	public int getCantidadItemsCompra() {
		return cantidadItemsCompra;
	}
	public int getCantidadItemsPresupuesto() {
		return cantidadItemsPresupuesto;
	}
	public List<Item> getItemsNoValidadosOrdenCompra() {
		return itemsNoValidadosOrdenCompra;
	}
	public List<Item> getItemsFaltantesPresupuesto() {
		return itemsFaltantesPresupuesto;
	}

	/*SETTERS*/
	public void setCantidadItemsCompra(int cantidadItemsCompra) {
		this.cantidadItemsCompra = cantidadItemsCompra;
	}
	public void setCantidadItemsPresupuesto(int cantidadItemsPresupuesto) {
		this.cantidadItemsPresupuesto = cantidadItemsPresupuesto;
	}
	public void setItemsNoValidadosOrdenCompra(List<Item> itemsNoValidadosOrdenCompra) {
		this.itemsNoValidadosOrdenCompra = itemsNoValidadosOrdenCompra;
	}
	public void setItemsFaltantesPresupuesto(List<Item> itemsFaltantesPresupuesto) {
		this.itemsFaltantesPresupuesto = itemsFaltantesPresupuesto;
	}

	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		nombre = "Validación de Items de Compra y Presupuesto";
		boolean verificarCantidadItems;
		boolean verificarItemsCompra;
		boolean validacion;
		
		cantidadItemsCompra = ordenDeCompra.getItems().size();
		cantidadItemsPresupuesto = ordenDeCompra.presupuestoAceptado().getItems().size();
		
		verificarCantidadItems = ( cantidadItemsCompra == cantidadItemsPresupuesto);
		verificarItemsCompra = this.verificarItems(ordenDeCompra);
		
		// Verificar Items en Orden de Compra por Id Producto
		if(!verificarItemsCompra) {
			itemsNoValidadosOrdenCompra = obtenerItemsFaltantes(ordenDeCompra.getItems(), ordenDeCompra.presupuestoAceptado().getItems());
			itemsFaltantesPresupuesto = obtenerItemsFaltantes(ordenDeCompra.presupuestoAceptado().getItems(), ordenDeCompra.getItems());
		}
			
	    
		validacion = verificarCantidadItems && verificarItemsCompra;
		
		reporte.resultadoValidacionItems(this, validacion);
		
		return validacion;
	}
	
	private boolean verificarItems(OrdenDeCompra ordenDeCompra) {
		
		List<Integer> codigosDeItemsCompra;
		List<Integer> codigosDeItemsPresupuesto;
    	
    	codigosDeItemsCompra = ordenDeCompra.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	codigosDeItemsPresupuesto = ordenDeCompra.presupuestoAceptado().getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	
    	return codigosDeItemsPresupuesto.containsAll(codigosDeItemsCompra) && codigosDeItemsCompra.containsAll(codigosDeItemsPresupuesto);
	}
	
	public List<Item> obtenerItemsFaltantes(List<Item> listaSemilla, List<Item> listaAComparar) {
    	List<Item> IdsItemsNoValidados = new ArrayList<Item>();    	
		List<Integer> codigosDeItemsPresupuesto;
    	
    	codigosDeItemsPresupuesto = listaAComparar.stream().map(Item::obtenerCodigoProducto).collect(toList());
		
    	for(Item unItem : listaSemilla) {
    		if(!codigosDeItemsPresupuesto.contains(unItem.obtenerCodigoProducto())) {
    			IdsItemsNoValidados.add(unItem);
    		}	
    	}
    			
		return IdsItemsNoValidados;
    }
    
}
