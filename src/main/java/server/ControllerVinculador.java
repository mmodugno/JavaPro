package server;

import Vinculador.*;
import egreso.Ingreso;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioIngreso;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerVinculador {

    RepositorioEgreso repositorioEgreso;
    RepositorioIngreso repositorioIngreso;
    Vinculador vinculador = new Vinculador();

    public ControllerVinculador() throws CloneNotSupportedException {
        repositorioEgreso = new RepositorioEgreso();
        repositorioIngreso = new RepositorioIngreso();

    }

    public RepositorioEgreso getRepositorioEgreso() {
        return repositorioEgreso;
    }

    public void setRepositorioEgreso(RepositorioEgreso repositorioEgreso) {
        this.repositorioEgreso = repositorioEgreso;
    }

    public RepositorioIngreso getRepositorioIngreso() {
        return repositorioIngreso;
    }

    public void setRepositorioIngreso(RepositorioIngreso repositorioIngreso) {
        this.repositorioIngreso = repositorioIngreso;
    }

    public Vinculador getVinculador() {
        return vinculador;
    }

    public void setVinculador(Vinculador vinculador) {
        this.vinculador = vinculador;
    }


    public ModelAndView vinculaciones(Request request, Response response){


        Map<String, Object> map = new HashMap<>();
        List<BalanceEgreso> balances = new ArrayList<>();
        List<Ingreso> ingresos = new ArrayList<>();
        if(request.queryParams("mostrar") != null) {
            String mostrar = request.queryParams("mostrar");
            if (mostrar != null) {

                if (mostrar.equals("balances")) {

                    map.remove("ingresos", ingresos);

                    BalanceEgreso balance = new BalanceEgreso();
                    balance.setId(0303456);
                    balances.add(balance);

                    map.put("balances", balances);
                } else if (mostrar.equals("ingresos")) {

                    map.remove("balances", balances);

                    ingresos = repositorioIngreso.todos();
                    map.put("ingresos", ingresos);
                }
            }
        }
        List<CriterioDeVinculacion> criterios = new ArrayList<>();
        CriterioDeVinculacion criterio1= new PrimeroEgreso();
        criterio1.setNombre("Uno");
        CriterioDeVinculacion criterio2= new PrimeroEgreso();
        criterio2.setNombre("DOs");
        criterios.add(criterio1);
        criterios.add(criterio2);
        map.put("criterios",criterios);

        return new ModelAndView(map, "vinculaciones.html");
    }
}
