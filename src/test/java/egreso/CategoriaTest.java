package egreso;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import organizacion.Organizacion;
import producto.Item;
import producto.Proveedor;
import usuarios.Categoria;
import usuarios.CategoriaDelSistema;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;
import usuarios.UsuarioAdministrador;
import usuarios.UsuarioEstandard;

public class CategoriaTest {

	CreadorUsuario userMaker = new CreadorUsuario();
	Organizacion organizacion = new Organizacion();
	UsuarioAdministrador userAdmin;
	UsuarioEstandard userStandard;
	Categoria argentina;
	Presupuesto presupuesto;
	Presupuesto presupuesto2;
	OrdenDeCompra ordenDeCompra;
	
	Item item1 = new Item(null, 1, 0.00);


	 
	 @Before
	    public void init() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException, CloneNotSupportedException   {
		 
		ordenDeCompra = new OrdenDeCompra(1,5);
	    ordenDeCompra.agregarItem(item1);
	    
		userAdmin = new UsuarioAdministrador("usuarioAdmin","admin",organizacion,true);
			
		userStandard = new UsuarioEstandard ("usuarioStandard","standard",organizacion,true);
		
	    presupuesto = new Presupuesto(ordenDeCompra.getItems(),null,null);
			
		argentina = new Categoria("argentina","pais");
			
	    }
	 
	  @Test
	    public void sePuedeCategorizarUnCategorizable() {
		  
		  userStandard.categorizar(presupuesto,argentina);
		  
		  Assert.assertEquals(argentina,presupuesto.getCategoria());
	    }
	 
	 @Test
	 public void usuarioAdminPuedeCrearCategoria() {
		 
		userAdmin.crearCategoria("brasil","pais");
		 
		Assert.assertEquals(organizacion.getCategorias().size() > 0,true);
		 
		 
	 }
	 
	 @Test
	 public void usuarioAdminPuedeCrearCategoriaCompuesta() {
		 
		List<CategoriaDelSistema> listaSubCategorias = new ArrayList<CategoriaDelSistema>();
		
		listaSubCategorias.add(new Categoria("caba","provincia"));
		listaSubCategorias.add(new Categoria("Mendoza","provincia"));
		
		
		userAdmin.crearCategoria("brasil","pais");
		userAdmin.crearCategoriaCompuesta("argentina","provincia",listaSubCategorias);
		 
		Assert.assertEquals(organizacion.getCategorias().size() > 1,true);
		 
		 
	 }
	 
	 
	 
	 
}
