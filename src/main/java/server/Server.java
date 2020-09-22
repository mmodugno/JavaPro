package server;

import egreso.Egreso;
import producto.Producto;
import repositorios.RepositorioEgreso;
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


    public static void main(String[] args) {
        enableDebugScreen();
        port(9000);
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
        get("/egresos.html", Server::egresos, engine);
        get("/egreso/:id", Server::detalleEgreso, engine);
        get("/crearEgreso.html", Server::crearEgreso, engine);
        get("/modificarEgreso", Server::modificarEgreso, engine);
        get("/categorias", Server::mostrarCategorias, engine);


        get("/productos",Server::productos,engine);
        get("/CrearProducto", Server::nuevoProducto, engine);
        get( "/productos /:id", Server::detalleProducto, engine);

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

    public static ModelAndView crearEgreso(Request request, Response response) {
        return new ModelAndView(null, "formularioEgresos.html");
    }

    public static ModelAndView detalleEgreso(Request request, Response response) {
        return new ModelAndView(null, "detalleEgreso.html");
    }

    public static ModelAndView modificarEgreso(Request request, Response response) {
        return new ModelAndView(null, "formularioEgresos.html");
    }

    public static ModelAndView mostrarCategorias(Request request, Response response) {
        return new ModelAndView(null, "categorias.html");
    }

    //PRODUCTOS

    public static ModelAndView productos(Request request, Response response){

        //INIT
        RepositorioProducto repo = new RepositorioProducto();

        //DOMINIO
        List<Producto> productos = repo.todos();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("productos", productos);

        return new ModelAndView(map, "productos.html");

    }
    public static ModelAndView detalleProducto(Request request, Response response){
    return new ModelAndView(null,"detalleProducto.html");}

    public static ModelAndView nuevoProducto(Request request, Response response){
        return new ModelAndView(null,"nuevoProducto.html");
    }




    
}