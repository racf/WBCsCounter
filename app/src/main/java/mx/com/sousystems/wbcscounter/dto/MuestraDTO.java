package mx.com.sousystems.wbcscounter.dto;

public class MuestraDTO {
    private String tipo;
    private Integer cantidad;
    private double porcentaje;
    private double unidadMedida;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(double unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public String toString() {
        return "MuestraDto{" +
                "tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                ", porcentaje=" + porcentaje +
                ", unidadMedida=" + unidadMedida +
                '}';
    }
}
