package egreso;

import java.util.List;

import usuarios.CategoriaDelSistema;

public class Ingreso implements Categorizable{
	
public Ingreso(String descripcion, Float monto, List<Egreso> egresosAsociados) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.egresosAsociados = egresosAsociados;
	}

private String descripcion;
private Float monto;
private List<Egreso> egresosAsociados;
private CategoriaDelSistema categoria = null;

@Override
public void categorizar(CategoriaDelSistema categoria) {
	this.categoria = categoria;
}





}
