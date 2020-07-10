package organizacion;

import java.util.ArrayList;
import java.util.List;
import egreso.*;
import usuarios.CategoriaDelSistema;

public class Organizacion {

	public Organizacion(){
		super();
		this.entidades = new ArrayList<EntidadJuridica>();
		this.ordenesPendientes = new ArrayList<OrdenDeCompra>();
	}
	
	private List<EntidadJuridica> entidades;
	private List<OrdenDeCompra> ordenesPendientes;
	private List<CategoriaDelSistema> categorias = new ArrayList<CategoriaDelSistema>();
	
	public void agregarEntidad(EntidadJuridica entidad) {
		entidades.add(entidad);
	}

	public void sacarOrden(OrdenDeCompra ordenDeCompra) {
		ordenesPendientes.removeIf(unaOrden->unaOrden.getIdOrden() == ordenDeCompra.getIdOrden());
	}
	
	public List<EntidadJuridica>  getEntidades() {
		return entidades;
	}
	
	public void agregarCategoria(CategoriaDelSistema categoria) {
		categorias.add(categoria);
	}
	
	
}