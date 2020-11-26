package server;

import com.google.gson.Gson;
import egreso.Egreso;
import repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CategoriaDelSistema;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import egreso.Ingreso;

public class ControllerIngresos {

	public ControllerIngresos() {

	}

	public ModelAndView nuevoIngreso(Request request, Response response, EntityManager entityManager) {

		if((request.session().attribute("user") == null) || (request.session().attribute("admin").equals(true)))
			response.redirect("/login");

		RepositorioIngreso repo = new RepositorioIngreso(entityManager);

		RepositorioCategoria repoCategorias = new RepositorioCategoria(entityManager);
		List<CategoriaDelSistema> categorias = repoCategorias.todos();

		//DOMINIO
		List<RepositorioIngreso> repos = new ArrayList<>();

		repos.add(repo);
		//OUTPUT
		Map<String, Object> map = new HashMap<>();
		map.put("repos", repo);
		map.put("categorias", categorias);
		map.put("usuario", request.session().attribute("user"));

		return new ModelAndView(map, "formularioIngresos.html");
	}

	public static void asignarParametros(Ingreso ingreso, Request request) throws CloneNotSupportedException {


		String descripcion = request.queryParams("descripcion");
		String montoString = request.queryParams("monto");


		double monto = Double.parseDouble(montoString);
		;


		ingreso.setDescripcion(descripcion);
		ingreso.setMonto(monto);

	}


	public Response guardarIngreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, ClassNotFoundException, FileNotFoundException, SQLException, CreationError {


		RepositorioIngreso repo = new RepositorioIngreso(entityManager);
		Ingreso ingreso = new Ingreso();


		asignarParametros(ingreso, request);
		ingreso.setFecha(LocalDate.now());

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));

		userActual.getOrganizacion().getEntidades().get(0).getIngresos().add(ingreso);
		//repo.crear(ingreso);

		//LOGICA TRANSACCION NUEVO INGRESO
		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("ingreso");
		transaccion.setOperacion("crear");
		transaccion.setFecha(LocalDate.now().toString());

		transaccion.setNuevo(converter(ingreso));
		repositorioDocumentos.crearTransaccion(transaccion);

		response.redirect("/ingresos");

		return response;
	}


	public Response eliminarIngreso(Request request, Response response, EntityManager entityManager) throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError {


		//TODO Hacer que elimine por favor!! Revisar el de egreso que debe ser lo mismo
		RepositorioIngreso repo = new RepositorioIngreso(entityManager);
		String strID = request.params("id");
		int id = new Integer(strID);

		Ingreso ingreso = repo.byID(id);

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));
		userActual.getOrganizacion().getEntidades().get(0).getIngresos().remove(ingreso);
		repo.borrar(ingreso);

		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("ingreso");
		transaccion.setOperacion("eliminar");
		transaccion.setFecha(LocalDate.now().toString());

		transaccion.setViejo(converter(ingreso));
		repositorioDocumentos.crearTransaccion(transaccion);

		/*
		//Borra segun la descripcion:
		String desc = request.params("descripcion");
		repo.borrar(desc);*/

		//response.redirect("/ingresos");

		return response;
	}

	public ModelAndView modificarIngreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {
		if((request.session().attribute("user") == null) || (request.session().attribute("admin").equals(true)))
			response.redirect("/login");

		RepositorioIngreso repo = new RepositorioIngreso(entityManager);
		RepositorioCategoria repoCategorias = new RepositorioCategoria(entityManager);


		String strID = request.params("id");

		int id = Integer.parseInt(strID);

		Ingreso ingreso = repo.byID(id);

		List<CategoriaDelSistema> categorias = repoCategorias.todos();


		Map<String, Object> map = new HashMap<>();
		map.put("ingreso", ingreso);
		map.put("categorias", categorias);
		map.put("usuario", request.session().attribute("user"));

		return new ModelAndView(map, "formularioIngresos.html");

	}

	public Response persistirIngreso(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

		RepositorioIngreso repo = new RepositorioIngreso(entityManager);
		String strID = request.params("id");

		int id = Integer.parseInt(strID);

		Ingreso ingreso = repo.byID(id);


		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("ingreso");
		transaccion.setOperacion("modificar");
		transaccion.setFecha(LocalDate.now().toString());

		transaccion.setViejo(converter(ingreso));

		asignarParametros(ingreso, request);


		transaccion.setNuevo(converter(ingreso));
		repositorioDocumentos.crearTransaccion(transaccion);

		response.redirect("/ingresos");

		return response;

	}

	public ModelAndView ingresos(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, FileNotFoundException {

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

		Usuario userActual = repoUser.byNombre(request.session().attribute("user"));

		//RepositorioIngreso repo = new RepositorioIngreso(entityManager).byEntidades(userActual.getOrganizacion().getEntidades());

		//DOMINIO
		int cantidadTotal = userActual.getOrganizacion().getEntidades().get(0).getIngresos().size();
		
		//Paginado
        int nroPaginasCombo;
        String siguientePagina = new String();
        int paginaActual;
        String paginaAnterior = new String();
        if(request.queryParams("nroRegistros") == null) {
        	nroPaginasCombo = 5;
        } else {
        	nroPaginasCombo = Integer.parseInt(request.queryParams("nroRegistros"));
        }
        if(request.queryParams("pagActual") == null) {
        	paginaActual = 1;

        } else {
        	paginaActual = Integer.parseInt(request.queryParams("pagActual"));
        }
        
        int ultimaPagina = (int) (Math.ceil(cantidadTotal / nroPaginasCombo));
        if(ultimaPagina == 0)
        	ultimaPagina = 1;
        
        if(cantidadTotal > nroPaginasCombo ) {
        	if(paginaActual == 1) {
        		siguientePagina = "ingresos?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual + 1);
	            paginaAnterior = "#";
        	} else if(paginaActual == ultimaPagina) {
        		siguientePagina = "#";
        		paginaAnterior = "ingresos?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual - 1);
        	} else {

	        	siguientePagina = "ingresos?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual + 1);
	    		paginaAnterior = "ingresos?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual - 1);
        	}
        }
        
        if(cantidadTotal <= nroPaginasCombo ) {
        	paginaActual = 1;
	        siguientePagina = "#";
	        paginaAnterior = "#";
        }
        int indiceFrom = nroPaginasCombo * (paginaActual - 1);
        int indiceTo = (paginaActual + nroPaginasCombo) - 1;
        if(indiceTo > cantidadTotal)
        indiceTo = cantidadTotal;
        
        List<Ingreso> ingresos = userActual.getOrganizacion().getEntidades().get(0).getIngresos().subList(indiceFrom, indiceTo);

		//OUTPUT
		Map<String, Object> map = new HashMap<>();
		map.put("ingresos", ingresos);
		map.put("usuario", request.session().attribute("user"));
		map.put("nroPaginasCombo", nroPaginasCombo);
        map.put("paginaActual", paginaActual);
        map.put("ultimaPagina", ultimaPagina);
        map.put("pagSiguiente", siguientePagina);
        map.put("pagAnterior", paginaAnterior);

		return new ModelAndView(map, "ingresos.html");
	}


	static public String converter(Ingreso ingreso){
		HashMap map = new HashMap();

		map.put("ingreso", ingreso.getId());
		map.put("Descripcion", ingreso.getDescripcion());
		map.put("fecha", ingreso.getFecha());
		map.put("Valor", ingreso.getMonto());

		Gson gson = new Gson();
		String nuevo = gson.toJson(map);

		return nuevo;

	}
	
	

}
