package egreso;

import usuarios.Usuario;

public class Validador {

	private CondicionValidacion condicion;
	private CriterioSeleccion criterioSeleccion;
	
	public Validador( CondicionValidacion condicion, CriterioSeleccion criterioSeleccion) {
		super();
		this.condicion = condicion;
		this.criterioSeleccion = criterioSeleccion;
	}


	public CondicionValidacion getCondicion() {
		return condicion;
	}
	public CriterioSeleccion getCriterioSeleccion() {
		return criterioSeleccion;
	}
	
	public Presupuesto seleccionarPresupuesto(OrdenDeCompra ordenDeCompra) {

		Presupuesto presu= criterioSeleccion.seleccionar(ordenDeCompra);
		return presu;

	}
	
	public void validarOrden(OrdenDeCompra ordenDeCompra) throws ErrorDeValidacion {
		
		if(condicion.validarOrden(ordenDeCompra)){
			Presupuesto presupuestoElegido = seleccionarPresupuesto(ordenDeCompra);
			Egreso egreso = new Egreso(ordenDeCompra, presupuestoElegido);
			ordenDeCompra.getRevisores().forEach(usuario -> usuario.egresoValidado(egreso));


		}else{
			throw new ErrorDeValidacion("Error en validacion");
		}
		
	}


	
	
	
}
