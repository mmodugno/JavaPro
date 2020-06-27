package egreso;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import producto.Item;

public class CondicionValidacion {


    public boolean validarOrden(OrdenDeCompra ordenDeCompra){

        int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        

        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
    }
    
    public boolean presupuestoCorrecto(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
    	
    	List<Item> itemsDeOrden = ordenDeCompra.getItems().stream().collect(Collectors.toList());
    	List<Item> itemsDePresupuesto = presupuesto.getItems().stream().collect(Collectors.toList());
    	
    	List<Integer> codigosDeItems = ordenDeCompra.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	List<Integer> codigosDePresupuesto = presupuesto.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	
    	if(codigosDePresupuesto.containsAll(codigosDeItems) && codigosDeItems.containsAll(codigosDePresupuesto)) {
    		
    	boolean correspondenCantidades = itemsDeOrden.stream().allMatch(itemDeOrden -> itemDeOrden.getCantidad() == 
    	itemsDePresupuesto.stream().filter(itemDePresupuesto -> itemDePresupuesto.obtenerCodigoProducto() == itemDeOrden.obtenerCodigoProducto()).findFirst().get().getCantidad());
    	
    	return correspondenCantidades;
    	
    	}
    	
    	else return false;
    	
    }
    
}
