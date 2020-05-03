package seguridad;
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
import java.sql.*;


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
	public boolean weakPassword(String newPassword) throws ClassNotFoundException, SQLException {
		
		boolean bool;
		
		String connectionUrl = "jdbc:sqlserver://181.31.75.187;databaseName=Seguridad;user=sas;password=123";
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		Connection con = DriverManager.getConnection(connectionUrl);

		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("exec perteneceTop '"+newPassword+"'");
		
		rs.next();
		
		bool = (rs.getInt(1) == 1); //la funcion retorna 1 si la contraseña está en el top 10000
		
		return bool;
	}
}
