package organizacion;

import java.util.ArrayList;
import java.util.List;
import egreso.*;

public class Organizacion {

	public Organizacion(List<EntidadJuridica> entidades,
			List<Presupuesto> presupuestos) {
		super();
		this.entidades = entidades;
		this.egresos = egresos;
		this.ordenes = ordenes;
		this.presupuestos = presupuestos;
	}
	
	
	private List<EntidadJuridica> entidades;
	private List<Egreso> egresos;
	private List<OrdenDeCompra> ordenes = new ArrayList<OrdenDeCompra>();
	private List<Presupuesto> presupuestos  = new ArrayList<Presupuesto>();
	
	
	
	public void nuevoEgreso(Egreso nuevoEgreso) {
		egresos.add(nuevoEgreso);
	}
	
	public void agregarEntidad(EntidadJuridica entidad) {
		entidades.add(entidad);
	}



	
	
	
}
