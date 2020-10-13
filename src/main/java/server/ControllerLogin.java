package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

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
    
    public ModelAndView validarLogin(Request request, Response response) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
    	
    	String user = request.queryParams("username");
        String pass = request.queryParams("password");
        
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
    	
    	//List<Usuario> usuarios = repoUsuario.todos();
        //Usuario usuario = repoUsuario.checkUser(user, pass);
        Usuario usuario = repoUsuario.buscarUsuario(user);

        if(usuario == null) {
	        response.status(401);
	        response.redirect("/login/incorrecto");
        }
        
        if((usuario.getNombre().equals(user)) && (usuario.getPassword().equals(pass))) {
        	
        	request.session(true);
            request.session().attribute("user", user);
            request.session().attribute("usuario", usuario);
            request.session().maxInactiveInterval(3600);

            response.redirect("/inicio");
        }
        
        response.status(401);
        response.redirect("/login/incorrecto");

        return null;
    }

}
