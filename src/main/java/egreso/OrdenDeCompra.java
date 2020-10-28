package egreso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;

import producto.*;
import usuarios.Usuario;

@Entity
@Table
public class OrdenDeCompra {

	/*CONSTRUCTOR*/
	public OrdenDeCompra(int necesitaPresupuesto) {
		this.fecha = LocalDate.now();
		this.items = new ArrayList<Item>();
		this.necesitaPresupuesto = necesitaPresupuesto;
		this.presupuestos = new ArrayList<Presupuesto>();
		this.revisores = new ArrayList<Usuario>();

	}
	
	public OrdenDeCompra() {
		this.items = new ArrayList<Item>();
		this.presupuestos = new ArrayList<Presupuesto>();
		this.revisores = new ArrayList<Usuario>();
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	***Así tendria que ser
	* orden.setNecesitaPresupuesto(necesita);//esto le pasas la cantidad y si necesita va a ser mayor a 1
	* orden.setIdOrden(id)//TODO Tranquilamente podria ser autoincremental charlar en la entrega de persistencia
	 */

	/*ATRIBUTOS*/
	@Column(columnDefinition = "DATE")
	private LocalDate fecha;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOrden;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Item> items;
	private int necesitaPresupuesto;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Presupuesto> presupuestos;

	//TODO
	@Transient
	private List<Usuario> revisores;


	//TODO ver
	@Transient
	private CriterioSeleccion criterioSeleccion;
	private boolean cerrado = false; //esto es para saber cuando tiene un egreso y cuando no

	/*GETTERS*/
	public List<Item> getItems() {
		return items;
	}
	public LocalDate getFecha() {
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
	public CriterioSeleccion getCriterioSeleccion(){
		return this.criterioSeleccion;
	}

	public boolean isCerrado() {
		return cerrado;
	}

	public int getPrimerpresupuesto() {
		return presupuestos.get(0).getId(); 
	}
	
	public String getCerrada() {
		if(isCerrado()) return "No";
		else return "Si";
	}
	
	
	/*SETTERS*/
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setNecesitaPresupuesto(int necesitaPresupuesto) {
		this.necesitaPresupuesto = necesitaPresupuesto;
	}
	public void setPresupuestos(List<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}
	public void setRevisores(List<Usuario> revisores) {
		this.revisores = revisores;
	}
	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}
	public void setCriterioSeleccion(CriterioSeleccion unCriterio){
		this.criterioSeleccion = unCriterio;
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


	public double valorTotal() throws SinItemsExcepcion{
        if(items.isEmpty()){
            throw new SinItemsExcepcion();
        }
        return items.stream().mapToDouble(Item::obtenerPrecio).sum();
    }

	public void cerrarOrden() {
		items.forEach(i -> i.fijarPrecio());
	}
	
	public Presupuesto presupuestoAceptado(){
		return presupuestos.stream().filter(presupuesto -> presupuesto.getAceptado()).collect(toList()).get(0);
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}
	
	
}
