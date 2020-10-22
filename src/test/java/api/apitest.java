package api;


import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mercadopago.*;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPRestException;


public class apitest {

	
	
	@Test
	public void validate() throws MPRestException {
		MercadoPago.SDK.setBaseUrl("https://api.mercadopago.com");
		MercadoPago.SDK.configure("TEST-4876257682735912-102121-020195b6c403bfe2f119a8dd2474a72c-333551634");
		@SuppressWarnings("deprecation")
		MPApiResponse response = MercadoPago.SDK.Get("/v1/payment_methods");
		System.out.println(response.getStringResponse());
		assertNotNull(response);

	}
}
