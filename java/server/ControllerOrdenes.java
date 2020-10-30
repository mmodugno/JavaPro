package server;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Item;
import producto.Producto;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioProducto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;

import javax.persistence.EntityManager;

public class ControllerOrdenes {
	  private static RepositorioOrdenDeCompra repo;
	  
    public ControllerOrdenes() throws CloneNotSupportedException {

    }

    public ModelAndView ordenes(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

        //DOMINIO
		RepositorioOrdenDeCompra repo = new RepositorioOrdenDeCompra(entityManager);
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

    public Response crear(Request request, Response response, EntityManager entityManager) throws ParseException, CloneNotSupportedException {
    	RepositorioOrdenDeCompra repo = new RepositorioOrdenDeCompra(entityManager);
    	OrdenDeCompra nuevaOrden = new OrdenDeCompra();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	RepositorioProducto repoPro = new RepositorioProducto(entityManager);

    	nuevaOrden.setNecesitaPresupuesto(Integer.parseInt(request.queryParams("presupuesto")));

    	String fecha =  request.queryParams("fecha");   	
    	LocalDate fechaFinal = LocalDate.parse(fecha);
    	nuevaOrden.setFecha(fechaFinal);
    	
    	//Prespuesto:
    	/*
    	try {
    		Presupuesto pres = leerPresupuesto(request);
    		nuevaOrden.agregarPresupuesto(pres);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		nuevaOrden.agregarPresupuesto(new Presupuesto());
    	}
*/
    	leerPresupuestos( request, nuevaOrden); //TODO TESTING
    	
    	
    	
    	int cantidad = Integer.parseInt(request.queryParams("cantidadItems"));
    	for(int i = 1; i<=cantidad; i++)
		{
			Item item = new Item();
			item.setPrecioUnitario(Double.parseDouble(request.queryParams("monto"+i)));
			item.setProducto(repoPro.byCodPro(Integer.parseInt(request.queryParams("producto"+i))));
			item.setCantidad(Integer.parseInt(request.queryParams("cantidad"+i)));
			nuevaOrden.getItems().add(item);
		}
    	    	
    	
    	repo.crear(nuevaOrden);
    	
    	response.redirect("/ordenes");
    	
    	
    	return response;
    }
    
    
    public Presupuesto leerPresupuesto(Request request) throws IOException {
    	File archivo = new File(request.queryParams("presupuestoDeOrden"));
    	if (archivo.exists()) {
    	 Gson gson = new Gson();
    		 
    
   		 
     		String data;
     		 
     		Scanner myReader = new Scanner(archivo);
     		
     		data = myReader.nextLine();
          		
     		myReader.close();
     		
     		Presupuesto pres = gson.fromJson(data, Presupuesto.class);
    		
    		return pres;
    	}
    	//Esto nunca lo deberia hacer
    	return new Presupuesto();
    }
    
    public void leerPresupuestos(Request request,OrdenDeCompra orden) {
    	File archivo = new File(request.queryParams("presupuestoDeOrden"));
    	
    	if (archivo.exists()) {
    	 Gson gson = new Gson();
    		 
   		 
     		String data;
     		 
     		Scanner myReader = null;
			try {
				myReader = new Scanner(archivo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		
     		while(myReader.hasNextLine()){
     		data = myReader.nextLine();
     		Presupuesto pres = gson.fromJson(data, Presupuesto.class);
     		orden.agregarPresupuesto(pres);
     		}

     		myReader.close();
     		
    	}

    }
    
    
    public Response eliminarOrden(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException{
    	RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra(entityManager);
    	
		String strID = request.params("id");
		int id = new Integer(strID);
		OrdenDeCompra orden = repoOrden.byID(id);

		repoOrden.eliminar(orden);

		response.redirect("/ordenes");
		
		return response;
	}
    
}
