package auditoria;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import egreso.Egreso;
import egreso.ElMasBarato;
import egreso.MedioDePago;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import egreso.TipoMedioPago;
import organizacion.EntidadJuridica;
import organizacion.Organizacion;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

public class ValidarEgresoTest {
	
    Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
    Producto producto2 = new Producto(1,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
    Producto producto3 = new Producto(1,"Impresora", "Impresora HP", TipoItem.ARTICULO);
    Producto producto4 = new Producto(2,"Impresora", "Impresora Brother", TipoItem.ARTICULO);
    Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
    Proveedor proveedor2 = new Proveedor("Juan Computación","21123214569","1419");
    Item item1 = new Item(producto1, 2, 0.00);
    Item item2 = new Item(producto2, 3, 0.00);
    Item item3 = new Item(producto3, 1, 0.00);
    Item item4 = new Item(producto4, 1, 0.00);

    OrdenDeCompra ordenDeCompra;

    Organizacion primerONG = new Organizacion();
    
    EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
    
    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    
    Validador validador;
    
    Egreso egreso;
 
    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {
    	
    	primerONG.agregarEntidad(entidadJuridica);
    	
    	ordenDeCompra = new OrdenDeCompra(1,5);
    	ordenDeCompra.setCriterioSeleccion(new ElMasBarato() );
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        presupuesto1.setAceptado();
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",primerONG);
        ordenDeCompra.agregarRevisor(userAdmin);
        
        // Se instancia un Validador
        
        validador = new Validador();
        validador.agregarCondicionValidacion(new NecesitaPresupuesto());
        validador.agregarCondicionValidacion(new CantidadPresupuestos());
        validador.agregarCondicionValidacion(new Criterios());
        validador.agregarCondicionValidacion(new Items());
    }
    
    @Test
    public void validarEgresoOK() throws CloneNotSupportedException {
    	
    	ordenDeCompra.cerrarOrden();
    	
    	egreso = new Egreso(ordenDeCompra, presupuesto1);
    	
    	Assert.assertTrue(validador.validarEgreso(egreso));
    	
    	//ordenDeCompra.getItems().get(1).setPrecioUnitario(30.00);
    	//primerOrganizacion.getEntidades().get(0).nuevoEgreso(ordenDeCompra);
    	//Assert.assertEquals(80.00, primerOrganizacion.getEntidades().get(0).getEgresos().get(0).getOrdenDeCompra().getItems().get(0).getPrecioUnitario(), 0.1);
    }
    
}
