package auditoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import producto.Item;
import egreso.Egreso;;

public class Reporte {
	
	private Egreso egreso;
	private Map<CondicionValidacion, Boolean> resultadoValidaciones;
	
	public Reporte() {
		resultadoValidaciones = new HashMap<CondicionValidacion, Boolean>();
	}
	
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	
	public void agregarResultadoValidacion(CondicionValidacion validador, boolean validacion) {
		System.out.println("Validacion : " + validador.getNombre() + " - Resultado : " + validacion);
		resultadoValidaciones.put(validador, validacion);
	}

}