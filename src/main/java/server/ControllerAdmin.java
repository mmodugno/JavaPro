package server;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerAdmin {
	
	public ModelAndView index(Request request, Response response) {
	    	
		System.out.println("Valor Atributo Session admin : " + request.session().attribute("admin"));
		
		if((request.session().attribute("user") == null) || (request.session().attribute("admin") == null))
    		response.redirect("/login");
    	if((request.session().attribute("admin").equals(false)))
    		response.redirect("/login");
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("usuario", request.session().attribute("user"));
        
        return new ModelAndView(map , "admin.html");
    }

}
