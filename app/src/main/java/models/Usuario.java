package models;

/**
 * Representa la abstracción base para todos los usuarios del sistema.
 * Define los atributos y métodos generales para la gestión de credenciales,
 * identificación y roles dentro de la aplicación.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public abstract class Usuario {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Identificador único del usuario
     */
    protected String idUsuario;
    /**
     * Nombre de usuario para autenticación
     */
    protected String nombreUsuario;
    /**
     * Contraseña de acceso al sistema
     */
    protected String contrasena;
    /**
     * Nombre completo del usuario
     */
    protected String nombreCompleto;
    /**
     * Rol o tipo de usuario asignado
     */
    protected TipoUsuario tipoUsuario;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Inicializa los atributos principales de un usuario.
     *
     * @param idUsuario      Identificador único del usuario
     * @param nombreUsuario  Nombre de usuario para el inicio de sesión
     * @param contrasena     Contraseña de acceso
     * @param nombreCompleto Nombre y apellido completo
     * @param tipoUsuario    Tipo de rol en el sistema
     */
    public Usuario(String idUsuario, String nombreUsuario, String contrasena,
                   String nombreCompleto, TipoUsuario tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
    }

    // =======================================
    // MÉTODOS GETTERS Y SETTERS
    // =======================================

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return Identificador del usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param idUsuario Nuevo identificador
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario utilizado para iniciar sesión.
     *
     * @return Nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario para el inicio de sesión.
     *
     * @param nombreUsuario Nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña de acceso.
     *
     * @return Contraseña del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña de acceso del usuario.
     *
     * @param contrasena Nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombreCompleto Nuevo nombre completo
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el rol o tipo de usuario en la aplicación.
     *
     * @return Tipo de usuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Establece el rol o tipo de usuario en la aplicación.
     *
     * @param tipoUsuario Nuevo tipo de usuario
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}