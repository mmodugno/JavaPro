package producto;

import javax.persistence.*;

@Entity
@Table
public class Producto {


	public Producto(int codigo, String unNombre, String descripcion, TipoItem tipo) {
		this.nombre = unNombre;
		this.descripcion = descripcion;
		this.codProducto = codigo;
		this.tipoProducto = tipo;

	}
	/*
	* setNombre(nombre);
	* setDescripcion(descripcion);
	* setCodProducto(codProducto);
	* setTipoProducto(tipoProducto);
	*/

	/*ATRIBUTOS*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	private int codProducto;
	private String nombre;
	private String descripcion;
	@Transient
	//TODO hacer converter
	private TipoItem tipoProducto;

    public Producto() {

    }

	/*GETTERS*/

	public int getIdProducto() {
		return idProducto;
	}
	public int getCodProducto() {
		return codProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public TipoItem getTipoProducto() {
		return tipoProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}

	/*SETTERS*/

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setTipoProducto(TipoItem tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}
