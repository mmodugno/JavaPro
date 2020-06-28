package organizacion;

import java.util.List;

public class Categorizador {

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
	
	
	public TipoEmpresa categorizarVentas(Rubro rubro, double promedioDeVentas){
	
		return rubro.reClasificar(promedioDeVentas);
}
	
	public TipoEmpresa reClasificarPorPersonal(Rubro rubro, int cantidadPersonal){

		return rubro.reClasificarPorPersonal(cantidadPersonal);
	}
	
}
