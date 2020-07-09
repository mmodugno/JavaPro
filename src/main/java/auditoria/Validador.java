package auditoria;

import java.util.List;

import egreso.Egreso;
import egreso.OrdenDeCompra;

public class Validador {

	List<CondicionValidacion> condicionesValidacion;
	Reporte reporteValidacion;
	
	private boolean resultadoValidacion;

	public boolean validarEgreso(Egreso egresoAvalidar) {
		
		reporteValidacion.setEgreso(egresoAvalidar);
		
		OrdenDeCompra ordenDeCompra;
		
		ordenDeCompra = egresoAvalidar.getOrdenDeCompra();
		
		return condicionesValidacion.stream().allMatch(unaCondicion -> this.ejecutarCondicion(unaCondicion, ordenDeCompra));
		
	}
	
	public void agregarCondicionValidacion(CondicionValidacion condicionValidacion) {
		condicionesValidacion.add(condicionValidacion);
	}
	
	private boolean ejecutarCondicion(CondicionValidacion unaCondicion, OrdenDeCompra ordenDeCompra) {
		boolean validacion;
		validacion = unaCondicion.validar(ordenDeCompra);
		
		reporteValidacion.agregarResultadoValidacion(unaCondicion, validacion);
		
		return validacion;
	}

}
