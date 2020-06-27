package producto;

public class Item implements Cloneable {

	
	private Producto producto;
	private int cantidad;
	private double precioUnitario;
	private boolean estaCerrada = false;
	
	public Item(Producto producto, int cantidad, double precioUnitario) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public Producto getProducto() {
		return producto;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	public Double obtenerPrecio() {
		Double precio = cantidad * precioUnitario;
		return precio;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) throws RuntimeException{
		if(estaCerrada) throw new RuntimeException("La operacion esta cerrada");
		else {
		this.precioUnitario = precioUnitario;
		}
	}

	// Overriding clone() method of Object class
	public Item clone() throws CloneNotSupportedException{
		return (Item) super.clone();
	}
	public  void fijarPrecio() {
		estaCerrada = true;
	}
	
	public int obtenerCodigoProducto() {
		return producto.getCodProducto();
	}
	
}
