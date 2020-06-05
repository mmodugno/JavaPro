package egreso;



public class DocumentoComercial {
public DocumentoComercial(int numero, Egreso egresoAsociado, String link) {
		super();
		this.numero = numero;
		this.egresoAsociado = egresoAsociado;
		this.link = link;
	}
private int numero;
private Egreso egresoAsociado;
private String link;


public int getNumero() {
	return numero;
}
public Egreso getEgresoAsociado() {
	return egresoAsociado;
}
public String getLink() {
	return link;
}

}
