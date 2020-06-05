package producto;

public class Item {

	
	private Producto producto;
	private int cantidad;
	
	public Item(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	public float getPrecio() {
		return producto.getPrecio();
	}
}
