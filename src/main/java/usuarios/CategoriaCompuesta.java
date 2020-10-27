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

	
	
	
	
	@Override
	public boolean esCompuesta() {
		return true;
	}
	


	

	

}
