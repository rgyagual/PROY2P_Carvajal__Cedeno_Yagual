package models.exceptions;

/**
 * Excepción personalizada que se dispara durante el proceso de autenticación o inicio de sesión
 * cuando las credenciales ingresadas (usuario o contraseña) son incorrectas o inválidas.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class CredencialesInvalidasException extends Exception {

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Construye la excepción con un mensaje descriptivo personalizado.
     *
     * @param mensaje Descripción detallada del error de credenciales
     */
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }

}