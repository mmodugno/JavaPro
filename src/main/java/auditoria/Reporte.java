package auditoria;

import java.util.List;

import producto.Item;
import egreso.Egreso;;

public class Reporte {
	
	Egreso egreso;
	List<Boolean> resultadoValidaciones;
	
	private List<Item> itemsFaltantesCompra;
	
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	
	public void setValidaciones(List<Boolean> validaciones) {
		this.resultadoValidaciones = validaciones;
	}
	

}