package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Administra el menú principal para los usuarios con rol de Participante.
 * Permite la navegación hacia las vistas de la tabla de posiciones,
 * registro de pronósticos, consulta de pronósticos personales y cierre de sesión.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class MenuParticipanteActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Tarjeta para navegar a la tabla de posiciones
     */
    CardView cdTablaPosiciones;
    /**
     * Tarjeta para navegar a la sección de pronósticos generales
     */
    CardView cdPronosticos;
    /**
     * Tarjeta para navegar a la sección de mis pronósticos
     */
    CardView cdMisPronosticos;
    /**
     * Tarjeta para cerrar todas las actividades y salir
     */
    CardView cdSalir;
    /**
     * Vista de texto para mostrar el nombre del participante autenticado
     */
    TextView txtNombreParticipante;

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad, habilita la vista y enlaza los
     * componentes gráficos con el diseño del layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_participante);

        // Enlace de vistas con los componentes de la interfaz
        cdTablaPosiciones = findViewById(R.id.cd_tablaPosiciones);
        cdPronosticos = findViewById(R.id.cd_pronosticos);
        cdMisPronosticos = findViewById(R.id.cd_misPronosticos);
        cdSalir = findViewById(R.id.cd_salir);
        txtNombreParticipante = findViewById(R.id.txt_nombreParticipante);

        // Muestra el nombre del participante actual
        txtNombreParticipante.setText(getNombreParticipante());

    }

    /**
     * Obtiene el nombre completo del participante
     *
     * @return Nombre completo del participante
     */
    public String getNombreParticipante() {
        Intent intent = getIntent();
        String nombreCompletos = intent.getStringExtra("nombreCompleto");
        return nombreCompletos;
    }

    /**
     * Obtiene el identificador único del usuario
     *
     * @return Identificador idUsuario
     */
    public String getIdUsuario() {
        Intent intent = getIntent();
        String idUsuario = intent.getStringExtra("idUsuario");
        return idUsuario;
    }

    /**
     * Navega hacia la pantalla de la tabla de posiciones enviando la información requerida.
     *
     * @param view Vista que genera el evento
     */
    public void mostrarTablaPosiciones(View view) {

        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                TablaPosicionesActivity.class
        );

        intent.putExtra("nombreCompleto", getNombreParticipante());
        startActivity(intent);
    }

    /**
     * Navega hacia la pantalla para ingresar o consultar pronósticos.
     *
     * @param view Vista que genera el evento
     */
    public void mostrarPronosticos(View view) {

        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                PronosticosActivity.class
        );
        intent.putExtra("idUsuario", getIdUsuario());
        startActivity(intent);
    }

    /**
     * Navega hacia la pantalla para consultar los pronósticos personales del participante.
     *
     * @param view Vista que genera el evento
     */
    public void mostrarMisPronosticos(View view) {
        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                MisPronosticosActivity.class
        );
        intent.putExtra("idUsuario", getIdUsuario());
        startActivity(intent);
    }

    /**
     * Cierra la aplicación finalizando la pila completa de actividades.
     *
     * @param view Vista que genera el evento
     */
    public void salir(View view) {
        finishAffinity();
    }
}