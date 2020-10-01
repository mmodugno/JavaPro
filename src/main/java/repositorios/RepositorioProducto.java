package repositorios;

import egreso.Egreso;
import producto.Producto;
import producto.TipoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioProducto {

    private static List<Producto> productos;

    public static List<Producto> getProductos() {
        return productos;
    }

    public RepositorioProducto() {

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

    public List<Producto> todos() {
        return productos;
    }

    public static Producto byID(int id) {

        Optional<Producto> producto = productos.stream().filter(e -> e.getIdProducto() == id).findFirst();

        if (producto.isPresent()) {
            return producto.get();
        }
        else return null;
    }

    int ordenarInt(int primero,int segundo){
        if (primero > segundo) return -1;
        if (primero < segundo) return 1;
        return 0;

    }

    public int proximoId(){
        productos.sort((Producto producto1, Producto producto2) -> {
            return ordenarInt(producto1.getIdProducto(),producto2.getIdProducto());
        });
        return productos.get(0).getIdProducto() + 1;
    }

    public static void agregar(Producto producto){
        productos.add(producto);
    }

    public static void modificar(Producto producto){

    }

    public static void eliminar(Producto producto){
        productos.remove(producto);
    }

}
