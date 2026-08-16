package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.TipoUsuario;
import models.Usuario;
import models.exceptions.CredencialesInvalidasException;
import utils.DataManager;

import java.util.ArrayList;

import models.ManipularArchivos;
import models.Participante;
import models.Usuario;
import models.exceptions.CredencialesInvalidasException;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_nombreUsuario;
    private EditText edt_contrasena;
    private Button btn_iniciarSesion;

    private EditText edtUsuario, edtContrasenia;
    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 1. Inicializar los archivos de texto desde assets si aún no existen
        DataManager.inicializarArchivos(this);

        // 2. Vincular vistas con los IDs del layout XML
        edtUsuario = findViewById(R.id.edt_nombreUsuario);
        edtContrasenia = findViewById(R.id.edt_contraseña);
        btnIniciarSesion = findViewById(R.id.btn_iniciarSesion);

        // 3. Listener del botón de inicio de sesión
        btnIniciarSesion.setOnClickListener(v -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String username = edtUsuario.getText().toString().trim();
        String password = edtContrasenia.getText().toString().trim();

        try {
            // Se valida directamente con el método autenticar de DataManager
            Usuario usuarioLogueado = DataManager.autenticar(this, username, password);

            // Redirigir según el tipo de usuario
            if (usuarioLogueado.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
                Intent intent = new Intent(LoginActivity.this, MenuParticipanteActivity.class);
                intent.putExtra("usuario_actual", usuarioLogueado);
                startActivity(intent);
                finish();
            } else if (usuarioLogueado.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                Intent intent = new Intent(LoginActivity.this, MenuAdministradorActivity.class);
                intent.putExtra("usuario_actual", usuarioLogueado);
                startActivity(intent);
                finish();
            }

        } catch (CredencialesInvalidasException e) {
            // Mostrar Toast con el mensaje de la excepción personalizada
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Manejo de errores generales
            Toast.makeText(this, "Problemas técnicos. Estamos resolviendo.", Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarSesion(View view) {
        String nombreUsuario = edt_nombreUsuario.getText().toString();
        String contrasena = edt_contrasena.getText().toString();

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