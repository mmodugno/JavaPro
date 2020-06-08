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

import static org.junit.Assert.fail;

public class CrearEgresoTest {

    Producto p1 = new Producto(1,"Yerba","yerba NoblezaGaucha", 80.0, TipoItem.ARTICULO);
    Producto p2 = new Producto(2,"Azucar", "azucar ledesma",55.50,TipoItem.ARTICULO);
    Producto p11 = new Producto(11,"Yerba","yerba Playadito", 100.0, TipoItem.ARTICULO);
    Producto p22 = new Producto(22,"Azucar", "azucar chango",60.0,TipoItem.ARTICULO);
    Proveedor proveedor1 = new Proveedor("carlos","22412145696", "6725");
    Proveedor proveedor2 = new Proveedor("Juan","21123214569","1419");



    Item item1 = new Item(p1, 1);
    Item item2 = new Item(p2,2);
    Item item11 = new Item(p11, 1);
    Item item22 = new Item(p22,2);

    CondicionValidacion condicionValidacion = new CondicionValidacion();
    ElMasBarato elMasBarato = new ElMasBarato();
    OrdenDeCompra ordenDeCompra;
    Validador validador;
    Organizacion organizacion = new Organizacion();
    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);


    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
        ordenDeCompra = new OrdenDeCompra(1);
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        validador = new Validador(condicionValidacion, elMasBarato);
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",organizacion);
        ordenDeCompra.agregarRevisor(userAdmin);



    }

    @Test
    public void validarConPresupuesto() throws ErrorDeValidacion{
        proveedor1.agregarProductos(p1);
        proveedor1.agregarProductos(p2);
        proveedor2.agregarProductos(p11);
        proveedor2.agregarProductos(p22);

        presupuesto1 = new Presupuesto(ordenDeCompra,proveedor1,medioDePago);
        presupuesto1.agregarItem(item1);
        presupuesto1.agregarItem(item2);

        presupuesto2 = new Presupuesto(ordenDeCompra,proveedor2,medioDePago);
        presupuesto2.agregarItem(item11);
        presupuesto2.agregarItem(item22);

        ordenDeCompra.agregarPresupuesto(presupuesto1);
        ordenDeCompra.agregarPresupuesto(presupuesto2);

        Assert.assertEquals(2,ordenDeCompra.getPresupuestos().size());

        Assert.assertEquals(0, organizacion.getEgresos().size());
        validador.validarOrden(ordenDeCompra);
        Assert.assertEquals(1, organizacion.getEgresos().size());

    }

    @Test
    public void validacionMala() throws ErrorDeValidacion {

        try {
            validador.validarOrden(ordenDeCompra);
            fail("Se esperaba la excepcion ErrorDeValidacion");
        } catch (ErrorDeValidacion e) {
        }
    }
}
