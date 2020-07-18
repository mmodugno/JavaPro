package auditoria;

import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.OrdenDeCompra;

public class Validador {

	List<CondicionValidacion> condicionesValidacion;
	Reporte reporteValidacion;
	
	private boolean resultadoValidacion;
	
	public Validador() {
		condicionesValidacion = new ArrayList<CondicionValidacion>();
		reporteValidacion = new Reporte();
	}

	public boolean validarEgreso(Egreso egresoAvalidar) {
		
		reporteValidacion.setEgreso(egresoAvalidar);
		
		boolean resultadoValidacion;
		
		OrdenDeCompra ordenDeCompra;
		
		ordenDeCompra = egresoAvalidar.getOrdenDeCompra();
		
		resultadoValidacion = condicionesValidacion.stream().allMatch(unaCondicion -> this.ejecutarCondicion(unaCondicion, ordenDeCompra));
		
		ordenDeCompra.getRevisores().forEach(usuario -> usuario.egresoValidado(reporteValidacion));
		
		return resultadoValidacion;
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
