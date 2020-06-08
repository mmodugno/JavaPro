package producto;

public class Producto {

		private int codProducto;
		private String nombre;
		private String descripcion;
		private Double precio;
		private TipoItem tipoProducto;
		
		
		
		public Producto(int codigo, String unNombre, String descripcion,Double unPrecio, TipoItem tipo) {
			this.nombre = unNombre;
			this.precio = unPrecio;
			this.descripcion = descripcion;
			this.codProducto = codigo;
			this.tipoProducto = tipo;
			
		}



		public int getCodProducto() {
			return codProducto;
		}



		public String getNombre() {
			return nombre;
		}



		public Double getPrecio() {
			return precio;
		}



		public TipoItem getTipoProducto() {
			return tipoProducto;
		}


}
