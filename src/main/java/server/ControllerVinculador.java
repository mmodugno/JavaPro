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

import javax.persistence.EntityManager;

public class ControllerVinculador {

    RepositorioEgreso repositorioEgreso;
    RepositorioIngreso repositorioIngreso;
    Vinculador vinculador = new Vinculador();

    public ControllerVinculador() throws CloneNotSupportedException {

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

        List<CriterioDeVinculacion> criterios = new ArrayList<>();
        CriterioDeVinculacion criterio1= new PrimeroEgreso();
        criterio1.setNombre("primeroEgreso");
        CriterioDeVinculacion criterio2= new PrimeroIngreso();
        criterio2.setNombre("PrimeroIngreso");
        criterios.add(criterio1);
        criterios.add(criterio2);
       // CriterioDeVinculacion criterio3= new Mix();
        //criterio3.setNombre("Mix");
        map.put("criterios",criterios);

        return new ModelAndView(map, "vinculaciones.html");
    }

    public String vincular(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, IOException, ListaVaciaExcepcion, MontoSuperadoExcepcion {

        if(request.queryParams("criterio") != null) {
            String criterio = request.queryParams("criterio");

            Vinculador vinculador = new Vinculador();
            PrimeroEgreso primeroEgreso = new PrimeroEgreso();
            PrimeroIngreso primeroIngreso = new PrimeroIngreso();
            EntidadJuridica entidadJuridica = new EntidadJuridica("Web Social ONG", "Web Social", "90-61775331-4", 1143, 01, Collections.emptyList());
            vinculador.setEntidadJuridica(entidadJuridica);

            RepositorioIngreso repoIngreso = new RepositorioIngreso(entityManager);
            RepositorioEgreso repoEgreso = new RepositorioEgreso(entityManager);
            List<Ingreso> ingresos = repoIngreso.todos();
            entidadJuridica.setIngresos(ingresos);
            List<Egreso> egresos = repoEgreso.todos();
            entidadJuridica.setEgresos(egresos);
            vinculador.obtenerIngresosEgresos();

            if(criterio.equals("primeroEgreso")){
                vinculador.vincular(primeroEgreso);
            }
            if(criterio.equals("primeroIngreso")){
                vinculador.vincular(primeroIngreso);
            }

            Gson gson = new Gson();
            String JSON1 = gson.toJson(vinculador.getBalanceIngresos());
            String JSON2 = gson.toJson(vinculador.getBalanceEgresos());

            String JSON = JSON1 + JSON2;
            return JSON;
            /*
            System.out.println(JSON);
            devuelveJSON(JSON);
            
            String ruta = "resultadoValidacion.json";

            File file = new File(ruta);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(JSON);
            bw.close();

           // response.redirect("/vinculaciones");
         */

        }

       // return new ModelAndView(null,"index.html");
        return "Reintentar";
    }


public static String devuelveJSON(String JSON) throws CloneNotSupportedException, IOException {
	return JSON;

}
/*
    public static List<Ingreso> ingresosDesde(){}
    public static List<Ingreso> ingresosHasta(){}
    public static List<Egreso> egresosDesde(){}
    public static List<Egreso> egresosDesde(){}*/
}

/*
   public static String Validar(Request request, Response response) throws CloneNotSupportedException, IOException {
    	
    	RepositorioEgreso repo = new RepositorioEgreso();

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
 */
