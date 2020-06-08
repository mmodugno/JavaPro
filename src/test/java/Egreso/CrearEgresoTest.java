package Egreso;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import org.junit.Before;
import producto.Item;
import producto.*;
import producto.TipoItem;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrearEgresoTest {

    Producto p1 = new Producto(1,"Yerba","yerba Marolio", 80.0, TipoItem.ARTICULO);
    Producto p2 = new Producto(2,"Azucar", "azucar ledesma",55.50,TipoItem.ARTICULO);
    Item item1 = new Item(p1, 1);
    Item item2 = new Item(p2,2);

    OrdenDeCompra ordenDeCompra = new OrdenDeCompra(0);


    @Before
    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
        ordenDeCompra.agregarItem(item1);
        ordenDeCompra.agregarItem(item2);

    }




}
