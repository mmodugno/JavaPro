package repositorios;

import producto.Producto;
import producto.TipoItem;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioProducto {

    private EntityManager entityManager;

   /* private static List<Producto> productos;

    public static List<Producto> getProductos() {
        return productos;
    }*/

    public RepositorioProducto(EntityManager entityManager) {
        this.entityManager = entityManager;
/*
        Producto producto1 = new Producto(1, "Monitor", "Monitor 32", TipoItem.ARTICULO);
            Producto producto2 = new Producto(2, "Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
            Producto producto3 = new Producto(3, "Office", "Office365", TipoItem.SERVICIO);
            producto1.setIdProducto(1231);
            producto2.setIdProducto(3445);
            producto3.setIdProducto(6621);
            productos = new ArrayList<>();
            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
*/ //Aca esta el llenado de productos
    }

    public List<Producto> todos() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> consulta = cb.createQuery(Producto.class);
        Root<Producto> productos = consulta.from(Producto.class);
        return this.entityManager.createQuery(consulta.select(productos)).getResultList();
    }

    public Producto byID(int id) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> consulta = cb.createQuery(Producto.class);
        Root<Producto> productos = consulta.from(Producto.class);
        Predicate condicion = cb.equal(productos.get("idProducto"), id);
        CriteriaQuery<Producto> where = consulta.select(productos).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
    }

    int ordenarInt(int primero,int segundo){
        if (primero > segundo) return -1;
        if (primero < segundo) return 1;
        return 0;

    }

    public void agregar(Producto producto){

        this.entityManager.persist(producto);

    }

    public void eliminar(int id){
        Producto producto = this.byID(id);
        this.entityManager.remove(producto);

        //todo aca tengo que hacer el delete de la base
    }

    public Producto byCodPro(int codPro) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> consulta = cb.createQuery(Producto.class);
        Root<Producto> productos = consulta.from(Producto.class);
        Predicate condicion = cb.equal(productos.get("codProducto"), codPro);
        CriteriaQuery<Producto> where = consulta.select(productos).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
    }
}
