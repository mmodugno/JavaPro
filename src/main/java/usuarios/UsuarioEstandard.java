package usuarios;

import organizacion.Organizacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import egreso.Categorizable;

@Entity
@DiscriminatorValue("Estandar")
public class UsuarioEstandard extends Usuario {
	
	public UsuarioEstandard() {
    }

	public UsuarioEstandard(String nombre, String password, Organizacion organizacion, boolean creadoConExito) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
		super(nombre, password, organizacion, creadoConExito);
	}
	
	public void categorizar(Categorizable categorizable, Categoria categoria) {
		categorizable.categorizar(categoria);
	}
	

	
	
	
}
