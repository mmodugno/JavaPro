package organizacion;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import egreso.*;
import usuarios.CategoriaDelSistema;

@Entity
@Table
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
	
	public EntidadJuridica() {
		super();
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
	@Id
	private String razonSocial;
	private String nombre;
	private String cuit;
	private int direccionPostal;
	private int codInscripcion;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<EntidadBase> entidadesBase;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Egreso> egresos;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrdenDeCompra> ordenesPendientes;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	
	public void agregarUnaOrden(OrdenDeCompra orden) {
		this.ordenesPendientes.add(orden);
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
