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

	public Egreso(OrdenDeCompra ordenDeCompra, Presupuesto presupuesto) {
		this.documentosComerciales = new ArrayList<DocumentoComercial>();
		this.ordenDeCompra = ordenDeCompra;
		this.presupuesto = presupuesto;
		fecha = LocalDate.now();
	}

	private List<DocumentoComercial> documentosComerciales;
	private OrdenDeCompra ordenDeCompra;
	private Presupuesto presupuesto;
	private CategoriaDelSistema categoria = null;
	private LocalDate fecha;
	private Ingreso ingresoAsociado;

	public Ingreso getIngresoAsociado() {
		return ingresoAsociado;
	}

	public void setIngresoAsociado(Ingreso ingresoAsociado) {
		this.ingresoAsociado = ingresoAsociado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public double valorTotal() {
		return this.ordenDeCompra.valorTotal();
	}
	
	public void agregarDocumentoComercial(DocumentoComercial documento) {
		documentosComerciales.add(documento);
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
	
	@Override
	public void categorizar(CategoriaDelSistema categoria) {
		this.categoria = categoria;
	}

	
}
