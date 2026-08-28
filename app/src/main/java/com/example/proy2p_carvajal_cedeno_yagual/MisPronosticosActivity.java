package com.example.proy2p_carvajal_cedeno_yagual;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MisPronosticosActivity extends AppCompatActivity {

    LinearLayout contenedorPronosticos;
    LinearLayout btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_pronosticos);

        contenedorPronosticos = findViewById(R.id.contenedor_pronosticos);
        btnVolver = findViewById(R.id.btnVolver);

    }


}