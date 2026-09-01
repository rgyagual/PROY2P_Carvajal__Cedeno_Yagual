package models;

/**
 * Representa el resultado oficial registrado para un partido disputado.
 * Almacena los marcadores finales de las selecciones involucradas y provee
 * métodos para determinar el ganador del encuentro.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class Resultado {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Identificador único del resultado
     */
    private String idResultado;
    /**
     * Identificador único del partido al que pertenece el resultado
     */
    private String idPartido;
    /**
     * Cantidad de goles anotados por la selección local (Selección 1)
     */
    private int golesSeleccion1;
    /**
     * Cantidad de goles anotados por la selección visitante (Selección 2)
     */
    private int golesSeleccion2;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Inicializa los datos del resultado oficial de un partido.
     *
     * @param idResultado     Identificador único del resultado
     * @param idPartido       Identificador único del partido
     * @param golesSeleccion1 Goles anotados por el equipo local
     * @param golesSeleccion2 Goles anotados por el equipo visitante
     */
    public Resultado(String idResultado, String idPartido, int golesSeleccion1, int golesSeleccion2) {
        this.idResultado = idResultado;
        this.idPartido = idPartido;
        this.golesSeleccion1 = golesSeleccion1;
        this.golesSeleccion2 = golesSeleccion2;
    }

    // =======================================
    // MÉTODOS GETTERS Y SETTERS
    // =======================================

    /**
     * Obtiene el identificador único del resultado.
     *
     * @return Identificador del resultado
     */
    public String getIdResultado() {
        return idResultado;
    }

    /**
     * Establece el identificador único del resultado.
     *
     * @param idResultado Nuevo identificador del resultado
     */
    public void setIdResultado(String idResultado) {
        this.idResultado = idResultado;
    }

    /**
     * Obtiene el identificador del partido asociado.
     *
     * @return Identificador del partido
     */
    public String getIdPartido() {
        return idPartido;
    }

    /**
     * Establece el identificador del partido asociado.
     *
     * @param idPartido Nuevo identificador del partido
     */
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    /**
     * Obtiene la cantidad de goles anotados por la selección local.
     *
     * @return Goles de la selección 1
     */
    public int getGolesSeleccion1() {
        return golesSeleccion1;
    }

    /**
     * Establece la cantidad de goles anotados por la selección local.
     *
     * @param golesSeleccion1 Número de goles
     */
    public void setGolesSeleccion1(int golesSeleccion1) {
        this.golesSeleccion1 = golesSeleccion1;
    }

    /**
     * Obtiene la cantidad de goles anotados por la selección visitante.
     *
     * @return Goles de la selección 2
     */
    public int getGolesSeleccion2() {
        return golesSeleccion2;
    }

    /**
     * Establece la cantidad de goles anotados por la selección visitante.
     *
     * @param golesSeleccion2 Número de goles
     */
    public void setGolesSeleccion2(int golesSeleccion2) {
        this.golesSeleccion2 = golesSeleccion2;
    }

}