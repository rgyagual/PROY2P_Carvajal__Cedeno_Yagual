package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partido implements Comparable {
    private String idPartido;
    private LocalDate fecha;
    private LocalTime hora;
    private String estadio;
    private String seleccion1;
    private String seleccion2;
    private Estado estado;

    public Partido(String idPartido, LocalDate fecha, LocalTime hora,
                   String estadio, String seleccion1, String seleccion2,
                   Estado estado) {
        this.idPartido = idPartido;
        this.fecha = fecha;
        this.hora = hora;
        this.estadio = estadio;
        this.seleccion1 = seleccion1;
        this.seleccion2 = seleccion2;
        this.estado = estado;
    }

    public String getIdPartido() {
        return idPartido;
    }
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getEstadio() {
        return estadio;
    }
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getSeleccion1() {
        return seleccion1;
    }
    public void setSeleccion1(String seleccion1) {
        this.seleccion1 = seleccion1;
    }

    public String getSeleccion2() {
        return seleccion2;
    }
    public void setSeleccion2(String seleccion2) {
        this.seleccion2 = seleccion2;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
