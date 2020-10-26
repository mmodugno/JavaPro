package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.MedioDePago;
import egreso.OrdenDeCompra;
import egreso.Presupuesto;
import egreso.TipoMedioPago;
import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.CategoriaDelSistema;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class RepositorioOrdenDeCompra {
	

	EntityManager entityManager;
    public RepositorioOrdenDeCompra(EntityManager entityManager) throws CloneNotSupportedException {
		 this.entityManager = entityManager;

	 }
	 public RepositorioOrdenDeCompra(){};

    public OrdenDeCompra byID(int id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OrdenDeCompra> consulta = cb.createQuery(OrdenDeCompra.class);
        Root<OrdenDeCompra> ordenes = consulta.from(OrdenDeCompra.class);
        Predicate condicion = cb.equal(ordenes.get("idOrden"), id);
        CriteriaQuery<OrdenDeCompra> where = consulta.select(ordenes).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
  }


    public List<OrdenDeCompra> todos() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OrdenDeCompra> consulta = cb.createQuery(OrdenDeCompra.class);
        Root<OrdenDeCompra> ordenes = consulta.from(OrdenDeCompra.class);
        return this.entityManager.createQuery(consulta.select(ordenes)).getResultList();
    }
    
    public void crear(OrdenDeCompra orden) {
    	this.entityManager.persist(orden);
    }

    public void ordenesSinVincular(){

    }

	

}
