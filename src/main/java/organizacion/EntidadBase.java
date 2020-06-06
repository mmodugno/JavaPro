package organizacion;

public class EntidadBase {
	public EntidadBase(String nombre, String descripcion, EntidadJuridica entidadJuridica) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.entidadJuridica = entidadJuridica;
	}
	private String nombre;
	private String descripcion;
	private EntidadJuridica entidadJuridica;
	
}
