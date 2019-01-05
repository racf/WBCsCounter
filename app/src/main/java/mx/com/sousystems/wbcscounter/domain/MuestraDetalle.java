package mx.com.sousystems.wbcscounter.domain;

import java.io.Serializable;

public class MuestraDetalle implements Serializable {
    private Integer id;
    private long muestraId;
    private String celulaId;
    private int cantidad;
    private int estatus;
    private Muestra muestra;
    private Celula celula;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getMuestraId() {
        return muestraId;
    }

    public void setMuestraId(long muestraId) {
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

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

    public Celula getCelula() {
        return celula;
    }

    public void setCelula(Celula celula) {
        this.celula = celula;
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
