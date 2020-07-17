package Vinculador;

import egreso.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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


public class VincularTest {

    //PRODUCTOS-ITEM-PROVEEDOR
    Producto p1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
    Producto p2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
    Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
    Item item1 = new Item(p1, 1, 0.00);
    Item item2 = new Item(p2, 2, 0.00);

    //    CondicionValidacion condicionValidacion = new CondicionValidacion();
//    ElMasBarato elMasBarato = new ElMasBarato();
    OrdenDeCompra ordenDeCompra;

    //ORGANIZCION-USUARIO-PRESUPUESTO-MEDIO DE PAGO
    Organizacion primerOrganizacion = new Organizacion();

    EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());

    CreadorUsuario userMaker = new CreadorUsuario();
    Presupuesto presupuesto1;
    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
    Egreso egreso;

    //VINCULADOR
    Vinculador vinculador;
    CondicionPrecio condicionPrecio;
    CondicionFecha condicionFecha;
    List<CondicionObligatoria> condicionesObligatorias = new ArrayList<CondicionObligatoria>();
    PrimeroIngreso primeroIngreso;
    PrimeroEgreso primeroEgreso;
    Mix mix;

    //INGRESO
    Ingreso ingreso;

    //MAS INGRESOS Y EGRESOS
    OrdenDeCompra ordenDeCompra2;
    Presupuesto presupuesto2;
    Item item3 = new Item(p1, 10, 0.00);
    Item item4 = new Item(p2, 20, 0.00);
    OrdenDeCompra ordenDeCompra3;
    Presupuesto presupuesto3;
    Item item5 = new Item(p1, 50, 0.00);
    Egreso egreso2;
    Egreso egreso3;
    Ingreso ingreso2;
    Ingreso ingreso3;


    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {

        primerOrganizacion.agregarEntidad(entidadJuridica);

        ordenDeCompra = new OrdenDeCompra(0,5);
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);
        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.agregarPresupuesto(presupuesto1);
        presupuesto1.setAceptado();
        Usuario userAdmin = userMaker.crearUsuario("guidoAdmin", "pru3b@tesT", "admin",primerOrganizacion);
        ordenDeCompra.agregarRevisor(userAdmin);
        ordenDeCompra.getItems().get(0).setPrecioUnitario(80.00);
        ordenDeCompra.getItems().get(1).setPrecioUnitario(30.00);
        ordenDeCompra.cerrarOrden();
        //TODO ATENTOS QUE EN NINGUN MOMENTO AGREGO UNA ORDEN DE COMPRA A LA ORGANIZACION POR ESO NUNCA NOS TIRO ERROR NADA. (TAMPOCO LO PROBAMOS NNCA)
        primerOrganizacion.getEntidades().get(0).nuevoEgreso(ordenDeCompra); // Obtengo primera Entidad para agregarle los Egresos
        egreso = entidadJuridica.getEgresos().get(0);


        //INSTANCIO VINCULADOR
        condicionFecha = new CondicionFecha();
        condicionPrecio = new CondicionPrecio();
        condicionesObligatorias.add(condicionFecha);
        condicionesObligatorias.add(condicionPrecio);
        primeroEgreso = new PrimeroEgreso(condicionesObligatorias);
        primeroIngreso = new PrimeroIngreso(condicionesObligatorias);
        mix = new Mix(condicionesObligatorias);
        vinculador = new Vinculador(entidadJuridica);

        //INGRESOS
        ingreso = new Ingreso("Donacion",1000.0);



        entidadJuridica.getIngresos().add(ingreso);


        //EGRESO 2
        ordenDeCompra2 = new OrdenDeCompra(0,6);
        ordenDeCompra2.agregarItem(item3);
        ordenDeCompra2.agregarItem(item4);

        presupuesto2 = new Presupuesto(ordenDeCompra2.getItems(),proveedor1,medioDePago);
        presupuesto2.getItems().get(0).setPrecioUnitario(80.00);
        presupuesto2.getItems().get(1).setPrecioUnitario(60.00);
        ordenDeCompra2.agregarPresupuesto(presupuesto2);
        presupuesto2.setAceptado();
        ordenDeCompra2.getItems().get(0).setPrecioUnitario(80.00);
        ordenDeCompra2.getItems().get(1).setPrecioUnitario(60.00);
        ordenDeCompra.cerrarOrden();

        //EGRESO 3
        ordenDeCompra3 = new OrdenDeCompra(0,6);
        ordenDeCompra3.agregarItem(item5);

        presupuesto3 = new Presupuesto(ordenDeCompra2.getItems(),proveedor1,medioDePago);
        presupuesto3.getItems().get(0).setPrecioUnitario(100.00);
        ordenDeCompra3.agregarPresupuesto(presupuesto3);
        presupuesto3.setAceptado();
        ordenDeCompra3.getItems().get(0).setPrecioUnitario(100.00);
        ordenDeCompra.cerrarOrden();



    }

    @Test
    public void vincularIngresoEgreso(){
        vinculador.obtenerIngresosEgresos();
        Assert.assertEquals(0,ingreso.getEgresosAsociados().size());
        vinculador.vincular(primeroEgreso);
        Assert.assertEquals(1,ingreso.getEgresosAsociados().size());

        Assert.assertEquals(ingreso, egreso.getIngresoAsociado());

    }


    @Test
    public void criterioPrimeroEgreso()

    {
        //LOS NUEVOS INGRESOS
        ingreso2 = new Ingreso("Donacion",2000.0);
        ingreso3 = new Ingreso("Donacion",10000.0);
        entidadJuridica.getIngresos().add(ingreso3);
        entidadJuridica.getIngresos().add(ingreso2);

        //ACOMODO LOS EGRESOS
        primerOrganizacion.getEntidades().get(0).nuevoEgreso(ordenDeCompra2); // Obtengo primera Entidad para agregarle los Egresos
        egreso2 = entidadJuridica.getEgresos().get(1);
        primerOrganizacion.getEntidades().get(0).nuevoEgreso(ordenDeCompra3); // Obtengo primera Entidad para agregarle los Egresos
        egreso3 = entidadJuridica.getEgresos().get(2);

        vinculador.obtenerIngresosEgresos();
        vinculador.vincular(primeroEgreso);

        /*si ordena a MENOR A MAYOR egreso 3 a nadie
        egreso 3 null
        egreso = ingreso
        egreso2 = ingreso2
        ingreso 3 no le aueda nada*/
        
        /* --------- 
         * List<Ingreso> ingresos = { 1000.0, 10000.0, 2000.0 }
         * List<Egreso> egresos = { 140, 2000.0, 5000.0 } 
         * Ingresos 3 : 2000.0 
         * 
         * Vincula Ingreso 1000 con Egreso 140
         * Vincula Ingreso 2000 con Egreso 2000
         * Vincula Ingreso 10000 con Egreso 5000
         * */
        
        Assert.assertEquals(ingreso, egreso.getIngresoAsociado());
        Assert.assertEquals(ingreso2, egreso2.getIngresoAsociado());
        Assert.assertEquals(ingreso3, egreso3.getIngresoAsociado());
        Assert.assertEquals(1,ingreso3.getEgresosAsociados().size());
        //Assert.assertTrue(egreso3.getIngresoAsociado() == null); //TENDRIAMOS QUE TENER UN EGRESO/INGRESO EQUIVALENTE A NULL

    }



    }


