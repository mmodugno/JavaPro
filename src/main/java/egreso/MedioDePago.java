package egreso;

public class MedioDePago {

	/*CONSTRUCTOR*/
	public MedioDePago(TipoMedioPago payment_type, int id) {
		super();
		this.payment_type = payment_type;
		this.id = id;
	}
	/*
	*** Así tendria que crearse
	* medio.setPayment_type(payment_type);
	* medio.id(setId);//Esta id no va a ser auto incremental como lo van a ser la mayoria en la BD
	 */

	/*ATRIBUTOS*/
	private TipoMedioPago payment_type;
	private int id;
	private String imageRoute;

	/*GETTERS*/
	public TipoMedioPago getPayment_type() {
		return payment_type;
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
}
