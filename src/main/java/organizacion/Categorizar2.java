package organizacion;

import java.util.List;

public class Categorizar2 {

	public TipoEmpresa elegirTipo(List<TipoEmpresa> listaEmpresas) {
		
		if(listaEmpresas.contains(TipoEmpresa.MedianaTramo2)) {
			
			return TipoEmpresa.MedianaTramo2;
			
		}
		
		if(listaEmpresas.contains(TipoEmpresa.MedianaTramo1)) {
			
			return TipoEmpresa.MedianaTramo2;
			
		}
		
		if(listaEmpresas.contains(TipoEmpresa.Pequenia)) {
	
			return TipoEmpresa.Pequenia;
	
		}

		return TipoEmpresa.Micro;
		
	}
	
	
	public TipoEmpresa categorizarVentas(Categoria2 categoria, double promedioDeVentas){
	
		return categoria.reClasificar(promedioDeVentas);
}
	
	public TipoEmpresa reClasificarPorPersonal(Categoria2 categoria, int cantidadPersonal){

		return categoria.reClasificarPorPersonal(cantidadPersonal);
	}
	
}
