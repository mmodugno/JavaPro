package auditoria;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import egreso.OrdenDeCompra;
import producto.Item;

public class Items extends CondicionValidacion {
	
	private int cantidadItemsCompra;
	private int cantidadItemsPresupuesto;
	List<Item> itemsNoValidadosOrdenCompra;
	

	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		nombre = "Validador de Items de Compra y Presupuesto\n\n";
		boolean verificarCantidadItems;
		boolean verificarItemsCompra;
		boolean validacion;
		
		verificarCantidadItems = (ordenDeCompra.getItems().size() == ordenDeCompra.presupuestoAceptado().getItems().size());
		verificarItemsCompra = this.verificarItems(ordenDeCompra);
		
		// Verificar Items en Orden de Compra por Id Producto
		if(!verificarItemsCompra)
			itemsNoValidadosOrdenCompra = obtenerItemsFaltantes(ordenDeCompra.getItems(), ordenDeCompra.presupuestoAceptado().getItems());
	    
		validacion = verificarCantidadItems || verificarItemsCompra;
		
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
	
	public List<Item> obtenerItemsFaltantes(List<Item> listaOrdenCompra, List<Item> listaPresupuesto) {
    	List<Item> itemsFaltantes;
		itemsFaltantes = listaOrdenCompra;
		listaOrdenCompra.removeAll(listaPresupuesto);
		return itemsFaltantes;

    }

	public int getCantidadItemsCompra() {
		return cantidadItemsCompra;
	}

	public int getCantidadItemsPresupuesto() {
		return cantidadItemsPresupuesto;
	}

	public List<Item> getItemsNoValidadosOrdenCompra() {
		return itemsNoValidadosOrdenCompra;
	}
	
	
    
}
