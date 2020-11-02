package meliApi;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.*;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPRestException;

import java.lang.reflect.Type;

public class api {
	
	public List<Map<String, Object>> getListPaymentMethods() throws MPRestException {
		MercadoPago.SDK.setBaseUrl("https://api.mercadopago.com");
		MercadoPago.SDK.configure("TEST-4876257682735912-102121-020195b6c403bfe2f119a8dd2474a72c-333551634");
		@SuppressWarnings("deprecation")
		MPApiResponse response = MercadoPago.SDK.Get("/v1/payment_methods");
		JsonElement rta = response.getJsonElementResponse();
		
		Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();

		List<Map<String,Object>> yourList = new Gson().fromJson(rta, resultType);
		
		return yourList;
		

	}

	public String getRouteByName(String nombreMedioPago) throws MPRestException {
		
		List<Map<String,Object>> lista = this.getListPaymentMethods();
		
		Optional<Map<String,Object>> medioPago = lista.stream().filter(m -> m.get("name").equals(nombreMedioPago)).findFirst();
		
		if (medioPago.isPresent()) {
			return (String) medioPago.get().get("thumbnail");
		}
		else return null;
	}
}
