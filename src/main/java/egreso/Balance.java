package egreso;

import java.util.ArrayList;
import java.util.List;

public class Balance {

    /*No uso constructor por lo que se hablo en el seminario que dieron de persistencia
      Trae problemas con el hibernate, si usamos otra cosa y queremos lo creo */


    /*ATRIBUTOS*/
    Egreso egreso;



    List<Ingreso> ingresosVinculados = new ArrayList<Ingreso>();
    List<Double> valorIngresos = new ArrayList<Double>();

    /*SETTERS/ADD*/

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    public void setIngresosVinculados(List<Ingreso> ingresosVinculados) {
        this.ingresosVinculados = ingresosVinculados;
    }

    public void setValorIngresos(List<Double> valorIngresos) {
        this.valorIngresos = valorIngresos;
    }

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


}
