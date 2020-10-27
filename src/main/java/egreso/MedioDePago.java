package egreso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mercadopago.exceptions.MPRestException;

import meliApi.api;

@Entity
@Table
public class MedioDePago {
	
	public MedioDePago()  {
		
	};

	/*CONSTRUCTOR*/
	public MedioDePago(TipoMedioPago payment_type) {
		super();
		this.payment_type = payment_type;
		//this.id = id;
		try {
			this.imageRoute = new api().getRouteByName(payment_type.toString());
		} catch (MPRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	*** Así tendria que crearse
	* medio.setPayment_type(payment_type);
	* medio.id(setId);//Esta id no va a ser auto incremental como lo van a ser la mayoria en la BD
	 */

	public MedioDePago(TipoMedioPago payment_type, int i) {
		this.payment_type = payment_type;
		this.id = i;
	}

	/*ATRIBUTOS*/
	@Enumerated(EnumType.STRING)
	private TipoMedioPago payment_type;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String imageRoute;

	/*GETTERS*/
	public TipoMedioPago getPayment_type() {
		return payment_type;
	}
	
	public String getTipoPago() {
		return payment_type.toString().toLowerCase();
	}
	
	public int getId() {
		return id;
	}

	/*SETTERS*/
	public void setPayment_type(TipoMedioPago payment_type) {
		this.payment_type = payment_type;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setImageRoute(String route) {
		this.imageRoute = route;
	}
	
	public String getImageRoute(String route) {
		return this.imageRoute;
	}
}
