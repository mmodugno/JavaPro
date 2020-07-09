package organizacion;
import java.util.ArrayList;
import java.util.List;

import egreso.Egreso;

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
		this.egresos = new ArrayList<Egreso>();
		
	}
	private String razonSocial;
	private String nombre;
	private String cuit;
	private int direccionPostal;
	private int codInscripcion;
	private List<EntidadBase> entidadesBase;
	private List<Egreso> egresos;
	
	
	public void agregarEntidadBase(EntidadBase entidad) {
		entidadesBase.add(entidad);
	}
	
	public void nuevoEgreso(Egreso nuevoEgreso) {
		egresos.add(nuevoEgreso);
	}
	
	public List<Egreso> getEgresos() {
		return egresos;
	}
}
