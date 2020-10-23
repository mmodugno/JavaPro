package organizacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.*;
import usuarios.CategoriaDelSistema;

public class EntidadJuridica {

	/*CONSTRUCTOR*/
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
	/*
	 * setRazonSocial(razonSocial);
	 * setNombre(nombre);
	 * setCuit(cuit);
	 * setDireccionPostal(direccionPostal);
	 * setCodInscripcion(codInscripcion);
	 * setEntidadesBase(entidadesBase);
	 */

	/*ATRIBUTOS*/
	private String razonSocial;
	private String nombre;
	private String cuit;
	private int direccionPostal;
	private int codInscripcion;

	private List<EntidadBase> entidadesBase;
	private List<Egreso> egresos;
	private List<OrdenDeCompra> ordenesPendientes;
	private List<Ingreso> ingresos;

	/*GETTERS*/
	public List<Ingreso> getIngresos() {
		return ingresos;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCuit() {
		return cuit;
	}
	public int getDireccionPostal() {
		return direccionPostal;
	}
	public int getCodInscripcion() {
		return codInscripcion;
	}
	public List<EntidadBase> getEntidadesBase() {
		return entidadesBase;
	}
	public List<OrdenDeCompra> getOrdenesPendientes() {
		return ordenesPendientes;
	}
	public List<Egreso> getEgresos() {
		return egresos;
	}

	/*SETTERS*/
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public void setDireccionPostal(int direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
	public void setCodInscripcion(int codInscripcion) {
		this.codInscripcion = codInscripcion;
	}
	public void setEntidadesBase(List<EntidadBase> entidadesBase) {
		this.entidadesBase = entidadesBase;
	}
	public void setOrdenesPendientes(List<OrdenDeCompra> ordenesPendientes) {
		this.ordenesPendientes = ordenesPendientes;
	}
	public void setEgresos(List<Egreso> egresos) {
		this.egresos = egresos;
	}
	public void setIngresos(List<Ingreso> ingresos) {
		this.ingresos = ingresos;
	}


	public void agregarEntidadBase(EntidadBase entidad) {
		entidadesBase.add(entidad);
	}
	
	public void nuevoEgreso(OrdenDeCompra ordenDeCompra) {
		Egreso egreso = new Egreso(ordenDeCompra, ordenDeCompra.presupuestoAceptado());
		egresos.add(egreso);
	}

	public void sacarOrden(OrdenDeCompra ordenDeCompra) {
		ordenesPendientes.removeIf(unaOrden->unaOrden.getIdOrden() == ordenDeCompra.getIdOrden());
	}

	//TODO falta ver lo de presupuestos
	public void devolverCategorias( List<Categorizable> listaALlenar , CategoriaDelSistema unaCategoria){
		
		List<Categorizable> listaEgresos = egresos.stream().filter(e->e.esDeCategoria(unaCategoria)).collect(Collectors.toList());
		
		listaEgresos.forEach(e -> listaALlenar.add(e));
		
		List<Presupuesto> listaPresupuestos = egresos.stream().map(e->e.getPresupuesto()).collect(Collectors.toList());
		
		listaPresupuestos.stream().filter(p->p.esDeCategoria(unaCategoria)).collect(Collectors.toList());
	
		listaPresupuestos.forEach(p -> listaALlenar.add(p));
		
		List<Categorizable> listaIngresos = ingresos.stream().filter(i->i.esDeCategoria(unaCategoria)).collect(Collectors.toList());
		
		listaIngresos.forEach(i -> listaALlenar.add(i));
		
	
	}

	
	
}
