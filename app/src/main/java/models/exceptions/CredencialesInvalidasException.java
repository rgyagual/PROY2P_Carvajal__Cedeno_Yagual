package models.exceptions;

public class CredencialesInvalidasException extends Exception{

    public CredencialesInvalidasException(){
        super("El usuario o la contraseña estan mal");
    }
    public CredencialesInvalidasException(String mensaje){

        super(mensaje);
    }

}
