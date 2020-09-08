package organizacion;

public class EntidadBase {
	public EntidadBase(String nombre, String descripcion, EntidadJuridica entidadJuridica) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.entidadJuridica = entidadJuridica;
	}
	/*
	*	setNombre(nombre);
	* 	setDescripcion(descripcion);
	* 	setEntidadJuridica(entidadJuridica);
	*/

	/*ATRIBUTOS*/
	private String nombre;
	private String descripcion;
	private EntidadJuridica entidadJuridica;

	/*GETTERS*/
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public EntidadJuridica getEntidadJuridica() {
		return entidadJuridica;
	}

	/*SETTERS*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
		this.entidadJuridica = entidadJuridica;
	}

}
