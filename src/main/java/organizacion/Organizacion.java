package organizacion;

import java.util.ArrayList;
import java.util.List;
import egreso.*;

public class Organizacion {

	public Organizacion(){
		super();
		this.entidades = new ArrayList<EntidadJuridica>();
		this.egresos = new ArrayList<Egreso>();
		this.ordenesPendientes = new ArrayList<OrdenDeCompra>();
	}
	
	
	private List<EntidadJuridica> entidades;
	private List<Egreso> egresos;
	private List<OrdenDeCompra> ordenesPendientes;
	
	
	public void nuevoEgreso(Egreso nuevoEgreso) {
		egresos.add(nuevoEgreso);
	}
	
	public void agregarEntidad(EntidadJuridica entidad) {
		entidades.add(entidad);
	}

	public List<Egreso> getEgresos() {
		return egresos;
	}

	public void sacarOrden(OrdenDeCompra ordenDeCompra) {
		ordenesPendientes.removeIf(unaOrden->unaOrden.getIdOrden() == ordenDeCompra.getIdOrden());
	}
	
}