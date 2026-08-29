package models;

public class Resultado {
    private String idResultado;
    private String idPartido;
    private int golesSeleccion1;
    private int golesSeleccion2;

    public Resultado(String idResultado, String idPartido, int golesSeleccion1, int golesSeleccion2) {
        this.idResultado = idResultado;
        this.idPartido = idPartido;
        this.golesSeleccion1 = golesSeleccion1;
        this.golesSeleccion2 = golesSeleccion2;
    }

    public String getIdResultado() {
        return idResultado;
    }
    public void setIdResultado(String idResultado) {
        this.idResultado = idResultado;
    }

    public String getIdPartido() {
        return idPartido;
    }
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public int getGolesSeleccion1() {
        return golesSeleccion1;
    }
    public void setGolesSeleccion1(int golesSeleccion1) {
        this.golesSeleccion1 = golesSeleccion1;
    }

    public int getGolesSeleccion2() {
        return golesSeleccion2;
    }
    public void setGolesSeleccion2(int golesSeleccion2) {
        this.golesSeleccion2 = golesSeleccion2;
    }

    public boolean ganoEquipoLocal(){
        if(golesSeleccion1>golesSeleccion2){
            return true;
        }
        return false;
    }
}
