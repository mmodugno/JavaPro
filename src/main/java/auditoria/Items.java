package auditoria;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import egreso.OrdenDeCompra;
import producto.Item;

public class Items implements CondicionValidacion {

	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra) {
		boolean cantidadItems;
		boolean itemsCompra;
		cantidadItems = (ordenDeCompra.getItems().size() == ordenDeCompra.presupuestoAceptado().getItems().size());
		itemsCompra = this.verificarItems(ordenDeCompra);
	    return cantidadItems || itemsCompra;
	}
	
	private boolean verificarItems(OrdenDeCompra ordenDeCompra) {
    	
    	List<Integer> codigosDeItemsCompra = ordenDeCompra.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	List<Integer> codigosDeItemsPresupuesto = ordenDeCompra.presupuestoAceptado().getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	
    	return codigosDeItemsPresupuesto.containsAll(codigosDeItemsCompra) && codigosDeItemsCompra.containsAll(codigosDeItemsPresupuesto);
	}
	
	public List<Item> obtenerItemsFaltantes(List<Item> listaConMasItems, List<Item> listaConMenosItems) {
    	List<Item> itemsFaltantes;
    	if(listaConMasItems.size() > listaConMenosItems.size()) {
    		itemsFaltantes = listaConMasItems;
    		itemsFaltantes.removeAll(listaConMenosItems);
    		return itemsFaltantes;
    	}
    	return Collections.emptyList();
    }
    
}
