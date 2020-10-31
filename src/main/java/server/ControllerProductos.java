package server;

import org.apache.maven.artifact.repository.metadata.RepositoryMetadataStoreException;
import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioProducto;
import spark.*;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerProductos {


    public ControllerProductos(){}


    public ModelAndView productos(Request request, Response response, EntityManager entityManager){
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
        RepositorioProducto repositorio = new RepositorioProducto(entityManager);
        //DOMINIO
        List<Producto> productos = repositorio.todos();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("productos", productos);
        map.put("usuario", request.session().attribute("user"));

        return new ModelAndView(map, "productos.html");

    }

    public ModelAndView nuevoProducto(Request request, Response response){
        return new ModelAndView(null,"nuevoProducto.html");
    }

    public ModelAndView detalleProducto(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException{

        RepositorioProducto repositorio = new RepositorioProducto(entityManager);
        String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Producto producto = repositorio.byID(id);

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

    public Response guardarProducto(Request request, Response response, EntityManager entityManager){
        Producto producto = new Producto();
        RepositorioProducto repositorio = new RepositorioProducto(entityManager);

        asignarParametrosProducto(producto, request);

        repositorio.agregar(producto);
        producto.setTipoProducto(TipoItem.ARTICULO);

        response.redirect("/productos");

        return response;
    }

    public Response modificarProducto(Request request, Response response, EntityManager entityManager){

        RepositorioProducto repositorio = new RepositorioProducto(entityManager);
        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repositorio.byID(id);

        asignarParametrosProducto(producto, request);

        response.redirect("/productos");

        return response;
    }

    public Response eliminarProducto(Request request, Response response, EntityManager entityManager){

        RepositorioProducto repositorio = new RepositorioProducto(entityManager);

        String strID = request.params("id");
        int id = new Integer(strID);

        repositorio.eliminar(id);


        return response;
    }


}
