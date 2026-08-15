package models.exceptions;

public class DatosIncompletosException extends Exception{
    public DatosIncompletosException (String mensaje){
        super(mensaje);
    }
}
