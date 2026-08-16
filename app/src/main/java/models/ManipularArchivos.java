package models;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManipularArchivos {
    public static final String[] ARCHIVOS = {"usuarios.txt", "participantes.txt", "administradores.txt", "partidos.txt", "resultados.txt"};
    public static void iniciarArchivo(Context context){
        for (String nombreArchivo : ARCHIVOS) {
            File archivoDestino = new File(context.getFilesDir(), nombreArchivo);
            if (!archivoDestino.exists()) {
                try (InputStream entrada = context.getAssets().open(nombreArchivo);
                     FileOutputStream salida = new FileOutputStream(archivoDestino)) {
                    byte[] buffer = new byte[1024];
                    int cantidad;
                    while ((cantidad = entrada.read(buffer)) != -1) {
                        salida.write(buffer, 0, cantidad);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<Usuario> cargarUsuario(Context context){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        File archivo = new File(context.getFilesDir(), "usuarios.txt");
        if (!archivo.exists()){
            return listaUsuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeralinea=true;
            while ((linea = br.readLine()) != null) {
                //saltar el encabezado
                if (primeralinea){
                    primeralinea=false;
                    continue;
                }
                if(linea.isEmpty()){
                    continue;
                }
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    String idUsuario = datos[0];
                    String nombreUsuario = datos[1];
                    String contrasena = datos[2];
                    String nombreCompleto = datos[3];
                    String tipoUsuario = datos[4];

                    if (tipoUsuario.equalsIgnoreCase("PARTICIPANTE")) {
                        // Buscar el puntaje de participante en participantes.txt
                        int puntaje = 0;
                        File archivoParticipantes = new File(context.getFilesDir(), "participantes.txt");
                        if (archivoParticipantes.exists()) {
                            try (BufferedReader brP = new BufferedReader(new FileReader(archivoParticipantes))) {
                                String lineaP;
                                boolean primeraLineaP = true;
                                while ((lineaP = brP.readLine()) != null) {
                                    if (primeraLineaP) {
                                        primeraLineaP = false;
                                        continue;
                                    }
                                    if (lineaP.isEmpty()) {
                                        continue;
                                    }
                                    String[] datosP = lineaP.split(";");
                                    if (datosP.length >= 2 && datosP[0].equals(idUsuario)) {
                                        puntaje = Integer.parseInt(datosP[1]);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        listaUsuarios.add(new Participante(idUsuario, nombreUsuario, contrasena, nombreCompleto, TipoUsuario.PARTICIPANTE, puntaje));
                    } else if (tipoUsuario.equalsIgnoreCase("ADMINISTRADOR")) {
                        //BUSCAR CARGO PARTICIPANTE
                        String cargo = "";
                        File archivoadmi = new File(context.getFilesDir(), "administradores.txt");

                        if (archivoadmi.exists()) {
                            try (BufferedReader brA = new BufferedReader(new FileReader(archivoadmi))) {
                                String lineaA;
                                boolean primeraLineaA = true;
                                while ((lineaA = brA.readLine()) != null) {
                                    if (primeraLineaA) {
                                        primeraLineaA = false;
                                        continue;
                                    }
                                    if (lineaA.isEmpty()) {
                                        continue;
                                    }
                                    String[] datosA = lineaA.split(";");
                                    if (datosA.length >= 2 && datosA[0].equals(idUsuario)) {
                                        cargo = datosA[1];
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        listaUsuarios.add(new Administrador(idUsuario, nombreUsuario, contrasena, nombreCompleto, cargo));

                    }
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return listaUsuarios;
    }
}
