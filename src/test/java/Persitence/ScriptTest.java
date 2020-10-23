package Persitence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.metamodel.binding.EntityIdentifier;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import db.EntityManagerHelper;
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

public class ScriptTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void productoTest() throws CloneNotSupportedException {

	    //PRODUCTOS
		Producto producto1 = new Producto(1, "Monitor", "Monitor 32", TipoItem.ARTICULO);
        Producto producto2 = new Producto(2, "Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
        Producto producto3 = new Producto(3, "Office", "Office365", TipoItem.SERVICIO);

        //PROVEEDOR

        Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
        Proveedor proveedor2 = new Proveedor("Juan Computación","21123214569","1419");

        //ITEMS PRESUPUESTO PRUEBA
        Item item1 = new Item(producto1, 1, 0.00);
        Item item2 = new Item(producto2, 2, 0.00);

            //ORDEN DE COMPRA
         OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1,1);
         OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra(3,2);

         ordenDeCompra.agregarItem(item1);
         ordenDeCompra.agregarItem(item2);
         ordenDeCompra2.agregarItem(item1);
         ordenDeCompra2.agregarItem(item2);


            //un medio para el presupuesto
        MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);

        //PRESUPUESTO
        Presupuesto presupuesto1;
        Presupuesto presupuesto2;

        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);

        //CATEGORIAS
        Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
        Categoria categoriaMendoza = new Categoria("Mendoza","Provincia");
        CategoriaCompuesta categoriaARGENTINA = new CategoriaCompuesta("argentina","pais");

        List<CategoriaDelSistema> listaSubCategorias = new ArrayList<CategoriaDelSistema>();
        listaSubCategorias.add(categoriaBSAS);
        listaSubCategorias.add(categoriaMendoza);
        categoriaARGENTINA.setSubCategorias(listaSubCategorias);


        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(producto1);
        EntityManagerHelper.getEntityManager().persist(producto2);
        EntityManagerHelper.getEntityManager().persist(producto3);
        EntityManagerHelper.commit();

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(proveedor1);
        EntityManagerHelper.getEntityManager().persist(proveedor2);
        EntityManagerHelper.commit();

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(categoriaBSAS);
        EntityManagerHelper.getEntityManager().persist(categoriaMendoza);
        EntityManagerHelper.getEntityManager().persist(categoriaARGENTINA);
        EntityManagerHelper.commit();


        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(presupuesto1);
        EntityManagerHelper.commit();

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ordenDeCompra);
        EntityManagerHelper.getEntityManager().persist(ordenDeCompra2);
        EntityManagerHelper.commit();
        
	}
	
	/*
	@Test
	public void presupuestoTest() throws CloneNotSupportedException {
		
Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
    	
    	Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
    	
    	MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    	
    	Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
    	Producto producto2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
    	Item item1 = new Item(producto1, 2, 0.00);
    	Item item2 = new Item(producto2, 3, 0.00);
    	
    	OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1,1);
    	
    	ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);

    	Presupuesto presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        
        //presupuesto1.setId(6);
        
        presupuesto1.setCategoria(categoriaBSAS);
        
        
        Presupuesto presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);
        
       // presupuesto2.setId(7);
        
        
        
	}
	*/
}