package auditoria;

import egreso.Egreso;;

public class Reporte {
	
	private Egreso egreso;
	private String informe = "";
	private boolean resultadoValidacion;
	
	public Reporte() {
		informe += "\n\n##### Reporte Validación de Egreso #####\n\n";
	}
	
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
		informe += "Egreso Nro : \n" ;
	}
	
	public void resultadoValidacionPresupuestos(CantidadPresupuestos condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n\nCantidad Presupuestos : " + condValidacion.getCantidadPresupuestos() + "\n";
		else
			informe += "\n\n - Tiene " + condValidacion.getCantidadPresupuestos() + " Presupuestos y requiere " + condValidacion.getPresupuestosRequeridos();
	}
	
	public void resultadoValidacionMontoPresupuestos(MontoPresupuesto condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n\nMonto Total de la Compra : " + condValidacion.getMontoCompra() + "\n";
		else
			informe += "\n\nMonto de la Compra : " + condValidacion.getMontoCompra() + "\nMonto del Presupuesto Aceptado : " + condValidacion.getMontoPresupuestoAceptado() + "\n";
	}
	
	public void resultadoValidacionCriterios(Criterios condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n\nEl Presupuesto Aceptado es el Seleccionado por el Criterio " + "\n";
		else {
			informe += "\n\nEl Presupuesto Aceptado difiere del Seleccionado por el Criterio : ";
			informe += "\n\t Id Presupuesto Aceptado : " + condValidacion.getIdPresupuestoAceptado();
			informe += "\n\t Id Presupuesto que debió Aceptarse : " + condValidacion.getIdPresupuestoCriterio();
			informe += " - Con Monto :" + condValidacion.getPresupuestoCriterio().valorTotal() + "\n";
		}
	}
	
	public void resultadoValidacionItems(Items condValidacion, boolean resultado) {
		
		nombreYResultado(condValidacion, resultado);
		
		if(resultado)
			informe += "\n\nLos Items de Compra se validaron Correctamente ";
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
		informe += "\n ----- " + condValidacion.getNombre() + " -----\n";
		informe += "\nResultado : " + resultadoString(resultado);
	}
	
	private String resultadoString(boolean resultado) {
		if (resultado)
			return " Válido";
		else
			return " No Válido"; 
	}

	public String getInforme() {
		return informe;
	}

	public void setResultadoValidacion(boolean resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
		informe += "\n\n### Resultado Validación Egreso : " + resultadoString(resultadoValidacion) + " ###\n";
	}
	
	

}