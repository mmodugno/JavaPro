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
	List<Producto> productos;
	String fecha; 
	int necesitaPresupuesto;
	List<Presupuesto> presupuestos;
	List<CreadorUsuario> revisores;
	
	public double valorTotal() throws SinItemsExcepcion{
        if(productos.isEmpty()){
            throw new SinItemsExcepcion();
        }
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
	
	//public void generarDocumentoComercial()

	
}
