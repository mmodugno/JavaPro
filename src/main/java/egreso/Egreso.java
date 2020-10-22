package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import producto.*;
import egreso.*;
import usuarios.Categoria;
import usuarios.CategoriaDelSistema;
import usuarios.CreadorUsuario;

@Entity
@Table
public class Egreso implements Categorizable{

	/*CONSTRUCTOR*/
	public Egreso(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
		this.documentosComerciales = new ArrayList<DocumentoComercial>();
		this.ordenDeCompra = ordenDeCompra;
		this.presupuesto = presupuesto;
		fecha = LocalDate.now();
		valorTotal = presupuesto.valorTotal();
		ordenDeCompra.setCerrado(true);

	}

	public Egreso(){
		this.documentosComerciales = new ArrayList<DocumentoComercial>();
	}
	/*
	***Se tendria que hacer así***
	* egreso.setOrdenDeCompra(ordenDeComrpra);
	* egreso.setPresupuesto(presupuesto);
	 */

	/*ATRIBUTOS*/
	@Id
	private int id;
	
	@Column(columnDefinition = "DATE")
	private LocalDate fecha;
	
	private Double valorTotal;
	
	
	//siempre que hay una coleccion como atributo, la notation es algo to many (many to many / one to many)
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//un egreso puede tener varios doc pero un doc esta solo en un egreso
	private List<DocumentoComercial> documentosComerciales;
	
	@Transient
	private OrdenDeCompra ordenDeCompra;
	
	@Transient
	private Presupuesto presupuesto;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CategoriaDelSistema categoria = null;
	
	

	/*GETTERS*/

	public CategoriaDelSistema getCategoria() {
		if(categoria != null)
		return categoria;
		else return new Categoria("sin categoria","sin categoria");
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public List<DocumentoComercial> getDocumentosComerciales() {
		return documentosComerciales;
	}
	public OrdenDeCompra getOrdenDeCompra() {
		return ordenDeCompra;
	}
	public Presupuesto getPresupuesto() {
		return presupuesto;
	}
	public int getId() {
		return id;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	/*SETTERS*/
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setDocumentosComerciales(List<DocumentoComercial> documentosComerciales) {
		this.documentosComerciales = documentosComerciales;
	}
	public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
		this.ordenDeCompra = ordenDeCompra;
		ordenDeCompra.setCerrado(true);
	}
	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	public void setCategoria(CategoriaDelSistema categoria) {
		this.categoria = categoria;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setValorTotal(Double valor) {
		this.valorTotal = valor;
	}

	/*Para agregar de a uno solo*/
	public void agregarDocumentoComercial(DocumentoComercial documento) {
		documentosComerciales.add(documento);
	}

	@Override
	public void categorizar(CategoriaDelSistema unaCategoria) {
		this.categoria = unaCategoria;
	}

	public boolean esDeCategoria(CategoriaDelSistema unaCategoria) {
		if(categoria == null) return false;
		else {
			if(unaCategoria.esCompuesta()) {
				return unaCategoria.getSubCategorias().stream().anyMatch(c -> this.esDeCategoria(c));
			} else {
				return categoria.getCategoria().equals(unaCategoria.getCategoria());
			}
		}
	}	
		
}
	
	

