package server;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioCategoria;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioPresupuesto;
import repositorios.RepositorioProducto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;



public class Server {

    private static ControllerProductos controllerProductos= new ControllerProductos();
    private static ControllerEgresos controllerEgresos= new ControllerEgresos();

    public static void main(String[] args) {
        enableDebugScreen();
        port(1133);
        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/resources");
        }


        // Ejemplo de acceso: http://localhost:9000/inicio
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();


        //get("/hola",((request, response) -> "Yoel"));
        get("/inicio", Server::mostrarIndex, engine);
        get("/login", Server::login, engine);
        get("/egresos", Server::egresos, engine);
        get("/egreso/:id", Server::detalleEgreso, engine);
        get("/crearEgreso", Server::crearEgreso, engine);
        //get("/modificarEgreso", Server::modificarEgreso, engine);
        get("/categorias", Server::mostrarCategorias, engine);
        
        post("/egreso",controllerEgresos::guardarEgreso);
        //acciones productos
        get("/productos",controllerProductos::productos,engine);
        get("/producto", controllerProductos::nuevoProducto, engine);
        get( "/producto/:id", controllerProductos::detalleProducto, engine);
        post("/producto", controllerProductos::guardarProducto);
        post("/producto/:id",controllerProductos::modificarProducto);
        delete("/producto/:id",controllerProductos::eliminarProducto);

    }

    public static ModelAndView mostrarIndex(Request request, Response response) {
        return new ModelAndView(null, "index.html");
    }

    public static ModelAndView login(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }

    public static ModelAndView egresos(Request request, Response response) throws CloneNotSupportedException {

        //INIT
        RepositorioEgreso repo = new RepositorioEgreso();

        //DOMINIO
        List<Egreso> egresos = repo.todos();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("egresos", egresos);

        return new ModelAndView(map, "egresos.html");
    }
    

 /*   private static void asignarParametrosEgreso(Egreso egreso,Request request) {

      /*  producto.setCodProducto(new Integer(request.queryParams("codigo")));
        producto.setNombre(request.queryParams("nombre"));
        producto.setDescripcion(request.queryParams("descripcion"));


        if(request.queryParams("opciones").equals("Articulo")){
            producto.setTipoProducto(TipoItem.ARTICULO);
        }
        else if(request.queryParams("opciones").equals("Servicio")){
            producto.setTipoProducto(TipoItem.SERVICIO);
        }*/
    
    

    
    public static ModelAndView crearEgreso(Request request, Response response){
        return new ModelAndView(null,"formularioEgresos.html");
    }
    public static ModelAndView detalleEgreso(Request request, Response response) throws CloneNotSupportedException{
    	
    	RepositorioEgreso repo = new RepositorioEgreso();
    	
    	String strID = request.params("id");
    	
    	int id = Integer.parseInt(strID);
    	
    	Egreso egreso = repo.byID(id);
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("egreso", egreso);
    	
        return new ModelAndView(map,"detalleEgreso.html");
    }

    public static ModelAndView mostrarCategorias(Request request, Response response) {
        return new ModelAndView(null, "categorias.html");
    }

    //PRODUCTOS






    
}