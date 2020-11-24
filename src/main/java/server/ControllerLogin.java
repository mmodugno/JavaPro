package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.apache.commons.codec.digest.DigestUtils;

import repositorios.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuarios.CreationError;
import usuarios.Usuario;

public class ControllerLogin {
		
    public ModelAndView login(Request request, Response response) {
        return new ModelAndView(null, "login.html");
    }
    
    public ModelAndView loginIncorrecto(Request request, Response response) {
        return new ModelAndView(null, "loginIncorrecto.html");
    }
    
    public ModelAndView validarLogin(Request request, Response response, EntityManager entityManager) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
    	
    	String user = request.queryParams("username");
        String pass = request.queryParams("password");
        
        RepositorioUsuario repoUsuario = new RepositorioUsuario(entityManager);
    	
        Usuario usuario = repoUsuario.buscarUsuario(user);

        if(usuario == null) {
	        response.status(401);
	        response.redirect("/loginIncorrecto");
        }
        
        if((usuario.getNombre().equals(user)) && (usuario.getPassword().equals(DigestUtils.md5Hex(pass)))) {
        	
        	request.session(true);
            request.session().attribute("user", user);
            request.session().attribute("usuario", usuario);
            request.session().maxInactiveInterval(3600);
            
            if(usuario.getNombre().equals("administrador") ) {
            	request.session().attribute("admin", true);
            	response.redirect("/administracion");
            } else {
            	request.session().attribute("admin", false);
            	response.redirect("/inicio");
            }
            
        }
        
        response.status(401);
        response.redirect("/loginIncorrecto");

        return null;
    }
    
    public ModelAndView logout(Request request, Response response) {
        request.session().removeAttribute("user");
        response.redirect("/login");
        return null;
    }
}

