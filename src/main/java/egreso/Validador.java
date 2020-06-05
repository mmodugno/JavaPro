package egreso;

public class Validador {
	
	private int cantidadNecesaria;
	private CondicionValidacion condicion;
	private CriterioSeleccion criterioSeleccion;
	
	public Validador(int cantidadNecesaria, CondicionValidacion condicion, CriterioSeleccion criterioSeleccion) {
		super();
		this.cantidadNecesaria = cantidadNecesaria;
		this.condicion = condicion;
		this.criterioSeleccion = criterioSeleccion;
	}

	public int getCantidadNecesaria() {
		return cantidadNecesaria;
	}
	public CondicionValidacion getCondicion() {
		return condicion;
	}
	public CriterioSeleccion getCriterioSeleccion() {
		return criterioSeleccion;
	}
	
	public Presupuesto seleccionarPresupuesto(OrdenDeCompra ordenDeCompra) {
	
		return criterioSeleccion.seleccionar(ordenDeCompra);

	}
	
	public boolean validarOrden(OrdenDeCompra ordenDeCompra) {
		
		return ordenDeCompra.getPresupuestos().size() == cantidadNecesaria;
		
	}

	
	
	
}
