package seguridad;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.List;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import org.junit.Before;
import organizacion.Organizacion;
import producto.Item;
import producto.Producto;
import producto.TipoItem;

import org.junit.Assert;
import org.junit.Test;
import usuarios.*;

public class CrearUsuarioTest {
	
	CreadorUsuario userMaker = new CreadorUsuario();
	Organizacion organizacion = new Organizacion();
	
	@Test
	public void crearUserAdminCorrecto() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin", organizacion);
	    Assert.assertTrue(userAdmin.creadoConExito());
	}
	
	@Test
	public void crearUserStandardCorrecto() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		Usuario userStandard = userMaker.crearUsuario("guidoEstandard", "pru3b@tesT", "admin", organizacion);
	    Assert.assertTrue(userStandard.creadoConExito());
	}
	
	
	@Test
	public void crearUserStandardIncorrectoLanzaExcepcion() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		try{
			Usuario userStandard = userMaker.crearUsuario("guidoEstandard", "pepitaLaPistolera", "admin", organizacion);
			fail("Se esperaba la excepcion CreationError");
			} catch(CreationError e){}
	}
	
	@Test
	public void usuarioSuscriptoConExito() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",organizacion);
		Producto p1 = new Producto(1,"Yerba","yerba Marolio", TipoItem.ARTICULO);
		Producto p2 = new Producto(2,"Azucar", "azucar ledesma", TipoItem.ARTICULO);
		Item item1 = new Item(p1, 1, 80);
		Item item2 = new Item(p2,2, 90);

		OrdenDeCompra ordenDeCompra = new OrdenDeCompra(0);

		ordenDeCompra.agregarItem(item1);
		ordenDeCompra.agregarItem(item2);

		userAdmin.suscribirse(ordenDeCompra);

		assertEquals(1,ordenDeCompra.getRevisores().size());		
	}
	
	@Test
	public void nuevoUsuarioSinSuscripciones() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		Usuario userStandard = userMaker.crearUsuario("guidoEstandard", "pru3b@tesT", "admin", organizacion);
		
		assertEquals(0, userStandard.getSuscripciones().size());
	}
	/*
	@Test
	public void noSePuedeModificarUnaOrdenDeCompra() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",organizacion);
		Producto p1 = new Producto(1,"Yerba","yerba Marolio", TipoItem.ARTICULO);
		Producto p2 = new Producto(2,"Azucar", "azucar ledesma", TipoItem.ARTICULO);
		Item item1 = new Item(p1, 1, 80);
		Item item2 = new Item(p2,2, 90);
		OrdenDeCompra ordenDeCompra = new OrdenDeCompra(0, 1);

		ordenDeCompra.agregarItem(item1);
		ordenDeCompra.agregarItem(item2);
		
		
	} */

}