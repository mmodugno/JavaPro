package server;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class ControllerEgresos {

    private static RepositorioEgreso repo;

    public ControllerEgresos()  {
        try {
			repo = new RepositorioEgreso();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /*public ModelAndView egresos(Request request, Response response){

        //DOMINIO
        List<Egreso> egresos = repo.todos();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("egresos", egresos);

        return new ModelAndView(map, "egresos.html");

    }

    public ModelAndView nuevoProducto(Request request, Response response){
        return new ModelAndView(null,"nuevoProducto.html");
    }

    public ModelAndView detalleEgreso(Request request, Response response) throws CloneNotSupportedException{


        String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Egreso egreso = repo.byID(id);

        Map<String, Object> map = new HashMap<>();
        map.put("egreso", egreso);

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

    public Response guardarProducto(Request request, Response response){
        Producto producto = new Producto();
        producto.setIdProducto(repo.proximoId());

        asignarParametrosProducto(producto, request);

        repo.agregar(producto);
        producto.setTipoProducto(TipoItem.ARTICULO);

        response.redirect("/productos");

        return response;
    }

    public Response modificarProducto(Request request, Response response){

        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repo.byID(id);

        asignarParametrosProducto(producto, request);

        repo.modificar(producto);//Esto va a hacer algo cuando tengamos la base

        response.redirect("/productos");

        return response;
    }

    public Response eliminarProducto(Request request, Response response){

        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repo.byID(id);

        repo.eliminar(producto);

        //response.redirect("/productos");

        return response;
    }*/
    
    public Response guardarEgreso(Request request, Response response) throws CloneNotSupportedException{
    	
       	
   	 	RepositorioEgreso repoEgreso = new RepositorioEgreso();
   	 	RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra();
   	 	RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto();
   	 	RepositorioCategoria repoCategoria = new RepositorioCategoria();
   	 	
   	 	String ordenDeCompra = request.queryParams("orden");
   	 	
   	 	String pres = request.queryParams("presupuesto");
   	 	
   	 	String categoria = request.queryParams("categoria");
      
   	 	//String fecha = request.queryParams("fecha");
      
     
   	 	int idOrden = Integer.parseInt(ordenDeCompra);
     
   	 	OrdenDeCompra orden = repoOrden.byID(idOrden);
     
   	 	
     
   	 	int idPresupuesto = Integer.parseInt(pres);
     
   	 	Presupuesto presupuesto = repoPresupuesto.byID(idPresupuesto);
     
        Egreso egreso = new Egreso(orden,presupuesto);
        //producto.setIdProducto(repo.proximoId());
        
        String monto = request.queryParams("monto");
        
        int idMonto = Integer.parseInt(monto);
        
      
        Egreso new_egreso = new Egreso(orden,presupuesto);
        
        repoEgreso.crear(new_egreso);
        
        response.redirect("/egresos.html");

        return response;
    }
}
