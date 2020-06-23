package egreso;

public class ValidacionIncorrecta {
    private MotivoFalla motivo;
    private OrdenDeCompra orden;

    public ValidacionIncorrecta(MotivoFalla motivo, OrdenDeCompra orden) {
        this.motivo = motivo;
        this.orden = orden;
    }
    //ESTO EN ALGUN MOMENTO VA A HACER ALGO MAS, POR EL MOMENTO ES UN GET SOLAMENTE HASTA SABER QUE MAS HACER
    public MotivoFalla obtenerResultado(){
        return motivo;
    }
}
