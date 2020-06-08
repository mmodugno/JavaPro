package organizacion;

import java.util.ArrayList;
import java.util.List;
import egreso.*;

public class Organizacion {

	public Organizacion(){
		super();
		this.entidades = new ArrayList<EntidadJuridica>();
		this.egresos = new ArrayList<Egreso>();
		this.ordenes = new ArrayList<OrdenDeCompra>();
	}
	
	
	private List<EntidadJuridica> entidades;
	private List<Egreso> egresos;
	private List<OrdenDeCompra> ordenes;
	
	
	
	public void nuevoEgreso(Egreso nuevoEgreso) {
		egresos.add(nuevoEgreso);
	}
	
	public void agregarEntidad(EntidadJuridica entidad) {
		entidades.add(entidad);
	}

	public List<Egreso> getEgresos() {
		return egresos;
	}
}
