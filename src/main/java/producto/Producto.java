package producto;

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
	private int codProducto;
	private String nombre;
	private String descripcion;
	private TipoItem tipoProducto;
		
	/*GETTERS*/
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
