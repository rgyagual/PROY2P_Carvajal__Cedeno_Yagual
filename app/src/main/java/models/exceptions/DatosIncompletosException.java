package models.exceptions;

/**
 * Excepción personalizada que se dispara cuando una operación del sistema
 * detecta que no se han proporcionado todos los campos o datos obligatorios
 * necesarios para su procesamiento.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class DatosIncompletosException extends Exception {

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Construye una nueva excepción con el mensaje descriptivo especificado.
     *
     * @param mensaje Descripción detallada del error de validación
     */
    public DatosIncompletosException(String mensaje) {
        super(mensaje);
    }
}