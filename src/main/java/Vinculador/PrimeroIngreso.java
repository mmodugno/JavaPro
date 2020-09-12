package Vinculador;

import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.Ingreso;
import organizacion.EntidadJuridica;

public class PrimeroIngreso extends CriterioDeVinculacion {

    public PrimeroIngreso(List<CondicionObligatoria> condicionesObligatorias) {
        super(condicionesObligatorias);
    }

    @Override
    void vincular(List<Egreso> egresos, List<Ingreso> ingresos, EntidadJuridica entidadJuridica) throws ListaVaciaExcepcion {
        ordenarValor(ingresos, egresos);
        for(int i = 0; i<egresos.size(); i++){
            List<Integer> indexDestruir = new ArrayList<Integer>();
            for(int j = 0; j<ingresos.size(); j++){
                if(pasaCondiciones(ingresos.get(j),egresos.get(i))){
                    ingresos.get(j).asociarEgreso(egresos.get(i));
                    egresos.get(i).setVinculado(true);//       AGREGO INGRESO A EGRESO
                    indexDestruir.add(i);
                }
                for(int z = 0; z<indexDestruir.size(); z++)
                {
                    ingresos.remove(indexDestruir.get(z)-z);
                }
            }
        }

    }
}
