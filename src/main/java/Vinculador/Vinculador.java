package Vinculador;
import egreso.*;
import organizacion.EntidadJuridica;
import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;

public class Vinculador {

    private List<Egreso> egresosSinVincular;
    private List<Ingreso> ingresosSinVincular;
    private EntidadJuridica entidadJuridica;


    public Vinculador(Organizacion organizacion) {
        this.egresosSinVincular = new ArrayList<Egreso>();
        this.ingresosSinVincular = new ArrayList<Ingreso>();
        organizacion = organizacion;
    }

    void obtenerIngresosEgresos(){
        egresosSinVincular = entidadJuridica.getEgresos();//ACA EL FILTRO
        ingresosSinVincular = entidadJuridica.getIngresos();//ACA EL FILTRO
        //TODO FILTRAR

    }
    void vincular(CriterioDeVinculacion criterio){
        criterio.vincular(egresosSinVincular,ingresosSinVincular);
    }
}
