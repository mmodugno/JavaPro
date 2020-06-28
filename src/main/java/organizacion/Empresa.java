package organizacion;

import java.util.ArrayList;
import java.util.List;

public class Empresa extends EntidadJuridica{

	
	public Empresa(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase, int cantidadDePersonal, String actividad, Rubro rubro,
			double promedioDeVentas) {
		super(razonSocial, nombre, cuit, direccionPostal, codInscripcion, entidadesBase);
		this.cantidadDePersonal = cantidadDePersonal;
		this.actividad = actividad;
		this.rubro = rubro;
		this.promedioDeVentas = promedioDeVentas;
		this.tipoEmpresa = calcularTipo();
	}
	
	private int cantidadDePersonal;
	private String actividad;
	private Rubro rubro;
	private double promedioDeVentas;
	private TipoEmpresa tipoEmpresa;
	
	//Busca la nueva categorizacion y modifica a la empresa
	public TipoEmpresa calcularTipo() {
		
		List<TipoEmpresa> listaTipos =  new ArrayList<TipoEmpresa>();
		
		Categorizador categorizador = new Categorizador();
		
		listaTipos.add(categorizador.categorizarVentas(rubro,promedioDeVentas));
		listaTipos.add(categorizador.reClasificarPorPersonal(rubro,cantidadDePersonal));
		
		TipoEmpresa tipo = categorizador.elegirTipo(listaTipos);
		
		return tipo;
	}
	
	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}
	
	
	
	public void actualizarTipoEmpresa() {
		tipoEmpresa = calcularTipo();
	}
	
	
	
	//Cuando se modifican las ventas o personal, se recategoriza la empresa:
	public void setPromedioDeVentas(double valor) {
		promedioDeVentas = valor;
		actualizarTipoEmpresa();
	}
	public void setCantidadDePersonal(int valor) {
		cantidadDePersonal = valor;
		actualizarTipoEmpresa();
	}
	
}
