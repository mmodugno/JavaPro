package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioPresupuesto;
import repositorios.RepositorioProducto;
import repositorios.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CreationError;
import usuarios.Usuario;

public class ControllerPresupuesto {
	
	public ModelAndView presupuestos(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

		if(request.session().attribute("user") == null) {
			response.redirect("/login");
			return new ModelAndView(null, "ingresos.html");
		}
	
		RepositorioUsuario repoUser = null;
		try {
			repoUser = new RepositorioUsuario(entityManager);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//Usuario userActual = repoUser.byNombre(request.session().attribute("user"));
	    
		RepositorioPresupuesto repo = new RepositorioPresupuesto(entityManager);
	    List<Presupuesto> presupuestos = repo.todos();

	    Map<String, Object> map = new HashMap<>();
	    map.put("presupuestos", presupuestos);
	    //map.put("usuario", request.session().attribute("user"));
	
	    return new ModelAndView(map, "presupuestos.html");
	}
	
	public ModelAndView detallePresupuesto(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException{

    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
    	
    	RepositorioPresupuesto repositorio = new RepositorioPresupuesto(entityManager);
        String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Presupuesto presupuesto = repositorio.byID(id);

        Map<String, Object> map = new HashMap<>();
        map.put("presupuesto", presupuesto);
        map.put("usuario", request.session().attribute("user"));

        return new ModelAndView(map,"detallePresupuesto.html");
    }
	
	public Response modificarCategoria(Request request, Response response, EntityManager entityManager) {

        RepositorioProducto repositorio = new RepositorioProducto(entityManager);
        String strID = request.params("id");
        int id = new Integer(strID);
        Producto producto = repositorio.byID(id);

        actualizarCategoria(producto, request);

        response.redirect("/presupuestos");

        return response;
    }
	
	private static void actualizarCategoria(Producto producto,Request request) {

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

}
