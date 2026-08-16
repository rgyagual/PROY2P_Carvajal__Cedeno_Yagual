package com.example.proy2p_carvajal_cedeno_yagual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuAdministradorActivity extends AppCompatActivity {
    CardView cdAdministarPartidos;
    CardView cdActualizarPuntajes;
    CardView cdSalir;
    TextView txtNombreAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_administrador);

        cdActualizarPuntajes = findViewById(R.id.cd_administrarP);
        cdActualizarPuntajes =findViewById(R.id.cd_actualizar);
        cdSalir=findViewById(R.id.cd_salir);
        txtNombreAdministrador=findViewById(R.id.txt_nombreAdministrador);
        txtNombreAdministrador.setText(getNombreAdministrador());



    }
    public String getNombreAdministrador(){
        Intent intent = getIntent();
        String nombreCompletos = intent.getStringExtra("nombreCompleto");
        return nombreCompletos;
    }
/**Descomentar cuando esté pantalla administrar Partidos
    public void administrarPartidos(View view){
        Intent intent = new Intent(
                MenuAdministradorActivity.this,
                AdministarPartidosActivity.class
        );
        starActivity(intent);

    }
*/
    /**Descomentar cuando esté la pantalla actualizar Puntajes
    public void actualizarPuntajes(View view){
        Intent intent = new Intent(
                MenuAdministradorActivity.this,
                ActualizarPuntajesActivity.class
        );
        startActivity(intent);

    }
*/
    public void salir(View view){
        finish();
    }
}