package mx.com.sousystems.wbcscounter.domain;

import java.io.Serializable;

public class Muestra implements Serializable {
    private Integer id;
    private Integer pacienteId = 0;
    private int cantidadInput;
    private Integer cantidadTotalCelula;
    private String fecha;
    private double totalWbcSnrbc;
    private double totalWbcCnrbc;
    private int estatus;

    private Paciente paciente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getCantidadInput() {
        return cantidadInput;
    }

    public void setCantidadInput(int cantidadInput) {
        this.cantidadInput = cantidadInput;
    }

    public Integer getCantidadTotalCelula() {
        return cantidadTotalCelula;
    }

    public void setCantidadTotalCelula(Integer cantidadTotalCelula) {
        this.cantidadTotalCelula = cantidadTotalCelula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotalWbcSnrbc() {
        return totalWbcSnrbc;
    }

    public void setTotalWbcSnrbc(double totalWbcSnrbc) {
        this.totalWbcSnrbc = totalWbcSnrbc;
    }

    public double getTotalWbcCnrbc() {
        return totalWbcCnrbc;
    }

    public void setTotalWbcCnrbc(double totalWbcCnrbc) {
        this.totalWbcCnrbc = totalWbcCnrbc;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    @Override
    public String toString() {
        return "Muestra{" +
                "id=" + id +
                ", pacienteId=" + pacienteId +
                ", cantidadInput=" + cantidadInput +
                ", cantidadInput=" + cantidadTotalCelula +
                ", fecha='" + fecha + '\'' +
                ", totalWbcSnrbc=" + totalWbcSnrbc +
                ", totalWbcCnrbc=" + totalWbcCnrbc +
                ", estatus=" + estatus +
                '}';
    }
}
