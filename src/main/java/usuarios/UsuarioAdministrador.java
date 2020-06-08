package usuarios;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UsuarioAdministrador extends Usuario {
	
	public UsuarioAdministrador(String nombreUser, String unaPass) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		super(nombreUser, unaPass);
	}
}
