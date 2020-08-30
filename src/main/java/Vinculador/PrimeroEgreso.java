package Vinculador;

import egreso.Balance;
import egreso.Egreso;
import egreso.Ingreso;
import egreso.MontoSuperadoExcepcion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimeroEgreso extends CriterioDeVinculacion {
    public PrimeroEgreso(List<CondicionObligatoria> condicionesObligatorias) {
        super(condicionesObligatorias);
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        ordenarValor(ingresos, egresos);

        boolean seAsigno = true; //PARA QUE SALGA DEL WHILE CUANDO YA NO SE PUEDA ASIGNAR MAS
        while(seAsigno&&egresos.size()>0) {
            seAsigno = false;

            for (int i = 0; i < ingresos.size(); i++) {

                int egresosTamanio = egresos.size();

                for (int j = 0; j < egresosTamanio ; j++) {
                    if (pasaCondiciones(ingresos.get(i), egresos.get(j))) {

                        ingresos.get(i).asociarEgreso(egresos.get(j));//AGREGO EGRESO A INGRESO
                        egresos.get(j).setIngresoAsociado(ingresos.get(i));//AGREGO INGRESO A EGRESO
                        seAsigno = true;//SE AVISA QUE SE ASIGNA PARA QUE NO SALGA DEL WHILE
                        egresos.remove(j);//SE RETIRA EGRESO
                        j = egresosTamanio;

                    }
                }
            }
        }
        /*Esto se tendria que ejecutar despues de que se haya dado toda la vuelta y no se pueda vincular mas uno a uno*/
        formarBalance(egresos,ingresos);
    }

    private void formarBalance(List<Egreso> egresos, List<Ingreso> ingresos) throws MontoSuperadoExcepcion {


        boolean salir = false;

        while(!egresos.isEmpty()){//While para ver de no quedarme sin egresos

            //ESTAS VARIABLES SE REINICIARIAN DESPUES DE CADA INTERACCION CON EL WHILE
            List<Ingreso> ingresosUsados=new ArrayList<Ingreso>();
            List<Double>  montoUsado = new ArrayList<Double>();
            double montoEgreso = egresos.get(0).valorTotal();
            double montoIngresos = 0.00;

            while(!ingresos.isEmpty() && !salir){//While para no quedarme sin ingresos
                montoIngresos += ingresos.get(0).getMontoSinVincular();
                if (montoEgreso<=montoIngresos) {
                    salir = true;

                    //Por si el ingreso luego de el balance tiene dinero disponible
                    if (montoEgreso<montoIngresos){
                        double montoQueQueda = ingresos.get(0).getMonto() - (montoIngresos - montoEgreso);
                        montoUsado.add(montoQueQueda - ingresos.get(0).getMontoSinVincular());
                        ingresosUsados.add(ingresos.get(0))
                        ingresos.get(0).setMontoVinculado(montoQueQueda);

                    }
                    else{
                        montoUsado.add(ingresos.get(0).getMontoSinVincular());
                        ingresosUsados.add(ingresos.remove(0));
                    }


                }
                if(!salir) { //Si el ingreso no le queda dinero luego del balance
                    montoUsado.add(ingresos.get(0).getMontoSinVincular());
                    ingresosUsados.add(ingresos.remove(0));
                }
            }//While ingresos

        if(montoEgreso<=montoIngresos){
            for(int i = 0;i < ingresosUsados.size()-1;i++){
                ingresosUsados.get(i).setMontoVinculado(ingresosUsados.get(i).getMonto());
            }
            Balance balance = new Balance();
            balance.setIngresosVinculados(ingresosUsados);
            balance.setValorIngresos(montoUsado);
            balance.setEgreso(egresos.get(0));

            //TODO TEST, DONDE GUARDAR BALANCE?
        }
            egresos.remove(0);
        }//While Egresos
    }


}
