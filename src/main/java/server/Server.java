package server;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.MedioDePago;
import egreso.MontoSuperadoExcepcion;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import meliApi.api;
import producto.Producto;
import producto.TipoItem;
import repositorios.RepositorioCategoria;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioIngreso;
import repositorios.RepositorioOrdenDeCompra;
import repositorios.RepositorioPresupuesto;
import repositorios.RepositorioProducto;
import spark.*;

import spark.template.handlebars.HandlebarsTemplateEngine;
import usuarios.Categoria;
import usuarios.CategoriaDelSistema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import auditoria.CantidadPresupuestos;
import auditoria.Criterios;
import auditoria.Items;
import auditoria.MontoPresupuesto;
import auditoria.Reporte;
import auditoria.Validador;
import db.EntityManagerHelper;

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
        post("/login", controllerLogin::validarLogin, engine);
        get("/logout", controllerLogin::logout, engine);

        //EGRESOS
        get("/egresos",TemplWithTransaction(Server::egresos),engine);
        get("/egreso/:id", TemplWithTransaction((req, res, em) -> {
			try {
				return detalleEgreso(req, res, em);
			} catch (MPRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}), engine);
        get("/crearEgreso", TemplWithTransaction(Server::crearEgreso), engine);
        get("/modificarEgreso/:id", controllerEgresos::modificarEgresoGet,engine);

        get("/categorias", TemplWithTransaction(Server::mostrarCategorias), engine);
        get("/categoria", TemplWithTransaction(Server::mostrarCategorias), engine);
        
        post("/egreso",RouteWithTransaction(controllerEgresos::guardarEgreso));
        delete("/egreso/:id", controllerEgresos::eliminarEgreso);
        post("/egreso/:id", RouteWithTransaction(controllerEgresos::modificarEgreso));
        
        //INGRESOS
        get("/ingresos", controllerIngresos::ingresos, engine);
        get("/crearIngreso", Server::crearIngreso, engine);
        post("/ingreso",controllerIngresos::guardarIngreso);
        delete("/ingreso/:id", controllerIngresos::eliminarIngreso);
        get("/ingreso/:id", controllerIngresos::modificarIngreso,engine); ///VER EL POST
        post("/ingreso/:id", controllerIngresos::persistirIngreso);

        //acciones productos
        get("/productos",TemplWithTransaction(controllerProductos::productos),engine);
        get("/producto", controllerProductos::nuevoProducto, engine);
        get( "/producto/:id", TemplWithTransaction(controllerProductos::detalleProducto), engine);
        post("/producto", RouteWithTransaction(controllerProductos::guardarProducto));
        post("/producto/:id",controllerProductos::modificarProducto);

        delete("/producto/:id",RouteWithTransaction(controllerProductos::eliminarProducto));

        //validaciones
       get("/egreso/:id/validacion",(request,response) -> {
    	   return RouteWithTransaction((req, res, em) -> {
			try {
				return Validar(req, res, em);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return request;
		});
       });
       
       
        //ORDENES DE COMPRA

        get("/ordenes",controllerOrdenes::ordenes,engine);
        get("/crearOrden",TemplWithTransaction(controllerOrdenes::nuevaOrden),engine);
       // get("/orden/:id",,engine);
        post("/orden", controllerOrdenes::crear);
       // post("orden:id");

        //VINCULADOR

        get("/vinculaciones", controllerVinculador::vinculaciones, engine);
       // get("/vincular", controllerVinculador::vincular, engine);
        
        get("/vincular",(request,response) -> {
     	   return RouteWithTransaction((req, res, em) -> {
			try {
				return controllerVinculador.vincular(req, res, em);
			} catch (IOException | ListaVaciaExcepcion | MontoSuperadoExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return request;
     	  });
        });
        
        get("/working",Server::work,engine);
        

    }

    
    
    public static String Validar(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException, IOException {
    	
    	RepositorioEgreso repo = new RepositorioEgreso(entityManager);

    	String strID = request.params("id");

        int id = Integer.parseInt(strID);

        Egreso egreso = repo.byID(id);
        
        if (egreso == null) {
        	Map<String, Object> map = new HashMap<>();
        	map.put("egreso","No existe Egreso con id "+strID);
        	
        	map.put("informe","Egreso no v�lido");
        	
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

        Gson gson = new Gson();
        String JSON = gson.toJson(resultadoReporte);

        System.out.println(JSON);

      //esto por si queres chequearlo no mas

        
        String ruta = "resultadoEgreso"+strID+"Validacion.json";

        File file = new File(ruta);
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(JSON);
        bw.close();
             
       return JSON;
    }
    
    

    public static ModelAndView mostrarIndex(Request request, Response response) {
    	
    	if(request.session().attribute("user") == null )
    		response.redirect("/login");
        return new ModelAndView(null, "index.html");
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
        RepositorioEgreso repo = new RepositorioEgreso(entityManager);

        //DOMINIO
        List<Egreso> egresos = repo.todos();
 
         
   
        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("egresos", egresos);

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
    	
    	RepositorioOrdenDeCompra repoOrdenesCompra = new RepositorioOrdenDeCompra();
    	RepositorioPresupuesto repoPresupuestos = new RepositorioPresupuesto(entityManager);
    	RepositorioCategoria repoCategorias = new RepositorioCategoria();
    	
    	List<OrdenDeCompra> ordenes = repoOrdenesCompra.todos();
    	List<Presupuesto> presupuestos = repoPresupuestos.todos();
    	List<CategoriaDelSistema> categorias = repoCategorias.todos();
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);
        map.put("presupuestos", presupuestos);
        map.put("categorias", categorias);
        map.put("repoPresupuesto", repoPresupuestos);
    	
        return new ModelAndView(map ,"crearEgreso.html");
    }
    
    public static ModelAndView detalleEgreso(Request request, Response response,EntityManager entityManager) throws CloneNotSupportedException, MPRestException{
    	
    	RepositorioEgreso repo = new RepositorioEgreso(entityManager);
    	
    	String strID = request.params("id");
    	
    	int id = Integer.parseInt(strID);
    	
    	Egreso egreso = repo.byID(id);
    	
    	MedioDePago medioPagoEgreso = egreso.getPresupuesto().getMedioDePago();
    	
    	String nombreMedioPago = medioPagoEgreso.getPayment_type().toString();
    	
    	String imagenMedioPago = new api().getRouteByName(nombreMedioPago);
    	
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("egreso", egreso);
        
        if(medioPagoEgreso != null) {
        map.put("nombreMedioPago", nombreMedioPago);
        map.put("imagenMedioPago", imagenMedioPago);
        
        }
        
     
        return new ModelAndView(map,"detalleEgreso.html");
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
    	
    	RepositorioCategoria repoCategoria = new RepositorioCategoria();
    	
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
    		RepositorioIngreso repoIngresos = new RepositorioIngreso();
    		List<Ingreso> ingresos = repoIngresos.todos().stream().filter(a -> a.esDeCategoria(categoria)).collect(Collectors.toList());
    		map.put("documentos",ingresos);
    	}
    	
    	if(tipoDocumentoString.equals("Presupuestos")) {
    		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto();
    		Presupuesto pre = repoPresupuesto.byID(6);
    		Boolean resultado = pre.esDeCategoria(categoria);
    		List<Presupuesto> presupuestos = repoPresupuesto.todos().stream().filter(a -> a.esDeCategoria(categoria)).collect(Collectors.toList());
    		map.put("presupuestos",presupuestos);
    	}
    	
    	if(categoria != null && tipoDocumentoString != null) {
    		map.put("categoriaString",categoriaString);
    		map.put("tipoDocumento",tipoDocumentoString);
    	}
    	
    	map.put("categorias", categorias);
    	
		return new ModelAndView(map, "categorias.html");
    	
    	
    }

    //PRODUCTOS
    public static ModelAndView work(Request request, Response response){
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