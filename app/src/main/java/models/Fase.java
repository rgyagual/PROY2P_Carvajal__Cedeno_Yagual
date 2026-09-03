package models;

/**
 * Representa las diferentes fases o etapas en las que se desarrolla el torneo del Mundial.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public enum Fase {
    /**
     * Primera fase de la competición donde los equipos compiten en grupos
     */
    FASE_DE_GRUPOS,

    /**
     * Etapa de eliminación directa con 32 equipos clasificatorios
     */
    DIECISEISAVOS_DE_FINAL,

    /**
     * Etapa de eliminación directa con 16 equipos
     */
    OCTAVOS_DE_FINAL,

    /**
     * Cuartos de final del torneo
     */
    CUARTOS_DE_FINAL,

    /**
     * Semifinales del torneo
     */
    SEMIFINALES,

    /**
     * Partido para definir el tercer lugar de la competición
     */
    TERCER_LUGAR,

    /**
     * Gran final para definir al campeón del torneo
     */
    FINAL
}