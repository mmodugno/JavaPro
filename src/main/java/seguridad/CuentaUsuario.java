package seguridad;


import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CuentaUsuario {
	private String usuario;
	private String contrasenia;
	
	private int totalAttempts = 3;
	
	private AccountFieldValidation validator = new AccountFieldValidation();
	
	public CuentaUsuario(String NewUser, String NewPassword) throws CreationError, FileNotFoundException, ClassNotFoundException, SQLException {
		if(!validator.validateUser(NewUser)) throw new CreationError("El usuario no cumple los requisitos.");
		if(!validator.validatePassword(NewPassword)) throw new CreationError("La password no cumple los requisitos.");
		if(validator.weakPassword(NewPassword)) throw new CreationError("La password es demasiado debil. Por favor, elija otra.");
		
		this.usuario = NewUser;
		this.contrasenia = NewPassword;
	}
	
	
	public void ExecuteLogin(String tempUser, String tempPass) throws AttemptError {
		
		//String tempUser;
		//String tempPass;
		
		if(totalAttempts != 0) {
			if(usuario.equals(tempUser) && contrasenia.equals(tempPass)) {
				System.out.println("Successfully Logged In!");
			} else {
				System.out.println("El usuario y/o contraseña no coinciden.");
				totalAttempts--;
			}
		} else {
			throw new AttemptError("Has alcanzado el maximo numero de intentos. Por favor, intente más tarde.");
		}
	}
	
	
	public String getUsuario() {
		return usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}
	
	
	public static void main(String[] args) throws CreationError, AttemptError, FileNotFoundException, ClassNotFoundException, SQLException {
		CuentaUsuario test = new CuentaUsuario("nubevi23", "pru3b@tesT");
		System.out.println("El usuario es: " + test.getUsuario());
		System.out.println("La contraseña es: " + test.getContrasenia());
		
		test.ExecuteLogin("hola", "hola");
		test.ExecuteLogin("hola", "asdffdsa");
		test.ExecuteLogin("hola", "a3f3afd");
		test.ExecuteLogin("hola", "heabbaebola");
	}
}