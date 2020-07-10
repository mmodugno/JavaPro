package usuarios;

public class Categoria implements CategoriaDelSistema{
	public Categoria(String nombre, String criterio) {
		super();
		this.nombre = nombre;
		this.criterio = criterio;
	}

	private String nombre;
	private String criterio;
	
	@Override
	public String getCategoria() {
		return nombre;
	}
	
	public String getCriterio() {
		return criterio;
	}
	
	
	
}
