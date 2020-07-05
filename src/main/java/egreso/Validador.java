package egreso;

import java.util.List;

import auditoria.Reporte;
import producto.Item;

public class Validador {

	private CondicionValidacion condicion;
	private CriterioSeleccion criterioSeleccion;
	private boolean resultadoValidacion;
	private List<Boolean> validaciones;
	
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

	public void validarEgreso(Egreso egresoAvalidar) {

		// Inicio Validación de Egreso

		Reporte reporteValidacion = new Reporte();

		OrdenDeCompra ordenDeCompra = egresoAvalidar.getOrdenDeCompra();

		List<Item> itemsFaltantesEnPresupuesto;
		List<Item> itemsFaltantesEnCompra;
		
		reporteValidacion.setEgreso(egresoAvalidar);
		
		//Verifico cantidad de Presupuestos Necesarios para la Compra
		
		resultadoValidacion = condicion.validarCantidadPresupuestos(ordenDeCompra);
		
		validaciones.add(resultadoValidacion);
		
		//Verifico si Monto Compra es Igual Monto Presupuesto
		
		resultadoValidacion = condicion.validarMontoPresupuestoConCompra(ordenDeCompra);
		
		validaciones.add(resultadoValidacion);
		
		//Verifico si cantidad de Items son Iguales
		
		resultadoValidacion = condicion.validarCantidadItems(ordenDeCompra, ordenDeCompra.presupuestoAceptado());
		
		if(resultadoValidacion) {
			validaciones.add(resultadoValidacion);
		} else {
			validaciones.add(resultadoValidacion);
			itemsFaltantesEnPresupuesto = condicion.obtenerItemsFaltantes(ordenDeCompra.getItems(), ordenDeCompra.presupuestoAceptado().getItems());
			itemsFaltantesEnCompra = condicion.obtenerItemsFaltantes(ordenDeCompra.presupuestoAceptado().getItems(), ordenDeCompra.getItems());
		}
				
		// Envio resultado de las validaciones realizadas
		
		reporteValidacion.setValidaciones(validaciones);
		
		// Verifico si cumple Criterio de Selección

//		if(condicion.validarOrden(ordenCompra)){
//			Presupuesto presupuestoElegido = seleccionarPresupuesto(ordenDeCompra);
//			Egreso egreso = new Egreso(ordenDeCompra, presupuestoElegido);
//		}
		
		//ordenDeCompra.getRevisores().forEach(usuario -> usuario.egresoValidado(egreso));

	}
	
}
