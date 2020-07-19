package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import usuarios.CategoriaDelSistema;

public class Ingreso implements Categorizable{



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



	private double montoVinculado = 0.00;

	public LocalDate getFecha() {
		return fecha;
	}

	private LocalDate fecha;

@Override
public void categorizar(CategoriaDelSistema categoria) {
	this.categoria = categoria;
}

//CREO ESTO PARA SABER RAPIDO QUE MONTO ME QUEDA SIN VINCULAR (FUTURO USO BALANCE)
public double getMontoSinVincular(){

	return monto - montoVinculado;
}

//CUANDO ASOCIO EGRESO MODIFICO EL MONTO VINCULADO
public void asociarEgreso(Egreso egreso){


	montoVinculado += egreso.valorTotal();
	egresosAsociados.add(egreso);
}

	public double getMonto() {
		return monto;
	}

	//POR SI NECESITO EL GET Y SET PARA HACER EL BALANCE
	public double getMontoVinculado() {
		return montoVinculado;
	}

	public void setMontoVinculado(double montoVinculado) throws MontoSuperadoExcepcion {

		if(montoVinculado > monto){
			throw new MontoSuperadoExcepcion("No podes poner una cantidad que supere "+ monto);
		}
		this.montoVinculado = montoVinculado;
	}
	//PARA CUANDO HAGA LOS FILTROS
	public boolean puedoVincular(){
		return this.getMontoSinVincular()>0;
	}







}
