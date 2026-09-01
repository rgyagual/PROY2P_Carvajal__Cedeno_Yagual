package models;

/**
 * Representa a un usuario administrador dentro del sistema de pronósticos del Mundial.
 * Hereda de la clase base Usuario e incluye información específica del
 * rol administrativo, como el cargo o puesto que desempeña en la organización.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class Administrador extends Usuario {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Cargo o función administrativa asignada dentro de la organización
     */
    private String cargo;

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Constructor para inicializar un objeto Administrador con sus credenciales de usuario
     * y su cargo específico.
     *
     * @param idUsuario      Identificador único del usuario
     * @param nombreUsuario  Nombre de usuario para iniciar sesión
     * @param contrasena     Contraseña de acceso al sistema
     * @param nombreCompleto Nombres y apellidos completos del administrador
     * @param cargo          Cargo o puesto administrativo asignado
     */
    public Administrador(String idUsuario, String nombreUsuario, String contrasena,
                         String nombreCompleto, String cargo) {
        super(idUsuario, nombreUsuario, contrasena, nombreCompleto, TipoUsuario.ADMINISTRADOR);
        this.cargo = cargo;
    }

    // =======================================
    // GETTERS Y SETTERS
    // =======================================

    /**
     * Devuelve el cargo del administrador
     *
     * @return cargo asignado
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece o actualiza el cargo del administrador
     *
     * @param cargo cargo a asignar
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}