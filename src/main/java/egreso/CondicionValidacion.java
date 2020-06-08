package egreso;

public class CondicionValidacion {


    public boolean validarOrden(OrdenDeCompra ordenDeCompra){

        int cantidadPresupuestos = ordenDeCompra.getPresupuestos().size();

        return ordenDeCompra.getNecesitaPresupuesto() <= cantidadPresupuestos;
    }
}
