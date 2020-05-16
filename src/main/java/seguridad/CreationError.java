package seguridad;
public class CreationError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreationError(String ErrorMessage){
		super(ErrorMessage);
	}
}
