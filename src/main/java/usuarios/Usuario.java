package usuarios;

import egreso.*;
import organizacion.Organizacion;
import producto.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	
	public Usuario(String nombre, String password, Organizacion organizacion){
		this.nombre = nombre;
		this.password = password;
		this.suscripciones = new ArrayList<OrdenDeCompra>();
		this.organizacion = organizacion;
	}

	private String nombre;
	private String password;
	private Organizacion organizacion;
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
		ordenASuscribir.agregarRevisor(this);
	}

	public void egresoValidado(Egreso unEgreso) {
		organizacion.nuevoEgreso(unEgreso);
		organizacion.sacarOrden(unEgreso.getOrdenDeCompra());
		
	}

	
/*
	public boolean creadoConExito() {
		return true;
	}
	*/
	//public Presupuesto crearPresupuesto(OrdenDeCompra unaOrdenDeCompra) {}
}
