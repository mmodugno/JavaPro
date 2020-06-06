package organizacion;

import java.util.List;

public class Osc  extends Empresa{

	public Osc(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase, int cantidadDePersonal, String actividad, Categoria categoria,
			float promedioDeVentas) {
		super(razonSocial, nombre, cuit, direccionPostal, codInscripcion, entidadesBase, cantidadDePersonal, actividad,
				categoria, promedioDeVentas);
		
	}

}
