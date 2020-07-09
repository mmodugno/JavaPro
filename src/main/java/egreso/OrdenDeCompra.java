package egreso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.toList;

import producto.*;
import usuarios.Usuario;


public class OrdenDeCompra {

	public OrdenDeCompra(int necesitaPresupuesto,int idOrden) {
		this.fecha = new Date();
		this.items = new ArrayList<Item>();
		this.necesitaPresupuesto = necesitaPresupuesto;
		this.presupuestos = new ArrayList<Presupuesto>();
		this.revisores = new ArrayList<Usuario>();
		this.idOrden = idOrden;
	}

	private List<Item> items;
	private Date fecha; 
	private int necesitaPresupuesto;
	private List<Presupuesto> presupuestos;
	private List<Usuario> revisores;
	private int idOrden;
	private CriterioSeleccion criterioSeleccion;

	
	public double valorTotal() throws SinItemsExcepcion{
        if(items.isEmpty()){
            throw new SinItemsExcepcion();
        }
        return items.stream().mapToDouble(Item::obtenerPrecio).sum();
    }

	public void cerrarOrden() {
		items.forEach(i -> i.fijarPrecio());
	}
	
	public List<Item> getItems() {
		return items;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getNecesitaPresupuesto() {
		return necesitaPresupuesto;
	}
	public int getIdOrden() {
		return idOrden;
	}

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public List<Usuario> getRevisores() {

		return revisores;
	}

	public void agregarItem(Item item){
		items.add(item);
	}

	public void agregarRevisor(Usuario usuario){
		revisores.add(usuario);
	}

	public void agregarPresupuesto(Presupuesto presupuesto){
		presupuestos.add(presupuesto);
	}

	public void setCriterioSeleccion(CriterioSeleccion unCriterio){
		this.criterioSeleccion = unCriterio;
	}
	
	public CriterioSeleccion getCriterioSeleccion(){
		return this.criterioSeleccion;
	}
	
	public Presupuesto presupuestoAceptado(){
		return presupuestos.stream().filter(presupuesto -> presupuesto.getAceptado()).collect(toList()).get(0);
	}
}
