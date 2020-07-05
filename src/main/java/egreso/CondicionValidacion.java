package egreso;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import producto.Item;

public class CondicionValidacion {


    public boolean validarOrden(OrdenDeCompra ordenDeCompra){

        int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        
        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
    }
    
    public boolean validarCantidadPresupuestos(OrdenDeCompra ordenDeCompra){

        int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        
        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
    }
    
    public boolean validarMontoPresupuestoConCompra(OrdenDeCompra ordenDeCompra){
        
        return ordenDeCompra.presupuestoAceptado().valorTotal() == ordenDeCompra.valorTotal();
    }
    
    public List<Item> obtenerItemsFaltantes(List<Item> listaConMasItems, List<Item> listaConMenosItems) {
    	List<Item> itemsFaltantes;
    	if(listaConMasItems.size() > listaConMenosItems.size()) {
    		itemsFaltantes = listaConMasItems;
    		itemsFaltantes.removeAll(listaConMenosItems);
    		return itemsFaltantes;
    		//reporteValidacion.itemsFaltantesPresupuesto(itemsFaltantesEnPresupuesto);
    	}
    	return Collections.emptyList();
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
    
    public boolean validarCantidadItems(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
    	return ordenDeCompra.getItems().size() == presupuesto.getItems().size();
    }
    
    
}
