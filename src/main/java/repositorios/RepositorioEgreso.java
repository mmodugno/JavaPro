package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.MedioDePago;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import egreso.TipoMedioPago;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.CategoriaDelSistema;
import usuarios.Usuario;

public class RepositorioEgreso {
	
	 static List<Egreso> egresos = null;
	 
	 public RepositorioEgreso() throws CloneNotSupportedException {
		 
		Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
		
		MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
		 
		Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
		Producto producto2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
		Item item1 = new Item(producto1, 2, 0.00);
		Item item2 = new Item(producto2, 3, 0.00);
		 
		OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1,1);
		OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra(3,2);
		
		ordenDeCompra.agregarItem(item1);
	    ordenDeCompra.agregarItem(item2);
	    ordenDeCompra2.agregarItem(item1);
	    ordenDeCompra2.agregarItem(item2);
	    
		Presupuesto presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        
        Presupuesto presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);
		 
		Egreso egreso1= new Egreso(ordenDeCompra, presupuesto1);
		Egreso egreso2= new Egreso(ordenDeCompra2, presupuesto2);
		Egreso egreso3= new Egreso(ordenDeCompra, presupuesto1);

		egresos.add(egreso1);
		 egresos.add(egreso2);
		 egresos.add(egreso3);

		
	       
	 }

	 public List<Egreso> byCategoria(CategoriaDelSistema unaCategoria) {
	        return egresos.stream().filter(a ->
	                a.esDeCategoria(unaCategoria)
	        ).collect(Collectors.toList());
	  }
	   
	 public List<Egreso> todos() {
	        return new ArrayList<>(egresos);
	    }   
	   
	public void crear(Egreso egreso) {
	    	egresos.add(egreso);
	    } 
	    
	   

}
