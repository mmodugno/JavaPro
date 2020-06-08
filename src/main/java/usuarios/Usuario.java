package usuarios;

import egreso.*;
import producto.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public abstract class Usuario {
	
	public Usuario(String nombre, String password){
		this.nombre = nombre;
		this.password = password;
		this.suscripciones = null;
		this.egresosValidados = null;
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

	public void suscribirse(Egreso egresoASuscribir) {
		
	}
	
	public void egresoValidado(Egreso unEgreso) {
		egresosValidados.add(unEgreso);
	}
	
	public OrdenDeCompra crearOrdenDeCompra(List<Producto> productos, int necesitaPresupuesto, List<Presupuesto> presupuestos,
            List<CreadorUsuario> revisores) {
		OrdenDeCompra unaOrden = new OrdenDeCompra(productos, necesitaPresupuesto, presupuestos, revisores);
		return unaOrden;
	}
	
	//public Presupuesto crearPresupuesto(OrdenDeCompra unaOrdenDeCompra) {}
}
