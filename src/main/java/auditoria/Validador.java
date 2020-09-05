package auditoria;

import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.OrdenDeCompra;

public class Validador {

	/*ATRIBUTOS*/
	List<CondicionValidacion> condicionesValidacion;
	Reporte reporteValidacion;
	private boolean resultadoValidacion;


	/*GETTERS*/

	public List<CondicionValidacion> getCondicionesValidacion() {
		return condicionesValidacion;
	}
	public Reporte getReporteValidacion() {
		return reporteValidacion;
	}
	public boolean isResultadoValidacion() {
		return resultadoValidacion;
	}

	/*SETTERS*/

	public void setCondicionesValidacion(List<CondicionValidacion> condicionesValidacion) {
		this.condicionesValidacion = condicionesValidacion;
	}
	public void setReporteValidacion(Reporte reporteValidacion) {
		this.reporteValidacion = reporteValidacion;
	}
	public void setResultadoValidacion(boolean resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}

	/* Adentro del constructor si se pueden realizar acciones, lo que no se puede en hibernate o le molesta un poco es
	* que le pases atributos por paramentros */
	public Validador() {
		condicionesValidacion = new ArrayList<CondicionValidacion>();
		reporteValidacion = new Reporte();
	}

	public boolean validarEgreso(Egreso egresoAvalidar) {
		
		reporteValidacion.setEgreso(egresoAvalidar);
		
		boolean resultadoValidacion;
		
		OrdenDeCompra ordenDeCompra;
		
		ordenDeCompra = egresoAvalidar.getOrdenDeCompra();
		
		resultadoValidacion = condicionesValidacion.stream().map(unaCondicion -> this.ejecutarCondicion(unaCondicion, ordenDeCompra)).reduce(Boolean::logicalAnd).orElse(false);
		
		reporteValidacion.setResultadoValidacion(resultadoValidacion);
		
		ordenDeCompra.getRevisores().forEach(usuario -> usuario.egresoValidado(reporteValidacion));
		
		System.out.println(reporteValidacion.getInforme());
		
		return resultadoValidacion;
	}

	public void agregarCondicionValidacion(CondicionValidacion condicionValidacion) {
		condicionesValidacion.add(condicionValidacion);
	}
	
	private boolean ejecutarCondicion(CondicionValidacion unaCondicion, OrdenDeCompra ordenDeCompra) {
		boolean validacion;
		validacion = unaCondicion.validar(ordenDeCompra, reporteValidacion);
		
		return validacion;
	}

}
