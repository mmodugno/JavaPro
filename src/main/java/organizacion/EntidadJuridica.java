package organizacion;
import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;
import egreso.Ingreso;
import egreso.OrdenDeCompra;

public class EntidadJuridica {

	public EntidadJuridica(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase) {
		super();
		this.razonSocial = razonSocial;
		this.nombre = nombre;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
		this.codInscripcion = codInscripcion;
		this.entidadesBase = entidadesBase;
		this.egresos = new ArrayList<Egreso>();
		this.ordenesPendientes = new ArrayList<OrdenDeCompra>();
		this.ingresos = new ArrayList<Ingreso>();
		
	}
	private String razonSocial;
	private String nombre;
	private String cuit;
	private int direccionPostal;
	private int codInscripcion;
	private List<EntidadBase> entidadesBase;
	private List<Egreso> egresos;
	private List<OrdenDeCompra> ordenesPendientes;

	public List<Ingreso> getIngresos() {
		return ingresos;
	}

	private List<Ingreso> ingresos;
	
	
	public void agregarEntidadBase(EntidadBase entidad) {
		entidadesBase.add(entidad);
	}
	
	public void nuevoEgreso(OrdenDeCompra ordenDeCompra) {
		Egreso egreso = new Egreso(ordenDeCompra, ordenDeCompra.presupuestoAceptado());
		egresos.add(egreso);
	}
	
	public List<Egreso> getEgresos() {
		return egresos;
	}

	public void sacarOrden(OrdenDeCompra ordenDeCompra) {
		ordenesPendientes.removeIf(unaOrden->unaOrden.getIdOrden() == ordenDeCompra.getIdOrden());
	}
}
