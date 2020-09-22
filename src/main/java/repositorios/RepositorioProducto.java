package repositorios;

import egreso.Egreso;
import producto.Producto;
import producto.TipoItem;

import java.util.ArrayList;
import java.util.List;

public class RepositorioProducto {

    List<Producto> productos = null;

    public RepositorioProducto() {

        if (productos == null) {
            Producto producto1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
            Producto producto2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
            Producto producto3 = new Producto(3,"Office", "Office365",TipoItem.SERVICIO);

            productos = new ArrayList<>();
            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
        }
    }

    public List<Producto> todos() {
        return new ArrayList<>(productos);
    }
}
