package Vinculador;

import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.MontoSuperadoExcepcion;
import organizacion.EntidadJuridica;

public class PrimeroIngreso extends CriterioDeVinculacion {

    public PrimeroIngreso() {
        super();
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos, Vinculador vinculador) throws ListaVaciaExcepcion, MontoSuperadoExcepcion {
        ordenarValor(ingresos, egresos);


            boolean seAsigno = true;

            while(seAsigno && egresos.size()>0 && ingresos.size()>0){
                seAsigno = false;
                int tam_egresos = egresos.size();
                for (int e = 0; e<tam_egresos; e++) {
                    Egreso egreso = egresos.get(e);
                    for (int z = 0; z < ingresos.size(); z++) {
                        BalanceIngreso balanceIngreso = null;
                        if (egreso.getValorTotal() <= ingresos.get(z).getMontoSinVincular()) {

                            if (vinculador.getBalanceIngresos().contains(ingresos.get(z))) {
                                balanceIngreso = vinculador.byID(ingresos.get(z).getId());
                            } else {
                                balanceIngreso = new BalanceIngreso();
                                balanceIngreso.setIngreso(ingresos.get(z));
                            }
                            ingresos.get(z).asociarEgreso(egreso.getValorTotal());
                            balanceIngreso.asociarEgreso(egresos.remove(e));
                            e -= 1;
                            seAsigno = true;
                            z=ingresos.size();
                        }
                        if(balanceIngreso != null) {
                            vinculador.getBalanceIngresos().add(balanceIngreso);
                        }
                    }

                }
            }
        formarBalanceEgreso(egresos,ingresos,vinculador);
        }

    }

