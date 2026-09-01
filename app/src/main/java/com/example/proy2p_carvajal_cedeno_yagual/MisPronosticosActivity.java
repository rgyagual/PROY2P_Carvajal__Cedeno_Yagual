package com.example.proy2p_carvajal_cedeno_yagual;

import static android.view.View.INVISIBLE;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import models.Estado;
import models.Fase;
import models.ManipularArchivos;
import models.Partido;
import models.Pronostico;
import models.Resultado;

/**
 * Administra la interfaz y la lógica para visualizar los pronósticos
 * registrados por el usuario.
 * Muestra el detalle de cada partido, las predicciones realizadas,
 * los resultados oficiales y los puntos obtenidos en cada fase.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class MisPronosticosActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Contenedor dinámico donde se cargarán las tarjetas de pronósticos
     */
    LinearLayout contenedorPronosticos;
    /**
     * Botón para regresar a la pantalla anterior
     */
    LinearLayout btnVolver;
    /**
     * Lista para almacenar los pronósticos del usuario
     */
    ArrayList<Pronostico> misPronosticos = new ArrayList<>();
    /**
     * Identificador único del usuario actual
     */
    String idUsuario;

    /**
     * Lista para almacenar todos los partidos registrados
     */
    ArrayList<Partido> partidos = new ArrayList<>();

    /**
     * Lista de Resultados
     */
    ArrayList<Resultado> resultados;

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad, carga la lista de partidos y recupera
     * los pronósticos del usuario en todas las fases
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_pronosticos);

        // Enlace de vistas y carga inicial de partidos
        contenedorPronosticos = findViewById(R.id.contenedor_pronosticos);
        btnVolver = findViewById(R.id.btnVolver);
        partidos = ManipularArchivos.cargarPartidos(this);
        resultados = ManipularArchivos.cargarResultados(this);
        String[] fases = {
                "FASE_DE_GRUPOS",
                "DIECISEISAVOS_DE_FINAL",
                "OCTAVOS_DE_FINAL",
                "CUARTOS_DE_FINAL",
                "SEMIFINALES",
                "TERCER_LUGAR",
                "FINAL"
        };

        // Obtención del ID del usuario y carga de sus pronósticos por cada fase
        idUsuario = getIntent().getStringExtra("idUsuario");
        for (String fase : fases) {
            ArrayList<Pronostico> pronosticos = ManipularArchivos.cargarPronosticos(this, idUsuario, Fase.valueOf(fase));
            if (pronosticos != null && !pronosticos.isEmpty()) {
                misPronosticos.addAll(pronosticos);

            }
        }
        mostrarPronosticos();
    }

    /**
     * Genera dinámicamente las tarjetas de pronósticos en pantalla
     * mostrando el detalle del partido, pronósticos y resultados oficiales.
     */
    private void mostrarPronosticos() {
        // Limpieza del contenedor e inflado dinámico de las tarjetas de pronósticos
        contenedorPronosticos.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Pronostico pronostico : misPronosticos) {
            View tarjetaPronosticos = inflater.inflate(R.layout.plantilla_mispronosticos, contenedorPronosticos, false);

            TextView txtFase = tarjetaPronosticos.findViewById(R.id.txt_fase);
            TextView txtEstado = tarjetaPronosticos.findViewById(R.id.txt_estado);
            TextView txtFecha = tarjetaPronosticos.findViewById(R.id.txt_fecha);
            TextView txtHora = tarjetaPronosticos.findViewById(R.id.txt_hora);
            TextView txtEstadio = tarjetaPronosticos.findViewById(R.id.txt_estadio);
            TextView txtNombreLocal = tarjetaPronosticos.findViewById(R.id.txt_nombreLocal);
            TextView txtNombreVisitante = tarjetaPronosticos.findViewById(R.id.txt_nombreVisitante);
            TextView txtPronosticoLocal = tarjetaPronosticos.findViewById(R.id.txt_pronosticoLocal);
            TextView txtPronosticoVisitante = tarjetaPronosticos.findViewById(R.id.txt_pronosticoVisitante);
            TextView txtResultadoOficial = tarjetaPronosticos.findViewById(R.id.txt_resultadoOficial);
            LinearLayout lyResultadoFinal = tarjetaPronosticos.findViewById(R.id.ly_resultadoFinal);
            TextView txtMensaje = tarjetaPronosticos.findViewById(R.id.txt_mensajeResultado);
            ImageView imgBanderaLocal = tarjetaPronosticos.findViewById(R.id.img_banderaLocal);
            ImageView imgBanderaVisitante = tarjetaPronosticos.findViewById(R.id.img_banderaVisitante);

            // Búsqueda de datos del partido e inyección de la información en la tarjeta
            Partido partido = obtenerPartido(pronostico.getIdPartido());
            if (partido == null) {
                continue;
            }
            txtFase.setText(String.valueOf(partido.getFase()).toString());
            txtEstado.setText(partido.getEstado().toString());
            txtFecha.setText(partido.getFecha().toString());
            txtHora.setText(partido.getHora().toString());
            txtEstadio.setText(partido.getEstadio());
            txtNombreLocal.setText(partido.getSeleccion1());
            txtNombreVisitante.setText(partido.getSeleccion2());
            ManipularArchivos.asignarBandera(this, partido, imgBanderaLocal, imgBanderaVisitante);
            txtPronosticoLocal.setText(String.valueOf(pronostico.getGolesSel1()));
            txtPronosticoVisitante.setText(String.valueOf(pronostico.getGolesSel2()));

            // Validación del estado del partido para mostrar resultados y mensajes
            if (partido.getEstado() == Estado.FINALIZADO) {
                Resultado resultado = obtenerResultado(partido.getIdPartido());
                if (resultado != null) {
                    txtResultadoOficial.setText(resultado.getGolesSeleccion1() + " - " + resultado.getGolesSeleccion2());
                } else {
                    txtResultadoOficial.setText("PENDIENTE");
                }
                if (pronostico.getPuntosObtenidos() > 0) {
                    txtMensaje.setText("✓  ¡¡Acertaste el Ganador!! Obtuviste " + pronostico.getPuntosObtenidos() + " puntos");
                } else if (pronostico.getPuntosObtenidos() == 0) {
                    txtMensaje.setText("No has acertado :(");
                } else {
                    txtMensaje.setText("Ha ocurrido un error, comunicate con Servicio al Cliente");
                }
            } else if (partido.getEstado() == Estado.CERRADO) {
                lyResultadoFinal.setVisibility(INVISIBLE);
                txtMensaje.setText("🔒 Los pronósticos para este partido están cerrados");
            } else if (partido.getEstado() == Estado.ABIERTO) {
                lyResultadoFinal.setVisibility(INVISIBLE);
                txtMensaje.setText("🖌️ Puedes modificar tu pronóstico mientras el partido esté abierto");
            }

            // Agregado de la tarjeta al contenedor principal
            contenedorPronosticos.addView(tarjetaPronosticos);

        }
    }

    /**
     * Busca y devuelve un partido específico según su identificador
     *
     * @param idPartido Identificador único del partido
     * @return partido encontrado o null si no existe
     */
    private Partido obtenerPartido(String idPartido) {

        // Recorrido de la lista para encontrar coincidencia de ID
        for (Partido p : partidos) {
            if (p.getIdPartido().equals(idPartido)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve el resultado oficial de un partido según su identificador
     *
     * @param idPartido Identificador único del partido
     * @return resultado encontrado o null si no existe
     */
    private Resultado obtenerResultado(String idPartido) {
        if (idPartido == null) {
            return null;
        }
        String idBuscado = idPartido.trim();
        for (Resultado r : resultados) {
            if (r.getIdPartido() != null && r.getIdPartido().trim().equals(idBuscado)) {
                return r; // Retorna en la primera coincidencia
            }
        }
        return null;
    }

    /**
     * Cierra la pantalla actual y regresa a la anterior
     *
     * @param view Vista que genera el evento
     */
    public void volver(View view) {
        finish();
    }

}