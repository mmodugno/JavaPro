package usuarios;

import organizacion.Organizacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UsuarioAdministrador extends Usuario {
	
	public UsuarioAdministrador(String nombreUser, String unaPass, Organizacion organizacion) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		super(nombreUser, unaPass, organizacion);
	}
}
