package com.example.proy2p_carvajal_cedeno_yagual.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String username;
    private String password;
    private String nombreCompleto;
    private String tipoUsuario; // "Participante" o "Administrador"

    public Usuario(String id, String username, String password, String nombreCompleto, String tipoUsuario) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

}

