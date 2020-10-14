package egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import usuarios.CategoriaDelSistema;

public class Ingreso implements Categorizable{

	/*CONSTRUCTOR*/
	public Ingreso(String descripcion, double monto) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.fecha = LocalDate.now();
	}
	
	
	public Ingreso() {
		
	}
	/*
	*** se tendría que hacer as0
	* ingreso.setDescripcion(descripcion);
	* ingreso.setMonto(mkonto);
	*
	 */


	/*ATRIBUTOS*/
	private String descripcion;
	private double monto;
	private CategoriaDelSistema categoria = null;
	private double montoVinculado = 0.00;
	private LocalDate fecha;
	private int id;

	/*GETTERS*/
	public LocalDate getFecha() {
		return fecha;
	}
	public double getMonto() {
		return monto;
	}
	public double getMontoVinculado() {
		return montoVinculado;
	}
	public double getMontoSinVincular(){

		return monto - montoVinculado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	/*SETTERS*/

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public void setCategoria(CategoriaDelSistema categoria) {
		this.categoria = categoria;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setMontoVinculado(double montoVinculado) throws MontoSuperadoExcepcion {

		if(montoVinculado > monto){
			throw new MontoSuperadoExcepcion("No podes poner una cantidad que supere "+ monto);
		}
		this.montoVinculado = montoVinculado;
	}

	@Override
	public void categorizar(CategoriaDelSistema categoria) {
		this.categoria = categoria;
	}

	//CUANDO ASOCIO EGRESO MODIFICO EL MONTO VINCULADO
	public void asociarEgreso(double valor){


		montoVinculado += valor;
	}

	public boolean puedoVincular(){
		return this.getMontoSinVincular()>0;
	}


	public boolean esDeCategoria(CategoriaDelSistema unaCategoria) {
		if(categoria == null) return false;
		else {
			if(unaCategoria.esCompuesta()) {
				return unaCategoria.getSubCategorias().stream().anyMatch(c -> this.esDeCategoria(c));
			} else {
				return categoria.getCategoria().equals(unaCategoria.getCategoria());
			}
		}
	}	

	public void setId(int i) {
		this.id = i;
		
	}
	
	public int getId() {
		return this.id;
		
	}



}
