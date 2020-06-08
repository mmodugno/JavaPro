package egreso;

import java.util.ArrayList;
import java.util.List;

import producto.*;
import egreso.*;
import usuarios.CreadorUsuario;

public class Egreso {

	public Egreso(OrdenDeCompra ordenDeCompra,
			Presupuesto presupuesto) {
		this.documentosComerciales = new ArrayList<DocumentoComercial>();
		this.ordenDeCompra = ordenDeCompra;
		this.presupuesto = presupuesto;
	}

	private List<DocumentoComercial> documentosComerciales;
	private OrdenDeCompra ordenDeCompra;
	private Presupuesto presupuesto;
	
	
	public double valorTotal() {
		return ordenDeCompra.valorTotal();
	}
	
	public void generarDocumentoComercial(DocumentoComercial documento) {
		documentosComerciales.add(documento);
	}
}
