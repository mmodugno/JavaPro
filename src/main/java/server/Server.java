package server;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.MedioDePago;
import egreso.MontoSuperadoExcepcion;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import meliApi.api;
import repositorios.*;
import spark.*;

import spark.template.handlebars.HandlebarsTemplateEngine;
import usuarios.CategoriaDelSistema;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import auditoria.CantidadPresupuestos;
import auditoria.Reporte;
import auditoria.Validador;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPRestException;

import Vinculador.ListaVaciaExcepcion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Server {
    static EntityManagerFactory entityManagerFactory;

    private static ControllerProductos controllerProductos= new ControllerProductos();
    private static ControllerEgresos controllerEgresos= new ControllerEgresos();
    private static ControllerIngresos controllerIngresos= new ControllerIngresos();
    private static ControllerOrdenes controllerOrdenes;
    private static ControllerVinculador controllerVinculador;
	private static ControllerLogin controllerLogin= new ControllerLogin();
    
	
	
    static {
        try {
            controllerVinculador = new ControllerVinculador();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        try {
            controllerOrdenes = new ControllerOrdenes();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
   
 
    public static void main(String[] args) {
        enableDebugScreen();
        port(1133);
        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/resources");
        }

        
        entityManagerFactory = Persistence.createEntityManagerFactory("db");

        controllerProductos= new ControllerProductos();
       

        // Ejemplo de acceso: http://localhost:9000/inicio
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        
        redirect.get("/", "/login");
        
        //get("/hola",((request, response) -> "Yoel"));
        get("/inicio", Server::mostrarIndex, engine);
        get("/inicio2", Server::mostrarIndex2, engine);
        get("/pageblank", Server::mostrarPageBlank, engine);
        get("/login", controllerLogin::login, engine);
        get("/loginIncorrecto", controllerLogin::loginIncorrecto, engine);
        post("/login", TemplWithTransaction((req, res, em) -> {
			try {
				return controllerLogin.validarLogin(req, res, em);
			} catch (FileNotFoundException | ClassNotFoundException | CreationError | SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		}), engine);
        get("/logout", controllerLogin::logout, engine);

       
        //EGRESOS

        get("/egresos",TemplWithTransaction(Server::egresos),engine);
        get("/egreso/:id", TemplWithTransaction((req, res, em) -> {
			try {
				return detalleEgreso(req, res, em);
			} catch (MPRestException e) {
				e.printStackTrace();
			}
			return null;
		}), engine);
        get("/crearEgreso", TemplWithTransaction(Server::crearEgreso), engine);
        get("/modificarEgreso/:id", TemplWithTransaction(controllerEgresos::modificarEgresoGet),engine);

        get("/categorias", TemplWithTransaction(Server::mostrarCategorias), engine);
        get("/categoria", TemplWithTransaction(Server::mostrarCategorias), engine);
       
        post("/egreso",RouteWithTransaction(controllerEgresos::guardarEgreso));
        delete("/egreso/:id", RouteWithTransaction(controllerEgresos::eliminarEgreso));
        post("/egreso/:id", RouteWithTransaction(controllerEgresos::modificarEgreso));
        
        //INGRESOS
        get("/ingresos", TemplWithTransaction(controllerIngresos::ingresos), engine);
        get("/crearIngreso", TemplWithTransaction(controllerIngresos::nuevoIngreso), engine);
        post("/ingreso",RouteWithTransaction(controllerIngresos::guardarIngreso));
        delete("/ingreso/:id", RouteWithTransaction(controllerIngresos::eliminarIngreso));
        get("/ingreso/:id", TemplWithTransaction(controllerIngresos::modificarIngreso),engine); ///VER EL POST
        post("/ingreso/:id", RouteWithTransaction(controllerIngresos::persistirIngreso));

        //acciones productos
        get("/productos",TemplWithTransaction(controllerProductos::productos),engine);
        get("/producto", controllerProductos::nuevoProducto, engine);
        get( "/producto/:id", TemplWithTransaction(controllerProductos::detalleProducto), engine);
        post("/producto", RouteWithTransaction(controllerProductos::guardarProducto));
        post("/producto/:id",RouteWithTransaction(controllerProductos::modificarProducto));

        delete("/producto/:id",RouteWithTransaction(controllerProductos::eliminarProducto));

        //validaciones
       get("/egreso/:id/validacion",(req,resp) -> Validar(req,resp));
    		   
      
       
        //ORDENES DE COMPRA

        get("/ordenes",TemplWithTransaction(controllerOrdenes::ordenes),engine);
        get("/crearOrden",TemplWithTransaction(controllerOrdenes::nuevaOrden),engine);
        
        //get("/orden/:id",Server::detalleOrden,engine); //TODO
        
        get("/orden/:id", TemplWithTransaction((req, res, em) -> {
			try {
				return detalleOrden(req, res, em);
			} catch (MPRestException e) {
				e.printStackTrace();
			}
			return null;
		}), engine);
        
        delete("/orden/:id", controllerOrdenes::eliminarOrden);
        
        post("/orden", RouteWithTransaction(controllerOrdenes::crear));
       // post("orden:id");

        //VINCULADOR

        get("/vinculaciones", TemplWithTransaction(controllerVinculador::vinculaciones), engine);
       // get("/vincular", controllerVinculador::vincular, engine);

        get("/vincular", RouteWithTransaction(controllerVinculador::vincular));

        
        get("/working",Server::work,engine);


        //API DOCUMENTAL
        get("/transaccion",Server::documentos);
        

    }

    private static Object documentos(Request request, Response response) {

        String tipo = request.queryParams("tipo");
        String operacion = request.queryParams("operacion");

        RepositorioDocumentos repositorioDocumentos = new RepositorioDocumentos();
        return repositorioDocumentos.documentos(tipo,operacion);
    }


    public static String Validar(Request request, Response response) throws CloneNotSupportedException {
    	
    	EntityManager entityManager = entityManagerFactory.createEntityManager();;
		RepositorioEgreso repo = new RepositorioEgreso(entityManager );

    	String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Egreso egreso = repo.byID(id);
        
        if (egreso == null) {
        	Map<String, Object> map = new HashMap<>();
        	map.put("egreso","No existe Egreso con id "+strID);
        	
        	map.put("informe","Egreso no valido");
        	
        	map.put("Resultado Validacion",false);
        	
        	Gson gson = new Gson();
            String JSON = gson.toJson(map);

            return JSON;
        	
        }

        Validador validador = new Validador();

        validador.agregarCondicionValidacion(new CantidadPresupuestos());
       // validador.agregarCondicionValidacion(new MontoPresupuesto());
       // validador.agregarCondicionValidacion(new Criterios());
        // validador.agregarCondicionValidacion(new Items());

        validador.validarEgreso(egreso);

        Reporte resultadoReporte = validador.getReporteValidacion();
        
        Map<String, String> map = new HashMap<>();
        
        map.put("EgresoId",strID);
        map.put("Informe",resultadoReporte.getInforme());
        map.put("ResultadoValidacion",resultadoReporte.resultadoString(resultadoReporte.resultadoValidacion()));

        Gson gson = new Gson();
        String JSON = gson.toJson(map);

        System.out.println(JSON);

      //esto por si queres chequearlo no mas

        
        String ruta = "resultadoEgreso"+strID+"Validacion.json";

        File file = new File(ruta);
        try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedWriter bw = new BufferedWriter(fw);

        try {
			bw.write(JSON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
       return JSON;
    }
    
    

    public static ModelAndView mostrarIndex(Request request, Response response) {
    	
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
    	Map<String, Object> map = new HashMap<>();
        map.put("usuario", request.session().attribute("user"));
        return new ModelAndView( map , "index.html");
    }
    
    
    public static ModelAndView mostrarIndex2(Request request, Response response) {
        return new ModelAndView(null, "index2.html");
    }
    
    public static ModelAndView mostrarPageBlank(Request request, Response response) {
        return new ModelAndView(null, "pageBlank.html");
    }


    public static ModelAndView login(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }
    
    public static ModelAndView validarLogin(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }
    
    public static ModelAndView crearIngreso(Request request, Response response){
    	
    	
    	
        return new ModelAndView(null,"formularioIngresos.html");
    }
    

    public static ModelAndView egresos(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException {

        //INIT
        if(request.session().attribute("user") == null) {
            response.redirect("/login");
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

        Usuario userActual = repoUser.byNombre(request.session().attribute("user"));


        //DOMINIO
        List<Egreso> egresos = userActual.getOrganizacion().getEntidades().get(0).getEgresos();
         
   
        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("egresos", egresos);
        map.put("usuario", request.session().attribute("user"));

        return new ModelAndView(map, "egresos.html");
    }
    

    
 /*   private static void asignarParametrosEgreso(Egreso egreso,Request request) {

      /*  producto.setCodProducto(new Integer(request.queryParams("codigo")));
        producto.setNombre(request.queryParams("nombre"));
        producto.setDescripcion(request.queryParams("descripcion"));


        if(request.queryParams("opciones").equals("Articulo")){
            producto.setTipoProducto(TipoItem.ARTICULO);
        }
        else if(request.queryParams("opciones").equals("Servicio")){
            producto.setTipoProducto(TipoItem.SERVICIO);
        }*/

    public static ModelAndView crearEgreso(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException{
    	
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
    	
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
        map.put("repoPresupuesto", repoPresupuestos);
        map.put("usuario", request.session().attribute("user"));
        return new ModelAndView(map ,"crearEgreso.html");
    }
    
    public static ModelAndView detalleEgreso(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException, MPRestException{
    	
    	if(request.session().attribute("user") == null) {
            response.redirect("/login");
        }
    	RepositorioEgreso repo = new RepositorioEgreso(entityManager);
    	
    	String strID = request.params("id");
    	
    	int id = Integer.parseInt(strID);
    	
    	Egreso egreso = repo.byID(id);
    	
    	MedioDePago medioPagoEgreso = egreso.getPresupuesto().getMedioDePago();
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("egreso", egreso);
    	
    	if (medioPagoEgreso != null) {
    	
    	String nombreMedioPago = medioPagoEgreso.getPayment_type().toString();
    	
    	String imagenMedioPago = new api().getRouteByName(nombreMedioPago);
  
        
        map.put("nombreMedioPago", nombreMedioPago);
        map.put("imagenMedioPago", imagenMedioPago);
        map.put("usuario", request.session().attribute("user"));
        
        	
    	}
        
        return new ModelAndView(map,"detalleEgreso.html");
    }
    
 public static ModelAndView detalleOrden(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException, MPRestException{
    	
	 if(request.session().attribute("user") == null) {
         response.redirect("/login");
     }	

	 RepositorioOrdenDeCompra repo = new RepositorioOrdenDeCompra(entityManager);

	 String strID = request.params("id");

	 int id = Integer.parseInt(strID);

	 OrdenDeCompra orden = repo.byID(id);

	 Map<String, Object> map = new HashMap<>();

	 map.put("orden", orden);
	 map.put("usuario", request.session().attribute("user"));

	 return new ModelAndView(map,"detalleOrden.html");
	 
 }

    /*public static ModelAndView mostrarCategorias(Request request, Response response) throws CloneNotSupportedException {
    	
    	RepositorioCategoria repoCategoria = new RepositorioCategoria();
    	//RepositorioEgreso repoEgresos = new RepositorioEgreso();
    	//RepositorioIngreso repoIngresos = new RepositorioIngreso();
    	
    	List<CategoriaDelSistema> categorias = repoCategoria.todos();
    	//List<Egreso> ingresos = repoEgresos.todos();
    	//List<Ingreso> egresos = repoIngresos.todos();
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("categorias", categorias);
   //     map.put("ingresos", ingresos);
    //    map.put("egresos", egresos);
    	
        return new ModelAndView(map, "categorias.html");
    }*/
    
    public static ModelAndView mostrarCategorias(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException {
    	
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
    	
    	RepositorioCategoria repoCategoria = new RepositorioCategoria(entityManager);
    	
    	List<CategoriaDelSistema> categorias = repoCategoria.todos();
    	
    	String categoriaString = (request.queryParams("categoria") != null) ? request.queryParams("categoria") : "";
    	
    	String tipoDocumentoString = (request.queryParams("tipoDoc") != null) ? request.queryParams("tipoDoc") : "";
    	
    	if(!categoriaString.contains("%20")) {
    		categoriaString = categoriaString.replace("%20"," ");
    	};
    	

    	CategoriaDelSistema categoria = repoCategoria.buscar(categoriaString);
    	
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	if(tipoDocumentoString.equals("Egresos")) {
    		RepositorioEgreso repoEgresos = new RepositorioEgreso(entityManager);	
    		List<Egreso> egresos = repoEgresos.todos().stream().filter(a -> a.esDeCategoria(categoria)).collect(Collectors.toList());
    		map.put("documentos",egresos);
    	}
    	
    	if(tipoDocumentoString.equals("Ingresos")) {
    		RepositorioIngreso repoIngresos = new RepositorioIngreso(entityManager);
    		List<Ingreso> ingresos = repoIngresos.todos().stream().filter(a -> a.esDeCategoria(categoria)).collect(Collectors.toList());
    		map.put("documentos",ingresos);
    	}
    	
    	if(tipoDocumentoString.equals("Presupuestos")) {
    		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto(entityManager);
    		//Presupuesto pre = repoPresupuesto.byID(6);
    		//Boolean resultado = pre.esDeCategoria(categoria);
    		List<Presupuesto> presupuestos = repoPresupuesto.todos().stream().filter(a -> a.esDeCategoria(categoria)).collect(Collectors.toList());
    		map.put("presupuestos",presupuestos);
    	}
    	
    	if(categoria != null && tipoDocumentoString != null) {
    		map.put("categoriaString",categoriaString);
    		map.put("tipoDocumento",tipoDocumentoString);
    	}
    	
    	map.put("categorias", categorias);
    	map.put("usuario", request.session().attribute("user"));
    	
		return new ModelAndView(map, "categorias.html");
    	
    	
    }

    //PRODUCTOS
    public static ModelAndView work(Request request, Response response){
    	
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	map.put("usuario", request.session().attribute("user"));
        return new ModelAndView(null,"index2.html");
    }



    private static TemplateViewRoute TemplWithTransaction(WithTransaction<ModelAndView> fn) {
        TemplateViewRoute r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                ModelAndView result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
    }

    private static Route RouteWithTransaction(WithTransaction<Object> fn) {
        Route r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                Object result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
    }



    
}