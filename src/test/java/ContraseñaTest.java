import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seguridad.*;

public class ContraseñaTest {

	
	AccountFieldValidation validator = new AccountFieldValidation();
	
	@Test
	public void testContraseñaIncluidaTop() throws ClassNotFoundException, SQLException {
		String contraseña = "johnson1";
	    Assert.assertTrue(validator.weakPassword(contraseña));
	}
	
	@Test
	public void testContraseñaNoIncluidaTop() throws ClassNotFoundException, SQLException {
		String contraseña = "Swld6+zz";
	    Assert.assertFalse(validator.weakPassword(contraseña));
	}
	
	@Test
	public void testContraseñaVacia() throws ClassNotFoundException {
		String contraseña = "";
		Assert.assertFalse(validator.validatePassword(contraseña));
	}
	
	@Test
	public void testContraseñaCumpleOWASP() throws ClassNotFoundException {
		String contraseña = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contraseña));
	}
	
	@Test
	public void testContraseñaNoCumpleOWASP() throws ClassNotFoundException {
		String contraseña = "abc1234z";
	    Assert.assertFalse(validator.validatePassword(contraseña));
	}
	
	@Test
	public void testContraseñaValida() throws ClassNotFoundException, SQLException {
		String contraseña = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contraseña));
	    Assert.assertFalse(validator.weakPassword(contraseña));
	}
		

}
