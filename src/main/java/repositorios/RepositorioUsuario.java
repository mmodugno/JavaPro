package repositorios;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import organizacion.Organizacion;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

public class RepositorioUsuario {
    static List<Usuario> usuarios = null;

    public RepositorioUsuario() throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
        if (usuarios == null) {
        	
        	CreadorUsuario userMaker = new CreadorUsuario();
        	Organizacion organizacion = new Organizacion();
        	
        	Usuario userStandard = userMaker.crearUsuario("userStandard", "pru3b@tesT", "estandar", organizacion); 
            usuarios = new ArrayList<>();
            usuarios.add(userStandard);
        }
    }

    public Usuario buscarUsuario(String nombre) {
        Usuario unUsuario = usuarios.stream().filter(usuario -> usuario.getNombre().equals(nombre)).findFirst().get();
        return unUsuario;
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
