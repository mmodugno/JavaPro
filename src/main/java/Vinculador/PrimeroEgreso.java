package Vinculador;

import egreso.Egreso;
import egreso.Ingreso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimeroEgreso extends CriterioDeVinculacion {
    public PrimeroEgreso(List<CondicionObligatoria> condicionesObligatorias) {
        super(condicionesObligatorias);
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) {
        ordenarValor(ingresos, egresos);

        boolean seAsigno = true; //PARA QUE SALGA DEL WHILE CUANDO YA NO SE PUEDA ASIGNAR MAS
        while(seAsigno&&egresos.size()>0) {
            seAsigno = false;

            for (int i = 0; i < ingresos.size(); i++) {

                int egresosTamaño = egresos.size();
                int egresosRetirados = 0;
                for (int j = 0; j < egresosTamaño ; j++) {
                    if (pasaCondiciones(ingresos.get(i), egresos.get(j-egresosRetirados))) {

                        ingresos.get(i).asociarEgreso(egresos.get(j-egresosRetirados));//AGREGO EGRESO A INGRESO
                        egresos.get(j-egresosRetirados).setIngresoAsociado(ingresos.get(i));//AGREGO INGRESO A EGRESO
                        seAsigno = true;//SE AVISA QUE SE ASIGNA PARA QUE NO SALGA DEL WHILE
                        egresosRetirados += 1;//SE SUMA UN EGRESO POR SI TIENE QUE VOLVER A REVISAR, LO DEJO AUNQUE CREO QUE YA NO ES NECESARIO
                        egresos.remove(j-egresosRetirados);//SE RETIRA EGRESO

                    }
                }
            }
        }

    }
}
