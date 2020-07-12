package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

public class CondicionFecha implements CondicionObligatoria {


    @Override
    public boolean cumpleCondicion(Ingreso ingreso, Egreso egreso) {
        //TODO FALTA COMPARAR FECHAS
        return true;
    }
}
