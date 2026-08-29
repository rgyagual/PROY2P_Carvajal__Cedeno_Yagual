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

public class MisPronosticosActivity extends AppCompatActivity {

    LinearLayout contenedorPronosticos;
    LinearLayout btnVolver;
    ArrayList<Pronostico> misPronosticos = new ArrayList<>();
    String idUsuario;

    ArrayList<Partido> partidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_pronosticos);

        contenedorPronosticos = findViewById(R.id.contenedor_pronosticos);
        btnVolver = findViewById(R.id.btnVolver);
        partidos = ManipularArchivos.cargarPartidos(this);
        String[] fases = {
                "FASE_DE_GRUPOS",
                "DIECISEISAVOS",
                "OCTAVOS_DE_FINAL",
                "CUARTOS_DE_FINAL",
                "SEMIFINALES",
                "TERCER_LUGAR",
                "FINAL"
        };

        idUsuario = getIntent().getStringExtra("idUsuario");
        for (String fase : fases) {
            ArrayList<Pronostico> pronosticos = ManipularArchivos.cargarPronosticos(this, idUsuario, Fase.valueOf(fase));
            if (pronosticos != null && !pronosticos.isEmpty()) {
                misPronosticos.addAll(pronosticos);

            }
        }
        mostrarPronosticos();
    }

    private void mostrarPronosticos() {
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
            TextView txtPuntosObtenidos = tarjetaPronosticos.findViewById(R.id.txt_puntosObtenidos);
            LinearLayout lyResultadoFinal = tarjetaPronosticos.findViewById(R.id.ly_resultadoFinal);
            TextView txtMensaje = tarjetaPronosticos.findViewById(R.id.txt_mensajeResultado);
            ImageView imgBanderaLocal = tarjetaPronosticos.findViewById(R.id.img_banderaLocal);
            ImageView imgBanderaVisitante = tarjetaPronosticos.findViewById(R.id.img_banderaVisitante);


            Partido partido = obtenerPartido(pronostico.getIdPartido());

            txtFase.setText(String.valueOf(partido.getFase()).toString());
            txtEstado.setText(partido.getEstado().toString());
            txtFecha.setText(partido.getFecha().toString());
            txtHora.setText(partido.getHora().toString());
            txtEstadio.setText(partido.getEstadio());
            txtNombreLocal.setText(partido.getSeleccion1());
            txtNombreVisitante.setText(partido.getSeleccion2());
            ManipularArchivos.asignarBandera(this, partido, imgBanderaLocal, imgBanderaVisitante);
            txtPronosticoLocal.setText(pronostico.getGolesSel1());
            txtPronosticoVisitante.setText(pronostico.getGolesSel2());
            txtPuntosObtenidos.setText(pronostico.getPuntosObtenidos());

            if (partido.getEstado() == Estado.FINALIZADO) {
                Resultado resultado = obtenerResultado(partido.getIdResultado());
                txtResultadoOficial.setText(resultado.getGolesSeleccion1() + " - " + resultado.getGolesSeleccion2());
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

            contenedorPronosticos.addView(tarjetaPronosticos);

        }
    }

    private Partido obtenerPartido(String idPartido) {

        for (Partido p : partidos) {
            if (p.getIdPartido().equals(idPartido)) {
                return p;
            }
        }
        return null;
    }

    private Resultado obtenerResultado(String idResultado) {
        ArrayList<Resultado> resultados = ManipularArchivos.cargarResultados(this);
        for (Resultado r : resultados) {
            if (r.getIdResultado().equals(idResultado)) {
                return r;
            }
        }
        return null;

    }

    public void volver(View view) {
        finish();
    }

}
