package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import usuarios.Usuario;

public class RepositorioUsuario {
    static List<Usuario> usuarios = null;

    public RepositorioUsuario() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
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
