package Persitence;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import egreso.*;
import organizacion.EntidadJuridica;
import organizacion.Organizacion;

import org.hibernate.metamodel.binding.EntityIdentifier;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import db.EntityManagerHelper;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.Categoria;
import usuarios.CategoriaCompuesta;
import usuarios.CategoriaDelSistema;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

public class ScriptTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void productoTest() throws CloneNotSupportedException, FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		
		//USUARIO y organizaciones
		
		CreadorUsuario userMaker = new CreadorUsuario();
    	Organizacion organizacion = new Organizacion();
    	
    	EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
    	
    	Usuario userStandard = userMaker.crearUsuario("userStandard", "pru3b@tesT", "estandar", organizacion);
        
    	
		//PRODUCTOS
		Producto producto1 = new Producto(1, "Monitor", "Monitor 32", TipoItem.ARTICULO);
        Producto producto2 = new Producto(2, "Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
        Producto producto3 = new Producto(3, "Office", "Office365", TipoItem.SERVICIO);
        
        //PROVEEDOR

        Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
        Proveedor proveedor2 = new Proveedor("Juan Computacion","21123214569","1419");

        //ITEMS PRESUPUESTO PRUEBA
        Item item1 = new Item(producto1, 1, 0.00);
        Item item2 = new Item(producto2, 2, 0.00);

            //ORDEN DE COMPRA
         OrdenDeCompra ordenDeCompra = new OrdenDeCompra(1);
         OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra(3);

         ordenDeCompra.agregarItem(item1);
         ordenDeCompra.agregarItem(item2);
         ordenDeCompra2.agregarItem(item1);
         ordenDeCompra2.agregarItem(item2);


            //un medio para el presupuesto
        MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard);
        
        MedioDePago medioDePago2 = new MedioDePago(TipoMedioPago.Visa);

        //PRESUPUESTO
        Presupuesto presupuesto1;
        Presupuesto presupuesto2;

        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);

        presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor2,medioDePago2);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);

        ordenDeCompra.agregarPresupuesto(presupuesto1);
        ordenDeCompra2.agregarPresupuesto(presupuesto2);
        //CATEGORIAS
        Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
        Categoria categoriaMendoza = new Categoria("Mendoza","Provincia");
        CategoriaCompuesta categoriaARGENTINA = new CategoriaCompuesta("argentina","pais");

        List<CategoriaDelSistema> listaSubCategorias = new ArrayList<CategoriaDelSistema>();
        listaSubCategorias.add(categoriaBSAS);
        listaSubCategorias.add(categoriaMendoza);
        categoriaARGENTINA.setSubCategorias(listaSubCategorias);


        //EGRESOS
        Egreso egreso1= new Egreso(ordenDeCompra, presupuesto1);
        Egreso egreso2= new Egreso(ordenDeCompra2, presupuesto2);
        Egreso egreso3= new Egreso(ordenDeCompra, presupuesto1);

        //INGRESOS
        Ingreso ingreso1 = new Ingreso("Donacion",1000.0);
        Ingreso ingreso2 = new Ingreso("Venta",10000.0);
        Ingreso ingreso3 = new Ingreso("Venta",500.0);


        egreso1.setCategoria(categoriaBSAS);

		egreso2.setCategoria(categoriaMendoza);
		 
		egreso3.setCategoria(categoriaARGENTINA);
		
		//LISTAS DE EGRESOS/INGRESOS/ORDENES PARA UNA ORG

		List<CategoriaDelSistema> listaCategorias = new ArrayList<>();
		
		listaCategorias.add(categoriaBSAS);
		listaCategorias.add(categoriaMendoza);
		listaCategorias.add(categoriaARGENTINA);
		
		List<Egreso> listaEgresos1 = new ArrayList<>();
		
		listaEgresos1.add(egreso1);
		listaEgresos1.add(egreso2);
		listaEgresos1.add(egreso3);
		
		List<Ingreso> listaIngresos1 = new ArrayList<>();
		
		listaIngresos1.add(ingreso1);
		listaIngresos1.add(ingreso2);
		listaIngresos1.add(ingreso3);
		
		List<OrdenDeCompra> listaOrdenes1 = new ArrayList<>();
		
		listaOrdenes1.add(ordenDeCompra);
		listaOrdenes1.add(ordenDeCompra2);
		
		//ASIGNANDO DOCS
		
		entidadJuridica.setEgresos(listaEgresos1);
		entidadJuridica.setIngresos(listaIngresos1);
		entidadJuridica.setOrdenesPendientes(listaOrdenes1);
		
		organizacion.agregarEntidad(entidadJuridica);

        //PERSISTIENDO

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(item1);
		EntityManagerHelper.getEntityManager().persist(item2);
		EntityManagerHelper.commit();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(medioDePago);
		EntityManagerHelper.getEntityManager().persist(medioDePago2);
		EntityManagerHelper.commit();

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
		EntityManagerHelper.persist(presupuesto2);
		EntityManagerHelper.commit();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(ordenDeCompra);
		EntityManagerHelper.getEntityManager().persist(ordenDeCompra2);
		EntityManagerHelper.commit();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(egreso1);
		EntityManagerHelper.persist(egreso2);
		EntityManagerHelper.persist(egreso3);
		EntityManagerHelper.commit();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(ingreso1);
		EntityManagerHelper.persist(ingreso2);
		EntityManagerHelper.persist(ingreso3);
		EntityManagerHelper.commit();
			
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(entidadJuridica);
		EntityManagerHelper.commit();

		
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(organizacion);
		EntityManagerHelper.commit();
		
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(userStandard);
		EntityManagerHelper.commit();
        
	}
	
	
	/*SCRIPT BORRAR TABLAS
	    Use gesoc;
        Drop TABLE gesoc.egreso_documentocomercial, gesoc.documentocomercial, gesoc.egreso,
        gesoc.ordendecompra_item, gesoc.presupuesto_item,gesoc.ordendecompra_presupuesto,
        gesoc.ordendecompra, gesoc.presupuesto, gesoc.item, gesoc.producto, gesoc.ingreso,gesoc.categoriadelsistema_categoriadelsistema,
        gesoc.categoriadelsistema, gesoc.proveedor,gesoc.mediodepago;
	*/
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