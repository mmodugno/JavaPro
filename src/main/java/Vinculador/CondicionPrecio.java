package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

public class CondicionPrecio implements CondicionObligatoria {

    @Override
    public boolean cumpleCondicion(Ingreso ingreso, Egreso egreso) {

        return egreso.getValorTotal() <= ingreso.getMontoSinVincular();
        }


    }

