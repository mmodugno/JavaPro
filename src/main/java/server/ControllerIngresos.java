package server;

import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioCategoria;
import repositorios.RepositorioIngreso;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioPresupuesto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

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
    
    public static void asignarParametros(Ingreso ingreso, Request request) throws CloneNotSupportedException {

		
		String descripcion = request.queryParams("descripcion");
		String montoString = request.queryParams("monto");
		
	
		int monto = Integer.parseInt(montoString);
		
		
		ingreso.setDescripcion(descripcion);
		ingreso.setMonto(monto);
		
	}
    
    
    public Response guardarIngreso(Request request, Response response) throws CloneNotSupportedException{

        Ingreso ingreso= new Ingreso();
        ingreso.setId(repo.proximoId());

		asignarParametros(ingreso, request);
		ingreso.setFecha(LocalDate.now());
		
        repo.crear(ingreso);
        
        response.redirect("/ingresos");

        return response;
    }

    

	public Response eliminarIngreso(Request request, Response response){

		String strID = request.params("id");
		int id = new Integer(strID);
		
		Ingreso ingreso= repo.byID(id);
		repo.borrar(ingreso.getDescripcion());
		
		//Borra segun la descripcion:
		String desc = request.params("descripcion");
		repo.borrar(desc);
		
		response.redirect("/ingresos");
		
		return response;
	}
	
	public ModelAndView modificarIngreso(Request request, Response response) throws CloneNotSupportedException {

		String strID = request.params("id");
		
		int id = Integer.parseInt(strID);
		
		Ingreso ingreso = repo.byID(id);

		
		Map<String, Object> map = new HashMap<>();
		map.put("ingreso", ingreso);

		return new ModelAndView(map,"formularioIngresos.html");
		
	}
	
	public Response persistirIngreso(Request request, Response response) throws CloneNotSupportedException {
		
		String strID = request.params("id");
		
		int id = Integer.parseInt(strID);
		
		Ingreso ingreso = repo.byID(id);

		asignarParametros(ingreso, request);

		response.redirect("/ingresos");

		return response;

	}
	
	

}
