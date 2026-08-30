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

public class PronosticosActivity extends AppCompatActivity {
    private Spinner spFase;
    private LinearLayout contenedorPartidos;
    private CardView cdVolver;
    private String idUsuarioActual;
    private Participante participanteActual;
    private ArrayList<Partido> listaPartidos;

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

        spFase = findViewById(R.id.spFase);
        contenedorPartidos = findViewById(R.id.contenedorPartidos);
        cdVolver = findViewById(R.id.cd_volver);
        idUsuarioActual = getIntent().getStringExtra("idUsuario");

        ArrayList<Usuario> listaUsuarios = ManipularArchivos.cargarUsuario(this);
        for (Usuario u : listaUsuarios) {
            if (u.getIdUsuario().equals(idUsuarioActual) && u instanceof Participante) {
                participanteActual = (Participante) u;
            }
        }

        listaPartidos = ManipularArchivos.cargarPartidos(this);
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
        cdVolver.setOnClickListener(v -> {
            Intent intent = new Intent(PronosticosActivity.this, MenuParticipanteActivity.class);
            intent.putExtra("idUsuario", idUsuarioActual);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarPartidosPorFase(Fase fase) {
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

            txtFase.setText(fase.toString());
            txtEstado.setText(partido.getEstado().toString());
            txtFecha.setText(partido.getFecha().toString());
            txtHora.setText(partido.getHora().toString());
            txtEstadio.setText(partido.getEstadio());
            txtNombreLocal.setText(partido.getSeleccion1());
            txtNombreVisitante.setText(partido.getSeleccion2());

            ManipularArchivos.asignarBandera(this,partido,imgBanderaLocal,imgBanderaVisitante);

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

            Partido partidoFinal=partido;
            btnGuardar.setOnClickListener(v -> guardarPronostico(partidoFinal,fase,edtGolesLocal,edtGolesVisitante));
            contenedorPartidos.addView(vistaPartido);
        }
    }

    private void guardarPronostico(Partido partido, Fase fase, EditText edtGolesLocal,
                                   EditText edtGolesVisitante) {
        try{
            if (partido.getEstado() != Estado.ABIERTO){
                throw new PronosticoFueraDeTiempoException(
                        "El periodo para registrar pronósticos de este partido ya ha finalizado.");
            }
            String txt_GolesLocal = edtGolesLocal.getText().toString().trim();
            String txt_GolesVisitante = edtGolesVisitante.getText().toString().trim();

            if (txt_GolesLocal.isEmpty() || txt_GolesVisitante.isEmpty()) {
                throw new DatosIncompletosException(
                        "No se han ingresado todos los datos necesarios para registrar el pronóstico.");
            }
            int golesLocal = Integer.parseInt(txt_GolesLocal);
            int golesVisitante = Integer.parseInt(txt_GolesVisitante);
            if (golesLocal < 0 || golesVisitante < 0) {
                throw new DatosIncompletosException(
                        "Los goles deben ser números enteros mayores o iguales a cero");
            }
            String idPronostico = "PR" + idUsuarioActual + "_" + partido.getIdPartido();
            Pronostico pronostico = new Pronostico(idPronostico, participanteActual,
                    partido.getIdPartido(), golesLocal, golesVisitante, 0);
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