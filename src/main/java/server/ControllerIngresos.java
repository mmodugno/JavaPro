package server;

import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioIngreso;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import egreso.Ingreso;

public class ControllerIngresos {

    private static RepositorioIngreso repo;

    public ControllerIngresos() {
        repo = new RepositorioIngreso();
    }

 

    public ModelAndView nuevoIngreso(Request request, Response response){
    	
    	RepositorioIngreso repo = new RepositorioIngreso();

        //DOMINIO
        List<RepositorioIngreso> repos = new ArrayList<>();
        
        repos.add(repo);
        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("repos", repo);
    	
   
        return new ModelAndView(map,"formularioIngresos.html");
    }

    
}
