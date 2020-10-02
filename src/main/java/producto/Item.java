package producto;

public class Item implements Cloneable {

	/*CONSTRUCTOR*/
	public Item(Producto producto, int cantidad, double precioUnitario) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}
	/*
	* setProducto(producto);
	* setCantidad(cantidad);
	* setPrecioUnitario(precioUnitario);
	*/

	/*Atributos*/
	private Producto producto;
	private int cantidad;
	private double precioUnitario;
	private boolean estaCerrada = false;

    public Item() {

    }


    /*GETTERS*/
	public Producto getProducto() {
		return producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public boolean isEstaCerrada() {
		return estaCerrada;
	}

	public Double obtenerPrecio() {
		Double precio = cantidad * precioUnitario;
		return precio;
	}

	/*SETTERS*/
	public void setPrecioUnitario(double precioUnitario) throws RuntimeException{
		if(estaCerrada) throw new RuntimeException("La operacion esta cerrada");
		else {
		this.precioUnitario = precioUnitario;
		}
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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
