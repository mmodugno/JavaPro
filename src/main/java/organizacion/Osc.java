package organizacion;

import java.util.List;

public class Osc  extends Empresa{

	/*Hay que hacer los sets cuando se cree como tod!*/
	public Osc(String razonSocial, String nombre, String cuit, int direccionPostal, int codInscripcion,
			List<EntidadBase> entidadesBase, int cantidadDePersonal, String actividad, Rubro rubro,
			float promedioDeVentas) {
		super(razonSocial, nombre, cuit, direccionPostal, codInscripcion, entidadesBase, cantidadDePersonal, actividad,
				rubro, promedioDeVentas);
		
	}

}
