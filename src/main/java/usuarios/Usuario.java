package usuarios;

import egreso.*;
import producto.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	
	public Usuario(String nombre, String password){
		this.nombre = nombre;
		this.password = password;
		this.suscripciones = new ArrayList<OrdenDeCompra>();
		this.egresosValidados = new ArrayList<Egreso>();
	}

	private String nombre;
	private String password;
	// private organizacion organizacion;
	private List<OrdenDeCompra> suscripciones;
	private List<Egreso> egresosValidados;
	
	
	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}

	public void suscribirse(OrdenDeCompra ordenASuscribir) {
		suscripciones.add(ordenASuscribir);
	}

	public void egresoValidado(Egreso unEgreso) {
		egresosValidados.add(unEgreso);
	}

	public OrdenDeCompra crearOrdenDeCompra(List<Producto> productos, int necesitaPresupuesto, List<Presupuesto> presupuestos,
			List<Usuario> revisores) {
		OrdenDeCompra unaOrden = new OrdenDeCompra(productos, necesitaPresupuesto, presupuestos, revisores);
		return unaOrden;
	}
	
	public boolean creadoConExito() {
		return true;
	}
	//public Presupuesto crearPresupuesto(OrdenDeCompra unaOrdenDeCompra) {}
}
