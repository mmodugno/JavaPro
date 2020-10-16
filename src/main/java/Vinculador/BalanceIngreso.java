package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

import java.util.ArrayList;
import java.util.List;

public class BalanceIngreso {

    public BalanceIngreso(){}
    /*ATRIBUTOS*/
    private int id;
    private Ingreso ingreso;
    private List<Egreso> egresosVinculados = new ArrayList<Egreso>();
    private List<Double> valorEgresos = new ArrayList<Double>();


    //GETTERS

    public Ingreso getIngreso() {
        return ingreso;
    }

    public int getId() {
        return id;
    }

    public List<Egreso> getEgresosVinculados() {
        return egresosVinculados;
    }

    public List<Double> getValorEgresos() {
        return valorEgresos;
    }
    //SETTERS


    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }
    public void asociarEgreso(Egreso egreso){
        egresosVinculados.add(egreso);
    }


}
