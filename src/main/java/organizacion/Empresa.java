package organizacion;

import java.util.ArrayList;
import java.util.List;

public class Empresa extends EntidadJuridica{

	
	public Empresa(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase, int cantidadDePersonal, String actividad, Categoria categoria,
			double promedioDeVentas) {
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
	private double promedioDeVentas;
	private TipoEmpresa tipoEmpresa;
	
	public TipoEmpresa getTipo() {
		
		List<TipoEmpresa> listaTipos =  new ArrayList<TipoEmpresa>();
		
		Categorizar categorizador = new Categorizar();
		
		listaTipos.add(categorizador.categorizarVentas(categoria,promedioDeVentas));
		listaTipos.add(categorizador.categorizarPersonal(categoria,cantidadDePersonal));
		
		TipoEmpresa tipo = categorizador.elegirTipo(listaTipos);
		
		return tipo;
	}	
	
}
