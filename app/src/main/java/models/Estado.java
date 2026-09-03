package models;

/**
 * Representa los posibles estados en los que se puede encontrar un partido en el sistema.
 * Sirve para determinar si un partido aún acepta pronósticos, si está en juego o bloqueado,
 * o si ya ha finalizado con un resultado registrado.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public enum Estado {
    /**
     * El partido aún no ha comenzado y está disponible para registrar o editar pronósticos
     */
    ABIERTO,

    /**
     * El partido está próximo a iniciar o en juego; ya no se admiten pronósticos ni modificaciones
     */
    CERRADO,

    /**
     * El encuentro ha concluido y cuenta con un resultado oficial registrado
     */
    FINALIZADO
}