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
import usuarios.Categoria;
import usuarios.CategoriaDelSistema;

public class RepositorioPresupuesto {
	
	static List<Presupuesto> presupuestos = null;
	
    public RepositorioPresupuesto() throws CloneNotSupportedException {
		 
    	Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
    	
    	Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
    	
    	MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    	
    	MedioDePago medioDePago2 = new MedioDePago(TipoMedioPago.Visa, 221144);
    	
    	Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
    	Producto producto2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
    	Item item1 = new Item(producto1, 2, 0.00);
    	Item item2 = new Item(producto2, 3, 0.00);
    	
    	OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1);
    	
    	ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);

    	Presupuesto presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        
        presupuesto1.setId(6);
        
        presupuesto1.setCategoria(categoriaBSAS);
        
        
        Presupuesto presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago2);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);
        
        presupuesto2.setId(7);
        
        if (presupuestos == null) {
        presupuestos = new ArrayList<>();
        presupuestos.add(presupuesto1);
        presupuestos.add(presupuesto2);
        }
	 }
    
    public List<Presupuesto> byCategoria(CategoriaDelSistema unaCategoria) {
        return presupuestos.stream().filter(a ->
                a.esDeCategoria(unaCategoria)
        ).collect(Collectors.toList());
    }
    
    public Presupuesto byID(int id) {
        return presupuestos.stream().filter(a ->
                a.getId() == id
        ).findFirst().get();
  }


    public List<Presupuesto> todos() {
        return new ArrayList<>(presupuestos);
    }
    
    public void crear(Presupuesto presupuesto) {
    	presupuestos.add(presupuesto);
    }

    
}
