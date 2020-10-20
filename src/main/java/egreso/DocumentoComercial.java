package egreso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class DocumentoComercial {

	/*CONSTRUCTOR*/

	public DocumentoComercial(int numero, Egreso egresoAsociado, String link) {
		super();
		this.numero = numero;
		this.egresoAsociado = egresoAsociado;
		this.link = link;
	}
	/*
	***Se va a tener que hacer así***

	* documentoComercial.setNumero(numero);
	* documentoComercial.setEgresoAsociado(egreso);
	* documentoComercial.setLink(link);
	*/


	/*ATRIBUTOS*/
	@Id
	private int numero;
	@ManyToOne //un egreso puede tener varios documentos pero un documento solo 1 egreso asociado
	private Egreso egresoAsociado;
	@Column
	private String link;

	/*GETTERS*/
	public int getNumero() {
		return numero;
	}
	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}
	public String getLink() {
		return link;
	}

	/*SETTERS*/
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public void setEgresoAsociado(Egreso egresoAsociado) {
		this.egresoAsociado = egresoAsociado;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
