package usuarios;
/**
 * En esta clase se hashea y deberia guardar en una base de datos
 * las contraseñas de las cuentas recien creadas
 */


import java.security.*;

public class HashingAccount {
	String passwordToHash;
	String algorithm = "MD5";
	byte[] salt = createSalt();
	
	public static byte[] createSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	private static byte[] generateHash(String toHash, String algorithm, byte[] salt) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		digest.update(salt);
		byte[] hashed = digest.digest(toHash.getBytes());
		
		return hashed;
	}
	
}
