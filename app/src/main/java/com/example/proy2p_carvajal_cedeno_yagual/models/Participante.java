package com.example.proy2p_carvajal_cedeno_yagual.models;

public class Participante extends Usuario implements Comparable<Participante> {
    private int puntajeAcumulado;

    public  Participante(String id, String username, String password, String nombreCompleto,
                              int puntajeAcumulado) {
        super(id,username,password,nombreCompleto,"Participante");
        this.puntajeAcumulado =puntajeAcumulado;
    }

    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    @Override
    public int compareTo(Participante o) {
        // Orden descendente por puntaje acumulado
        if (this.puntajeAcumulado != o.puntajeAcumulado) {
            return Integer.compare(o.puntajeAcumulado, this.puntajeAcumulado);
        }
        // Si empatan, alfabéticamente por username
        return this.getUsername().compareToIgnoreCase(o.getUsername());
    }
}