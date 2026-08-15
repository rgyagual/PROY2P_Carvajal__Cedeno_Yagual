package models;

public class Administrador extends Usuario{

    private String cargo;

    public Administrador(String idUsuario, String nombreUsuario, String contrasena,
                         String nombreCompleto, String cargo) {
        super(idUsuario, nombreUsuario, contrasena, nombreCompleto, TipoUsuario.ADMINISTRADOR);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
