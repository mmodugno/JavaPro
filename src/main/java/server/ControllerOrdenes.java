package server;

import egreso.Egreso;
import egreso.MedioDePago;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import usuarios.CreationError;
import usuarios.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ControllerOrdenes {
	  private static RepositorioOrdenDeCompra repo;

    public ControllerOrdenes() throws CloneNotSupportedException {

    }

    public ModelAndView ordenes(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException {

		//INIT
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
        
        //DOMINIO
		RepositorioOrdenDeCompra repo = new RepositorioOrdenDeCompra(entityManager);
		
		int cantidadTotal = userActual.getOrganizacion().getEntidades().get(0).getOrdenesPendientes().size();
		
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
        		siguientePagina = "ordenes?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual + 1);
	            paginaAnterior = "#";
        	} else if(paginaActual == ultimaPagina) {
        		siguientePagina = "#";
        		paginaAnterior = "ordenes?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual - 1);
        	} else {

	        	siguientePagina = "ordenes?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual + 1);
	    		paginaAnterior = "ordenes?nroRegistros=" + nroPaginasCombo + "&pagActual=" + (paginaActual - 1);
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

        List<OrdenDeCompra> ordenes = userActual.getOrganizacion().getEntidades().get(0).getOrdenesPendientes();
        /** **PARA PROBAR SI ANDA EL CERRAR** **/
        //repo.todos().get(0).setCerrado(true);
        /** No las marca como cerrada si o no porque técnicamente las creamos de 0 y no son creadas con el egreso.
         Tener en cuenta a la hora de probar la funcionalidad completa **/

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);
        map.put("usuario", request.session().attribute("user"));
        map.put("nroPaginasCombo", nroPaginasCombo);
        map.put("paginaActual", paginaActual);
        map.put("ultimaPagina", ultimaPagina);
        map.put("pagSiguiente", siguientePagina);
        map.put("pagAnterior", paginaAnterior);

        return new ModelAndView(map, "ordenes.html");
    }

    public ModelAndView nuevaOrden(Request request, Response response, EntityManager entityManager){

    	if((request.session().attribute("user") == null) || (request.session().attribute("admin").equals(true)))
    		response.redirect("/login");
    	
		RepositorioProducto repoPro = new RepositorioProducto(entityManager);
        Map<String, Object> map = new HashMap<>();
        List<Producto> productos = repoPro.todos();
        map.put("productos", productos);
        map.put("usuario", request.session().attribute("user"));
        return new ModelAndView(map,"formularioOrden.html");
    }

    public Response crear(Request request, Response response, EntityManager entityManager) throws ParseException, CloneNotSupportedException, ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
    	RepositorioOrdenDeCompra repo = new RepositorioOrdenDeCompra(entityManager);
    	OrdenDeCompra nuevaOrden = new OrdenDeCompra();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	RepositorioProducto repoPro = new RepositorioProducto(entityManager);

    	nuevaOrden.setNecesitaPresupuesto(Integer.parseInt(request.queryParams("presupuesto")));

    	String fecha =  request.queryParams("fecha");
		LocalDate fechaFinal = LocalDate.parse(fecha);
    	nuevaOrden.setFecha(fechaFinal);

    	//String presupuesto = request.queryParams("presupuestoi");
    	
    	leerPresupuestos( request, nuevaOrden, entityManager);
    	
    	
    	
    	int cantidad = Integer.parseInt(request.queryParams("cantidadItems"));
    	for(int i = 1; i<=cantidad; i++)
		{
			Item item = new Item();
			item.setPrecioUnitario(Double.parseDouble(request.queryParams("monto"+i)));
			item.setProducto(repoPro.byCodPro(Integer.parseInt(request.queryParams("producto"+i))));
			item.setCantidad(Integer.parseInt(request.queryParams("cantidad"+i)));
			nuevaOrden.getItems().add(item);
		}

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));

		repo.crear(nuevaOrden);
		
		userActual.getOrganizacion().getEntidades().get(0).agregarUnaOrden(nuevaOrden);


		//LOGICA TRANSACCION NUEVO INGRESO
		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("orden");
		transaccion.setOperacion("crear");
		transaccion.setFecha(LocalDate.now().toString());

		transaccion.setNuevo(converter(nuevaOrden));
		repositorioDocumentos.crearTransaccion(transaccion);
    	
    	response.redirect("ordenes");
    	
    	
    	return response;
    }

	private Presupuesto generarPresupuestoPersist(Presupuesto pres, EntityManager entityManager) {
    	RepositorioProducto repo = new RepositorioProducto(entityManager);

		Presupuesto presupuesto = new Presupuesto();
		//ITEMS
		for(int i = 0; i< pres.getItems().size();i++){
			Item item= new Item();
			item.setCantidad(pres.getItems().get(i).getCantidad());
			item.setPrecioUnitario(pres.getItems().get(i).getPrecioUnitario());
			item.setProducto(repo.byID(pres.getItems().get(i).getProducto().getIdProducto()));
			presupuesto.getItems().add(item);
		}
		//PROVEEDOR
		presupuesto.setAceptado(pres.isAceptado());
		presupuesto.setCategoria(pres.getCategoria());
		presupuesto.setProveedor(proveByCuit(entityManager, pres.getProveedor().getCuilOCuit()));
		//MEDIOdePAGO
		presupuesto.setMedioDePago(medioById(pres.getMedioDePago().getId(),entityManager));

		return presupuesto;
	}

	private Proveedor proveByCuit(EntityManager entityManager, String cuit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proveedor> consulta = cb.createQuery(Proveedor.class);
		Root<Proveedor> proveedores = consulta.from(Proveedor.class);
		Predicate condicion = cb.equal(proveedores.get("cuilOCuit"), cuit);
		CriteriaQuery<Proveedor> where = consulta.select(proveedores).where(condicion);
		return entityManager.createQuery(where).getSingleResult();
	}

	private MedioDePago medioById(int id, EntityManager entityManager){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MedioDePago> consulta = cb.createQuery(MedioDePago.class);
		Root<MedioDePago> medios = consulta.from(MedioDePago.class);
		Predicate condicion = cb.equal(medios.get("id"), id);
		CriteriaQuery<MedioDePago> where = consulta.select(medios).where(condicion);
		return entityManager.createQuery(where).getSingleResult();
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
    
    public void leerPresupuestos(Request request,OrdenDeCompra orden, EntityManager entityManager) {
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
     		Presupuesto presupuestoPersistido = generarPresupuestoPersist(pres, entityManager);
     		orden.agregarPresupuesto(presupuestoPersistido);
     		}



     		myReader.close();
     		
    	}else {
			Gson gson = new Gson();
			String data = request.queryParams("presupuestoDeOrden");
			String data2 = request.queryParams("presupuesoi");
			String[] splitpresu = data2.split("\n");
			for (int i = 0; i<splitpresu.length; i++){
				orden.agregarPresupuesto(generarPresupuestoPersist(gson.fromJson(splitpresu[i], Presupuesto.class), entityManager));
			}

		}

    }
    
    
    public Response eliminarOrden(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, ClassNotFoundException, FileNotFoundException, SQLException, CreationError {
    	RepositorioOrdenDeCompra repositorioOrdenDeCompra = new RepositorioOrdenDeCompra(entityManager);
    	
		String strID = request.params("id");
		int id = new Integer(strID);
		OrdenDeCompra orden = repositorioOrdenDeCompra.byID(id);

		RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
		Transaccion transaccion = new Transaccion();
		transaccion.setDocumento("orden");
		transaccion.setOperacion("eliminar");
		transaccion.setFecha(LocalDate.now().toString());

		transaccion.setViejo(converter(orden));
		repositorioDocumentos.crearTransaccion(transaccion);

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(entityManager);
		Usuario userActual = repositorioUsuario.byNombre(request.session().attribute("user"));
		userActual.getOrganizacion().getEntidades().get(0).getOrdenesPendientes().remove(orden);
		repositorioOrdenDeCompra.eliminar(orden);

		return response;
	}

	static public String converter(OrdenDeCompra orden){
		HashMap map = new HashMap();

		map.put("orden", orden.getIdOrden());
		map.put("Presupuestos que necesita", orden.getNecesitaPresupuesto());
		List<String> listaPre = orden.getPresupuestos().stream().map(a -> "ID:" + a.getId()).collect(Collectors.toList());
		map.put("Presupuestos", listaPre);
		map.put("fecha", orden.getFecha());
		List<String> listaItems = orden.getItems().stream().map(i -> i.getProducto().getNombre() + " cantidad " + i.getCantidad()).collect(Collectors.toList());
		map.put("items", listaItems);

		Gson gson = new Gson();
		String nuevo = gson.toJson(map);

		return nuevo;
	}
}

