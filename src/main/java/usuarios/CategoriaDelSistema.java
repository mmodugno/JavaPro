package usuarios;

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
	
	public List<CategoriaDelSistema> getSubCategorias() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
