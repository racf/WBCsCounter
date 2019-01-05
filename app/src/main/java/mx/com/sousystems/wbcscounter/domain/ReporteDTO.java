package mx.com.sousystems.wbcscounter.domain;

import java.util.List;

public class ReporteDTO {
    private String nombre;
    private String fecha;
    private String telefono;
    private Integer cantidadTotalWbc;
    private double totalConNrbc;
    private double totalSinNrbc;
    private String fechaGeneracion;
    private ReporteEtiquetasDTO reporteEtiquetasDTO;
    private List<MuestraDetalle> listaMuestraDetalle;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCantidadTotalWbc() {
        return cantidadTotalWbc;
    }

    public void setCantidadTotalWbc(Integer cantidadTotalWbc) {
        this.cantidadTotalWbc = cantidadTotalWbc;
    }

    public double getTotalConNrbc() {
        return totalConNrbc;
    }

    public void setTotalConNrbc(double totalConNrbc) {
        this.totalConNrbc = totalConNrbc;
    }

    public double getTotalSinNrbc() {
        return totalSinNrbc;
    }

    public void setTotalSinNrbc(double totalSinNrbc) {
        this.totalSinNrbc = totalSinNrbc;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public ReporteEtiquetasDTO getReporteEtiquetasDTO() {
        return reporteEtiquetasDTO;
    }

    public void setReporteEtiquetasDTO(ReporteEtiquetasDTO reporteEtiquetasDTO) {
        this.reporteEtiquetasDTO = reporteEtiquetasDTO;
    }

    public List<MuestraDetalle> getListaMuestraDetalle() {
        return listaMuestraDetalle;
    }

    public void setListaMuestraDetalle(List<MuestraDetalle> listaMuestraDetalle) {
        this.listaMuestraDetalle = listaMuestraDetalle;
    }
}
