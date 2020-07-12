package egreso;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import usuarios.CategoriaDelSistema;

public class Ingreso implements Categorizable{

	public Float getMonto() {
		return monto;
	}

	public void asociarEgreso(Egreso egreso) {
		egresosAsociados.add(egreso);
	}

	public List<Egreso> getEgresosAsociados() {
		return egresosAsociados;
	}

	public Ingreso(String descripcion, Float monto, List<Egreso> egresosAsociados, Date fecha) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.egresosAsociados = egresosAsociados;
		this.fecha = LocalDate.now();
	}

private String descripcion;
private Float monto;
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
