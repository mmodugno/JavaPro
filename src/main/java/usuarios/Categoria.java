package usuarios;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "simple")

public class Categoria extends CategoriaDelSistema{
	public Categoria(String nombre, String criterio) {
		super(nombre,criterio);
	}
	
	public Categoria( ) {
		super();
	}
	
}
