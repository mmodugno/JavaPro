package usuarios;

import organizacion.Organizacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UsuarioEstandard extends Usuario {

	public UsuarioEstandard(String nombre, String password, Organizacion organizacion) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		super(nombre, password, organizacion);
	}
}
