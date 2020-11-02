package Vinculador;
import egreso.*;
import organizacion.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Vinculador {

    /*CONSTRUCTOR*/
    public Vinculador(EntidadJuridica unaEntidadJuridica) {
        balanceEgresos = new ArrayList<BalanceEgreso>();
        balanceIngresos = new ArrayList<BalanceIngreso>();
        this.egresosSinVincular = new ArrayList<Egreso>();
        this.ingresosSinVincular = new ArrayList<Ingreso>();
        this.condiciones = new ArrayList<CondicionObligatoria>();
        entidadJuridica = unaEntidadJuridica;
    }
    /*setEntidadJuridica(entidadJuridica)*/

    /*ATRIBUTOS*/
    private List<Egreso> egresosSinVincular;
    private List<Ingreso> ingresosSinVincular;
    private EntidadJuridica entidadJuridica;
    /**Nuevos Balances y las condiciones**/
    private List<BalanceEgreso> balanceEgresos;
    private List<BalanceIngreso> balanceIngresos;
    private List<CondicionObligatoria> condiciones;


    public Vinculador() {
        balanceEgresos = new ArrayList<BalanceEgreso>();
        balanceIngresos = new ArrayList<BalanceIngreso>();
        this.egresosSinVincular = new ArrayList<Egreso>();
        this.ingresosSinVincular = new ArrayList<Ingreso>();
        this.condiciones = new ArrayList<CondicionObligatoria>();
    }

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

    public List<BalanceEgreso> getBalanceEgresos() {
        return balanceEgresos;
    }
    public List<BalanceIngreso> getBalanceIngresos() {
        return balanceIngresos;
    }
    public List<CondicionObligatoria> getCondiciones() {
        return condiciones;
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

    public void setBalanceEgresos(List<BalanceEgreso> balanceEgresos) {
        this.balanceEgresos = balanceEgresos;
    }
    public void setBalanceIngresos(List<BalanceIngreso> balanceIngresos) {
        this.balanceIngresos = balanceIngresos;
    }
    public void setCondiciones(List<CondicionObligatoria> condiciones) {
        this.condiciones = condiciones;
    }

    public void obtenerIngresosEgresos() throws ListaVaciaExcepcion{
        if(entidadJuridica.getIngresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de ingresos de la entidad juruidica esta vacia");
        }
        if(entidadJuridica.getEgresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de egresos de la entidad juridica esta vacia");
        }

        /**Siempre traigo todos los ingresos/egresos acá**/
        egresosSinVincular = entidadJuridica.getEgresos();
        ingresosSinVincular = entidadJuridica.getIngresos();


        /**Lo que hago aca es limpiar si algun ingreso, por algun motivo tenia algo ya vinculado**/

        ingresosSinVincular.forEach( ingreso -> {
            try {
                ingreso.setMontoVinculado(0.00);
            } catch (MontoSuperadoExcepcion montoSuperadoExcepcion) {
                montoSuperadoExcepcion.printStackTrace();
            }
        });


        /*
        egresosSinVincular = filtrarEgresos(entidadJuridica.getEgresos());
        ingresosSinVincular = filtrarIngresos(entidadJuridica.getIngresos());*/

    }

    public void vincular(CriterioDeVinculacion criterio) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        criterio.vincular(egresosSinVincular,ingresosSinVincular,this);
    }

    public BalanceIngreso byID(int id) {

        BalanceIngreso ingreso = balanceIngresos.stream().filter(e -> e.getIngreso().getId() == id).findFirst().get();


            return ingreso;


    }



}
