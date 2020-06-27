package producto;

public class Producto {

		private int codProducto;
		private String nombre;
		private String descripcion;
		private TipoItem tipoProducto;
		
		
		
		public Producto(int codigo, String unNombre, String descripcion, TipoItem tipo) {
			this.nombre = unNombre;
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


		public TipoItem getTipoProducto() {
			return tipoProducto;
		}


}
