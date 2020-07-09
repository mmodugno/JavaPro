package auditoria;

import java.util.List;
import java.util.Map;

import producto.Item;
import egreso.Egreso;;

public class Reporte {
	
	private Egreso egreso;
	private Map<CondicionValidacion, Boolean> resultadoValidaciones;
	
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	
	public void agregarResultadoValidacion(CondicionValidacion validador, boolean validacion) {
		resultadoValidaciones.put(validador, validacion);
	}
	

}