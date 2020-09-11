package server;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Server {




    public static void main(String[] args) {
        enableDebugScreen();
        port(4567);
        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }

        // Ejemplo de acceso: http://localhost:4567/auto
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        
    }
}