package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Administra el menú principal para los usuarios con rol de Administrador.
 * Permite la navegación hacia las vistas de gestión de partidos,
 * actualización de puntajes de los participantes y cierre de sesión.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class MenuAdministradorActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Tarjeta para navegar a la sección de administración de partidos
     */
    CardView cdAdministarPartidos;
    /**
     * Tarjeta para navegar a la sección de actualización de puntajes
     */
    CardView cdActualizarPuntajes;
    /**
     * Tarjeta para cerrar todas las actividades y salir
     */
    CardView cdSalir;
    /**
     * Vista de texto para mostrar el nombre del administrador autenticado
     */
    TextView txtNombreAdministrador;

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
        setContentView(R.layout.activity_menu_administrador);

        // Enlace de vistas con los componentes de la interfaz
        cdAdministarPartidos = findViewById(R.id.cd_administrarP);
        cdActualizarPuntajes = findViewById(R.id.cd_actualizar);
        cdSalir = findViewById(R.id.cd_salir);
        txtNombreAdministrador = findViewById(R.id.txt_nombreAdministrador);

        // Muestra el nombre del administrador actual
        txtNombreAdministrador.setText(getNombreAdministrador());

    }

    /**
     * Obtiene el nombre completo del administrador
     *
     * @return Nombre completo del administrador
     */
    public String getNombreAdministrador() {
        Intent intent = getIntent();
        String nombreCompletos = intent.getStringExtra("nombreCompleto");
        return nombreCompletos;
    }

    /**
     * Navega hacia la pantalla de administración de partidos.
     *
     * @param view Vista que genera el evento
     */
    public void administrarPartidos(View view) {
        Intent intent = new Intent(
                MenuAdministradorActivity.this,
                AdministrarPartidosActivity.class
        );
        startActivity(intent);

    }

    /**
     * Navega hacia la pantalla de actualización de puntajes.
     *
     * @param view Vista que genera el evento
     */
    public void actualizarPuntajes(View view) {
        Intent intent = new Intent(
                MenuAdministradorActivity.this,
                ActualizarPuntajesActivity.class
        );
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