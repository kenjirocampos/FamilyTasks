package com.example.familytasks.Model;

import com.google.type.DateTime;

public class taskFamily {
    String nombreTarea;
    String detalleTarea;
    String fechaInicio;
    String fechaTermino;
    boolean estadoTarea;
    boolean estadoAsignado;
    String idTarea;

    String idUsuario;

    public taskFamily (String nombreTarea, String detalleTarea, String fechaInicio, String fechaTermino, boolean estadoTarea, boolean estadoAsignado, String idTarea,String idUsuario) {
        this.nombreTarea = nombreTarea;
        this.detalleTarea = detalleTarea;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.estadoTarea = estadoTarea;
        this.estadoAsignado = estadoAsignado;
        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
    }
    public taskFamily(){}

    public String getIdUsuario() {
        return idUsuario;
    }
    public String getNombreTarea() {
        return nombreTarea;
    }

    public String getDetalleTarea() {
        return detalleTarea;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public boolean isEstadoTarea() {
        return estadoTarea;
    }

    public boolean isEstadoAsignado() {
        return estadoAsignado;
    }

    public String getIdTarea() {
        return idTarea;
    }
}
