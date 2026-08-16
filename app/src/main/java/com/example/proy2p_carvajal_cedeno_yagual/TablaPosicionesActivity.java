package com.example.proy2p_carvajal_cedeno_yagual;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

import models.Participante;
import models.Partido;
import models.Usuario;
import utils.DataManager;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edt_nombreUsuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });

    }

    public ArrayList<Participante> obtenerPosiciones(){
        ArrayList<Participante> participantes = new ArrayList<>();
        for (Usuario u: DataManager.cargarUsuariosCompletos(this)){
            if(u instanceof Participante){
                partipantes.add((Participante)u);
            }
        }
        Collections.sort(participantes);


    }
}