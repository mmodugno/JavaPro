package usuarios;

import java.util.List;

public class Categoria implements CategoriaDelSistema{
	public Categoria(String nombre, String criterio) {
		super();
		this.nombre = nombre;
		this.criterio = criterio;
	}

	private String nombre;
	private String criterio;
	
	@Override
	public String getCategoria() {
		return nombre;
	}
	
	public String getCriterio() {
		return criterio;
	}
	
	public boolean esCompuesta() {
		return false;
	}

	@Override
	public List<CategoriaDelSistema> getSubCategorias() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
