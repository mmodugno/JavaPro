package organizacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.Categorizable;
import egreso.Egreso;
import egreso.Ingreso;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import usuarios.CategoriaDelSistema;

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


//LO USAMOS SOLO PARA LOS TESTS
	public void setEgresos(List<Egreso> egresos) {
		this.egresos = egresos;
	}

	public void setIngresos(List<Ingreso> ingresos) {
		this.ingresos = ingresos;
	}
	
	
	
	
	
}
