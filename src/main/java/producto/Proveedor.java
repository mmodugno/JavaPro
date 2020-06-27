package producto;

import java.util.ArrayList;
import java.util.List;


public class Proveedor {

	public Proveedor(String nombre, String cuilOCuit, String direccionPostal) {
		this.nombre = nombre;
		this.cuilOCuit = cuilOCuit;
		this.direccionPostal = direccionPostal;
	}

	private String nombre;
	private String cuilOCuit;
	private String direccionPostal;

	public String getCuilOCuit() {
		return cuilOCuit;
	}


	
}
