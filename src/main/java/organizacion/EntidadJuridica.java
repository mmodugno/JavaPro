package organizacion;
import java.util.List;

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
	}
	private String razonSocial;
	private String nombre;
	private String cuit;
	private int direccionPostal;
	private int codInscripcion;
	private List<EntidadBase> entidadesBase;
	
	
	public void agregarEntidadBase(EntidadBase entidad) {
		entidadesBase.add(entidad);
	}
}
