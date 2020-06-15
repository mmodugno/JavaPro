package egreso;

import static java.util.stream.Collectors.toList;

import java.util.List;

import producto.Item;

public class CondicionValidacion {


    public boolean validarOrden(OrdenDeCompra ordenDeCompra){

        int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();
        

        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
    }
    
    public boolean presupuestoCorrecto(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
    	List<Integer> codigosDeItems = ordenDeCompra.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	List<Integer> codigosDePresupuesto = presupuesto.getItems().stream().map(Item::obtenerCodigoProducto).collect(toList());
    	
    	return codigosDePresupuesto.containsAll(codigosDeItems) && codigosDeItems.containsAll(codigosDePresupuesto);
    }
    
}
