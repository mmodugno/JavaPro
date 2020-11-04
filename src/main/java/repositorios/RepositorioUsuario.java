package repositorios;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import db.EntityManagerHelper;
import egreso.Egreso;
import organizacion.Organizacion;
import producto.Producto;
import usuarios.CreadorUsuario;
import usuarios.CreationError;
import usuarios.Usuario;

public class RepositorioUsuario {
	
	private EntityManager entityManager;
	
    static List<Usuario> usuarios = null;

    public RepositorioUsuario(EntityManager entityManager) throws FileNotFoundException, ClassNotFoundException, CreationError, SQLException {
        
    	this.entityManager = entityManager;
    	
    }

    public Usuario buscarUsuario(String nombre) {
    	Usuario unUsuario = null;
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> consulta = cb.createQuery(Usuario.class);
        Root<Usuario> usuarios = consulta.from(Usuario.class);
        Predicate condicion = cb.equal(usuarios.get("nombre"), nombre);
        CriteriaQuery<Usuario> where = consulta.select(usuarios).where(condicion);

        try {
        	unUsuario = this.entityManager.createQuery(where).getSingleResult();
        	return unUsuario;
        } catch (NoResultException nre){
        	
    	}

    	if(unUsuario == null){
    	 return null;
    	}
    	return unUsuario;
    }

    public List<Usuario> todos() {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> consulta = cb.createQuery(Usuario.class);
        Root<Usuario> usuarios = consulta.from(Usuario.class);
        return this.entityManager.createQuery(consulta.select(usuarios)).getResultList();
    }

    public void borrar(String nombre) {
        usuarios = usuarios.stream().filter(usuario -> !usuario.getNombre().equals(nombre)).collect(Collectors.toList());
    }
    
    public Usuario byNombre(String nombre) {
    	
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> consulta = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = consulta.from(Usuario.class);
        Predicate condicion = cb.equal(usuario.get("nombre"),nombre);
        CriteriaQuery<Usuario> where = consulta.select(usuario).where(condicion);
        
        return this.entityManager.createQuery(where).getSingleResult();
      
    }

    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

}
