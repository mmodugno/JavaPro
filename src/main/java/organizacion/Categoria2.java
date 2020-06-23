package organizacion;

public enum Categoria2 {
	Construccion(15230000.0, 90310000.0, 503880000.0, 755740000.0, 12, 45, 200 ,590),
	Servicios(8500000.0, 50950000.0, 425170000.0, 607210000.0, 7, 30 , 165, 535),
	Comercio(29740000.0, 178860000.0, 1502750000.0, 2146810000.0, 7, 35 , 125, 345),
	IndustriaMineria(26540000.0, 190410000.0, 1190330000.0, 1739590000.0, 15, 60, 235, 655),
	Agropecuario(12890000.0, 48480000.0, 345430000.0, 547890000.0, 5, 10, 50, 215);
	
	private Categoria2(double micro,  double pequenia, double medianaTramoUno, double medianaTramoDos, int persMicro, int persPequenia, int persMedianaTramoUno, int persMedianaTramoDos) {
		this.vtasMicro = micro;
		this.vtasPequenia = pequenia;
		this.vtasMedianaTramoUno = medianaTramoUno;
		this.vtasMedianaTramoDos = medianaTramoDos;
		this.persMicro = persMicro;
		this.persPequenia = persPequenia;
		this.persMedianaTramoUno = persMedianaTramoUno;
		this.persMedianaTramoDos = persMedianaTramoDos;
		
	}
	
	double vtasMicro;
	double vtasPequenia;
	double vtasMedianaTramoUno;
	double vtasMedianaTramoDos;
	int persMicro;
	int persPequenia;
	int persMedianaTramoUno;
	int persMedianaTramoDos;
	
	public TipoEmpresa reClasificar(Double promedioDeVentas) {
				
		if(promedioDeVentas > this.vtasMedianaTramoDos) {
			return TipoEmpresa.MedianaTramo2;
		}
		
		if(promedioDeVentas > this.vtasMedianaTramoUno) {
			return TipoEmpresa.MedianaTramo1;
		}
		
		if(promedioDeVentas > this.vtasPequenia) {
			return TipoEmpresa.Pequenia;
		}
		
		return TipoEmpresa.Micro;
	}
	
	public TipoEmpresa reClasificarPorPersonal(int cantidadPersonal) {
		
		if(cantidadPersonal > this.persMedianaTramoDos) {
			return TipoEmpresa.MedianaTramo2;
		}
		
		if(cantidadPersonal > this.vtasMedianaTramoUno) {
			return TipoEmpresa.MedianaTramo1;
		}
		
		if(cantidadPersonal > this.persPequenia) {
			return TipoEmpresa.Pequenia;
		}
		
		return TipoEmpresa.Micro;
	}
}
