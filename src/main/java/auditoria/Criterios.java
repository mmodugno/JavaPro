package auditoria;

import egreso.CriterioSeleccion;
import egreso.Egreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;

public class Criterios extends CondicionValidacion {

	/*ATRIBUTOS*/
	private int idPresupuestoCriterio;
	private int idPresupuestoAceptado;
	private Presupuesto presupuestoCriterio;
	private Presupuesto presupuestoAceptado;

	/*GETTERS*/
	public int getIdPresupuestoCriterio() {
		return idPresupuestoCriterio;
	}
	public Presupuesto getPresupuestoCriterio() {
		return presupuestoCriterio;
	}
	public int getIdPresupuestoAceptado() {
		return idPresupuestoAceptado;
	}
	public Presupuesto getPresupuestoAceptado() {
		return presupuestoAceptado;
	}

	/*SETTERS*/
	public void setIdPresupuestoCriterio(int idPresupuestoCriterio) {
		this.idPresupuestoCriterio = idPresupuestoCriterio;
	}
	public void setIdPresupuestoAceptado(int idPresupuestoAceptado) {
		this.idPresupuestoAceptado = idPresupuestoAceptado;
	}
	public void setPresupuestoCriterio(Presupuesto presupuestoCriterio) {
		this.presupuestoCriterio = presupuestoCriterio;
	}
	public void setPresupuestoAceptado(Presupuesto presupuestoAceptado) {
		this.presupuestoAceptado = presupuestoAceptado;
	}

	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte) {
		
		nombre = "Criterio de Selección de Presupuesto";
		boolean validacion;
		CriterioSeleccion criterioDeSeleccion;
		
		criterioDeSeleccion = ordenDeCompra.getCriterioSeleccion();
		
		presupuestoCriterio = criterioDeSeleccion.seleccionar(ordenDeCompra);
		
		presupuestoAceptado = ordenDeCompra.presupuestoAceptado();
		
		if(ordenDeCompra.getPresupuestos().size() > 1) {		
			
			validacion = (presupuestoCriterio == presupuestoAceptado);
			
			this.idPresupuestoCriterio = ordenDeCompra.getPresupuestos().indexOf(presupuestoCriterio);
			
			this.idPresupuestoAceptado = ordenDeCompra.getPresupuestos().indexOf(presupuestoAceptado);
			
		} else {
			
			validacion = (presupuestoCriterio == presupuestoAceptado);
			
		}
		
		reporte.resultadoValidacionCriterios(this, validacion);
		
		return validacion;
		
	}
	@Override
	public boolean validar(OrdenDeCompra ordenDeCompra, Reporte reporte, Egreso egreso) {
		// TODO Auto-generated method stub
		return false;
	}

}
