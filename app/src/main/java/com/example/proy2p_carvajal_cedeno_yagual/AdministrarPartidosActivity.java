package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import models.Estado;
import models.Fase;
import models.ManipularArchivos;
import models.Partido;
import models.Resultado;
import models.exceptions.DatosIncompletosException;

public class AdministrarPartidosActivity extends AppCompatActivity {

    private Spinner spFases;
    private LinearLayout layoutPartidos;
    private MaterialButton btnVolver;
    private String nombreCompletoAdmin;
    private ArrayList<Partido> listaPartidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_partidos);

        spFases = findViewById(R.id.spFasesAdmin);
        layoutPartidos = findViewById(R.id.layoutPartidosAdminContainer);
        btnVolver = findViewById(R.id.btnVolverAdminMenu);

        nombreCompletoAdmin = getIntent().getStringExtra("nombreCompleto");
        listaPartidos = ManipularArchivos.cargarPartidos(this);

        ArrayAdapter<Fase> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Fase.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFases.setAdapter(adapter);
        spFases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fase faseSeleccionada = (Fase) adapterView.getItemAtPosition(i);
                mostrarPartidosPorFase(faseSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarPartidosActivity.this, MenuAdministradorActivity.class);
            intent.putExtra("nombreCompleto", nombreCompletoAdmin);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarPartidosPorFase(Fase fase) {
        layoutPartidos.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Partido partido : listaPartidos) {
            if (partido.getFase() != fase) {
                continue;
            }
            View card = inflater.inflate(R.layout.item_partido_admin, layoutPartidos, false);

            TextView tvEstado = card.findViewById(R.id.tvAdminEstado);
            TextView tvId = card.findViewById(R.id.tvAdminIdPartido);
            TextView tvFecha = card.findViewById(R.id.tvAdminFecha);
            TextView tvHora = card.findViewById(R.id.tvAdminHora);
            TextView tvEstadio = card.findViewById(R.id.tvAdminEstadio);
            TextView tvSel1 = card.findViewById(R.id.tvAdminSeleccion1);
            TextView tvSel2 = card.findViewById(R.id.tvAdminSeleccion2);
            ImageView ivB1 = card.findViewById(R.id.ivBandera1);
            ImageView ivB2 = card.findViewById(R.id.ivBandera2);
            TextView tvVs = card.findViewById(R.id.tvVsOMarcador);

            Button btnCerrar = card.findViewById(R.id.btnCerrarPronosticos);
            Button btnMostrarFormulario = card.findViewById(R.id.btnRegistrarResultado);
            LinearLayout layoutFormResultado = card.findViewById(R.id.layoutAdminCerrado);
            EditText etGolesOficial1 = card.findViewById(R.id.etGolesOficial1);
            EditText etGolesOficial2 = card.findViewById(R.id.etGolesOficial2);
            Button btnGuardarResultado = card.findViewById(R.id.btnGuardarResultado);

            LinearLayout layoutFinalizado = card.findViewById(R.id.layoutAdminFinalizado);
            TextView tvGolesFin1 = card.findViewById(R.id.tvGolesFin1);
            TextView tvGolesFin2 = card.findViewById(R.id.tvGolesFin2);

            TextView tvMsg = card.findViewById(R.id.tvMsgInformativo);

            tvId.setText("Id: " + partido.getIdPartido());
            tvFecha.setText("📅 " + partido.getFecha());
            tvHora.setText("🕒 " + partido.getHora());
            tvEstadio.setText("🏟 " + partido.getEstadio());
            tvSel1.setText(partido.getSeleccion1());
            tvSel2.setText(partido.getSeleccion2());
            ManipularArchivos.asignarBandera(this, partido, ivB1, ivB2);

            btnCerrar.setVisibility(View.GONE);
            btnMostrarFormulario.setVisibility(View.GONE);
            layoutFormResultado.setVisibility(View.GONE);
            layoutFinalizado.setVisibility(View.GONE);

            if (partido.getEstado() == Estado.ABIERTO) {
                tvEstado.setText("ABIERTO");
                tvVs.setText("VS");
                btnCerrar.setVisibility(View.VISIBLE);
                btnCerrar.setOnClickListener(v -> cerrarPronosticos(partido, fase));
                tvMsg.setText("Los participantes pueden registrar o modificar sus pronósticos.");

            } else if (partido.getEstado() == Estado.CERRADO) {
                tvEstado.setText("CERRADO");
                tvVs.setText("VS");
                btnMostrarFormulario.setVisibility(View.VISIBLE);
                btnMostrarFormulario.setOnClickListener(v -> {
                    btnMostrarFormulario.setVisibility(View.GONE);
                    layoutFormResultado.setVisibility(View.VISIBLE);
                });
                btnGuardarResultado.setOnClickListener(v -> guardarResultadoPartido(partido, fase, etGolesOficial1, etGolesOficial2));
                tvMsg.setText("Los pronósticos están cerrados. Registra el resultado oficial cuando el partido haya finalizado.");

            } else if (partido.getEstado() == Estado.FINALIZADO) {
                tvEstado.setText("FINALIZADO");
                layoutFinalizado.setVisibility(View.VISIBLE);
                Resultado resultado = obtenerResultadoPorPartido(partido.getIdPartido());
                if (resultado != null) {
                    tvVs.setText(resultado.getGolesSeleccion1() + " - " + resultado.getGolesSeleccion2());
                    tvGolesFin1.setText(String.valueOf(resultado.getGolesSeleccion1()));
                    tvGolesFin2.setText(String.valueOf(resultado.getGolesSeleccion2()));
                }
                tvMsg.setText("Resultado registrado. El partido ha finalizado.");
            }
            layoutPartidos.addView(card);
        }
    }
    private void cerrarPronosticos (Partido partido, Fase fase){
        partido.setEstado(Estado.CERRADO);
        ManipularArchivos.guardarPartidos(this, listaPartidos);
        Toast.makeText(this, "Pronósticos cerrados para el partido " + partido.getIdPartido(), Toast.LENGTH_SHORT).show();
        mostrarPartidosPorFase(fase);
    }

    private void guardarResultadoPartido (Partido partido, Fase fase, EditText etGoles1, EditText etGoles2){
        try {
            String txtGoles1 = etGoles1.getText().toString().trim();
            String txtGoles2 = etGoles2.getText().toString().trim();

            if (txtGoles1.isEmpty() || txtGoles2.isEmpty()) {
                throw new DatosIncompletosException("No se han ingresado todos los datos necesarios para registrar el resultado.");
            }
            int goles1 = Integer.parseInt(txtGoles1);
            int goles2 = Integer.parseInt(txtGoles2);

            if (goles1 < 0 || goles2 < 0) {
                throw new DatosIncompletosException("Los goles deben ser números enteros mayores o iguales a cero.");
            }
            String idResultado = "RES_" + partido.getIdPartido();
            Resultado resultado = new Resultado(idResultado, partido.getIdPartido(), goles1, goles2);
            ManipularArchivos.guardarResultado(this, resultado);

            partido.setEstado(Estado.FINALIZADO);
            ManipularArchivos.guardarPartidos(this, listaPartidos);

            Toast.makeText(this, "Resultado registrado correctamente.", Toast.LENGTH_SHORT).show();
            mostrarPartidosPorFase(fase);

        } catch (DatosIncompletosException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingresa números válidos para los goles.", Toast.LENGTH_SHORT).show();
        }
    }

    private Resultado obtenerResultadoPorPartido (String idPartido){
        ArrayList<Resultado> resultados = ManipularArchivos.cargarResultados(this);
        for (Resultado r : resultados) {
            if (r.getIdPartido().equals(idPartido)) {
                return r;
            }
        }
        return null;
    }
}