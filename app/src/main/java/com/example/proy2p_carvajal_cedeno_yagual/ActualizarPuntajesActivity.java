package com.example.proy2p_carvajal_cedeno_yagual;

import static android.widget.Toast.LENGTH_SHORT;
import static models.ManipularArchivos.cargarUsuario;

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

/**
 * Administra la interfaz y la lógica para actualizar los puntajes
 * de los participantes en la aplicación.
 * Permite calcular los puntos obtenidos según los pronósticos registrados
 * y acumular el puntaje total
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class ActualizarPuntajesActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /**
     * Botón para ejecutar la actualización de puntajes
     */
    LinearLayout btnActualizarPuntaje;
    /**
     * Botón para regresar a la pantalla anterior
     */
    LinearLayout btnVolver;

    /**
     * Lista de todos los pronósticos registrados en el sistema
     */
    ArrayList<Pronostico> pronosticosTotales = new ArrayList<>();
    /**
     * Lista de partidos cargados en el sistema
     */
    ArrayList<Partido> partidos = new ArrayList<>();
    /**
     * Lista de usuarios del Sitema
     */
    ArrayList<Usuario> usuarios = new ArrayList<>();


    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad, enlaza los componentes de la interfaz
     * y carga los pronósticos y partidos guardados.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_puntajes);

        // Enlace de vistas y carga de información
        btnActualizarPuntaje = findViewById(R.id.btn_actualizarPuntaje);
        btnVolver = findViewById(R.id.btnVolver);
        pronosticosTotales = ManipularArchivos.cargarPronosticosGeneral(this);
        partidos = ManipularArchivos.cargarPartidos(this);
        usuarios = ManipularArchivos.cargarUsuario(this);

    }

    /**
     * Ejecuta el cálculo y acumulación de puntajes y muestra un aviso en pantalla
     *
     * @param view Vista que genera el evento
     */
    public void actualizarPuntaje(View view) {
        actualizar();
        acumularPuntajeParticipante();
        Toast.makeText(this, "Los puntajes se actualizaron correctamente", LENGTH_SHORT).show();
    }

    /**
     * Cierra la pantalla actual y regresa a la anterior
     *
     * @param view Vista que genera el evento
     */
    public void volver(View view) {
        finish();
    }

    /**
     * Devuelve una lista únicamente con los partidos que ya finalizaron
     *
     * @return lista de partidos finalizados
     */
    private ArrayList<Partido> obtenerPartidosFinalizados() {
        ArrayList<Partido> partidosFinalizados = new ArrayList<>();
        // Filtrado de partidos según su estado
        for (Partido p : partidos) {
            if (p.getEstado() == Estado.FINALIZADO) {
                partidosFinalizados.add(p);
            }
        }
        return partidosFinalizados;
    }

    /**
     * Compara los pronósticos con los resultados reales de los partidos
     * y asigna los puntos correspondientes
     */
    private void actualizar() {
        ArrayList<Partido> partidos = obtenerPartidosFinalizados();
        ArrayList<Resultado> resultados = ManipularArchivos.cargarResultados(this);

        // Recorrido de pronósticos y partidos para asignación de puntos
        for (Pronostico pronostico : pronosticosTotales) {
            for (Partido partido : partidos) {
                if (pronostico.getIdPartido().equals(partido.getIdPartido())) {
                    for (Resultado resultado : resultados) {
                        if (resultado.getIdPartido().equals(partido.getIdPartido())) {
                            int golesLocal = resultado.getGolesSeleccion1();
                            int golesVisitante = resultado.getGolesSeleccion2();
                            int diferenciaGoles = golesLocal - golesVisitante;

                            int difPronostico = pronostico.getGolesSel1() - pronostico.getGolesSel2();

                            // Asignación de puntos según el acierto del pronóstico
                            if ((pronostico.getGolesSel1() == golesLocal) && (pronostico.getGolesSel2() == golesVisitante)) {
                                pronostico.setPuntosObtenidos(3);
                            } else if ((pronostico.getGolesSel1() == pronostico.getGolesSel2()) && (golesLocal == golesVisitante)) {
                                pronostico.setPuntosObtenidos(2);
                            } else if (((diferenciaGoles > 0 && difPronostico > 0) || (diferenciaGoles < 0 && difPronostico < 0))
                                    && (difPronostico == diferenciaGoles)) {
                                pronostico.setPuntosObtenidos(2);
                            } else if ((diferenciaGoles > 0 && difPronostico > 0) || (diferenciaGoles < 0 && difPronostico < 0)) {
                                pronostico.setPuntosObtenidos(1);
                            } else {
                                pronostico.setPuntosObtenidos(0);
                            }
                            // Guardado del pronóstico actualizado
                            ManipularArchivos.guardarPronostico(this, pronostico, partido.getFase());
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Suma y acumula los puntos obtenidos por cada participante en todas las fases
     * y guarda el registro
     */
        public void acumularPuntajeParticipante() {
            String[] fases = {
                    "FASE_DE_GRUPOS",
                    "DIECISEISAVOS_DE_FINAL",
                    "OCTAVOS_DE_FINAL",
                    "CUARTOS_DE_FINAL",
                    "SEMIFINALES",
                    "TERCER_LUGAR",
                    "FINAL"
            };

            ArrayList<Participante> participantesActualizados = new ArrayList<>();

            // Recorremos la lista global de usuarios
            for (Usuario u : usuarios) {
                if (u instanceof Participante) {
                    Participante participante = (Participante) u;

                    // Variable temporal para acumular TODAS las fases
                    int totalAcumulado = 0;

                    for (String fase : fases) {
                        ArrayList<Pronostico> pronosticos = ManipularArchivos.cargarPronosticos(this, participante.getIdUsuario(), Fase.valueOf(fase));

                        if (pronosticos != null && !pronosticos.isEmpty()) {
                            for (Pronostico p : pronosticos) {
                                if (p.getPuntosObtenidos() > 0) {
                                    // Sumamos al total acumulado de todas las fases
                                    totalAcumulado += p.getPuntosObtenidos();
                                }
                            }
                        }
                    }

                    // Asignamos la suma TOTAL fuera del bucle de fases
                    participante.setPuntajeAcumulado(totalAcumulado);
                    participantesActualizados.add(participante);
                }
            }
            ManipularArchivos.guardarParticipantes(this, participantesActualizados);
        }
    }