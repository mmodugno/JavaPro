package server;

import Vinculador.*;
import repositorios.RepositorioEgreso;
import repositorios.RepositorioIngreso;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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

        return new ModelAndView(null, "vinculaciones.html");
    }
}
