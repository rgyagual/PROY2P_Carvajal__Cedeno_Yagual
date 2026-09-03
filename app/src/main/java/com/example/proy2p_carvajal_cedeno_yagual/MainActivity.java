package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Actividad principal y punto de entrada inicial de la aplicación.
 * a la pantalla de inicio de sesión (LoginActivity).
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class MainActivity extends AppCompatActivity {

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad principal y redirige al inicio de sesión.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ejecución de la redirección inicial
        pantallaPrincipal();
    }

    /**
     * Realiza la transición de pantalla hacia la actividad de inicio de sesión (LoginActivity).
     */
    public void pantallaPrincipal() {
        Intent intent = new Intent(
                this,
                LoginActivity.class
        );
        startActivity(intent);
    }
}