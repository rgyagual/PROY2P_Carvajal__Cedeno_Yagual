package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuParticipanteActivity extends AppCompatActivity {

    CardView cdTablaPosiciones;
    CardView cdPronosticos;
    CardView cdMisPronosticos;
    CardView cdSalir;
    TextView txtNombreParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_participante);

        Toast.makeText(this,"Entre",Toast.LENGTH_LONG);

        cdTablaPosiciones = findViewById(R.id.cd_tablaPosiciones);
        cdPronosticos = findViewById(R.id.cd_pronosticos);
        cdMisPronosticos = findViewById(R.id.cd_misPronosticos);
        cdSalir = findViewById(R.id.cd_salir);
        txtNombreParticipante = findViewById(R.id.txt_nombreParticipante);
        txtNombreParticipante.setText(getNombreParticipante());

    }

    public String getNombreParticipante(){
        Intent intent = getIntent();
        String nombreCompletos = intent.getStringExtra("nombreCompleto");
        return nombreCompletos;
    }
    public void mostrarTablaPosiciones(View view) {

        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                TablaPosicionesActivity.class
        );

        intent.putExtra("nombreCompleto", getNombreParticipante());
        startActivity(intent);
    }

    public void mostrarPronosticos(View view){

        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                PronosticosActivity.class
        );
        starActivity(intent);
    }

    public void mostrarMisPronosticos(View view){
        Intent intent = new Intent(
                MenuParticipanteActivity.this,
                PronosticosActivity.class
                );
        startActivity(intent);
    }

    public void salir(View view){
        finishAffinity();
    }
}