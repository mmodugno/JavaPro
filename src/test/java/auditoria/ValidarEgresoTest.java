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
    OrdenDeCompra ordenDeCompra2;

    Organizacion primerONG = new Organizacion();
    
    EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
    
    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    Presupuesto presupuesto3;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    
    Validador validador;
    
    Egreso egreso;
 
    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {
    	
    	primerONG.agregarEntidad(entidadJuridica);
    	
    	ordenDeCompra = new OrdenDeCompra(1,1);
    	ordenDeCompra2 = new OrdenDeCompra(3,2); // Orden De Compra con 3 Presupuestos
    	ordenDeCompra.setCriterioSeleccion(new ElMasBarato());
    	ordenDeCompra2.setCriterioSeleccion(new ElMasBarato());
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        ordenDeCompra2.agregarItem(item1);
        ordenDeCompra2.agregarItem(item2);
        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        ordenDeCompra2.agregarPresupuesto(presupuesto1);
        presupuesto1.setAceptado();
        ordenDeCompra.getItems().get(0).setPrecioUnitario(80.00);
        ordenDeCompra.getItems().get(1).setPrecioUnitario(30.00);
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",primerONG);
        ordenDeCompra.agregarRevisor(userAdmin);
        ordenDeCompra2.agregarRevisor(userAdmin);
        
        presupuesto2 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto2.getItems().get(0).setPrecioUnitario(90.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(40.00);
        
        presupuesto3 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto3.getItems().get(0).setPrecioUnitario(70.00);
        presupuesto3.getItems().get(1).setPrecioUnitario(20.00);
        
        // Se instancia un Validador
        
        validador = new Validador();
        validador.agregarCondicionValidacion(new CantidadPresupuestos());
        validador.agregarCondicionValidacion(new MontoPresupuesto());
        validador.agregarCondicionValidacion(new Criterios());
        validador.agregarCondicionValidacion(new Items());
    }
    
    @Test
    public void validarEgresoOK() throws CloneNotSupportedException {
    	
    	ordenDeCompra.cerrarOrden();
    	
    	egreso = new Egreso(ordenDeCompra, presupuesto1);
    	
    	Assert.assertTrue(validador.validarEgreso(egreso));

    }
    
    @Test
    public void validarOrdenCompraConMenosPresupuestos() throws CloneNotSupportedException {
    	
    	ordenDeCompra2.cerrarOrden();
    	
    	egreso = new Egreso(ordenDeCompra2, presupuesto1);
    	
    	Assert.assertFalse(validador.validarEgreso(egreso));
    	
    }
    
    @Test
    public void validarMontosDiferentesOrdenCompraPresupuestoAceptado() throws CloneNotSupportedException {
    	
    	ordenDeCompra.getItems().get(0).setPrecioUnitario(180.00);
        ordenDeCompra.getItems().get(1).setPrecioUnitario(300.00);
    	
    	ordenDeCompra.cerrarOrden();
    	
    	egreso = new Egreso(ordenDeCompra, presupuesto1);
    	
    	Assert.assertFalse(validador.validarEgreso(egreso));

    }
    
    @Test
    public void validarPresupuestoAceptadoDiferentePresupuestoValidado() throws CloneNotSupportedException {
    	
    	ordenDeCompra.agregarPresupuesto(presupuesto2);
    	ordenDeCompra.agregarPresupuesto(presupuesto3);
    	
    	ordenDeCompra.cerrarOrden();
    	
    	egreso = new Egreso(ordenDeCompra, presupuesto1);
    	
    	Assert.assertFalse(validador.validarEgreso(egreso));

    }
    
}
