package server;

import com.google.gson.Gson;
import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import org.hibernate.Hibernate;
import org.hibernate.engine.spi.SessionImplementor;
import repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CategoriaDelSistema;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;
import usuarios.CreationError;
import usuarios.Usuario;


import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerEgresos {

    private static RepositorioEgreso repo;

    public ControllerEgresos(){
      
    }


    public ModelAndView modificarEgresoGet(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException {

    	if(request.session().attribute("user") == null )
    		response.redirect("/login");

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
		map.put("usuario", request.session().attribute("user"));

		//HAY QUE VER COMO PASAR TODOS LOS REPOS, LO UNICO QUE SE ME OCURRE PASARLE TODOS LOS REPOS AL HTML. todo Charlar

		return new ModelAndView(map,"crearEgreso2.html");
	}

    public static void asignarParametros(Egreso egreso, Request request,EntityManager entityManager) throws CloneNotSupportedException {
    	OrdenDeCompra orden;
		RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra(entityManager);
		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto(entityManager);
		RepositorioCategoria repoCategoria = new RepositorioCategoria(entityManager);

		if(request.queryParams("orden")!= null){
		String ordenDeCompra = request.queryParams("orden");
		int idOrden = Integer.parseInt(ordenDeCompra);
		orden = repoOrden.byID(idOrden);
		}
		else {orden = egreso.getOrdenDeCompra();}
		
		String pres = request.queryParams("presupuesto");
		String categoriaString = (request.queryParams("categoria") != null) ? request.queryParams("categoria") : "";
		String fecha = request.queryParams("fecha");

		
		
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

		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("egreso");
		transaccion.setOperacion("modificar");
		transaccion.setViejo("");
		transaccion.setFecha(LocalDate.now().toString());
		
		String strID = request.params("id");
		
		int id = new Integer(strID);
		
		Egreso egreso = repo.byID(id);

		//VIEJO
		transaccion.setViejo(converter(egreso));
		
		asignarParametros(egreso, request,entityManager);
		
		repo.crear(egreso);

		//TERMINO CREAR TRANSACCION
		transaccion.setNuevo(converter(egreso));
		repositorioDocumentos.crearTransaccion(transaccion);

		response.redirect("/egresos");

		return response;

	}
    //ESTE ES EL QUE CREA
    public Response guardarEgreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
    	
    	RepositorioEgreso repo = new RepositorioEgreso(entityManager);
    	
        Egreso egreso = new Egreso();
        //egreso.setId(repo.proximoId());

		//LOGICA TRANSACCION NUEVO EGRESO
		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("egreso");
		transaccion.setOperacion("crear");
		transaccion.setFecha(LocalDate.now().toString());

		asignarParametros(egreso, request,entityManager);


        //repo.crear(egreso);
		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));

		userActual.getOrganizacion().getEntidades().get(0).getEgresos().add(egreso);

		//TERMINO CREAR TRANSACCION
		transaccion.setNuevo(converter(egreso));
		repositorioDocumentos.crearTransaccion(transaccion);
        
        response.redirect("/egresos");

        return response;
    }

	public Response eliminarEgreso(Request request, Response response, EntityManager entityManager) throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {

    	repo = new RepositorioEgreso(entityManager);
		String strID = request.params("id");
		int id = new Integer(strID);
		Egreso egreso = repo.byID(id);

		//TRANSACCION
		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("egreso");
		transaccion.setOperacion("eliminar");
		transaccion.setViejo("");
		transaccion.setFecha(LocalDate.now().toString());
		transaccion.setViejo(converter(egreso));
		repositorioDocumentos.crearTransaccion(transaccion);

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));
		userActual.getOrganizacion().getEntidades().get(0).getEgresos().remove(egreso);
		repo.eliminar(egreso);

		return response;
	}

	static public String converter(Egreso egreso){
		HashMap map = new HashMap();

		map.put("Egreso",egreso.getId());
		map.put("fecha", egreso.getFecha());
		map.put("Orden de Compra",egreso.getOrdenDeCompra().getIdOrden());
		map.put("Presupuesto",egreso.getPresupuesto().getId());
		map.put("Categoria", egreso.getCategoria().getCategoria());

		Gson gson = new Gson();
		String nuevo = gson.toJson(map);

		return nuevo;

	}



}

