package organizacion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import egreso.*;
import usuarios.CategoriaDelSistema;

@Entity
@Table
public class Organizacion {

	public Organizacion(){
		super();
		this.entidades = new ArrayList<EntidadJuridica>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EntidadJuridica> entidades;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CategoriaDelSistema> categorias = new ArrayList<CategoriaDelSistema>();
	
	public void agregarEntidad(EntidadJuridica entidad) {
		entidades.add(entidad);
	}

	
	public List<EntidadJuridica>  getEntidades() {
		return entidades;
	}
	
	public void agregarCategoria(CategoriaDelSistema categoria) {
		categorias.add(categoria);
	}
	
	public void agregarCategorias(List<CategoriaDelSistema> categorias) {
		this.categorias = categorias;
	}
	
	public List<CategoriaDelSistema> getCategorias() {
		return this.categorias;
	}

	
	
	
	public List<Categorizable> obtenerCategorizables(CategoriaDelSistema unaCategoria){
		List<Categorizable> listaCat = new ArrayList<Categorizable>();
		
		entidades.forEach(e->e.devolverCategorias(listaCat, unaCategoria));
		
		return listaCat;
	}
	
	
}