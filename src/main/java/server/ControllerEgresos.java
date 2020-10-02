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

*/

    public ModelAndView modificarEgresoGet(Request request, Response response) throws CloneNotSupportedException {

		String strID = request.params("id");
		int id = Integer.parseInt(strID);
		Egreso egreso = repo.byID(id);

		RepositorioOrdenDeCompra ordenes = new RepositorioOrdenDeCompra();

		Map<String, Object> map = new HashMap<>();
		map.put("egreso", egreso);

		//HAY QUE VER COMO PASAR TODOS LOS REPOS, LO UNICO QUE SE ME OCURRE PASARLE TODOS LOS REPOS AL HTML. todo Charlar

		return new ModelAndView(map,"formularioEgresos.html");
	}

    public static void asignarParametros(Egreso egreso, Request request) throws CloneNotSupportedException {

		RepositorioOrdenDeCompra repoOrden = new RepositorioOrdenDeCompra();
		RepositorioPresupuesto repoPresupuesto = new RepositorioPresupuesto();
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

	public Response modificarEgreso(Request request, Response response) throws CloneNotSupportedException {
		String strID = request.params("id");
		int id = new Integer(strID);
		Egreso egreso = repo.byID(id);

		asignarParametros(egreso, request);

		response.redirect("/egresos");

		return response;

	}
    //ESTE ES EL QUE CREA
    public Response guardarEgreso(Request request, Response response) throws CloneNotSupportedException{

        Egreso egreso = new Egreso();
        egreso.setId(repo.proximoId());

		asignarParametros(egreso, request);
        
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
