package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import egreso.Ingreso;
import organizacion.EntidadJuridica;
import producto.Producto;
import usuarios.CategoriaDelSistema;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioIngreso {

	public EntityManager entityManager;


    public RepositorioIngreso(EntityManager entityManager) {

        this.entityManager = entityManager;
    }
    
 
    /*
    public List<Ingreso> byCategoria(CategoriaDelSistema unaCategoria) {
        return ingresos.stream().filter(a ->
                a.esDeCategoria(unaCategoria)
        ).collect(Collectors.toList());
  }*/

    public Ingreso byID(int id) {

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Ingreso> consulta = cb.createQuery(Ingreso.class);
		Root<Ingreso> ingresos = consulta.from(Ingreso.class);
		Predicate condicion = cb.equal(ingresos.get("id"), id);
		CriteriaQuery<Ingreso> where = consulta.select(ingresos).where(condicion);
		return this.entityManager.createQuery(where).getSingleResult();
	}

    public List<Ingreso> todos() {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Ingreso> consulta = cb.createQuery(Ingreso.class);
		Root<Ingreso> ingresos = consulta.from(Ingreso.class);
		return this.entityManager.createQuery(consulta.select(ingresos)).getResultList();
    }

/*
    public Ingreso buscarIngreso(String descripcion) {
        Ingreso unIngreso = ingresos.stream().filter(ingreso -> ingreso.getDescripcion().equals(descripcion)).findFirst().get();
        return unIngreso;
    }*/
    
 
    
    
    public void borrar(Ingreso ingreso) {
        this.entityManager.remove(ingreso);
    }

    public void crear(Ingreso ingreso) {
    	this.entityManager.persist(ingreso);
    }

    int ordenarInt(int primero,int segundo){
		if (primero > segundo) return -1;
		if (primero < segundo) return 1;
		return 0;

	}

}
