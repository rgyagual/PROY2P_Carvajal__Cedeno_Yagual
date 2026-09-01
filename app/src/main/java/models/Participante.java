package models;

/**
 * Representa a un usuario participante en el sistema de pronósticos del Mundial.
 * Hereda de la clase base Usuario e implementa Comparable para
 * permitir la ordenación de los participantes en la tabla de posiciones según
 * su puntaje acumulado y nombre de usuario.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class Participante extends Usuario implements Comparable<Participante> {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Puntos acumulados por el participante según sus aciertos en los pronósticos
     */
    private int puntajeAcumulado;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Constructor para inicializar un objeto Participante con sus datos de usuario
     * y su puntaje acumulado.
     *
     * @param idUsuario        Identificador único del usuario
     * @param nombreUsuario    Nombre de usuario para iniciar sesión
     * @param contrasena       Contraseña de acceso al sistema
     * @param nombreCompleto   Nombres y apellidos completos del participante
     * @param tipoUsuario      Tipo o rol del usuario dentro del sistema
     * @param puntajeAcumulado Puntos iniciales o acumulados del participante
     */
    public Participante(String idUsuario, String nombreUsuario, String contrasena, String nombreCompleto,
                        TipoUsuario tipoUsuario, int puntajeAcumulado) {
        super(idUsuario, nombreUsuario, contrasena, nombreCompleto, TipoUsuario.PARTICIPANTE);
        this.puntajeAcumulado = puntajeAcumulado;
    }

    // =======================================
    // GETTERS Y SETTERS
    // =======================================

    /**
     * Devuelve el puntaje acumulado por el participante
     *
     * @return puntaje acumulado
     */
    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    /**
     * Establece o actualiza el puntaje acumulado del participante
     *
     * @param puntajeAcumulado puntaje a asignar
     */
    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    // =======================================
    // MÉTODOS SOBRESCRITOS
    // =======================================

    /**
     * Compara este participante con otro para establecer un orden
     * El criterio asigna mayor prioridad al puntaje acumulado de forma descendente.
     * En caso de empate en puntos, se ordena alfabéticamente por el nombre de usuario.
     *
     * @param o El participante con el cual se va a comparar
     * @return Un valor negativo si este participante va antes, cero si son equivalentes,
     * o positivo si va después.
     */
    @Override
    public int compareTo(Participante o) {
        // Orden descendente por puntaje acumulado
        if (this.puntajeAcumulado != o.puntajeAcumulado) {
            return Integer.compare(o.puntajeAcumulado, this.puntajeAcumulado);
        }
        // Si empatan, alfabéticamente por username
        return this.getNombreUsuario().compareToIgnoreCase(o.getNombreUsuario());
    }
}