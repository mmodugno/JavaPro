package repositorios;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import db.EntityManagerHelper;
import organizacion.Organizacion;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

public class RepositorioUsuario {
	
	private EntityManager entityManager;
	
    static List<Usuario> usuarios = null;

    public RepositorioUsuario(EntityManager entityManager) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
        
    	this.entityManager = entityManager;
    	
    	if (usuarios == null) {
    		
        	
        	CreadorUsuario userMaker = new CreadorUsuario();
        	Organizacion organizacion = new Organizacion();
        	
        	Usuario userStandard = userMaker.crearUsuario("userStandard", "pru3b@tesT", "estandar", organizacion);
            usuarios = new ArrayList<>();
            usuarios.add(userStandard);
            
        	EntityManagerHelper.beginTransaction();
		    EntityManagerHelper.getEntityManager().persist(userStandard);
		    EntityManagerHelper.commit();
		    
        }
    }

    public Usuario buscarUsuario(String nombre) {
    	if(usuarios.stream().filter(usuario -> usuario.getNombre().equals(nombre)).count() > 0 ) {
    		Usuario unUsuario = usuarios.stream().filter(usuario -> usuario.getNombre().equals(nombre)).findFirst().get();
            return unUsuario;
    	} else {
    		return null;
    	}
    }

    public List<Usuario> todos() {
        return new ArrayList<>(usuarios);
    }

    public void borrar(String nombre) {
        usuarios = usuarios.stream().filter(usuario -> !usuario.getNombre().equals(nombre)).collect(Collectors.toList());
    }

    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

}
