package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import producto.*;
import egreso.*;
import usuarios.Categoria;
import usuarios.CategoriaDelSistema;
import usuarios.CreadorUsuario;

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
	private int id;
	private List<DocumentoComercial> documentosComerciales;
	private OrdenDeCompra ordenDeCompra;
	private Presupuesto presupuesto;
	private CategoriaDelSistema categoria = null;
	private LocalDate fecha;
	private boolean vinculado = false;
	private Double valorTotal;

	/*GETTERS*/

	public CategoriaDelSistema getCategoria() {
		if(categoria != null)
		return categoria;
		else return new Categoria("sin categoria","sin categoria");
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public boolean isVinculado() {
		return vinculado;
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
	public void setVinculado(boolean vinculado) {
		this.vinculado = vinculado;
	}
	public void setId(int id) {
		this.id = id;
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
		else return categoria.getCategoria().equals(unaCategoria.getCategoria());
		
	}
	
	
}
