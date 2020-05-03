import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seguridad.*;

public class Contrase�aTest {

	
	AccountFieldValidation validator = new AccountFieldValidation();
	
	@Test
	public void testContrase�aIncluidaTop() throws ClassNotFoundException, SQLException {
		String contrase�a = "johnson1";
	    Assert.assertTrue(validator.weakPassword(contrase�a));
	}
	
	@Test
	public void testContrase�aNoIncluidaTop() throws ClassNotFoundException, SQLException {
		String contrase�a = "Swld6+zz";
	    Assert.assertFalse(validator.weakPassword(contrase�a));
	}
	
	@Test
	public void testContrase�aVacia() throws ClassNotFoundException {
		String contrase�a = "";
		Assert.assertFalse(validator.validatePassword(contrase�a));
	}
	
	@Test
	public void testContrase�aCumpleOWASP() throws ClassNotFoundException {
		String contrase�a = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contrase�a));
	}
	
	@Test
	public void testContrase�aNoCumpleOWASP() throws ClassNotFoundException {
		String contrase�a = "abc1234z";
	    Assert.assertFalse(validator.validatePassword(contrase�a));
	}
	
	@Test
	public void testContrase�aValida() throws ClassNotFoundException, SQLException {
		String contrase�a = "pru3b@tesT";
	    Assert.assertTrue(validator.validatePassword(contrase�a));
	    Assert.assertFalse(validator.weakPassword(contrase�a));
	}
		

}
