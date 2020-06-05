package egreso;

public class MedioDePago {

	public MedioDePago(TipoMedioPago payment_type, int id) {
		super();
		this.payment_type = payment_type;
		this.id = id;
	}
	private TipoMedioPago payment_type;
	private int id;
	
	public TipoMedioPago getPayment_type() {
		return payment_type;
	}
	public int getId() {
		return id;
	}
	
}
