package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import models.ManipularArchivos;
import models.Participante;
import models.Usuario;
import models.exceptions.CredencialesInvalidasException;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_nombreUsuario;
    private EditText edt_contrasena;
    private Button btn_iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ManipularArchivos.iniciarArchivo(this);

        edt_nombreUsuario = findViewById(R.id.edt_nombreUsuario);
        edt_contrasena = findViewById(R.id.edt_contrasena);
        btn_iniciarSesion = findViewById(R.id.btn_iniciarSesion);
    }

    private Usuario validarCredenciales(String nombreUsuario, String contrasena)
            throws CredencialesInvalidasException {

        ArrayList<Usuario> usuarios = ManipularArchivos.cargarUsuario(this);

        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
    }

    public void iniciarSesion(View view) {
        String nombreUsuario = edt_nombreUsuario.getText().toString().trim();
        String contrasena = edt_contrasena.getText().toString().trim();

        try {
            Usuario usuario = validarCredenciales(nombreUsuario, contrasena);

            Intent intent;
            if (usuario instanceof Participante) {
                intent = new Intent(this, MenuParticipanteActivity.class);
            } else {
                intent = new Intent(this, MenuAdministradorActivity.class);
            }
            //puExtra: pasar idUsuario y nombre de una activity a otra (putExtra("clave", valor))
            intent.putExtra("idUsuario", usuario.getIdUsuario());
            intent.putExtra("nombreCompleto", usuario.getNombreCompleto());
            //envía la información a al otra activity
            startActivity(intent);
            finish();

        } catch (CredencialesInvalidasException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}