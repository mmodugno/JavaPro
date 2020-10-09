package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.MedioDePago;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import egreso.TipoMedioPago;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.CategoriaDelSistema;

public class RepositorioOrdenDeCompra {
	
static List<OrdenDeCompra> ordenes = null;
	
    public RepositorioOrdenDeCompra() throws CloneNotSupportedException {
		 
    	Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
    	
    	MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    	
    	Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
    	Producto producto2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
    	Item item1 = new Item(producto1, 2, 0.00);
    	Item item2 = new Item(producto2, 3, 0.00);
    	
    	Presupuesto presu = new Presupuesto(  );
    	
    	OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1,1);
    	
    	ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);

    
        OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra(1,2);
        
        ordenDeCompra2.agregarItem(item1);
        ordenDeCompra2.agregarItem(item2);
        
       Presupuesto presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        presupuesto1.setAceptado();
        
        Presupuesto presupuesto2 = new Presupuesto(ordenDeCompra2.getItems(),proveedor1,medioDePago);
        presupuesto2.getItems().get(0).setPrecioUnitario(50.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(10.00);
        ordenDeCompra2.agregarPresupuesto(presupuesto2);
        presupuesto2.setAceptado();
        
        
        presupuesto1.setId(1);
        presupuesto2.setId(2);   
        
        if (ordenes == null) {
        ordenes = new ArrayList<>();
        ordenes.add(ordenDeCompra);
        ordenes.add(ordenDeCompra2);
        }
	       
	 }
    
    public OrdenDeCompra byID(int id) {
        return ordenes.stream().filter(a ->
                a.getIdOrden() == id
        ).findFirst().get();
  }


    public List<OrdenDeCompra> todos() {
        return new ArrayList<>(ordenes);
    }
    
    public void crear(OrdenDeCompra orden) {
    	ordenes.add(orden);
    }

    public void ordenesSinVincular(){

    }

    public int proximoId(){
        ordenes.sort((OrdenDeCompra orden1, OrdenDeCompra orden2) -> {
            return ordenarInt(orden1.getIdOrden(),orden2.getIdOrden());
        });
        return ordenes.get(0).getIdOrden() + 1;
    }
	
    int ordenarInt(int primero,int segundo){
        if (primero > segundo) return -1;
        if (primero < segundo) return 1;
        return 0;

    }

	public void agregar(OrdenDeCompra nuevaOrden) {
		ordenes.add(nuevaOrden);
		
	}
	
}
