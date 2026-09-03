package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

import models.ManipularArchivos;
import models.Participante;

/**
 * Administra la interfaz y la lógica para mostrar la tabla de posiciones
 * en la aplicación.
 * Recupera los datos de los participantes, ordena el ranking según sus
 * puntos acumulados y genera dinámicamente una tabla con las posiciones.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class TablaPosicionesActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Etiqueta de título de la vista
     */
    TextView labelTitulo;
    /**
     * Contenedor deslizable para la tabla
     */
    ScrollView scrollView;
    /**
     * Tabla donde se muestran dinámicamente las posiciones
     */
    TableLayout posicionesLayout;
    /**
     * Botón/Tarjeta para regresar a la pantalla anterior
     */
    CardView cdVolver;
    /**
     * Vista de texto para mostrar el nombre del participante actual
     */
    TextView txt_nombreParticipante;

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad, enlaza los componentes de la interfaz,
     * recupera el usuario actual y llena la tabla con el ranking de posiciones.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_posiciones);

        // Enlace de componentes visuales con el diseño layout
        posicionesLayout = findViewById(R.id.tb_tablaPosiciones);
        posicionesLayout.removeAllViews();
        cdVolver = findViewById(R.id.cd_volver);
        txt_nombreParticipante = findViewById(R.id.txt_nombreParticipante);

        // Muestra el nombre del participante activo
        txt_nombreParticipante.setText(getNombreParticipante());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Genera la tabla con las posiciones
        mostrarPosiciones(obtenerPosiciones(), posicionesLayout);
    }

    /**
     * Obtiene el nombre completo del participante extraído del Intent.
     *
     * @return Nombre completo del participante
     */
    public String getNombreParticipante() {
        Intent intent = getIntent();
        String nombreCompletos = intent.getStringExtra("nombreCompleto");
        return nombreCompletos;
    }

    /**
     * Carga los usuarios del sistema, filtra solo aquellos que son participantes
     * y los ordena en orden descendente según su puntaje acumulado.
     *
     * @return Lista ordenada de participantes
     */
    public ArrayList<Participante> obtenerPosiciones() {
        ArrayList<Participante> p = new ArrayList<>();
        // Carga de usuarios y filtrado de aquellos tipo Participante
        ManipularArchivos.cargarUsuario(this).forEach(u -> {
            if (u instanceof Participante) {
                p.add((Participante) u);
            }
        });
        // Ordenamiento según el criterio definido en la clase Participante
        Collections.sort(p);
        return p;
    }

    /**
     * Genera dinámicamente la estructura visual de la tabla agregando la cabecera
     * y cada una de las filas correspondientes a los participantes y sus puntos.
     *
     * @param p     Lista de participantes ordenados
     * @param tabla Contenedor TableLayout donde se mostrarán las filas
     */
    private void mostrarPosiciones(ArrayList<Participante> p, TableLayout tabla) {

        tabla.removeAllViews();
        // Creación y personalización de la fila de cabecera de la tabla
        TableRow fila = new TableRow(this);
        fila.setBackgroundColor(Color.parseColor("#102060"));
        TextView txt_pos = new TextView(this);
        TextView txt_tablaparticipante = new TextView(this);
        TextView txt_puntos = new TextView(this);

        txt_pos.setText("Pos.");
        txt_pos.setTextColor(Color.WHITE);
        txt_pos.setPadding(24, 24, 24, 24);
        txt_tablaparticipante.setText("Participante");
        txt_tablaparticipante.setTextColor(Color.WHITE);
        txt_pos.setPadding(24, 24, 24, 24);
        txt_puntos.setText("Puntos");
        txt_pos.setPadding(24, 24, 24, 24);
        txt_puntos.setTextColor(Color.WHITE);

        fila.addView(txt_pos);
        fila.addView(txt_tablaparticipante);
        fila.addView(txt_puntos);
        tabla.addView(fila);

        // Iteración sobre la lista de participantes para generar las filas de datos
        for (int i = 0; i < p.size(); i++) {
            TableRow fila_usuarios = new TableRow(this);
            TextView txt_posicion = new TextView(this);
            TextView txt_nombreParticipante = new TextView(this);
            TextView txt_puntajeParticipante = new TextView(this);

            txt_posicion.setText(String.valueOf(i + 1));
            txt_nombreParticipante.setText(p.get(i).getNombreCompleto());
            txt_puntajeParticipante.setText(
                    String.valueOf(
                            p.get(i).getPuntajeAcumulado()
                    ));
            txt_posicion.setTextColor(Color.BLUE);
            txt_nombreParticipante.setTextColor(Color.BLUE);
            txt_puntajeParticipante.setTextColor(Color.BLUE);
            txt_posicion.setPadding(20, 20, 20, 16);
            txt_nombreParticipante.setPadding(20, 20, 20, 16);
            txt_puntajeParticipante.setPadding(20, 20, 20, 16);

            fila_usuarios.addView(txt_posicion);
            fila_usuarios.addView(txt_nombreParticipante);
            fila_usuarios.addView(txt_puntajeParticipante);
            tabla.addView(fila_usuarios);
        }

    }

    /**
     * Cierra la pantalla actual y regresa a la vista anterior.
     *
     * @param view Vista que genera el evento
     */
    public void volver(View view) {
        finish();
    }

}