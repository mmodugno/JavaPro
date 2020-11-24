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
import repositorios.RepositorioCategoria;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioPresupuesto;
import repositorios.RepositorioProducto;
import repositorios.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CategoriaDelSistema;
import usuarios.CreationError;
import usuarios.Usuario;

public class ControllerPresupuesto {
	
	public ModelAndView presupuestos(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

		if((request.session().attribute("user") == null) || (request.session().attribute("admin").equals(true)))
			response.redirect("/login");

	
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
	    map.put("usuario", request.session().attribute("user"));
	
	    return new ModelAndView(map, "presupuestos.html");
	}
	
	public ModelAndView detallePresupuesto(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException{

		if((request.session().attribute("user") == null) || (request.session().attribute("admin").equals(true)))
    		response.redirect("/login");
    	
    	RepositorioPresupuesto repositorio = new RepositorioPresupuesto(entityManager);
    	RepositorioCategoria repoCategorias = new RepositorioCategoria(entityManager);
        String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Presupuesto presupuesto = repositorio.byID(id);
        List<CategoriaDelSistema> categorias = repoCategorias.todos();

        Map<String, Object> map = new HashMap<>();
        map.put("presupuesto", presupuesto);
        map.put("categorias", categorias);
        map.put("usuario", request.session().attribute("user"));

        return new ModelAndView(map,"detallePresupuesto.html");
    }
	
	public Response modificarCategoria(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

		RepositorioPresupuesto repositorio = new RepositorioPresupuesto(entityManager);
        String strID = request.params("id");
        int id = new Integer(strID);
        Presupuesto presupuesto = repositorio.byID(id);

        actualizarCategoria(presupuesto, request, entityManager);

        response.redirect("/presupuestos");

        return response;
    }
	
	private static void actualizarCategoria(Presupuesto presupuesto, Request request, EntityManager entityManager) {
		
		RepositorioCategoria repoCategorias = new RepositorioCategoria(entityManager);
		
		CategoriaDelSistema categoria = repoCategorias.buscar(request.queryParams("categoria"));

		presupuesto.setCategoria(categoria);

    }

}
