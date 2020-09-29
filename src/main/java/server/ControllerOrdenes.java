package server;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioOrdenDeCompra;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerOrdenes {

    RepositorioOrdenDeCompra repo = null;

    public ControllerOrdenes() throws CloneNotSupportedException {
        repo = new RepositorioOrdenDeCompra();
    }

    public ModelAndView ordenes(Request request, Response response) throws CloneNotSupportedException {

        //DOMINIO
        List<OrdenDeCompra> ordenes = repo.todos();
        /** **PARA PROBAR SI ANDA EL CERRAR** **/
        repo.todos().get(0).setCerrado(true);
        /** No las marca como cerrada si o no porque técnicamente las creamos de 0 y no son creadas con el egreso.
         Tener en cuenta a la hora de probar la funcionalidad completa **/

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);

        return new ModelAndView(map, "ordenes.html");
    }

    public ModelAndView nuevaOrden(Request request, Response response){
        return new ModelAndView(null,"formularioOrden.html");
    }


}
