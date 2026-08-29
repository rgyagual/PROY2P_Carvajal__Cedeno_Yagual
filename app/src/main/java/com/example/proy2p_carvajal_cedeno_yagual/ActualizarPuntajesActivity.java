package com.example.proy2p_carvajal_cedeno_yagual;

import static android.widget.Toast.LENGTH_SHORT;
import static models.ManipularArchivos.cargarUsuario;
import static models.ManipularArchivos.guardarParticipante;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.Estado;
import models.Fase;
import models.ManipularArchivos;
import models.Participante;
import models.Partido;
import models.Pronostico;
import models.Resultado;
import models.Usuario;

public class ActualizarPuntajesActivity extends AppCompatActivity {

    LinearLayout btnActualizarPuntaje;
    LinearLayout btnVolver;

    ArrayList<Pronostico> pronosticosTotales = new ArrayList<>();
    ArrayList<Partido> partidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_puntajes);

        btnActualizarPuntaje = findViewById(R.id.btn_actualizarPuntaje);
        btnVolver = findViewById(R.id.btnVolver);
        pronosticosTotales = ManipularArchivos.cargarPronosticosGeneral(this);
        partidos = ManipularArchivos.cargarPartidos(this);

    }

    public void actualizarPuntaje(View view) {
        actualizarPuntaje();
        acumularPuntajeParticipante();
        Toast.makeText(this, "Los puntajes se actualizaron correctamente", LENGTH_SHORT);
    }

    public void volver(View view) {
        finish();
    }

    private ArrayList<Partido> obtenerPartidosFinalizados() {
        ArrayList<Partido> partidosFinalizados = new ArrayList<>();
        for (Partido p : partidos) {
            if (p.getEstado() == Estado.FINALIZADO) {
                partidosFinalizados.add(p);
            }
        }
        return partidosFinalizados;
    }

    private void actualizarPuntaje() {
        ArrayList<Partido> partidos = obtenerPartidosFinalizados();
        ArrayList<Resultado> resultados = ManipularArchivos.cargarResultados(this);

        for (Pronostico pronostico : pronosticosTotales) {
            for (Partido partido : partidos) {
                if (pronostico.getIdPartido().equals(partido.getIdPartido())) {
                    for (Resultado resultado : resultados) {
                        if (resultado.getIdPartido().equals(partido.getIdPartido())) {
                            int golesLocal = resultado.getGolesSeleccion1();
                            int golesVisitante = resultado.getGolesSeleccion2();
                            int diferenciaGoles = resultado.getGolesSeleccion1() - resultado.getGolesSeleccion2();

                            if ((pronostico.getGolesSel1() == golesLocal) && (pronostico.getGolesSel2() == golesVisitante)) {
                                pronostico.setPuntosObtenidos(3);

                            } else if ((pronostico.getGolesSel1() > pronostico.getGolesSel2() && resultado.ganoEquipoLocal())
                                    && ((pronostico.getGolesSel1() - pronostico.getGolesSel2()) == diferenciaGoles)) {
                                pronostico.setPuntosObtenidos(2);
                            } else if ((pronostico.getGolesSel1() == pronostico.getGolesSel2()) && (golesLocal == golesVisitante)) {
                                pronostico.setPuntosObtenidos(2);
                            } else if (((pronostico.getGolesSel1() > pronostico.getGolesSel2() && resultado.ganoEquipoLocal()))) {
                                pronostico.setPuntosObtenidos(1);
                            } else {
                                pronostico.setPuntosObtenidos(0);
                            }

                        }
                    }
                }
            }

        }
    }

    public void acumularPuntajeParticipante() {
        String[] fases = {
                "FASE_DE_GRUPOS",
                "DIECISEISAVOS",
                "OCTAVOS_DE_FINAL",
                "CUARTOS_DE_FINAL",
                "SEMIFINALES",
                "TERCER_LUGAR",
                "FINAL"
        };
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<Participante> participantes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Participante) {
                Participante participante = (Participante) u;
                participante.setPuntajeAcumulado(0);
                for (String fase : fases) {
                    ArrayList<Pronostico> misPronosticos = new ArrayList<>();
                    ArrayList<Pronostico> pronosticos = ManipularArchivos.cargarPronosticos(this, participante.getIdUsuario(), Fase.valueOf(fase));
                    if (pronosticos != null && !pronosticos.isEmpty()) {
                        misPronosticos.addAll(pronosticos);
                    }
                    int puntajeObtenido = 0;
                    for (Pronostico p : misPronosticos) {
                        if (p.getPuntosObtenidos() >= 0) {
                            puntajeObtenido += p.getPuntosObtenidos();
                        }
                    }
                    participante.setPuntajeAcumulado(puntajeObtenido);
                    participantes.add(participante);

                }

            }

        }
        ManipularArchivos.guardarParticipantes(this, participantes);
    }
}
