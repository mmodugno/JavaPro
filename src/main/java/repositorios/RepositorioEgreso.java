package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import usuarios.CategoriaCompuesta;
import usuarios.CategoriaDelSistema;
import usuarios.Usuario;

import db.EntityManagerHelper;

public class RepositorioEgreso {
	
	private EntityManager entityManager;
	
	private static List<Egreso> egresos = null;

	 public RepositorioEgreso(EntityManager entityManager) throws CloneNotSupportedException {
		 
		this.entityManager = entityManager;
		 
		Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
		
		MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
		 
		MedioDePago medioDePago2 = new MedioDePago(TipoMedioPago.Visa, 221144);
		
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
	    
	    Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
	    
	    Categoria categoriaMendoza = new Categoria("Mendoza","Provincia");
	    
	    CategoriaCompuesta categoriaARGENTINA = new CategoriaCompuesta("argentina","pais");
    	
    	

    	List<CategoriaDelSistema> listaSubCategorias = new ArrayList<CategoriaDelSistema>();
		
		listaSubCategorias.add(categoriaBSAS);
		listaSubCategorias.add(categoriaMendoza);
		
		categoriaARGENTINA.setSubCategorias(listaSubCategorias);
	    
		Presupuesto presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        
        Presupuesto presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago2);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);
        
        presupuesto1.setId(6);
        presupuesto2.setId(7);
		 
		Egreso egreso1= new Egreso(ordenDeCompra, presupuesto1);
		egreso1.setId(1);
		Egreso egreso2= new Egreso(ordenDeCompra2, presupuesto2);
		egreso2.setId(2);
		Egreso egreso3= new Egreso(ordenDeCompra, presupuesto1);
		 egreso3.setId(3);
		 
		 egreso1.setCategoria(categoriaBSAS);

		// egreso2.setCategoria(categoriaMendoza);
		 
		 egreso3.setCategoria(categoriaARGENTINA);
		 
		 if (egresos == null) {
			 egresos = new ArrayList<>();
		egresos.add(egreso1);
		 egresos.add(egreso2);
		 egresos.add(egreso3);
		 
		
		 }

		
	       
	 }

	 public List<Egreso> byCategoria(CategoriaDelSistema unaCategoria) {
	        return this.todos().stream().filter(a ->
	                a.esDeCategoria(unaCategoria)
	        ).collect(Collectors.toList());
	  }
	   
	 public List<Egreso> todos() {
		 
		   CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	        CriteriaQuery<Egreso> consulta = cb.createQuery(Egreso.class);
	        Root<Egreso> egresos = consulta.from(Egreso.class);
	        return this.entityManager.createQuery(consulta.select(egresos)).getResultList();
	         
	    }   
	   
	public void crear(Egreso egreso) {
		this.entityManager.persist(egreso);
	    }
	
	/*public void reemplazar(Egreso egreso) {
		
		int index = egresos.indexOf(egreso);
		
		egresos.set(index,egreso);
		
	}*/

	public Egreso byID(int id) {
		
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Egreso> consulta = cb.createQuery(Egreso.class);
        Root<Egreso> egresos = consulta.from(Egreso.class);
        Predicate condicion = cb.equal(egresos.get("id"), id);
        CriteriaQuery<Egreso> where = consulta.select(egresos).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
       
	}

	public void eliminar(Egreso egreso){
	 	egresos.remove(egreso);
	}

	int ordenarInt(int primero,int segundo){
		if (primero > segundo) return -1;
		if (primero < segundo) return 1;
		return 0;

	}

	public int proximoId(){
		egresos.sort((Egreso egreso1, Egreso egreso2) -> {
			return ordenarInt(egreso1.getId(),egreso2.getId());
		});
		return egresos.get(0).getId() + 1;
	}

	    
	   

}
