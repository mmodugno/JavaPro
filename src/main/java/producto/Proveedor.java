package producto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class Proveedor {

	public Proveedor() {
		
	}

	

	/*CONSTRUCTOR*/
	public Proveedor(String nombre, String cuilOCuit, String direccionPostal) {
		this.nombre = nombre;
		this.cuilOCuit = cuilOCuit;
		this.direccionPostal = direccionPostal;
	}

	/*
	* setNombre(nombre);
	* setCuilOCuit(cuil);
	* setDireccionPostal(direccionPostal);
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/*ATRIBUTOS*/
	private String nombre;
	private String cuilOCuit;
	private String direccionPostal;

	/*GETTERS*/
	public String getCuilOCuit() {
		return cuilOCuit;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDireccionPostal() {
		return direccionPostal;
	}

	/*SETTERS*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCuilOCuit(String cuilOCuit) {
		this.cuilOCuit = cuilOCuit;
	}
	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
}
