package auditoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import producto.Item;
import egreso.Egreso;;

public class Reporte {
	
	private Egreso egreso;
	private String informe = "";
	
	public Reporte() {
		informe += " ----- Reporte Validacion -------";
	}
	
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
		informe += " \n\nEgreso Nro : \n\n" ;
	}
	
	public void resultadoValidacionPresupuestos(CantidadPresupuestos condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n Cantidad Presupuestos : " + condValidacion.getCantidadPresupuestos();
		else
			informe += "\n Tiene " + condValidacion.getCantidadPresupuestos() + " Presupuestos y requiere " + condValidacion.getPresupuestosRequeridos();
	}
	
	public void resultadoValidacionMontoPresupuestos(MontoPresupuesto condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n Monto Total de la Compra : " + condValidacion.getMontoCompra();
		else
			informe += "\n Monto de la Compra : " + condValidacion.getMontoCompra() + "\nMonto del Presupuesto Aceptado : " + condValidacion.getMontoPresupuestoAceptado();
	}
	
	public void resultadoValidacionCriterios(Criterios condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n El Presupuesto Aceptado es el Seleccionado por el Criterio ";
		else {
			informe += "\n El Presupuesto Aceptado difiere del Seleccionado por el Criterio : ";
			informe += "\n\t Id Presupuesto Aceptado : " + condValidacion.getIdPresupuestoAceptado();
			informe += "\n\t Id Presupuesto que debió Aceptarse :" + condValidacion.getIdPresupuestoCriterio();
		}
	}
	
	public void resultadoValidacionItems(Items condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n\t Los Items de Compra se validaron Correctamente ";
		else {
			informe += "\n\t Hay diferencias entre Items de Compra y Presupuesto";
			informe += "\n\t Cantidad Items Compra : " + condValidacion.getCantidadItemsCompra();
			informe += "\n\t Cantidad Items Presupuesto :" + condValidacion.getCantidadItemsPresupuesto();
			
			if(condValidacion.getItemsNoValidadosOrdenCompra().size() > 0) {
				informe += "\n\t Items No Validados";
				for (int i = 0; i < condValidacion.getItemsNoValidadosOrdenCompra().size(); i++) {
					informe +=  "\n\t Código Producto : " + condValidacion.getItemsNoValidadosOrdenCompra().get(i).obtenerCodigoProducto();
		        }
				
			}
		}
		
	}
	
	private void nombreYResultado(CondicionValidacion condValidacion, boolean resultado) {
		informe += "\n\n" + condValidacion.getNombre() + " \n\n";
		informe += "Resultado : " + resultadoString(resultado);
	}
	
	private String resultadoString(boolean resultado) {
		if (resultado)
			return " Válido ";
		else
			return " No Válido "; 
	}

	public String getInforme() {
		return informe;
	}
	
	

}