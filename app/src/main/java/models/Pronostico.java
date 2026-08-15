package models;

import java.io.Serializable;

public class Pronostico implements Serializable {
    private String idPronostico;
    private Participante participante;
    private String idPartido;
    private int golesSel1;
    private int golesSel2;
    private int puntosObtenidos;

    public Pronostico(String idPronostico, Participante participante, String idPartido,
                      int golesSel1, int golesSel2, int puntosObtenidos) {
        this.idPronostico = idPronostico;
        this.participante = participante;
        this.idPartido = idPartido;
        this.golesSel1 = golesSel1;
        this.golesSel2 = golesSel2;
        this.puntosObtenidos = puntosObtenidos;
    }

    public String getIdPronostico() {
        return idPronostico;
    }
    public void setIdPronostico(String idPronostico) {
        this.idPronostico = idPronostico;
    }

    public Participante getParticipante() {
        return participante;
    }
    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getIdPartido() {
        return idPartido;
    }
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public int getGolesSel1() {
        return golesSel1;
    }
    public void setGolesSel1(int golesSel1) {
        this.golesSel1 = golesSel1;
    }

    public int getGolesSel2() {
        return golesSel2;
    }
    public void setGolesSel2(int golesSel2) {
        this.golesSel2 = golesSel2;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }
    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

}
