package mx.com.sousystems.wbcscounter.domain;

import java.io.Serializable;

public class MuestraDetalle implements Serializable {
    private Integer id;
    private Integer muestraId;
    private String celulaId;
    private int cantidad;
    private int estatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMuestraId() {
        return muestraId;
    }

    public void setMuestraId(Integer muestraId) {
        this.muestraId = muestraId;
    }

    public String getCelulaId() {
        return celulaId;
    }

    public void setCelulaId(String celulaId) {
        this.celulaId = celulaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int status) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "MuestraDetalle{" +
                "id=" + id +
                ", muestraId=" + muestraId +
                ", celulaId='" + celulaId + '\'' +
                ", cantidad=" + cantidad +
                ", status=" + estatus +
                '}';
    }
}
