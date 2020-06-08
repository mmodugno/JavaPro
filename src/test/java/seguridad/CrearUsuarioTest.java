package seguridad;
import static org.junit.Assert.fail;

import java.awt.List;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Item;
import producto.Producto;
import producto.TipoItem;

import org.junit.Assert;
import org.junit.Test;
import usuarios.*;

public class CrearUsuarioTest {
	
	CreadorUsuario userMaker = new CreadorUsuario();
	
	@Test
	public void crearUserAdminCorrecto() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin");
	    Assert.assertTrue(userAdmin.creadoConExito());
	}
	
	@Test
	public void crearUserStandardCorrecto() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		Usuario userStandard = userMaker.crearUsuario("guidoEstandard", "pru3b@tesT", "admin");
	    Assert.assertTrue(userStandard.creadoConExito());
	}
	
	
	@Test
	public void crearUserStandardIncorrectoLanzaExcepcion() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException{
		try{
			Usuario userStandard = userMaker.crearUsuario("guidoEstandard", "pepitaLaPistolera", "admin");
			fail("Se esperaba la excepcion CreationError");
			} catch(CreationError e){}
	}
	
	/*
	public void usuarioSuscriptoConExito() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		Usuario usuarioPrueba = userMaker.crearUsuario("guidoAdministrador", "prue3b@tesT", "admin");
		Producto unProducto = new Producto(1, "lavandina", "cosmetico", 55.0, TipoItem.ARTICULO);
		Producto otroProducto = new Producto(23, "instalarWIFI", "otros", 951324.0, TipoItem.SERVICIO);
		Item unItem = new Item(unProducto, 2);
		Item otroItem = new Item(otroProducto, 1);
		
		
		
	}
	*/
}