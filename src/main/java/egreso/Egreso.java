package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import producto.*;
import egreso.*;
import usuarios.CategoriaDelSistema;
import usuarios.CreadorUsuario;

public class Egreso implements Categorizable{

	/*CONSTRUCTOR*/
	public Egreso(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
		this.documentosComerciales = new ArrayList<DocumentoComercial>();
		this.ordenDeCompra = ordenDeCompra;
		this.presupuesto = presupuesto;
		fecha = LocalDate.now();
	}
	/*
	***Se tendria que hacer así***
	* egreso.set
	 */

	/*ATRIBUTOS*/
	private List<DocumentoComercial> documentosComerciales;
	private OrdenDeCompra ordenDeCompra;
	private Presupuesto presupuesto;
	private CategoriaDelSistema categoria = null;
	private LocalDate fecha;
	private boolean vinculado = false;

	/*GETTERS*/
	public CategoriaDelSistema getCategoria() {
		return categoria;
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

	public double valorTotal() {
		return presupuesto.valorTotal();
	}//Yo lo considero un get

	/*SETTERS*/
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setDocumentosComerciales(List<DocumentoComercial> documentosComerciales) {
		this.documentosComerciales = documentosComerciales;
	}
	public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
		this.ordenDeCompra = ordenDeCompra;
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

	/*Para agregar de a uno solo*/
	public void agregarDocumentoComercial(DocumentoComercial documento) {
		documentosComerciales.add(documento);
	}

	@Override
	public void categorizar(CategoriaDelSistema unaCategoria) {
		this.categoria = unaCategoria;
	}

	public boolean esDeCategoria(CategoriaDelSistema unaCategoria) {
		return categoria.equals(unaCategoria);
	}
	
	
}
