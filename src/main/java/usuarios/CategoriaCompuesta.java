package usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;



@Entity
@DiscriminatorValue(value = "compuesta")
public class CategoriaCompuesta extends CategoriaDelSistema{

	public CategoriaCompuesta(String nombre, String criterio) {
		super(nombre,criterio);
	}

	public CategoriaCompuesta( ) {
		super();
	}

	@Transient
	private List<CategoriaDelSistema> subCategorias = new ArrayList<CategoriaDelSistema>();
	
	
	public void agregarSubCategoria(CategoriaDelSistema categoria) {
		subCategorias.add(categoria);
	}
	
	@Override
	public boolean esCompuesta() {
		return true;
	}
	


	public void setSubCategorias(List<CategoriaDelSistema> subCategorias) {
		this.subCategorias = subCategorias;
	}

	@Override
	public List<CategoriaDelSistema> getSubCategorias() {
		return this.subCategorias;
	}

	

}
