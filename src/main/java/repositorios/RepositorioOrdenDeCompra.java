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
    	
    	OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1,1);
    	
    	ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);

    
        OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra(1,2);
        
        ordenDeCompra2.agregarItem(item1);
        ordenDeCompra2.agregarItem(item2);
        
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

	
	
}
