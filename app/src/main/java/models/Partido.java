package models;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa un partido de fútbol dentro del sistema del Mundial.
 * Almacena la información del encuentro como la fase, fecha, hora,
 * estadio, selecciones participantes y su estado actual.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class Partido {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Identificador único del partido
     */
    private String idPartido;
    /**
     * Fase del torneo a la que pertenece el partido
     */
    private Fase fase;
    /**
     * Fecha programada para el encuentro
     */
    private LocalDate fecha;
    /**
     * Hora programada para el encuentro
     */
    private LocalTime hora;
    /**
     * Estadio donde se disputará el partido
     */
    private String estadio;
    /**
     * Nombre de la primera selección participante (Local)
     */
    private String seleccion1;
    /**
     * Nombre de la segunda selección participante (Visitante)
     */
    private String seleccion2;
    /**
     * Estado actual del partido
     */
    private Estado estado;
    /**
     * Identificador del resultado del partido
     */
    private String idResultado;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Constructor para inicializar un objeto Partido con sus datos principales.
     *
     * @param idPartido  Identificador único del partido
     * @param fase       Fase del torneo correspondiente
     * @param fecha      Fecha programada para el partido
     * @param hora       Hora programada para el partido
     * @param estadio    Estadio donde se jugará el encuentro
     * @param seleccion1 Primera selección en contienda
     * @param seleccion2 Segunda selección en contienda
     * @param estado     Estado inicial o actual del partido
     */
    public Partido(String idPartido, Fase fase, LocalDate fecha, LocalTime hora,
                   String estadio, String seleccion1, String seleccion2,
                   Estado estado) {
        this.idPartido = idPartido;
        this.fase = fase;
        this.fecha = fecha;
        this.hora = hora;
        this.estadio = estadio;
        this.seleccion1 = seleccion1;
        this.seleccion2 = seleccion2;
        this.estado = estado;
    }

    // =======================================
    // GETTERS Y SETTERS
    // =======================================

    /**
     * Devuelve el identificador único del partido
     *
     * @return id del partido
     */
    public String getIdPartido() {
        return idPartido;
    }

    /**
     * Establece el identificador único del partido
     *
     * @param idPartido id a asignar al partido
     */
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    /**
     * Devuelve la fase del torneo correspondiente al partido
     *
     * @return objeto Fase del partido
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * Establece la fase del torneo para el partido
     *
     * @param fase objeto Fase a asignar
     */
    public void setFase(Fase fase) {
        this.fase = fase;
    }

    /**
     * Devuelve la fecha programada para el partido
     *
     * @return objeto LocalDate con la fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha programada para el partido
     *
     * @param fecha objeto LocalDate a asignar
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la hora programada para el partido
     *
     * @return objeto LocalTime con la hora
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Establece la hora programada para el partido
     *
     * @param hora objeto LocalTime a asignar
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Devuelve el estadio donde se jugará el partido
     *
     * @return nombre del estadio
     */
    public String getEstadio() {
        return estadio;
    }

    /**
     * Establece el estadio donde se jugará el partido
     *
     * @param estadio nombre del estadio a asignar
     */
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    /**
     * Devuelve el nombre de la Selección 1
     *
     * @return nombre de la primera selección
     */
    public String getSeleccion1() {
        return seleccion1;
    }

    /**
     * Establece el nombre de la Selección 1
     *
     * @param seleccion1 nombre de la selección a asignar
     */
    public void setSeleccion1(String seleccion1) {
        this.seleccion1 = seleccion1;
    }

    /**
     * Devuelve el nombre de la Selección 2
     *
     * @return nombre de la segunda selección
     */
    public String getSeleccion2() {
        return seleccion2;
    }

    /**
     * Establece el nombre de la Selección 2
     *
     * @param seleccion2 nombre de la selección a asignar
     */
    public void setSeleccion2(String seleccion2) {
        this.seleccion2 = seleccion2;
    }

    /**
     * Devuelve el estado actual del partido
     *
     * @return objeto Estado del partido
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del partido
     *
     * @param estado objeto Estado a asignar
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el identificador del resultado asociado al partido
     *
     * @return id del resultado
     */
    public String getIdResultado() {
        return idResultado;
    }

    /**
     * Asigna el identificador del resultado al partido
     *
     * @param resultado id del resultado a asignar
     */
    public void setIdResultado(String resultado) {
        this.idResultado = resultado;
    }
}