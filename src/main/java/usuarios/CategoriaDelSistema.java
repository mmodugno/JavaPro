package usuarios;

import java.util.List;

public interface CategoriaDelSistema {

	String getCategoria();
	
	public boolean esCompuesta();
	
	public List<CategoriaDelSistema> getSubCategorias();
	
	
}
