package models;

import java.io.Serializable;

/**
 * Representa un pronóstico individual realizado por un participante en el sistema.
 * Almacena los marcadores predichos para cada selección y la puntuación obtenida.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class Pronostico implements Serializable {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Identificador único del pronóstico
     */
    private String idPronostico;
    /**
     * Participante que realizó el pronóstico
     */
    private Participante participante;
    /**
     * Identificador único del partido pronosticado
     */
    private String idPartido;
    /**
     * Cantidad de goles predichos para la Selección 1
     */
    private int golesSel1;
    /**
     * Cantidad de goles predichos para la Selección 2
     */
    private int golesSel2;
    /**
     * Puntos obtenidos por el participante según el resultado final del partido
     */
    private int puntosObtenidos;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Constructor para inicializar un objeto Pronostico con todos sus datos.
     *
     * @param idPronostico    Identificador único del pronóstico
     * @param participante    Participante asignado al pronóstico
     * @param idPartido       Identificador del partido correspondiente
     * @param golesSel1       Goles predichos para la primera selección
     * @param golesSel2       Goles predichos para la segunda selección
     * @param puntosObtenidos Puntos calculados para este pronóstico
     */
    public Pronostico(String idPronostico, Participante participante, String idPartido,
                      int golesSel1, int golesSel2, int puntosObtenidos) {
        this.idPronostico = idPronostico;
        this.participante = participante;
        this.idPartido = idPartido;
        this.golesSel1 = golesSel1;
        this.golesSel2 = golesSel2;
        this.puntosObtenidos = puntosObtenidos;
    }

    // =======================================
    // GETTERS Y SETTERS
    // =======================================

    /**
     * Devuelve el identificador único del pronóstico
     *
     * @return id del pronóstico
     */
    public String getIdPronostico() {
        return idPronostico;
    }

    /**
     * Establece el identificador único del pronóstico
     *
     * @param idPronostico id a asignar al pronóstico
     */
    public void setIdPronostico(String idPronostico) {
        this.idPronostico = idPronostico;
    }

    /**
     * Devuelve el participante que realizó el pronóstico
     *
     * @return objeto Participante
     */
    public Participante getParticipante() {
        return participante;
    }

    /**
     * Asigna el participante al pronóstico
     *
     * @param participante objeto Participante a asignar
     */
    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    /**
     * Devuelve el identificador del partido asociado al pronóstico
     *
     * @return id del partido
     */
    public String getIdPartido() {
        return idPartido;
    }

    /**
     * Asigna el identificador del partido al pronóstico
     *
     * @param idPartido id del partido a asignar
     */
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    /**
     * Devuelve los goles predichos para la Selección 1
     *
     * @return cantidad de goles predichos
     */
    public int getGolesSel1() {
        return golesSel1;
    }

    /**
     * Establece los goles predichos para la Selección 1
     *
     * @param golesSel1 goles a asignar
     */
    public void setGolesSel1(int golesSel1) {
        this.golesSel1 = golesSel1;
    }

    /**
     * Devuelve los goles predichos para la Selección 2
     *
     * @return cantidad de goles predichos
     */
    public int getGolesSel2() {
        return golesSel2;
    }

    /**
     * Establece los goles predichos para la Selección 2
     *
     * @param golesSel2 goles a asignar
     */
    public void setGolesSel2(int golesSel2) {
        this.golesSel2 = golesSel2;
    }

    /**
     * Devuelve los puntos obtenidos en este pronóstico
     *
     * @return puntos obtenidos
     */
    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    /**
     * Establece los puntos obtenidos en este pronóstico
     *
     * @param puntosObtenidos cantidad de puntos a asignar
     */
    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

}