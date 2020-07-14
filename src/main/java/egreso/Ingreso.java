package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import usuarios.CategoriaDelSistema;

public class Ingreso implements Categorizable{

	public double getMonto() {
		return monto;
	}

	public void asociarEgreso(Egreso egreso) {
		egresosAsociados.add(egreso);
	}

	public List<Egreso> getEgresosAsociados() {
		return egresosAsociados;
	}

	public Ingreso(String descripcion, double monto) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.egresosAsociados = new ArrayList<Egreso>();
		this.fecha = LocalDate.now();
	}

private String descripcion;
private double monto;
private List<Egreso> egresosAsociados;
private CategoriaDelSistema categoria = null;

	public LocalDate getFecha() {
		return fecha;
	}

	private LocalDate fecha;

@Override
public void categorizar(CategoriaDelSistema categoria) {
	this.categoria = categoria;
}





}
