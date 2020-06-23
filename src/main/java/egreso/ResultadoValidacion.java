package egreso;

public class ResultadoValidacion {
    private InfoResultado motivo;
    private OrdenDeCompra orden;

    public ResultadoValidacion(InfoResultado motivo, OrdenDeCompra orden) {
        this.motivo = motivo;
        this.orden = orden;
    }
    //ESTO EN ALGUN MOMENTO VA A HACER ALGO MAS, POR EL MOMENTO ES UN GET SOLAMENTE HASTA SABER QUE MAS HACER
    public InfoResultado obtenerResultado(){
        return motivo;
    }
    public boolean esCorrecto(){
        return motivo.equals(InfoResultado.Correcto);
    }
}
