package egreso;

import java.util.List;

import producto.*;
import usuarios.CreadorUsuario;


public class OrdenDeCompra {

	public OrdenDeCompra(List<Producto> productos, int necesitaPresupuesto, List<Presupuesto> presupuestos,
			List<CreadorUsuario> revisores) {
		
		this.productos = productos;
		this.necesitaPresupuesto = necesitaPresupuesto;
		this.presupuestos = presupuestos;
		this.revisores = revisores;
	}
<<<<<<< HEAD
	List<Producto> productos;
	String fecha; 
	int necesitaPresupuesto;
	List<Presupuesto> presupuestos;
	List<CreadorUsuario> revisores;
=======
	private List<Producto> productos;
	private String fecha; 
	private int necesitaPresupuesto;
	private List<Presupuesto> presupuestos;
	private List<CuentaUsuario> revisores;
>>>>>>> branch 'master' of https://github.com/dds-utn/2020-ma-no-group-09.git
	
	public double valorTotal() throws SinItemsExcepcion{
        if(productos.isEmpty()){
            throw new SinItemsExcepcion();
        }
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

	public List<Producto> getProductos() {
		return productos;
	}

	public String getFecha() {
		return fecha;
	}

	public int getNecesitaPresupuesto() {
		return necesitaPresupuesto;
	}

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public List<CuentaUsuario> getRevisores() {
		return revisores;
	}

	
	//public void generarDocumentoComercial()

	
}
