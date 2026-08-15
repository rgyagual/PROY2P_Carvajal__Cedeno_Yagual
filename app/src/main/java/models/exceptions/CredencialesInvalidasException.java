package models.exceptions;

public class CredencialesInvalidasException extends Exception{
    public CredencialesInvalidasException(String mensaje){
        super(mensaje);
    }
}
