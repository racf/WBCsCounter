package mx.com.sousystems.hemacount.domain;

import java.io.Serializable;

public class Celula implements Serializable{

    private String id;
    private String nombre;
    private String nombreAbrev;
    private String descripcion;
    private int estatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAbrev() {
        return nombreAbrev;
    }

    public void setNombreAbrev(String nombreAbrev) {
        this.nombreAbrev = nombreAbrev;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Celula{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nombreAbrev='" + nombreAbrev + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}
