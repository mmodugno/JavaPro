package Vinculador;
import egreso.*;
import organizacion.EntidadJuridica;
import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vinculador {

    private List<Egreso> egresosSinVincular;
    private List<Ingreso> ingresosSinVincular;
    private EntidadJuridica entidadJuridica;


    public Vinculador(EntidadJuridica entidadJuridica) {
        this.egresosSinVincular = new ArrayList<Egreso>();
        this.ingresosSinVincular = new ArrayList<Ingreso>();
        entidadJuridica = entidadJuridica;
    }

    void obtenerIngresosEgresos(){
        egresosSinVincular = filtrarEgresos(entidadJuridica.getEgresos());
        ingresosSinVincular = filtrarIngresos(entidadJuridica.getIngresos());

    }

    private List<Ingreso> filtrarIngresos(List<Ingreso> ingresos) {
        return ingresos.stream().filter(ingreso -> ingreso.getEgresosAsociados().size() == 0).collect(Collectors.toList());
    }

    private List<Egreso> filtrarEgresos(List<Egreso> egresos) {
        return egresos.stream().filter(egreso -> egreso.getIngresoAsociado() == null).collect(Collectors.toList());
    }

    void vincular(CriterioDeVinculacion criterio){
        criterio.vincular(egresosSinVincular,ingresosSinVincular);
    }
}
