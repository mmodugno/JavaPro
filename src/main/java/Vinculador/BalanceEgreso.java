package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

import java.util.ArrayList;
import java.util.List;

public class BalanceEgreso {
    /*ATRIBUTOS*/
    private int id;
    private Egreso egreso;
    private List<Ingreso> ingresosVinculados = new ArrayList<Ingreso>();
    private List<Double> valorIngresos = new ArrayList<Double>();

    /*GETTERS*/
    public Egreso getEgreso() {
        return egreso;
    }
    public List<Ingreso> getIngresosVinculados() {
        return ingresosVinculados;
    }
    public List<Double> getValorIngresos() {
        return valorIngresos;
    }

    public int getId() {
        return id;
    }

    /*SETTERS*/
    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }
    public void setIngresosVinculados(List<Ingreso> ingresosVinculados) {
        this.ingresosVinculados = ingresosVinculados;
    }
    public void setValorIngresos(List<Double> valorIngresos) {
        this.valorIngresos = valorIngresos;
    }

    public void setId(int id) {
        this.id = id;
    }
}
