package server;

import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioProducto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControllerProductos {

    private static RepositorioProducto repo;

    public ControllerProductos() {
        repo = new RepositorioProducto();
    }

    public ModelAndView productos(Request request, Response response){

        //DOMINIO
        List<Producto> productos = repo.todos();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("productos", productos);

        return new ModelAndView(map, "productos.html");

    }

    public ModelAndView nuevoProducto(Request request, Response response){
        return new ModelAndView(null,"nuevoProducto.html");
    }

    public ModelAndView detalleProducto(Request request, Response response) throws CloneNotSupportedException{


        String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Producto producto = repo.byID(id);

        Map<String, Object> map = new HashMap<>();
        map.put("producto", producto);


        return new ModelAndView(map,"nuevoProducto.html");
    }


    private static void asignarParametrosProducto(Producto producto,Request request) {



        producto.setCodProducto(new Integer(request.queryParams("codigo")));
        producto.setNombre(request.queryParams("nombre"));
        producto.setDescripcion(request.queryParams("descripcion"));


        if(request.queryParams("opciones").equals("Articulo")){
            producto.setTipoProducto(TipoItem.ARTICULO);
        }
        else if(request.queryParams("opciones").equals("Servicio")){
            producto.setTipoProducto(TipoItem.SERVICIO);
        }

    }

    public Response guardarProducto(Request request, Response response){
        Producto producto = new Producto();
        producto.setIdProducto(repo.proximoId());

        asignarParametrosProducto(producto, request);

        repo.agregar(producto);
        producto.setTipoProducto(TipoItem.ARTICULO);

        response.redirect("/productos");

        return response;
    }

    public Response modificarProducto(Request request, Response response){

        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repo.byID(id);

        asignarParametrosProducto(producto, request);

        repo.modificar(producto);//Esto va a hacer algo cuando tengamos la base

        response.redirect("/productos");

        return response;
    }

    public Response eliminarProducto(Request request, Response response){

        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repo.byID(id);

        repo.eliminar(producto);

        //response.redirect("/productos");

        return response;
    }
}
