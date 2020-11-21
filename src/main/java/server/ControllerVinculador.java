package server;

import Vinculador.*;
import auditoria.CantidadPresupuestos;
import auditoria.Reporte;
import auditoria.Validador;
import com.google.gson.Gson;
import com.mercadopago.resources.datastructures.merchantorder.Collector;
import egreso.Egreso;
import egreso.Ingreso;
import egreso.MontoSuperadoExcepcion;
import organizacion.EntidadJuridica;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioIngreso;
import repositorios.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CreationError;
import usuarios.Usuario;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import java.util.stream.Collectors;

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


    public ModelAndView vinculaciones(Request request, Response response, EntityManager entityManager) {

        if(request.session().attribute("user") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "ingresos.html");
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

        Map<String, Object> map = new HashMap<>();

        List<CriterioDeVinculacion> criterios = new ArrayList<>();
        CriterioDeVinculacion criterio1 = new PrimeroEgreso();
        criterio1.setNombre("primeroEgreso");
        CriterioDeVinculacion criterio2 = new PrimeroIngreso();
        criterio2.setNombre("primeroIngreso");
        criterios.add(criterio1);
        criterios.add(criterio2);
        // CriterioDeVinculacion criterio3= new Mix();
        //criterio3.setNombre("Mix");
        map.put("criterios", criterios);
        map.put("usuario", request.session().attribute("user"));
        map.put("usuarioActual", userActual);

        return new ModelAndView(map, "vinculaciones.html");
    }

    public String vincular(Request request, Response response, EntityManager entityManager) throws CloneNotSupportedException, IOException, ListaVaciaExcepcion, MontoSuperadoExcepcion {
        limpiarIngresos(entityManager);



        if (request.queryParams("criterio") != null) {
            String criterio = request.queryParams("criterio");

            Vinculador vinculador = new Vinculador();
            PrimeroEgreso primeroEgreso = new PrimeroEgreso();
            PrimeroIngreso primeroIngreso = new PrimeroIngreso();


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

            Usuario userActual = repoUser.byNombre(request.queryParams("user"));

            EntidadJuridica entidadJuridica = userActual.getOrganizacion().getEntidades().get(0);
            vinculador.setEntidadJuridica(entidadJuridica);



            /*
            String desdeFecha = request.params("fechaDesde");
            LocalDate desdeFechaFinal = LocalDate.parse(desdeFecha);

            String hastaFecha = request.params("fechaHasta");
            LocalDate hastaFechaFinal = LocalDate.parse(hastaFecha);*/
/*
            RepositorioIngreso repoIngreso = new RepositorioIngreso(entityManager);
            RepositorioEgreso repoEgreso = new RepositorioEgreso(entityManager);*/

            LocalDate desdeFechaFinal = null;
            if(request.queryParams("fechaDesde" )!= null) {
                String desdeFecha = request.queryParams("fechaDesde");
                desdeFechaFinal = LocalDate.parse(desdeFecha);
            }
            LocalDate hastaFechaFinal = null;
            if(request.queryParams("fechaHasta" )!=null) {
                String hastaFecha = request.queryParams("fechaHasta");
                hastaFechaFinal = LocalDate.parse(hastaFecha);
            }

            vinculador.obtenerIngresosEgresos(desdeFechaFinal, hastaFechaFinal);


            if (criterio.equals("primeroEgreso")) {
                vinculador.vincular(primeroEgreso);
            }
            if (criterio.equals("primeroIngreso")) {
                vinculador.vincular(primeroIngreso);
            }


            List<String> listaBalanceIngresos = vinculador.getBalanceIngresos().stream().map(i -> "Ingreso: " + i.getIngreso().getId()
                    + ", Descripcion:  " +i.getIngreso().getDescripcion() + ", Monto Total: "+i.getIngreso().getMonto() +", Monto Vinculado:" + i.getIngreso().getMontoVinculado()
            + "| Egresos: " + egresosLista(i.getEgresosVinculados())).collect(Collectors.toList());

            List<String> listaBalanceEgresos = vinculador.getBalanceEgresos().stream().map(i -> "Egreso: " + i.getEgreso().getId()
            + ", Monto: " +i.getEgreso().getValorTotal()+ ingresosLista(i.getIngresosVinculados(), i.getValorIngresos()))
                    .collect(Collectors.toList());


            Gson gson = new Gson();

            Map<String, Object> map = new HashMap<>();

            map.put("listaBalanceIngresos", listaBalanceIngresos);
            map.put("listaBalanceEgresos", listaBalanceEgresos);
            
           /* String JSON1 = gson.toJson(vinculador.getBalanceIngresos());
            String JSON2 = gson.toJson(vinculador.getBalanceEgresos());

            String JSON = JSON1 + JSON2;*/


            //return JSON;

            return gson.toJson(map);
            /* Gso
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

    private String ingresosLista(List<Ingreso> ingresosVinculados, List<Double> valorIngresos) {
        List<Integer> listaint = ingresosVinculados.stream().map(a -> a.getId()).collect(Collectors.toList());
        String lista = "Ingresos: "+listaint+ ", Valores: "+ valorIngresos;
           return lista;
    }

    private List<String> egresosLista(List<Egreso> egresosVinculados) {

        List<String> lista = egresosVinculados.stream().map(e -> "Id: " + e.getId() + ", Monto: " + e.getValorTotal()).collect(Collectors.toList());
                return lista;
    }

    private void limpiarIngresos(EntityManager entityManager) {
        RepositorioIngreso repo = new RepositorioIngreso(entityManager);
        repo.todos().forEach(a -> {
            try {
                a.setMontoVinculado(0.00);
            } catch (MontoSuperadoExcepcion montoSuperadoExcepcion) {
                montoSuperadoExcepcion.printStackTrace();
            }
        });
    }


    public static String devuelveJSON(String JSON) throws CloneNotSupportedException, IOException {
        return JSON;

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
}