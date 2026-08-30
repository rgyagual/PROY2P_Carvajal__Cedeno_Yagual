package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import models.Estado;
import models.Fase;
import models.ManipularArchivos;
import models.Participante;
import models.Partido;
import models.Pronostico;
import models.Resultado;
import models.Usuario;
import models.exceptions.DatosIncompletosException;
import models.exceptions.PronosticoFueraDeTiempoException;

/**
 * Administra la interfaz y la lógica para el registro y modificación de pronósticos
 * por el usuario en las distintas fases del mundial.
 * Controla la validación de estados de los partidos (ABIERTO, CERRADO, FINALIZADO),
 * el ingreso de marcador pronosticado, la gestión de excepciones personalizadas.
 *
 * @author Cedeño-Yagual-Carvajal
 */
public class PronosticosActivity extends AppCompatActivity {

    // =======================================
    // ATRIBUTOS
    // =======================================

    /** Desplegable para seleccionar la fase del torneo */
    private Spinner spFase;
    /** Contenedor dinámico donde se cargarán las tarjetas de pronósticos */
    private LinearLayout contenedorPartidos;
    /** Botón para regresar a la pantalla anterior */
    private CardView cdVolver;
    /** Identificador único del usuario actual */
    private String idUsuarioActual;
    /** Objeto del participante actual */
    private Participante participanteActual;
    /** Lista para almacenar todos los partidos registrados */
    private ArrayList<Partido> listaPartidos;

    // =======================================
    // MÉTODOS
    // =======================================

    /**
     * Inicializa la actividad, carga la lista de usuarios y recupera
     * los partidos y llena el selector de fases.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pronosticos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enlace de vistas
        spFase = findViewById(R.id.spFase);
        contenedorPartidos = findViewById(R.id.contenedorPartidos);
        cdVolver = findViewById(R.id.cd_volver);

        // Obtención del ID del usuario, carga de partidos y usuarios
        idUsuarioActual = getIntent().getStringExtra("idUsuario");
        ArrayList<Usuario> listaUsuarios = ManipularArchivos.cargarUsuario(this);
        for (Usuario u : listaUsuarios) {
            if (u.getIdUsuario().equals(idUsuarioActual) && u instanceof Participante) {
                participanteActual = (Participante) u;
            }
        }
        listaPartidos = ManipularArchivos.cargarPartidos(this);

        // Configuración del Spinner con los valores del enum Fase
        ArrayAdapter<Fase> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Fase.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFase.setAdapter(adapter);
        spFase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fase faseSeleccionada = (Fase) adapterView.getItemAtPosition(i);
                mostrarPartidosPorFase(faseSeleccionada);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Configuración de evento para volver al menú principal
        cdVolver.setOnClickListener(v -> {
            Intent intent = new Intent(PronosticosActivity.this, MenuParticipanteActivity.class);
            intent.putExtra("idUsuario", idUsuarioActual);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Muestra dinámicamente las tarjetas de partidos filtradas por la fase seleccionada.
     * Carga pronósticos previos del usuario si existen y deshabilita campos en caso de
     * que el partido esté cerrado o finalizado.
     *
     * @param fase Fase del torneo seleccionada
     */
    private void mostrarPartidosPorFase(Fase fase) {
        // Limpieza del contenedor e inflado dinámico de tarjetas
        contenedorPartidos.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        ArrayList<Pronostico> misPronosticos = ManipularArchivos.cargarPronosticos(this, idUsuarioActual, fase);
        for (Partido partido : listaPartidos) {
            if (partido.getFase() != fase) {
                continue;
            }
            View vistaPartido = inflater.inflate(R.layout.plantilla_tarjetapartido, contenedorPartidos, false);
            TextView txtFase = vistaPartido.findViewById(R.id.txt_fase);
            TextView txtEstado = vistaPartido.findViewById(R.id.txt_estado);
            TextView txtFecha = vistaPartido.findViewById(R.id.txt_fecha);
            TextView txtHora = vistaPartido.findViewById(R.id.txt_hora);
            TextView txtEstadio = vistaPartido.findViewById(R.id.txt_estadio);
            TextView txtNombreLocal = vistaPartido.findViewById(R.id.txt_nombreLocal);
            TextView txtNombreVisitante = vistaPartido.findViewById(R.id.txt_nombreVisitante);
            ImageView imgBanderaLocal = vistaPartido.findViewById(R.id.img_banderaLocal);
            ImageView imgBanderaVisitante = vistaPartido.findViewById(R.id.img_banderaVisitante);
            EditText edtGolesLocal = vistaPartido.findViewById(R.id.edt_golesPronosticoLocal);
            EditText edtGolesVisitante = vistaPartido.findViewById(R.id.edt_golesPronosticoVisitante);
            Button btnGuardar = vistaPartido.findViewById(R.id.btn_guardarPronostico);
            LinearLayout lyResultadoFinal = vistaPartido.findViewById(R.id.ly_resultadoFinal);
            TextView txtResultadoOficial = vistaPartido.findViewById(R.id.txt_resultadoOficial);
            TextView txtPuntosObtenidos = vistaPartido.findViewById(R.id.txt_puntosObtenidos);
            TextView txtMensaje = vistaPartido.findViewById(R.id.txt_mensajeResultado);

            // Inyección de la información del partido
            txtFase.setText(fase.toString());
            txtEstado.setText(partido.getEstado().toString());
            txtFecha.setText(partido.getFecha().toString());
            txtHora.setText(partido.getHora().toString());
            txtEstadio.setText(partido.getEstadio());
            txtNombreLocal.setText(partido.getSeleccion1());
            txtNombreVisitante.setText(partido.getSeleccion2());

            // Asignación de banderas según las selecciones participantes
            ManipularArchivos.asignarBandera(this,partido,imgBanderaLocal,imgBanderaVisitante);

            // Verificación de pronóstico realizado previamente
            Pronostico pronosticoExistente = null;
            for (Pronostico p : misPronosticos) {
                if (p.getIdPartido().equals(partido.getIdPartido())) {
                    pronosticoExistente = p;
                }
            }
            if (pronosticoExistente != null) {
                edtGolesLocal.setText(String.valueOf(pronosticoExistente.getGolesSel1()));
                edtGolesVisitante.setText(String.valueOf(pronosticoExistente.getGolesSel2()));
            }

            // Habilitación o inhabilitación de campos según el estado del partido
            boolean abierto = partido.getEstado() == Estado.ABIERTO;
            edtGolesLocal.setEnabled(abierto);
            edtGolesVisitante.setEnabled(abierto);
            btnGuardar.setEnabled(abierto);
            btnGuardar.setVisibility(View.VISIBLE);

            if(partido.getEstado()==Estado.FINALIZADO){
                Resultado resultado= obtenerResultadoPorPartido(partido.getIdPartido());
                if(resultado != null && pronosticoExistente != null){
                    lyResultadoFinal.setVisibility(View.VISIBLE);
                    txtResultadoOficial.setText(resultado.getGolesSeleccion1()+" - "+resultado.getGolesSeleccion2());
                    txtPuntosObtenidos.setText(pronosticoExistente.getPuntosObtenidos()+" puntos");
                    txtMensaje.setVisibility(View.VISIBLE);
                    txtMensaje.setText("🏆 ¡Partido finalizado! Ya conoces tus puntos.");
                }else{
                    lyResultadoFinal.setVisibility(View.GONE);
                    txtMensaje.setVisibility(View.VISIBLE);
                    txtMensaje.setText("Partido finalizado. Resultado pendiente de actualizar.");
                }
            }else if(partido.getEstado()==Estado.CERRADO){
                lyResultadoFinal.setVisibility((View.GONE));
                txtMensaje.setVisibility(View.VISIBLE);
                txtMensaje.setText("🔒 Los pronósticos para este partido están cerrados.");
            }else{
                lyResultadoFinal.setVisibility(View.GONE);
                txtMensaje.setVisibility(View.GONE);
            }

            // Evento de clic en el botón guardar pronóstico
            Partido partidoFinal=partido;
            btnGuardar.setOnClickListener(v -> guardarPronostico(partidoFinal,fase,edtGolesLocal,edtGolesVisitante));

            // Agregado de la tarjeta al contenedor principal
            contenedorPartidos.addView(vistaPartido);
        }
    }

