package models;

public class Participante extends Usuario implements Comparable<Participante> {
    private int puntajeAcumulado;

    public  Participante(String idUsuario, String nombreUsuario, String contrasena, String nombreCompleto,
                              TipoUsuario tipoUsuario, int puntajeAcumulado) {
        super(idUsuario,nombreUsuario,contrasena,nombreCompleto,TipoUsuario.PARTICIPANTE);
        this.puntajeAcumulado =puntajeAcumulado;
    }

    public int getPuntajeAcumulado() {

        return puntajeAcumulado;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

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