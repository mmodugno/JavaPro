package egreso;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import organizacion.*;
import producto.*;
import producto.TipoItem;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

//    CondicionValidacion condicionValidacion = new CondicionValidacion();
//    ElMasBarato elMasBarato = new ElMasBarato();
    OrdenDeCompra ordenDeCompra;

    //ORGANIZCION-USUARIO-PRESUPUESTO-MEDIO DE PAGO
    Organizacion primerOrganizacion = new Organizacion();
    
    EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
	
    
    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    
 
    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {
    	
    	primerOrganizacion.agregarEntidad(entidadJuridica);
    	
    	ordenDeCompra = new OrdenDeCompra(1,5);
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        presupuesto1.setAceptado();
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",primerOrganizacion);
        ordenDeCompra.agregarRevisor(userAdmin);
    }
    
    @Test
    public void crearEgreso() throws CloneNotSupportedException {
    	
    	ordenDeCompra.cerrarOrden();
    	primerOrganizacion.getEntidades().get(0).nuevoEgreso(ordenDeCompra); ; // Obtengo primera Entidad para agregarle los Egresos
    	
    	Assert.assertEquals(110.00, primerOrganizacion.getEntidades().get(0).getEgresos().get(0).valorTotal(), 0.1);
    }
    
}
