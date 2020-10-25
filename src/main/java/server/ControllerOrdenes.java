package server;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Producto;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioProducto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;

import javax.persistence.EntityManager;

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

    public ModelAndView nuevaOrden(Request request, Response response, EntityManager entityManager){


		RepositorioProducto repoPro = new RepositorioProducto(entityManager);
        Map<String, Object> map = new HashMap<>();
        List<Producto> productos = repoPro.todos();
        map.put("productos", productos);
        return new ModelAndView(map,"formularioOrden.html");
    }

    public Response crear(Request request, Response response) throws ParseException {
    	OrdenDeCompra nuevaOrden = new OrdenDeCompra();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	  	
    	
       	
    	nuevaOrden.setIdOrden(repo.proximoId());
    	nuevaOrden.setNecesitaPresupuesto(Integer.parseInt(request.queryParams("presupuesto")));

    	//nuevaOrden.agregarItem(item);
    	
    	String fecha =  request.queryParams("fecha");   	
    	LocalDate fechaFinal = LocalDate.parse(fecha);
    	nuevaOrden.setFecha(fechaFinal);
    	
    	//Prespuesto:
    	try {
    		Presupuesto pres = leerPresupuesto(request);
    		nuevaOrden.agregarPresupuesto(pres);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		nuevaOrden.agregarPresupuesto(new Presupuesto());
    	}
    	    	
    	
    	repo.agregar(nuevaOrden);
    	
    	response.redirect("/ordenes");
    	
    	
    	return response;
    }
    
    
    public Presupuesto leerPresupuesto(Request request) throws IOException {
    	File archivo = new File(request.queryParams("presupuestoDeOrden"));
    	if (archivo.exists()) {
    	 Gson gson = new Gson();
    		 
    	 /*
    		FileReader fr;
			
			fr = new FileReader(archivo);
    		BufferedReader br = new BufferedReader(fr);
    		String texto = br.readLine();
    		
    		Presupuesto pres = gson.fromJson(texto, Presupuesto.class);
    		//pres.setId();
    		fr.close();
    		*/
   		 
     		String data;
     		 
     		Scanner myReader = new Scanner(archivo);
     		
     		data = myReader.nextLine();
          		
     		myReader.close();
     		
     		Presupuesto pres = gson.fromJson(data, Presupuesto.class);
    		
    		return pres;
    	}
    	return new Presupuesto();
    }
    
    
    
   
    
    

}
