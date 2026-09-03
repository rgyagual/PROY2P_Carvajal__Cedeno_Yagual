package models.exceptions;

/**
 * Excepción personalizada que se dispara cuando un usuario intenta crear,
 * editar o guardar un pronóstico para un partido que ya no se encuentra
 * en estado abierto o cuyo tiempo límite ha expirado.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class PronosticoFueraDeTiempoException extends Exception {

    // =======================================
    // CONSTRUCTOR
    // =======================================

    /**
     * Construye una nueva excepción con el mensaje descriptivo especificado.
     *
     * @param mensaje Descripción detallada del motivo por el cual el pronóstico está fuera de tiempo
     */
    public PronosticoFueraDeTiempoException(String mensaje) {
        super(mensaje);
    }
}