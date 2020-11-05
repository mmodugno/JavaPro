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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.mercadopago.exceptions.MPRestException;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

import javax.persistence.EntityManager;

public class ControllerEgresos {

    private static RepositorioEgreso repo;

    public ControllerEgresos(){
      
    }


    public ModelAndView modificarEgresoGet(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException {
    	
    	RepositorioEgreso repoEgreso = new RepositorioEgreso(entityManager);
    	RepositorioOrdenDeCompra repoOrdenesCompra = new RepositorioOrdenDeCompra(entityManager);
    	RepositorioPresupuesto repoPresupuestos = new RepositorioPresupuesto(entityManager);
    	RepositorioCategoria repoCategorias = new RepositorioCategoria(entityManager);

    	
    	List<OrdenDeCompra> ordenes = repoOrdenesCompra.todos();
    	List<Presupuesto> presupuestos = repoPresupuestos.todos();
    	List<CategoriaDelSistema> categorias = repoCategorias.todos();
    	
    	Map<String, Object> map = new HashMap<>();

		if(request.queryParams("ordenDeCompraId")!= null){
			int id = Integer.parseInt(request.queryParams("ordenDeCompraId"));

			OrdenDeCompra orden = repoOrdenesCompra.byID(id);
			presupuestos = orden.getPresupuestos();
			map.put("orden",orden);

		}
        map.put("ordenes", ordenes);
        map.put("presupuestos", presupuestos);
        map.put("categorias", categorias);
        

		String strID = request.params("id");
		int id = Integer.parseInt(strID);
		Egreso egreso = repoEgreso.byID(id);
		
		String anio = String.valueOf(egreso.getFecha().getYear());
		String mes = String.valueOf(egreso.getFecha().getMonthValue());
		String dia = String.valueOf(egreso.getFecha().getDayOfMonth());
	
		String fecha = anio+"-"+mes+"-"+dia;

		//

		//Map<String, Object> map = new HashMap<>();
		map.put("egreso", egreso);
		map.put("fecha", fecha);

		//HAY QUE VER COMO PASAR TODOS LOS REPOS, LO UNICO QUE SE ME OCURRE PASARLE TODOS LOS REPOS AL HTML. todo Charlar

		return new ModelAndView(map,"crearEgreso2.html");
	}

    public static void asignarParametros(Egreso egreso, Request request,EntityManager entityManager) throws CloneNotSupportedException {

		RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra(entityManager);
		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto(entityManager);
		RepositorioCategoria repoCategoria = new RepositorioCategoria(entityManager);

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

    	if(pres!=null && !pres.equals("noHay")){
		int idPresupuesto = Integer.parseInt(pres);
		Presupuesto presupuesto = repoPresupuesto.byID(idPresupuesto);
		egreso.setPresupuesto(presupuesto);
		egreso.setValorTotal(presupuesto.valorTotal());
    	}else {
    		egreso.setValorTotal(orden.valorTotal());
		}

		egreso.setFecha(LocalDate.parse(fecha));
		orden.cerrarOrden();
		egreso.setOrdenDeCompra(orden);
		//egreso.setDocumentosComerciales();
		egreso.setCategoria(categoria);


	}

	public Response modificarEgreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {
		
		RepositorioEgreso repo = new RepositorioEgreso(entityManager);
		
		String strID = request.params("id");
		
		int id = new Integer(strID);
		
		Egreso egreso = repo.byID(id);
		
		asignarParametros(egreso, request,entityManager);
		
		repo.crear(egreso);

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

