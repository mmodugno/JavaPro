package producto;

import java.util.ArrayList;
import java.util.List;


public class Proveedor {

	public Proveedor(String nombre, String cuilOCuit, String direccionPostal) {
		this.nombre = nombre;
		this.cuilOCuit = cuilOCuit;
		this.direccionPostal = direccionPostal;
		this.productos = new ArrayList<Producto>();
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

	public void agregarProductos(Producto producto){
		productos.add(producto);
	}

	
}
