package com.example.proy2p_carvajal_cedeno_yagual;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

import models.ManipularArchivos;
import models.Participante;

public class TablaPosicionesActivity extends AppCompatActivity {
    TextView labelTitulo;
    ScrollView scrollView;
    TableLayout posicionesLayout;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_posiciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_tablaPosiciones), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            posicionesLayout.findViewById(R.id.tb_tablaPosiciones);
            btnVolver.findViewById(R.id.btn_volver);

            mostrarPosiciones(obtenerPosiciones(),posicionesLayout);
            return insets;


        });

    }

    public ArrayList<Participante> obtenerPosiciones(){
        ArrayList<Participante> p = new ArrayList<>();
        ManipularArchivos.cargarUsuario(this).forEach(u->{
            if(u instanceof Participante){
                p.add((Participante)u);
            }
        });
        Collections.sort(p);
    return p;
    }

    private void mostrarPosiciones(ArrayList<Participante> p, TableLayout tabla){

        TableRow fila = new TableRow(this);
        TextView txt_pos = new TextView(this);
        TextView txt_tablaparticipante = new TextView(this);
        TextView txt_puntos = new TextView(this);

        txt_pos.setText("Pos.");
        txt_pos.setTextColor(Color.WHITE);
        txt_tablaparticipante.setText("Participante");
        txt_tablaparticipante.setTextColor(Color.WHITE);
        txt_puntos.setText("Puntos");
        txt_puntos.setTextColor(Color.WHITE);

        fila.addView(txt_pos);
        fila.addView(txt_tablaparticipante);
        fila.addView(txt_puntos);
        tabla.addView(fila);

        for(int i = 0; i<p.size();i++){

            TextView txt_posicion = new TextView(this);
            TextView txt_nombreParticipante = new TextView(this);
            TextView txt_puntajeParticipante = new TextView(this);

            txt_posicion.setText(String.valueOf(i+1));
            txt_nombreParticipante.setText(p.get(i).getNombreCompleto());
            txt_puntajeParticipante.setText(
                    String.valueOf(
                            p.get(i).getPuntajeAcumulado()
            ));

            fila.addView(txt_posicion);
            fila.addView(txt_nombreParticipante);
            fila.addView(txt_puntajeParticipante);
            tabla.addView(fila);
        }

    }
}