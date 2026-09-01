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

/**
 * Administra el inicio de sesión de los usuarios en la aplicación.
 * Permite validar las credenciales ingresadas y redirigir al menú
 * correspondiente según el rol del usuario.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class LoginActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Campo de texto para ingresar el nombre de usuario
     */
    private EditText edt_nombreUsuario;
    /**
     * Campo de texto para ingresar la contraseña
     */
    private EditText edt_contrasena;
    /**
     * Botón para ejecutar el inicio de sesión
     */
    private Button btn_iniciarSesion;

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializar la actividad, habilitar la vista y preparar los archivos
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ManipularArchivos.iniciarArchivo(this);

        // Enlace de los componentes gráficos con el diseño
        edt_nombreUsuario = findViewById(R.id.edt_nombreUsuario);
        edt_contrasena = findViewById(R.id.edt_contrasena);
        btn_iniciarSesion = findViewById(R.id.btn_iniciarSesion);
    }

    /**
     * Validar que el usuario y la contraseña existan en el sistema
     *
     * @param nombreUsuario Nombre del usuario ingresado
     * @param contrasena    Contraseña del usuario ingresada
     * @return usuario registrado encontrado
     * @throws CredencialesInvalidasException Excepción lanzada cuando las credenciales son incorrectas
     */
    private Usuario validarCredenciales(String nombreUsuario, String contrasena)
            throws CredencialesInvalidasException {

        ArrayList<Usuario> usuarios = ManipularArchivos.cargarUsuario(this);

        // Búsqueda de coincidencia de usuario y contraseña
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
    }

    /**
     * Manejar el proceso de inicio de sesión y redirigir a la pantalla correspondiente
     *
     * @param view Vista que genera el evento
     */
    public void iniciarSesion(View view) {

        // Obtención de nombre de usuario y contraseña ingresados
        String nombreUsuario = edt_nombreUsuario.getText().toString().trim();
        String contrasena = edt_contrasena.getText().toString().trim();

        // Validación de credenciales e inicio de sesión
        try {
            Usuario usuario = validarCredenciales(nombreUsuario, contrasena);

            Intent intent;
            // Verificación del rol para mostrar la pantalla correspondiente

            if (usuario instanceof Participante) {
                intent = new Intent(this, MenuParticipanteActivity.class);
            } else {
                intent = new Intent(this, MenuAdministradorActivity.class);
            }
            // Envío de idUsuario y nombre completo hacia la siguiente actividad

            intent.putExtra("idUsuario", usuario.getIdUsuario());
            intent.putExtra("nombreCompleto", usuario.getNombreCompleto());

            // Apertura de la nueva pantalla y cierre de la actual

            startActivity(intent);
            finish();

        } catch (CredencialesInvalidasException e) {
            // Notificación en pantalla cuando las credenciales son incorrectas
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}