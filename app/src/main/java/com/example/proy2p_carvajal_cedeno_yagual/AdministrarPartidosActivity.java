package com.example.proy2p_carvajal_cedeno_yagual;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Estado;
import models.Fase;
import models.Partido;
import models.Resultado;
import models.exceptions.DatosIncompletosException;

public class AdministrarPartidosActivity extends AppCompatActivity {

    private Spinner spFase;
    private ScrollView scrollV;
    private LinearLayout layoutContenedorPartidos;

    private ArrayList<Partido> listaPartidos;
    private Map<String, Fase> mapeoFasesPorPartido;
    private Partido partidoSeleccionado;
    private EditText etGoles1Ref;
    private EditText etGoles2Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_partidos);

        spFase = findViewById(R.id.spFasesAdmin);
        scrollV = findViewById(R.id.scrollVAdmin);
        layoutContenedorPartidos = findViewById(R.id.layoutPartidosAdminContainer);

        Button btnVolver = findViewById(R.id.btnVolverAdminMenu);
        btnVolver.setOnClickListener(v -> finish());

        mapeoFasesPorPartido = new HashMap<>();
        listaPartidos = leerPartidosLocalmente();

        configurarSpinner();
    }

    private void configurarSpinner() {
        Fase[] fases = Fase.values();
        ArrayAdapter<Fase> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fases);
        spFase.setAdapter(adapter);

        spFase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarPartidos(fases[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void mostrarPartidos(Fase faseSeleccionada) {
        layoutContenedorPartidos.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Map<String, Resultado> resultados = leerResultadosLocalmente();

        for (Partido p : listaPartidos) {
            Fase fasePartido = mapeoFasesPorPartido.get(p.getIdPartido());
            if (fasePartido == null || fasePartido != faseSeleccionada) {
                continue;
            }

            View card = inflater.inflate(R.layout.item_partido_admin, layoutContenedorPartidos, false);

            TextView tvEstado = card.findViewById(R.id.tvAdminEstado);
            TextView tvId = card.findViewById(R.id.tvAdminIdPartido);
            TextView tvFecha = card.findViewById(R.id.tvAdminFecha);
            TextView tvHora = card.findViewById(R.id.tvAdminHora);
            TextView tvEstadio = card.findViewById(R.id.tvAdminEstadio);

            TextView tvEquipo1 = card.findViewById(R.id.tvAdminSeleccion1);
            TextView tvEquipo2 = card.findViewById(R.id.tvAdminSeleccion2);
            ImageView ivB1 = card.findViewById(R.id.ivBandera1);
            ImageView ivB2 = card.findViewById(R.id.ivBandera2);
            TextView tvVsOMarcador = card.findViewById(R.id.tvVsOMarcador);

            Button btnCerrar = card.findViewById(R.id.btnCerrarPronosticos);
            LinearLayout layoutCerrado = card.findViewById(R.id.layoutAdminCerrado);
            TextView lblG1 = card.findViewById(R.id.lblGolesSel1);
            TextView lblG2 = card.findViewById(R.id.lblGolesSel2);
            EditText etG1 = card.findViewById(R.id.etGolesOficial1);
            EditText etG2 = card.findViewById(R.id.etGolesOficial2);
            Button btnGuardar = card.findViewById(R.id.btnGuardarResultado);

            LinearLayout layoutFinalizado = card.findViewById(R.id.layoutAdminFinalizado);
            TextView lblFinG1 = card.findViewById(R.id.lblFinGolesSel1);
            TextView lblFinG2 = card.findViewById(R.id.lblFinGolesSel2);
            TextView tvGolesFin1 = card.findViewById(R.id.tvGolesFin1);
            TextView tvGolesFin2 = card.findViewById(R.id.tvGolesFin2);

            TextView tvMsgInformativo = card.findViewById(R.id.tvMsgInformativo);

            // Cargar datos informativos del partido
            tvId.setText("Id: " + p.getIdPartido());
            tvFecha.setText("📅 " + p.getFecha().toString());
            tvHora.setText("🕒 " + p.getHora().toString());
            tvEstadio.setText("🏟 " + p.getEstadio());
            tvEquipo1.setText(p.getSeleccion1());
            tvEquipo2.setText(p.getSeleccion2());

            if (lblG1 != null) lblG1.setText("Goles " + p.getSeleccion1());
            if (lblG2 != null) lblG2.setText("Goles " + p.getSeleccion2());
            if (lblFinG1 != null) lblFinG1.setText("Goles " + p.getSeleccion1());
            if (lblFinG2 != null) lblFinG2.setText("Goles " + p.getSeleccion2());

            cargarBandera(ivB1, p.getSeleccion1());
            cargarBandera(ivB2, p.getSeleccion2());

            // Configurar vistas y eventos según el estado
            if (p.getEstado() == Estado.ABIERTO) {
                tvEstado.setText("ABIERTO");
                tvEstado.setTextColor(Color.parseColor("#2E7D32"));
                tvEstado.setBackgroundColor(Color.parseColor("#E8F5E9"));

                tvVsOMarcador.setText("VS");
                btnCerrar.setVisibility(View.VISIBLE);
                btnCerrar.setOnClickListener(v -> cerrarPronosticos(p));

                tvMsgInformativo.setText("ⓘ  Los participantes pueden registrar o modificar sus pronósticos.");
                tvMsgInformativo.setTextColor(Color.parseColor("#2E7D32"));
                tvMsgInformativo.setBackgroundColor(Color.parseColor("#E8F5E9"));

            } else if (p.getEstado() == Estado.CERRADO) {
                tvEstado.setText("CERRADO");
                tvEstado.setTextColor(Color.parseColor("#E65100"));
                tvEstado.setBackgroundColor(Color.parseColor("#FFF3E0"));

                tvVsOMarcador.setText("VS");
                layoutCerrado.setVisibility(View.VISIBLE);

                btnGuardar.setOnClickListener(v -> {
                    registrarResultado(p);
                    etGoles1Ref = etG1;
                    etGoles2Ref = etG2;
                    guardarResultado();
                });

                tvMsgInformativo.setText("ⓘ  Los pronósticos están cerrados. Registra el resultado oficial cuando el partido haya finalizado.");
                tvMsgInformativo.setTextColor(Color.parseColor("#E65100"));
                tvMsgInformativo.setBackgroundColor(Color.parseColor("#FFF3E0"));

            } else if (p.getEstado() == Estado.FINALIZADO) {
                tvEstado.setText("FINALIZADO");
                tvEstado.setTextColor(Color.parseColor("#1565C0"));
                tvEstado.setBackgroundColor(Color.parseColor("#E3F2FD"));

                layoutFinalizado.setVisibility(View.VISIBLE);
                Resultado res = resultados.get(p.getIdPartido());
                if (res != null) {
                    tvVsOMarcador.setText(res.getGolesSeleccion1() + " - " + res.getGolesSeleccion2());
                    tvGolesFin1.setText(String.valueOf(res.getGolesSeleccion1()));
                    tvGolesFin2.setText(String.valueOf(res.getGolesSeleccion2()));
                }

                tvMsgInformativo.setText("✓  Resultado registrado. El partido ha finalizado.");
                tvMsgInformativo.setTextColor(Color.parseColor("#1565C0"));
                tvMsgInformativo.setBackgroundColor(Color.parseColor("#E3F2FD"));
            }

            layoutContenedorPartidos.addView(card);
        }
    }

    private void cargarBandera(ImageView iv, String nombrePais) {
        String recurso = nombrePais.toLowerCase()
                .replace(" ", "_")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("ñ", "n");
        int resId = getResources().getIdentifier(recurso, "drawable", getPackageName());
        if (resId != 0) {
            iv.setImageResource(resId);
        } else {
            iv.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    public void cerrarPronosticos(Partido partido) {
        partido.setEstado(Estado.CERRADO);
        guardarPartidosLocalmente();
        Toast.makeText(this, "Pronósticos cerrados para el partido " + partido.getIdPartido(), Toast.LENGTH_SHORT).show();
        mostrarPartidos((Fase) spFase.getSelectedItem());
    }

    public void registrarResultado(Partido partido) {
        this.partidoSeleccionado = partido;
    }

    public void guardarResultado() {
        if (partidoSeleccionado == null || etGoles1Ref == null || etGoles2Ref == null) {
            return;
        }

        try {
            String s1 = etGoles1Ref.getText().toString().trim();
            String s2 = etGoles2Ref.getText().toString().trim();

            if (s1.isEmpty() || s2.isEmpty()) {
                throw new DatosIncompletosException("No se han ingresado todos los datos necesarios para registrar el resultado.");
            }

            int g1, g2;
            try {
                g1 = Integer.parseInt(s1);
                g2 = Integer.parseInt(s2);
                if (g1 < 0 || g2 < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new DatosIncompletosException("Los goles deben ser números enteros mayores o iguales a cero.");
            }

            String idResultado = "RES_" + partidoSeleccionado.getIdPartido();
            Resultado nuevoResultado = new Resultado(idResultado, partidoSeleccionado.getIdPartido(), g1, g2);
            partidoSeleccionado.setIdResultado(idResultado);
            guardarResultadoEnArchivo(nuevoResultado);

            partidoSeleccionado.setEstado(Estado.FINALIZADO);
            guardarPartidosLocalmente();

            Toast.makeText(this, "Resultado registrado correctamente.", Toast.LENGTH_SHORT).show();
            mostrarPartidos((Fase) spFase.getSelectedItem());

        } catch (DatosIncompletosException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Partido> leerPartidosLocalmente() {
        ArrayList<Partido> lista = new ArrayList<>();
        File archivo = new File(getFilesDir(), "partidos.txt");

        if (!archivo.exists()) {
            copiarArchivoDesdeAssets("partidos.txt");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";");
                if (datos.length >= 8) {
                    String id = datos[0].trim();
                    String faseStr = datos[1].trim().toUpperCase().replace(" ", "_");
                    LocalDate fecha = LocalDate.parse(datos[2].trim());
                    LocalTime hora = LocalTime.parse(datos[3].trim());
                    String estadio = datos[4].trim();
                    String sel1 = datos[5].trim();
                    String sel2 = datos[6].trim();
                    Estado estado = Estado.valueOf(datos[7].trim().toUpperCase());

                    try {
                        Fase faseEnum = Fase.valueOf(faseStr);
                        mapeoFasesPorPartido.put(id, faseEnum);
                    } catch (IllegalArgumentException ignored) {
                    }

                    lista.add(new Partido(id, fecha, hora, estadio, sel1, sel2, estado));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void copiarArchivoDesdeAssets(String nombreArchivo) {
        File destino = new File(getFilesDir(), nombreArchivo);
        try (java.io.InputStream in = getAssets().open(nombreArchivo);
             java.io.OutputStream out = new java.io.FileOutputStream(destino)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Resultado> leerResultadosLocalmente() {
        Map<String, Resultado> mapa = new HashMap<>();
        File archivo = new File(getFilesDir(), "resultados.txt");
        if (!archivo.exists()) {
            return mapa;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";");
                if (datos.length >= 4) {
                    String idRes = datos[0].trim();
                    String idPar = datos[1].trim();
                    int g1 = Integer.parseInt(datos[2].trim());
                    int g2 = Integer.parseInt(datos[3].trim());
                    mapa.put(idPar, new Resultado(idRes, idPar, g1, g2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }

    private void guardarPartidosLocalmente() {
        File archivo = new File(getFilesDir(), "partidos.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            bw.write("idPartido;fase;fecha;horaUTC;estadio;seleccion1;seleccion2;estado");
            bw.newLine();
            for (Partido p : listaPartidos) {
                Fase f = mapeoFasesPorPartido.get(p.getIdPartido());
                String fStr = (f != null) ? f.name() : "GRUPOS";
                String linea = p.getIdPartido() + ";" + fStr + ";" + p.getFecha().toString() + ";" + p.getHora().toString() + ";" + p.getEstadio() + ";" + p.getSeleccion1() + ";" + p.getSeleccion2() + ";" + p.getEstado().name();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarResultadoEnArchivo(Resultado res) {
        File archivo = new File(getFilesDir(), "resultados.txt");
        boolean escribirEncabezado = !archivo.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            if (escribirEncabezado) {
                bw.write("idResultado;idPartido;golesSeleccion1;golesSeleccion2");
                bw.newLine();
            }
            String linea = res.getIdResultado() + ";" + res.getIdPartido() + ";" + res.getGolesSeleccion1() + ";" + res.getGolesSeleccion2();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}