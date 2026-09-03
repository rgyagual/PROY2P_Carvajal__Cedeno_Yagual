package models;

import android.content.Context;
import android.widget.ImageView;

import com.example.proy2p_carvajal_cedeno_yagual.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria encargada de la persistencia de datos y manipulación de archivos
 * del sistema (tanto texto plano `.txt` como binarios `.dat`), además de operaciones
 * de recursos visuales como la asignación de banderas.
 *
 * @author Yagual-Cedeño-Carvajal
 */
public class ManipularArchivos {

    // =======================================
    // CONSTANTES
    // =======================================

    /**
     * Arreglo con los nombres de los archivos base en formato plano
     */
    public static final String[] ARCHIVOS = {"usuarios.txt", "participantes.txt", "administradores.txt", "partidos.txt", "resultados.txt"};

    // =======================================
    // MÉTODOS ESTÁTICOS
    // =======================================

    /**
     * Copia los archivos base almacenados en los assets de la aplicación al almacenamiento
     * interno del dispositivo si aún no existen.
     *
     * @param context Contexto de la aplicación
     */
    public static void iniciarArchivo(Context context) {
        for (String nombreArchivo : ARCHIVOS) {
            File archivoDestino = new File(context.getExternalFilesDir(null), nombreArchivo);
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

    /**
     * Lee y construye la lista de usuarios del sistema desde `usuarios.txt`,
     * discriminando entre Participantes y Administradores y cargando su información
     * complementaria desde `participantes.txt` o `administradores.txt`.
     *
     * @param context Contexto de la aplicación
     * @return Lista de objetos Usuario que pueden ser instancias de Participante o Administrador
     */
    public static ArrayList<Usuario> cargarUsuario(Context context) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        File archivo = new File(context.getExternalFilesDir(null), "usuarios.txt");
        if (!archivo.exists()) {
            return listaUsuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeralinea = true;
            while ((linea = br.readLine()) != null) {
                // saltar el encabezado
                if (primeralinea) {
                    primeralinea = false;
                    continue;
                }
                if (linea.isEmpty()) {
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
                        File archivoParticipantes = new File(context.getExternalFilesDir(null), "participantes.txt");
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
                        // BUSCAR CARGO PARTICIPANTE
                        String cargo = "";
                        File archivoadmi = new File(context.getExternalFilesDir(null), "administradores.txt");

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

    /**
     * Reescribe el archivo `participantes.txt` actualizando los puntajes acumulados
     * de todos los participantes.
     *
     * @param context       Contexto de la aplicación
     * @param participantes Lista de participantes a guardar
     */
    public static void guardarParticipantes(Context context, List<Participante> participantes) {
        File file = new File(context.getExternalFilesDir(null), "participantes.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Escribir la cabecera
            bw.write("idUsuario;puntajeAcumulado");
            bw.newLine(); // Salto de línea
            // Escribir los datos de los participantes
            for (Participante p : participantes) {
                bw.write(p.getIdUsuario() + ";" + p.getPuntajeAcumulado());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todos los partidos registrados desde el archivo `partidos.txt`.
     *
     * @param context Contexto de la aplicación
     * @return Lista de objetos {@link Partido}
     */
    public static ArrayList<Partido> cargarPartidos(Context context) {
        ArrayList<Partido> listaPartidos = new ArrayList<>();

        File archivo = new File(context.getExternalFilesDir(null), "partidos.txt");
        if (!archivo.exists()) {
            return listaPartidos;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(";");
                if (datos.length >= 8) {
                    String idPartido = datos[0];
                    Fase fase = Fase.valueOf(datos[1]);
                    LocalDate fecha = LocalDate.parse(datos[2]);
                    LocalTime hora = LocalTime.parse(datos[3]);
                    String estadio = datos[4];
                    String seleccion1 = datos[5];
                    String seleccion2 = datos[6];
                    Estado estado = Estado.valueOf(datos[7]);

                    listaPartidos.add(new Partido(idPartido, fase, fecha, hora,
                            estadio, seleccion1, seleccion2, estado));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaPartidos;
    }

    /**
     * Carga la lista de pronósticos en formato serializado para un usuario y fase específicos.
     *
     * @param context   Contexto de la aplicación
     * @param idUsuario Identificador del usuario participante
     * @param fase      Fase del torneo solicitada
     * @return Lista de Pronostico del participante en la fase indicada
     */

    public static ArrayList<Pronostico> cargarPronosticos(Context context, String idUsuario, Fase fase) {
        ArrayList<Pronostico> lista = new ArrayList<>();
        String nombreArchivo = "pronostico_" + idUsuario + "_" + fase + ".dat";
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        if (!archivo.exists()) {
            return lista;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(archivo))) {
            lista = (ArrayList<Pronostico>) entrada.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Recorre todos los usuarios y fases del torneo para recopilar todos los pronósticos guardados.
     *
     * @param context Contexto de la aplicación
     * @return Lista general con todos los pronósticos registrados en el sistema
     */
    public static ArrayList<Pronostico> cargarPronosticosGeneral(Context context) {
        ArrayList<Pronostico> pronosticosGeneral = new ArrayList<>();
        String[] fases = {
                "FASE_DE_GRUPOS",
                "DIECISEISAVOS_DE_FINAL",
                "OCTAVOS_DE_FINAL",
                "CUARTOS_DE_FINAL",
                "SEMIFINALES",
                "TERCER_LUGAR",
                "FINAL"
        };
        ArrayList<Usuario> usuarios = cargarUsuario(context);
        for (Usuario u : usuarios) {
            String idUsuario = u.getIdUsuario();
            for (String f : fases) {
                ArrayList<Pronostico> pronosticosPorUsuario = cargarPronosticos(context, idUsuario, Fase.valueOf(f));
                pronosticosGeneral.addAll(pronosticosPorUsuario);
            }
        }
        return pronosticosGeneral;
    }

    /**
     * Guarda o actualiza un pronóstico en su respectivo archivo serializado.
     *
     * @param context    Contexto de la aplicación
     * @param pronostico Objeto Pronostico a guardar o actualizar
     * @param fase       Fase del torneo asociada al pronóstico
     */
    public static void guardarPronostico(Context context, Pronostico pronostico, Fase fase) {
        String idUsuario = pronostico.getParticipante().getIdUsuario();
        String nombreArchivo = "pronostico_" + idUsuario + "_" + fase + ".dat";
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);

        ArrayList<Pronostico> lista = cargarPronosticos(context, idUsuario, fase);
        boolean reemplazo = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdPartido().equals(pronostico.getIdPartido())) {
                lista.set(i, pronostico);
                reemplazo = true;
                break;
            }
        }
        if (!reemplazo) {
            lista.add(pronostico);
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(archivo))) {
            salida.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todos los resultados registrados desde el archivo `resultados.txt`.
     *
     * @param context Contexto de la aplicación
     * @return Lista de objetos Resultado
     */
    public static ArrayList<Resultado> cargarResultados(Context context) {
        ArrayList<Resultado> resultados = new ArrayList<>();
        File archivo = new File(context.getExternalFilesDir(null), "resultados.txt");
        if (!archivo.exists()) {
            return resultados;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datosResultado = linea.split(";");
                if (datosResultado.length >= 4) {
                    String idResultado = datosResultado[0];
                    String idPartido = datosResultado[1];
                    int golesSeleccion1 = Integer.parseInt(datosResultado[2]);
                    int golesSeleccion2 = Integer.parseInt(datosResultado[3]);
                    resultados.add(new Resultado(idResultado, idPartido, golesSeleccion1, golesSeleccion2));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    /**
     * Asigna dinámicamente las imágenes de las banderas a los `ImageView` de las selecciones
     * según los nombres de los países en el partido. Si no se encuentra el recurso, coloca
     * el logo por defecto del torneo.
     *
     * @param context            Contexto de la aplicación
     * @param p                  Objeto Partido del cual se extraen las selecciones
     * @param imageViewLocal     Componente visual donde se carga la bandera local
     * @param imageViewVisitante Componente visual donde se carga la bandera visitante
     */
    public static void asignarBandera(Context context, Partido p, ImageView imageViewLocal, ImageView imageViewVisitante) {
        String nombrePaisLocal = p.getSeleccion1().toLowerCase().replace("ñ", "n")
                .replace(" ", "").replace("á", "a")
                .replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u");
        int idImagenLocal = context.getResources().getIdentifier(nombrePaisLocal, "drawable", context.getPackageName());

        String nombrePaisVisitante = p.getSeleccion2().toLowerCase().replace("ñ", "n").
                replace(" ", "").replace("á", "a")
                .replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u");
        int idImagenVisitante = context.getResources().getIdentifier(nombrePaisVisitante, "drawable", context.getPackageName());

        if (idImagenLocal != 0) {
            imageViewLocal.setImageResource(idImagenLocal);
        } else {
            imageViewLocal.setImageResource(R.drawable.logwc26);
        }
        if (idImagenVisitante != 0) {
            imageViewVisitante.setImageResource(idImagenVisitante);
        } else {
            imageViewVisitante.setImageResource(R.drawable.logwc26);
        }

    }

    /**
     * Reescribe el archivo `partidos.txt` con la lista completa de partidos especificada.
     *
     * @param context       Contexto de la aplicación
     * @param listaPartidos Lista de partidos a guardar
     */
    public static void guardarPartidos(Context context, ArrayList<Partido> listaPartidos) {
        File archivo = new File(context.getExternalFilesDir(null), "partidos.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            bw.write("idPartido;fase;fecha;horaUTC;estadio;seleccion1;seleccion2;estado");
            bw.newLine();
            for (Partido p : listaPartidos) {
                String linea = p.getIdPartido() + ";" + p.getFase() + ";" + p.getFecha() + ";" + p.getHora() + ";"
                        + p.getEstadio() + ";" + p.getSeleccion1() + ";" + p.getSeleccion2() + ";" + p.getEstado();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega un nuevo registro al final del archivo `resultados.txt`.
     *
     * @param context   Contexto de la aplicación
     * @param resultado Objeto Resultado a guardar
     */
    public static void guardarResultado(Context context, Resultado resultado) {
        File archivo = new File(context.getExternalFilesDir(null), "resultados.txt");
        boolean escribirEncabezado = !archivo.exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            if (escribirEncabezado) {
                bw.write("idResultado;idPartido;golesSeleccion1;golesSeleccion2");
                bw.newLine();
            }
            String linea = resultado.getIdResultado() + ";" + resultado.getIdPartido() + ";"
                    + resultado.getGolesSeleccion1() + ";" + resultado.getGolesSeleccion2();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}