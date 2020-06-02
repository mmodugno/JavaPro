package producto;

import java.util.List;


public class Proveedor {

	public Proveedor(String nombre, String cuilOCuit, String direccionPostal, List<Producto> productos) {
		this.nombre = nombre;
		this.cuilOCuit = cuilOCuit;
		this.direccionPostal = direccionPostal;
		this.productos = productos;
	}

	private String nombre;
	private String cuilOCuit;
	private String direccionPostal;
	
	List<Producto> productos; // = new ArrayList<Producto>();

	public String getCuilOCuit() {
		return cuilOCuit;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	
}
