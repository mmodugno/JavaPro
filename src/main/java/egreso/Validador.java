package egreso;

import java.util.List;

import auditoria.Reporte;
import producto.Item;

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

	public void validarEgreso(Egreso egresoAvalidar) {
		
		// Inicio Validación de Egreso
		
		Reporte reporteValidacion = new Reporte();
		
		OrdenDeCompra ordenCompra = egresoAvalidar.getOrdenDeCompra();
		
		List<Item> itemsFaltantesEnPresupuesto;
		List<Item> itemsFaltantesEnCompra;
		
		//Verifico cantidad de Presupuestos Necesarios para la Compra
		
		reporteValidacion.setPresupuestos(ordenCompra.getNecesitaPresupuesto(), ordenCompra.getPresupuestos().size());
		
		reporteValidacion.validacionCantidadDePresupuestos(condicion.validarCantidadPresupuestos(ordenCompra));
		
		//Verifico si Monto Compra es Igual Monto Presupuesto
		
		reporteValidacion.setMontos(ordenCompra.valorTotal(), ordenCompra.presupuestoAceptado().valorTotal());
		
		reporteValidacion.validacionMontosCompraPresupuesto(condicion.validarMontoPresupuestoConCompra(ordenCompra));
		
		//Verifico si cantidad de Items son Iguales
		
		reporteValidacion.setCantidadItems(ordenCompra.getItems().size(), ordenCompra.presupuestoAceptado().getItems().size());
		
		if(condicion.validarCantidadItems(ordenCompra, ordenCompra.presupuestoAceptado())) {
			reporteValidacion.validacionCantidadItems(true);
		} else {
			reporteValidacion.validacionCantidadItems(false);
			if(ordenCompra.getItems().size() > ordenCompra.presupuestoAceptado().getItems().size()) {
				itemsFaltantesEnPresupuesto = ordenCompra.getItems();
				itemsFaltantesEnPresupuesto.removeAll(ordenCompra.presupuestoAceptado().getItems());
				reporteValidacion.itemsFaltantesPresupuesto(itemsFaltantesEnPresupuesto);
			} else {
				itemsFaltantesEnCompra = ordenCompra.presupuestoAceptado().getItems();
				itemsFaltantesEnCompra.removeAll(ordenCompra.getItems());
				reporteValidacion.itemsFaltantesCompra(itemsFaltantesEnCompra);
			}
		}
		
		
		
		// Verifico si cumple Criterio de Selección
		
		
		
//		if(condicion.validarOrden(ordenCompra)){
//			Presupuesto presupuestoElegido = seleccionarPresupuesto(ordenDeCompra);
//			Egreso egreso = new Egreso(ordenDeCompra, presupuestoElegido);
//		}
		
		//ordenDeCompra.getRevisores().forEach(usuario -> usuario.egresoValidado(egreso));
		
	}


	
	
	
}
