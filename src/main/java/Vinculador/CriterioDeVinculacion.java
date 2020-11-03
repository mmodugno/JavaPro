package Vinculador;

import egreso.*;
import organizacion.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class CriterioDeVinculacion {

    private String nombre = "";

    public CriterioDeVinculacion() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    void vincular(List<Egreso> egresos, List<Ingreso> ingresos, Vinculador vinculador) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {}


    void ordenarValor(List<Ingreso> ingresos, List<Egreso> egresos) throws ListaVaciaExcepcion{
        if(ingresos.isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de ingresos esta vacia");
        }
        if(egresos.isEmpty()) {
            throw new ListaVaciaExcepcion("La lista de egresos esta vacia");
        }
        ingresos.sort((Ingreso unIngreso, Ingreso otroIngreso) -> {
            return ordenarDouble(unIngreso.getMontoSinVincular(), otroIngreso.getMontoSinVincular());
        });
        egresos.sort((Egreso unEgreso, Egreso otroEgreso) -> {
            return ordenarDouble(unEgreso.getValorTotal(), otroEgreso.getValorTotal());
        });

    }

    boolean pasaCondiciones(Ingreso ingreso, Egreso egreso){

        return true;
    }

    int ordenarDouble(double primero,double segundo){
        if (primero > segundo) return -1;
        if (primero < segundo) return 1;
        return 0;

    }

    void formarBalanceEgreso(List<Egreso> egresos, List<Ingreso> ingresos, Vinculador vinculador) throws MontoSuperadoExcepcion {


        boolean salir = false;

        while (!egresos.isEmpty()) {//While para ver de no quedarme sin egresos

            //ESTAS VARIABLES SE REINICIARIAN DESPUES DE CADA INTERACCION CON EL WHILE
            List<Ingreso> ingresosUsados = new ArrayList<Ingreso>();
            List<Double> montoUsado = new ArrayList<Double>();
            double montoEgreso = egresos.get(0).getValorTotal();
            double montoIngresos = 0.00;

            while (!ingresos.isEmpty() && !salir) {//While para no quedarme sin ingresos
                montoIngresos += ingresos.get(0).getMontoSinVincular();
                if (montoEgreso <= montoIngresos) {
                    salir = true;

                    //Por si el ingreso luego de el balance tiene dinero disponible
                    if (montoEgreso < montoIngresos) {
                        double montoQueQueda = ingresos.get(0).getMontoSinVincular() - (montoIngresos - montoEgreso);
                        montoUsado.add(ingresos.get(0).getMontoSinVincular() - (montoIngresos - montoEgreso));
                        ingresosUsados.add(ingresos.get(0));
                        ingresos.get(0).setMontoVinculado(ingresos. get(0).getMonto() - montoQueQueda);

                    } else {
                        montoUsado.add(ingresos.get(0).getMontoSinVincular());
                        ingresosUsados.add(ingresos.remove(0));
                    }


                }
                if (!salir) { //Si el ingreso no le queda dinero luego del balance
                    montoUsado.add(ingresos.get(0).getMontoSinVincular());
                    ingresosUsados.add(ingresos.remove(0));
                }
            }//While ingresos

            if (montoEgreso <= montoIngresos) {
                for (int i = 0; i < ingresosUsados.size() - 1; i++) {
                    ingresosUsados.get(i).setMontoVinculado(ingresosUsados.get(i).getMonto());
                }
                BalanceEgreso balance = new BalanceEgreso();
                balance.setIngresosVinculados(ingresosUsados);
                balance.setValorIngresos(montoUsado);
                balance.setEgreso(egresos.get(0));
                vinculador.getBalanceEgresos().add(balance);

                //TODO TEST
            }
            egresos.remove(0);
        }//While Egresos
    }


}
