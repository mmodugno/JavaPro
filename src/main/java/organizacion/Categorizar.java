package organizacion;

import java.util.List;

public class Categorizar {
	
	public TipoEmpresa clasificar(Double aComparar,Double limite1,Double limite2, Double limite3, Double limite4) {
		
	
		if(aComparar > limite3) {
			return TipoEmpresa.MedianaTramo2;
		}
		
		if(aComparar > limite2) {
			return TipoEmpresa.MedianaTramo1;
		}
		
		if(aComparar > limite1) {
			return TipoEmpresa.Pequenia;
		}
		
		return TipoEmpresa.Micro;
		
		
	}

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
	
	
	public TipoEmpresa categorizarVentas(Categoria categoria, double promedioDeVentas){
		
		TipoEmpresa tipo = null;
		
		switch(categoria) {
		case Construccion:
		tipo = clasificar(promedioDeVentas,15230000.0,90310000.0,503880000.0,755740000.0);
		break;
		
		case Servicios:
		tipo = clasificar(promedioDeVentas,8500000.0,50950000.0,425170000.0,607210000.0);	
		break;
		
		case Comercio:
		tipo = clasificar(promedioDeVentas,29740000.0,178860000.0,1502750000.0,2146810000.0);	
		break;
		
		case IndustriaMineria:
		tipo = clasificar(promedioDeVentas,26540000.0,190410000.0,1190330000.0,1739590000.0);	
		break;
		
		case Agropecuario:
		tipo = clasificar(promedioDeVentas,12890000.0,48480000.0,345430000.0,547890000.0);
		break;
		
		}
	
		return tipo;
}
	
	public TipoEmpresa categorizarPersonal(Categoria categoria, int cantidadPersonal){
		
		
		double personal = cantidadPersonal;
		
		TipoEmpresa tipo = null;
		
		switch(categoria) {
		case Construccion:
		tipo = clasificar(personal,12.0,45.0,200.0,590.0);
		break;
		
		case Servicios:
		tipo = clasificar(personal,7.0,30.0,165.0,535.0);
		break;
		
		case Comercio:
			
		tipo = clasificar(personal,7.0,35.0,125.0,345.0);
			
		break;
		
		case IndustriaMineria:
		
		tipo = clasificar(personal,15.0,60.0,235.0,655.0);	
		
		break;
		
		case Agropecuario:
			
		tipo = clasificar(personal,5.0,10.0,50.0,215.0);
			
		break;
		
		}
		

		return tipo;
	}
	
	
}
