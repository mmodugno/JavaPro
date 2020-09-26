package repositorios;

import egreso.Egreso;
import producto.Producto;
import producto.TipoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioProducto {

    List<Producto> productos = null;

    public RepositorioProducto() {

        if (productos == null) {
            Producto producto1 = new Producto(1, "Monitor", "Monitor 32", TipoItem.ARTICULO);
            Producto producto2 = new Producto(2, "Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
            Producto producto3 = new Producto(3, "Office", "Office365", TipoItem.SERVICIO);
            producto1.setIdProducto(1231);
            producto2.setIdProducto(3445);
            producto3.setIdProducto(6621);
            productos = new ArrayList<>();
            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
        }
    }

    public List<Producto> todos() {
        return new ArrayList<>(productos);
    }

    public Producto byID(int id) {
        Optional<Producto> producto = productos.stream().filter(e -> e.getIdProducto() == id).findFirst();

        if (producto.isPresent()) {
            return producto.get();
        }
        else return null;
    }
}
