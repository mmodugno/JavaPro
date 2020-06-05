package egreso;

import java.util.List;

import seguridad.CuentaUsuario;
import producto.*;

public class Egreso {

	public Egreso(MedioDePago medioDePago, List<DocumentoComercial> documentosComerciales, OrdenDeCompra ordenDeCompra,
			Presupuesto presupuesto) {
				this.medioDePago = medioDePago;
		this.documentosComerciales = documentosComerciales;
		this.ordenDeCompra = ordenDeCompra;
		this.presupuesto = presupuesto;
	}
	private MedioDePago medioDePago;
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
