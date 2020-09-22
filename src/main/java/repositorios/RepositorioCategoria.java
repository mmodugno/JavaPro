package repositorios;

import java.util.ArrayList;
import java.util.List;

import usuarios.Categoria;
import usuarios.CategoriaCompuesta;
import usuarios.CategoriaDelSistema;

public class RepositorioCategoria {
	
	static List<CategoriaDelSistema> categorias = null;
	
	public RepositorioCategoria() {
        if (categorias == null) {
        	categorias = new ArrayList<>();

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
        return new ArrayList<>(categorias);
    }
	
    public void crear(CategoriaDelSistema categoria) {
    	categorias.add(categoria);
    }
	
}