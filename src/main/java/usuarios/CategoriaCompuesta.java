package usuarios;

import java.util.ArrayList;
import java.util.List;


public class CategoriaCompuesta implements CategoriaDelSistema{

	public CategoriaCompuesta(String nombre, String criterio) {
		super();
		this.nombre = nombre;
		this.criterio = criterio;
	}



	private String nombre;
	private String criterio;
	private List<CategoriaDelSistema> subCategorias = new ArrayList<CategoriaDelSistema>();
	
	
	public void agregarSubCategoria(CategoriaDelSistema categoria) {
		subCategorias.add(categoria);
	}
	
	public boolean esCompuesta() {
		return true;
	}
	

	public String getCriterio() {
		return criterio;
	}
	

	@Override
	public String getCategoria() {
		return nombre;
	}



	public void setSubCategorias(List<CategoriaDelSistema> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public List<CategoriaDelSistema> getSubCategorias() {
		return this.subCategorias;
	}

	

}
