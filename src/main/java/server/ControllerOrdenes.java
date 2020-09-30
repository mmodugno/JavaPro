package server;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioOrdenDeCompra;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControllerOrdenes {

    RepositorioOrdenDeCompra repo = null;

    public ControllerOrdenes() throws CloneNotSupportedException {
        repo = new RepositorioOrdenDeCompra();
    }

    public ModelAndView ordenes(Request request, Response response) throws CloneNotSupportedException {

        //DOMINIO
        List<OrdenDeCompra> ordenes = repo.todos();
        /** **PARA PROBAR SI ANDA EL CERRAR** **/
        //repo.todos().get(0).setCerrado(true);
        /** No las marca como cerrada si o no porque técnicamente las creamos de 0 y no son creadas con el egreso.
         Tener en cuenta a la hora de probar la funcionalidad completa **/

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);

        return new ModelAndView(map, "ordenes.html");
    }

    public ModelAndView nuevaOrden(Request request, Response response){
        return new ModelAndView(null,"formularioOrden.html");
    }

    public Response crear(Request request, Response response) throws ParseException {
    	OrdenDeCompra nuevaOrden = new OrdenDeCompra();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	
    	nuevaOrden.setIdOrden(repo.proximoId());
    	nuevaOrden.setNecesitaPresupuesto(Integer.parseInt(request.queryParams("presupuesto")));
    	
    	//nuevaOrden.agregarItem(item);
    	
    	String fecha =  request.queryParams("fecha");   	
    	Date fechaFinal = sdf.parse(fecha);
    	nuevaOrden.setFecha(fechaFinal);
    	
    	repo.agregar(nuevaOrden);
    	
    	response.redirect("/ordenes");
    	
    	
    	return response;
    }
    
    
    
    
   
    
    

}
