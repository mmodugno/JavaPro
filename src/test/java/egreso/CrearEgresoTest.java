package egreso;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import organizacion.Organizacion;
import producto.*;
import producto.TipoItem;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class CrearEgresoTest {
//PRODUCTOS-ITEM-PROVEEDOR
    Producto p1 = new Producto(1,"Televisor", "Televisor", TipoItem.ARTICULO);
    Producto p2 = new Producto(2,"Tostadora", "Tostadora", TipoItem.ARTICULO);
    Proveedor proveedor1 = new Proveedor("carlos","22412145696", "6725");
    Proveedor proveedor2 = new Proveedor("Juan","21123214569","1419");
    Item item1 = new Item(p1, 1,0.00);
    Item item2 = new Item(p2,2,0.00);

    CondicionValidacion condicionValidacion = new CondicionValidacion();
    ElMasBarato elMasBarato = new ElMasBarato();
    OrdenDeCompra ordenDeCompra;
    Validador validador;

    //ORGANIZCION-USUARIO-PRESUPUESTO-MEDIO DE PAGO
    Organizacion organizacion = new Organizacion();
    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    
 
    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
        ordenDeCompra = new OrdenDeCompra(1,5);
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        validador = new Validador(condicionValidacion, elMasBarato);
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",organizacion);
        ordenDeCompra.agregarRevisor(userAdmin);
    }
    
    @Test
   
    public void presupuestoYorden() throws CloneNotSupportedException {
    	

    	presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
    	
    	Assert.assertTrue(condicionValidacion.presupuestoCorrecto(ordenDeCompra,presupuesto1));
    }
    
    //TODO terminar de revisar este test
    /*
    @Test
    public void validarConPresupuesto() throws ErrorDeValidacion{


        
        presupuesto1 = new Presupuesto(ordenDeCompra,proveedor1,medioDePago);
        presupuesto1.agregarItem(item1);
        presupuesto1.agregarItem(item2);

        presupuesto2 = new Presupuesto(ordenDeCompra,proveedor2,medioDePago);

        
        Egreso egreso2 = new Egreso(ordenDeCompra, presupuesto2);
 
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        ordenDeCompra.agregarPresupuesto(presupuesto2);

        Assert.assertEquals(2,ordenDeCompra.getPresupuestos().size());
        Assert.assertEquals(0, organizacion.getEgresos().size());
        
        validador.validarOrden(ordenDeCompra);
        
        Assert.assertEquals(1, organizacion.getEgresos().size());

    }*/

    @Test
    public void validacionMala() throws ErrorDeValidacion {

        try {
            validador.validarOrden(ordenDeCompra);
            fail("Se esperaba la excepcion ErrorDeValidacion");
        } catch (ErrorDeValidacion e) {
        }
    }
}
