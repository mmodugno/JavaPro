package Vinculador;

import egreso.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimeroEgreso extends CriterioDeVinculacion {

    public PrimeroEgreso() {
        super();
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos, Vinculador vinculador) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        ordenarValor(ingresos, egresos);

        boolean seAsigno = true; //PARA QUE SALGA DEL WHILE CUANDO YA NO SE PUEDA ASIGNAR MAS
        int tam_ingresos = ingresos.size();
        while(seAsigno&&egresos.size()>0) {
            seAsigno = false;

            for (int i = 0; i < tam_ingresos; i++) {
                BalanceIngreso balanceIngreso;

                if(vinculador.getBalanceIngresos().contains(ingresos.get(i))) {
                    balanceIngreso = vinculador.byID(ingresos.get(i).getId());
                }
                else{
                    balanceIngreso = new BalanceIngreso();
                    balanceIngreso.setIngreso(ingresos.get(i));
                }

                int egresosTamanio = egresos.size();

                for (int j = 0; j < egresosTamanio ; j++) {
                    if (pasaCondiciones(ingresos.get(i), egresos.get(j))) {

                        ingresos.get(i).asociarEgreso(egresos.get(j).getValorTotal());//AGREGO Monto del egreso
                        balanceIngreso.asociarEgreso(egresos.remove(j));
                        seAsigno = true;//SE AVISA QUE SE ASIGNA PARA QUE NO SALGA DEL WHILE
                        j = egresosTamanio;
                    }
                }
                vinculador.getBalanceIngresos().add(balanceIngreso);
            }
        }
        /*Esto se tendria que ejecutar despues de que se haya dado toda la vuelta y no se pueda vincular mas uno a uno*/
        formarBalanceEgreso(egresos,ingresos,vinculador);
    }





}
