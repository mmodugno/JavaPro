package server;

public class OrdenNotFoundException extends Exception {
private int idOrden;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrdenNotFoundException() {
		super();
		
	}
	public OrdenNotFoundException(int id) {
		super();
		this.idOrden = id;
	}


}
