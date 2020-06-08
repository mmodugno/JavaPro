package usuarios;
/**
 * Esta clase verifica que se cumplan las normativas constituidas
 * en el OWASP (Proyecto Abierto de Seguridad en Aplicaciones Web).
 * 
 * La clase CuentaUsuario le manda los mensajes:
 * 
 * - validateUser(String)
 * - validatePassword(String)
 * 
 * a un checker para validar que tanto el usuario y contraseña,
 * cumplan las normas.
 *
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;

public class AccountFieldValidation {
	
	private String USER_REGEX = "^[a-zA-Z0-9]{6,16}$";
	private String PASSWORD_REGEX = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

	private Pattern user_pattern = Pattern.compile(USER_REGEX);
	private Pattern password_pattern = Pattern.compile(PASSWORD_REGEX);

	public boolean validateUser(String user) {
		Matcher matcher = user_pattern.matcher(user);
		return matcher.matches();
	}
	public boolean validatePassword(String password) throws ClassNotFoundException {
		Matcher matcher = password_pattern.matcher(password);
		return matcher.matches();
	}
	
	public boolean weakPassword(String newPassword) throws FileNotFoundException, CreationError {
		
		Scanner scan = null;
		
		try {
			scan = new Scanner(new File("top1000.txt"));
		} catch (Exception e) {
			System.out.println("No se pudo abrir el archivo...");
		}
		
		while(scan.hasNext()) {
			   String word = scan.next();
			   if (word.compareTo(newPassword) == 0) return true;
			}
		
		return false;

	}
	
}
