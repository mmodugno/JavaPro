package usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class CategoriaDelSistema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String nombre;
	
	@Column
	private String criterio;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<CategoriaDelSistema> subCategorias;
	
	public CategoriaDelSistema() {
		
	}
	
	public CategoriaDelSistema(String nombre, String criterio) {
		this.nombre = nombre;
		this.criterio = criterio;
	}
	


	public String getCategoria() {
		return nombre;
	}
	
	public String getCriterio() {
		return criterio;
	}
	
	
	public boolean esCompuesta() {
		return false;
	};
	
	public void agregarSubCategoria(CategoriaDelSistema categoria) {
		subCategorias.add(categoria);
	}
	
	public void setSubCategorias(List<CategoriaDelSistema> subCategorias) {
		this.subCategorias = subCategorias;
	}

	
	public List<CategoriaDelSistema> getSubCategorias() {
		return this.subCategorias;
	}

	
	
}
