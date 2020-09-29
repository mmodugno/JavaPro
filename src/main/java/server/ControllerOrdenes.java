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

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("ordenes", ordenes);

        return new ModelAndView(map, "ordenes.html");
    }


}
