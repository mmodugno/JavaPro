package organizacion;

import java.util.List;

public class Empresa extends EntidadJuridica{

	
	public Empresa(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase, int cantidadDePersonal, String actividad, Categoria categoria,
			float promedioDeVentas) {
		super(razonSocial, nombre, cuit, direccionPostal, codInscripcion, entidadesBase);
		this.cantidadDePersonal = cantidadDePersonal;
		this.actividad = actividad;
		this.categoria = categoria;
		this.promedioDeVentas = promedioDeVentas;
		this.tipoEmpresa = null;
	}
	
	private int cantidadDePersonal;
	private String actividad;
	private Categoria categoria;
	private float promedioDeVentas;
	private TipoEmpresa tipoEmpresa;
	
	public TipoEmpresa getTipo() {
		return tipoEmpresa;
	}
	
	public void calcularTipo() {
		
	}
	
	
	
	
}
