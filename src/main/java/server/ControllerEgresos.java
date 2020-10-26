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
import usuarios.CategoriaDelSistema;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class ControllerEgresos {

    private static RepositorioEgreso repo;

    public ControllerEgresos(){
      
    }

    public ModelAndView modificarEgresoGet(Request request, Response response) throws CloneNotSupportedException {
    	
    	RepositorioOrdenDeCompra repoOrdenesCompra = new RepositorioOrdenDeCompra();
    	RepositorioPresupuesto repoPresupuestos = new RepositorioPresupuesto();
    	RepositorioCategoria repoCategorias = new RepositorioCategoria();
    	
    	List<OrdenDeCompra> ordenes = repoOrdenesCompra.todos();
    	List<Presupuesto> presupuestos = repoPresupuestos.todos();
    	List<CategoriaDelSistema> categorias = repoCategorias.todos();
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);
        map.put("presupuestos", presupuestos);
        map.put("categorias", categorias);
        

		String strID = request.params("id");
		int id = Integer.parseInt(strID);
		Egreso egreso = repo.byID(id);

		//RepositorioOrdenDeCompra ordenes = new RepositorioOrdenDeCompra();

		//Map<String, Object> map = new HashMap<>();
		map.put("egreso", egreso);

		//HAY QUE VER COMO PASAR TODOS LOS REPOS, LO UNICO QUE SE ME OCURRE PASARLE TODOS LOS REPOS AL HTML. todo Charlar

		return new ModelAndView(map,"crearEgreso.html");
	}

    public static void asignarParametros(Egreso egreso, Request request,EntityManager entityManager) throws CloneNotSupportedException {

		RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra();
		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto(entityManager);
		RepositorioCategoria repoCategoria = new RepositorioCategoria();

		String ordenDeCompra = request.queryParams("orden");
		String pres = request.queryParams("presupuesto");
		String categoriaString = (request.queryParams("categoria") != null) ? request.queryParams("categoria") : "";
		String fecha = request.queryParams("fecha");

		int idOrden = Integer.parseInt(ordenDeCompra);
		OrdenDeCompra orden = repoOrden.byID(idOrden);
		
		if(!categoriaString.contains("%20")) {
    		categoriaString = categoriaString.replace("%20"," ");
    	};
    	

    	CategoriaDelSistema categoria = repoCategoria.buscar(categoriaString);

		int idPresupuesto = Integer.parseInt(pres);
		Presupuesto presupuesto = repoPresupuesto.byID(idPresupuesto);
		orden.cerrarOrden();
		egreso.setOrdenDeCompra(orden);
		//egreso.setDocumentosComerciales();
		egreso.setCategoria(categoria);
		presupuesto.setAceptado();
		egreso.setPresupuesto(presupuesto);
		egreso.setValorTotal(presupuesto.valorTotal());
		egreso.setFecha(LocalDate.parse(fecha));


	}

	public Response modificarEgreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {
		
		RepositorioEgreso repo = new RepositorioEgreso(entityManager);
		
		String strID = request.params("id");
		
		int id = new Integer(strID);
		
		Egreso egreso = repo.byID(id);
		
		asignarParametros(egreso, request,entityManager);
		
		repo.reemplazar(egreso);

		response.redirect("/egresos");

		return response;

	}
    //ESTE ES EL QUE CREA
    public Response guardarEgreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException{
    	
    	RepositorioEgreso repo = new RepositorioEgreso(entityManager);
    	
        Egreso egreso = new Egreso();
        //egreso.setId(repo.proximoId());

		asignarParametros(egreso, request,entityManager);
        
        repo.crear(egreso);
        
        response.redirect("/egresos");

        return response;
    }

	public Response eliminarEgreso(Request request, Response response){

		String strID = request.params("id");
		int id = new Integer(strID);
		Egreso egreso = repo.byID(id);

		repo.eliminar(egreso);

		return response;
	}
}
