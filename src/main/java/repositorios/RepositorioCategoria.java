package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import egreso.Egreso;
import egreso.OrdenDeCompra;
import usuarios.Categoria;
import usuarios.CategoriaCompuesta;
import usuarios.CategoriaDelSistema;

public class RepositorioCategoria {
	
	static List<CategoriaDelSistema> categorias = null;
	
	EntityManager entityManager;
	
	public RepositorioCategoria() {}
	
	public RepositorioCategoria(EntityManager entityManager) {
        
        	
        	categorias = new ArrayList<>();
        	
        	this.entityManager = entityManager;
        	
        	if (categorias == null) {

        	Categoria categoriaBSAS = new Categoria("Buenos Aires","Provincia");
        	Categoria categoriaMENDOZA = new Categoria("Mendoza","Provincia");
        	
        	
        	List<CategoriaDelSistema> listaSubCategorias = new ArrayList<CategoriaDelSistema>();
    		
    		listaSubCategorias.add(categoriaBSAS);
    		listaSubCategorias.add(categoriaMENDOZA);
    		
    		
        	CategoriaCompuesta categoriaARGENTINA = new CategoriaCompuesta("argentina","pais");
        	
        	categoriaARGENTINA.setSubCategorias(listaSubCategorias);
        	
        	
        	categorias.add(categoriaBSAS);
        	categorias.add(categoriaMENDOZA);
        	categorias.add(categoriaARGENTINA);
        	
        }
    }
	
	

    public List<CategoriaDelSistema> todos() {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoriaDelSistema> consulta = cb.createQuery(CategoriaDelSistema.class);
        Root<CategoriaDelSistema> categorias = consulta.from(CategoriaDelSistema.class);
        return this.entityManager.createQuery(consulta.select(categorias)).getResultList();
        
    }
	
    public void crear(CategoriaDelSistema categoria) {
    	this.entityManager.persist(categoria);
    }



	public CategoriaDelSistema buscar(String categoriaString) {
		
		Optional<CategoriaDelSistema> categoria = this.todos().stream().filter(e -> categoriaString.equals(e.getCategoria())).findFirst();
		
		if (categoria.isPresent()) {
			return categoria.get();
		}
		else return null;
		
	}
	
}