    /**
     * Valida la información ingresada y registra o reemplaza un pronóstico.
     * Genera y captura excepciones personalizadas si el partido no está abierto o
     * si los datos ingresados están incompletos.
     *
     * @param partido           Objeto Partido al cual pertenece el pronóstico
     * @param fase              Fase a la que corresponde el partido
     * @param edtGolesLocal     Campo de texto del marcador de la Selección 1
     * @param edtGolesVisitante Campo de texto del marcador de la Selección 2
     */
    private void guardarPronostico(Partido partido, Fase fase, EditText edtGolesLocal,
                                   EditText edtGolesVisitante) {
        try{
            // Validación del estado del partido
            if (partido.getEstado() != Estado.ABIERTO){
                throw new PronosticoFueraDeTiempoException(
                        "El periodo para registrar pronósticos de este partido ya ha finalizado.");
            }
            String txt_GolesLocal = edtGolesLocal.getText().toString().trim();
            String txt_GolesVisitante = edtGolesVisitante.getText().toString().trim();
            // Validación de datos
            if (txt_GolesLocal.isEmpty() || txt_GolesVisitante.isEmpty()) {
                throw new DatosIncompletosException(
                        "No se han ingresado todos los datos necesarios para registrar el pronóstico.");
            }
            int golesLocal = Integer.parseInt(txt_GolesLocal);
            int golesVisitante = Integer.parseInt(txt_GolesVisitante);
            // Validación de rango de enteros válidos
            if (golesLocal < 0 || golesVisitante < 0) {
                throw new DatosIncompletosException(
                        "Los goles deben ser números enteros mayores o iguales a cero");
            }

            // Construcción del id y del objeto Pronóstico
            String idPronostico = "PR" + idUsuarioActual + "_" + partido.getIdPartido();
            Pronostico pronostico = new Pronostico(idPronostico, participanteActual,
                    partido.getIdPartido(), golesLocal, golesVisitante, 0);
            // Almacenamiento serializado en archivo
            ManipularArchivos.guardarPronostico(this, pronostico, fase);
            Toast.makeText(this, "Pronóstico guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (DatosIncompletosException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (PronosticoFueraDeTiempoException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingresa números válidos para los goles.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Busca y obtiene el resultado de un partido específico.
     *
     * @param idPartido Identificador del partido a consultar
     * @return Objeto Resultado correspondiente o null si no existe
     */
    private Resultado obtenerResultadoPorPartido(String idPartido){
        ArrayList<Resultado> resultados = ManipularArchivos.cargarResultados(this);
        for (Resultado r : resultados) {
            if (r.getIdPartido().equals(idPartido)) {
                return r;
            }
        }
        return null;
    }
}