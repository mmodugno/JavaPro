package usuarios;

import organizacion.Organizacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class UsuarioAdministrador extends Usuario {
	
	public UsuarioAdministrador(String nombreUser, String unaPass, Organizacion organizacion, boolean creadoConExito) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		super(nombreUser, unaPass, organizacion, creadoConExito);
	}
	

	
	public void crearCategoriaCompuesta(String nombreCategoria, String criterio, List<CategoriaDelSistema> lista) {
		CategoriaCompuesta nuevaCategoria = new CategoriaCompuesta(nombreCategoria,criterio);
		
		nuevaCategoria.setSubCategorias(lista);
	}

	public void crearCategoria(String nombreCategoria, String criterio) {
		new Categoria(nombreCategoria,criterio);
	}


	
}
