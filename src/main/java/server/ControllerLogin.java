package server;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerLogin {
	
	
	
    public ModelAndView login(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }
    
    public ModelAndView validarLogin(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }

}
