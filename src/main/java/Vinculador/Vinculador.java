package Vinculador;
import egreso.*;
import organizacion.EntidadJuridica;
import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vinculador {

    /*CONSTRUCTOR*/
    public Vinculador(EntidadJuridica unaEntidadJuridica) {
        this.egresosSinVincular = new ArrayList<Egreso>();
        this.ingresosSinVincular = new ArrayList<Ingreso>();
        entidadJuridica = unaEntidadJuridica;
    }
    /*setEntidadJuridica(entidadJuridica)*/

    /*ATRIBUTOS*/
    private List<Egreso> egresosSinVincular;
    private List<Ingreso> ingresosSinVincular;
    private EntidadJuridica entidadJuridica;

    /*GETTERS*/

    public List<Egreso> getEgresosSinVincular() {
        return egresosSinVincular;
    }
    public List<Ingreso> getIngresosSinVincular() {
        return ingresosSinVincular;
    }
    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    /*SETTERS*/
    public void setEgresosSinVincular(List<Egreso> egresosSinVincular) {
        this.egresosSinVincular = egresosSinVincular;
    }
    public void setIngresosSinVincular(List<Ingreso> ingresosSinVincular) {
        this.ingresosSinVincular = ingresosSinVincular;
    }
    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }



    void obtenerIngresosEgresos() throws ListaVaciaExcepcion{
        if(entidadJuridica.getIngresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de ingresos de la entidad juruidica esta vacia");
        }
        if(entidadJuridica.getEgresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de egresos de la entidad juridica esta vacia");
        }
        egresosSinVincular = filtrarEgresos(entidadJuridica.getEgresos());
        ingresosSinVincular = filtrarIngresos(entidadJuridica.getIngresos());

    }

    private List<Ingreso> filtrarIngresos(List<Ingreso> ingresos) {
        return ingresos.stream().filter(ingreso -> ingreso.puedoVincular()).collect(Collectors.toList());
    }

    private List<Egreso> filtrarEgresos(List<Egreso> egresos) {
        return egresos.stream().filter(egreso -> egreso.isVinculado()).collect(Collectors.toList());
    }

    void vincular(CriterioDeVinculacion criterio) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        criterio.vincular(egresosSinVincular,ingresosSinVincular,entidadJuridica);
    }

}
