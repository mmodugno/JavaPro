package Vinculador;
import egreso.*;
import organizacion.EntidadJuridica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void obtenerIngresosEgresos(LocalDate desde, LocalDate hasta) throws ListaVaciaExcepcion{
        if(entidadJuridica.getIngresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de ingresos de la entidad juruidica esta vacia");
        }
        if(entidadJuridica.getEgresos().isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de egresos de la entidad juridica esta vacia");
        }

        /**Siempre traigo todos los ingresos/egresos acá**/
        egresosSinVincular = new ArrayList<Egreso>(entidadJuridica.getEgresos());
        ingresosSinVincular = new ArrayList<Ingreso>(entidadJuridica.getIngresos());


        /**Lo que hago aca es limpiar si algun ingreso, por algun motivo tenia algo ya vinculado**/

        ingresosSinVincular.forEach( ingreso -> {
            try {
                ingreso.setMontoVinculado(0.00);
            } catch (MontoSuperadoExcepcion montoSuperadoExcepcion) {
                montoSuperadoExcepcion.printStackTrace();
            }
        });
        ingresosSinVincular = ingresosDesde(desde, ingresosSinVincular);
        ingresosSinVincular = ingresosHasta(hasta,ingresosSinVincular);
        egresosSinVincular = egresosDesde(desde,egresosSinVincular);
        egresosSinVincular = egresosHasta(hasta,egresosSinVincular);

        /*
        egresosSinVincular = filtrarEgresos(entidadJuridica.getEgresos());
        ingresosSinVincular = filtrarIngresos(entidadJuridica.getIngresos());*/

    }

    public void vincular(CriterioDeVinculacion criterio) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        criterio.vincular(egresosSinVincular,ingresosSinVincular,this);
    }

    public BalanceIngreso byID(int id) {

        BalanceIngreso ingreso = balanceIngresos.stream().filter(e -> e.getIngreso().getId() == id).findAny().get();


            return ingreso;


    }
    public static List<Ingreso> ingresosDesde(LocalDate fecha, List<Ingreso> ingresos) {
        return ingresos.stream().filter(i -> i.getFecha().isAfter(fecha)).collect(Collectors.toList());
    }

    public static List<Ingreso> ingresosHasta(LocalDate fecha, List<Ingreso> ingresos) {
        return ingresos.stream().filter(i -> i.getFecha().isBefore(fecha)).collect(Collectors.toList());
    }

    public static List<Egreso> egresosDesde(LocalDate fecha, List<Egreso> egresos) {
        return egresos.stream().filter(e -> e.getFecha().isAfter(fecha)).collect(Collectors.toList());
    }

    public static List<Egreso> egresosHasta(LocalDate fecha, List<Egreso> egresos) {
        return egresos.stream().filter(e -> e.getFecha().isBefore(fecha)).collect(Collectors.toList());
    }



}
