package mx.com.sousystems.wbcscounter.dto;

public class HeaderTablaDTO {
    private String tipo;
    private String cantidad;
    private String porcentaje;
    private String unidadMedida;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public String toString() {
        return "HeaderTablaDTO{" +
                "tipo='" + tipo + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", porcentaje='" + porcentaje + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}
