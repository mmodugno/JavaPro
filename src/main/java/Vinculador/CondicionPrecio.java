package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

public class CondicionPrecio implements CondicionObligatoria {

    @Override
    public boolean cumpleCondicion(Ingreso ingreso, Egreso egreso) {
        double montoIngreso = ingreso.getMonto();
        if(ingreso.getEgresosAsociados().size() > 0){
            montoIngreso -= ingreso.getEgresosAsociados().stream().mapToDouble(e -> e.valorTotal()).sum();

            }
        return egreso.valorTotal() < montoIngreso;
        }


    }

