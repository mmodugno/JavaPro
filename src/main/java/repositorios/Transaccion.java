package repositorios;


import dev.morphia.annotations.Entity;

@Entity
public class Transaccion {

    private String operacion;
    private String documento;
    private String viejo;
    private String nuevo;
    private String fecha;

    public String getOperacion() {
        return operacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getViejo() {
        return viejo;
    }

    public void setViejo(String viejo) {
        this.viejo = viejo;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }
}
