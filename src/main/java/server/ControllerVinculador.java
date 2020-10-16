package server;

import Vinculador.*;
import auditoria.CantidadPresupuestos;
import auditoria.Reporte;
import auditoria.Validador;
import com.google.gson.Gson;
import egreso.Egreso;
import egreso.Ingreso;
import egreso.MontoSuperadoExcepcion;
import organizacion.EntidadJuridica;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioIngreso;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        criterio1.setNombre("primeroEgreso");
        //CriterioDeVinculacion criterio2= new PrimeroIngreso();
        //criterio2.setNombre("PrimeroIngreso");
        criterios.add(criterio1);
        //criterios.add(criterio2);
       // CriterioDeVinculacion criterio3= new Mix();
        //criterio3.setNombre("Mix");
        map.put("criterios",criterios);

        return new ModelAndView(map, "vinculaciones.html");
    }

    public ModelAndView vincular(Request request, Response response) throws CloneNotSupportedException, IOException, ListaVaciaExcepcion, MontoSuperadoExcepcion {



        if(request.queryParams("criterio") != null) {
            String criterio = request.queryParams("criterio");

            Vinculador vinculador2 = new Vinculador();
            PrimeroEgreso primeroEgreso = new PrimeroEgreso();
            EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
            vinculador2.setEntidadJuridica(entidadJuridica);

            RepositorioIngreso repoIngreso = new RepositorioIngreso();
            RepositorioEgreso repoEgreso = new RepositorioEgreso();
            entidadJuridica.setIngresos(repoIngreso.todos());
            entidadJuridica.setEgresos(repoEgreso.todos());
            vinculador2.obtenerIngresosEgresos();

            if(criterio.equals("primeroEgreso")){
                vinculador2.vincular(primeroEgreso);
            }

            Gson gson = new Gson();
            String JSON1 = gson.toJson(vinculador2.getBalanceIngresos());
            String JSON2 = gson.toJson(vinculador2.getBalanceEgresos());

            String JSON = JSON1 + JSON2;

            System.out.println(JSON);
            String ruta = "resultadoValidacion.json";

            File file = new File(ruta);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(JSON);
            bw.close();

            response.redirect("/vinculaciones");

        }

        return new ModelAndView(null,"index.html");
    }
}
