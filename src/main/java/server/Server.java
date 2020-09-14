package server;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

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

        // Ejemplo de acceso: http://localhost:9000/auto
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        get("/hola",((request, response) -> "Yoel"));
        get("/inicio",Server::mostrarIndex, engine );
        get("/login" ,Server::login, engine);

    }

    public static ModelAndView  mostrarIndex(Request request, Response response){
        return new ModelAndView(null,"index.html");
    }

    public static ModelAndView  login(Request request, Response response){
        return new ModelAndView(null,"login.html");
    }
}