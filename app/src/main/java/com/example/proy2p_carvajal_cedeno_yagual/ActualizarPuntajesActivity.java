package com.example.proy2p_carvajal_cedeno_yagual;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ActualizarPuntajesActivity extends AppCompatActivity {

    LinearLayout btnActualizarPuntaje;
    LinearLayout btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_puntajes);

        btnActualizarPuntaje = findViewById(R.id.btn_actualizarPuntaje);
        btnVolver = findViewById(R.id.btnVolver);
    }
}