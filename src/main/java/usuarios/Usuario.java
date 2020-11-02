package usuarios;

import egreso.*;
import auditoria.Reporte;
import organizacion.Organizacion;
import producto.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario {
	
	public Usuario() {
    }

	public Usuario(String nombre, String password, Organizacion organizacion, boolean creadoConExito){
		this.nombre = nombre;
		this.password = password;
		this.suscripciones = new ArrayList<OrdenDeCompra>();
		this.organizacion = organizacion;
		this.creadoConExito = creadoConExito;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	//TODO guardar passw como HASH
	private String password;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Organizacion organizacion;
	
	@Transient
	private List<OrdenDeCompra> suscripciones;
	@Transient
	private List<Egreso> egresosValidados;
	private boolean creadoConExito;
	@Transient
	private Reporte reporteValidacion;
	
	// Getters
	
	public List<OrdenDeCompra> getSuscripciones() {
		return suscripciones;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}
	
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	
	public boolean creadoConExito() {
		return creadoConExito;
	}
	
	// Setters

	public void suscribirse(OrdenDeCompra ordenASuscribir) {
		suscripciones.add(ordenASuscribir);
		ordenASuscribir.agregarRevisor(this);
	}

	public void egresoValidado(Reporte reporteValidacion) {
		this.reporteValidacion = reporteValidacion;
		//organizacion.nuevoEgreso(unEgreso);
		//organizacion.sacarOrden(unEgreso.getOrdenDeCompra());
	}


/*
	public boolean creadoConExito() {
		return true;
	}
	public Presupuesto crearPresupuesto(OrdenDeCompra unaOrdenDeCompra) {}
 */
}
