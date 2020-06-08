package seguridad;
import java.io.FileNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import usuarios.AccountFieldValidation;
import usuarios.CreationError;

public class ContraseniaTest {
	
	AccountFieldValidation validator = new AccountFieldValidation();
	
	@Test
	public void testClaveIncluidaTop() throws FileNotFoundException, CreationError  {
		String contrasenia = "johnson1";
	    Assert.assertTrue(validator.weakPassword(contrasenia));
	}
	
	@Test
	public void testClaveNoIncluidaTop() throws FileNotFoundException, CreationError {
		String contrasenia = "Swld6+zz";
	    Assert.assertFalse(validator.weakPassword(contrasenia));
	}
	
	@Test
	public void testClaveVacia() throws ClassNotFoundException {
		String contrasenia = "";
		Assert.assertFalse(validator.validatePassword(contrasenia));
	}
	
	@Test
	public void testClaveCumpleOWASP() throws ClassNotFoundException {
		String contrasenia = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contrasenia));
	}
	
	@Test
	public void testClaveNoCumpleOWASP() throws ClassNotFoundException {
		String contrasenia = "abc1234z";
	    Assert.assertFalse(validator.validatePassword(contrasenia));
	}
	
	@Test
	public void testClaveValida() throws FileNotFoundException, CreationError, ClassNotFoundException {
		String contrasenia = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contrasenia));
	    Assert.assertFalse(validator.weakPassword(contrasenia));
	}
	
	
		

}