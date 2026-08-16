package utils;

import android.content.Context;

import models.Administrador;
import models.Participante;
import models.models.exceptions.CredencialesInvalidasException;
import models.models.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Usuario.*;


public class DataManager {
    public static final String[] ARCHIVOS = {"usuarios.txt", "participantes.txt", "administradores.txt", "partidos.txt", "resultados.txt"};
    public static void iniciarArchivo(Context context) {
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

    // 2. Autenticación de usuarios
    public static Usuario autenticar(Context context, String username, String password) throws CredencialesInvalidasException {
        List<Usuario> usuarios = cargarUsuariosCompletos(context);
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        throw new CredencialesInvalidasException("El usuario o la contraseña son incorrectos.");
    }

    // Cargar la lista completa de Participantes y Administradores
    public static List<Usuario> cargarUsuariosCompletos(Context context) {
        List<Usuario> lista = new ArrayList<>();
        Map<String, Integer> puntajes = cargarPuntajesParticipantes(context);
        Map<String, String> cargos = cargarCargosAdministradores(context);

        File file = new File(context.getFilesDir(), "usuarios.txt");
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",");
                if (p.length >= 5) {
                    String id = p[0].trim();
                    String user = p[1].trim();
                    String pass = p[2].trim();
                    String nombre = p[3].trim();
                    String tipo = p[4].trim();

                    if (tipo.equalsIgnoreCase("Participante")) {
                        int puntos = puntajes.containsKey(id) ? puntajes.get(id) : 0;
                        lista.add(new Participante(id, user, pass, nombre, puntos));
                    } else if (tipo.equalsIgnoreCase("Administrador")) {
                        String cargo = cargos.containsKey(id) ? cargos.get(id) : "Administrador General";
                        lista.add(new Administrador(id, user, pass, nombre, cargo));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static Map<String, Integer> cargarPuntajesParticipantes(Context context) {
        Map<String, Integer> map = new HashMap<>();
        File file = new File(context.getFilesDir(), "participantes.txt");
        if (!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",");
                if (p.length >= 2) {
                    map.put(p[0].trim(), Integer.parseInt(p[1].trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Map<String, String> cargarCargosAdministradores(Context context) {
        Map<String, String> map = new HashMap<>();
        File file = new File(context.getFilesDir(), "administradores.txt");
        if (!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",");
                if (p.length >= 2) {
                    map.put(p[0].trim(), p[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 3. Manejo de Partidos
    public static List<Partido> cargarPartidos(Context context) {
        List<Partido> partidos = new ArrayList<>();
        File file = new File(context.getFilesDir(), "partidos.txt");
        if (!file.exists()) return partidos;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",");
                if (p.length >= 8) {
                    partidos.add(new Partido(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim(), p[6].trim(), p[7].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partidos;
    }

    public static void guardarPartidos(Context context, List<Partido> partidos) {
        File file = new File(context.getFilesDir(), "partidos.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Partido p : partidos) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                        p.getId(), p.getFase(), p.getFecha(), p.getHora(), p.getEstadio(), p.getSeleccion1(), p.getSeleccion2(), p.getEstado()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 4. Manejo de Resultados
    public static List<Resultado> cargarResultados(Context context) {
        List<Resultado> resultados = new ArrayList<>();
        File file = new File(context.getFilesDir(), "resultados.txt");
        if (!file.exists()) return resultados;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(",");
                if (p.length >= 4) {
                    resultados.add(new Resultado(p[0].trim(), p[1].trim(), Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    public static void guardarResultado(Context context, Resultado res) {
        List<Resultado> lista = cargarResultados(context);
        lista.removeIf(r -> r.getIdPartido().equals(res.getIdPartido()));
        lista.add(res);

        File file = new File(context.getFilesDir(), "resultados.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Resultado r : lista) {
                bw.write(String.format("%s,%s,%d,%d\n", r.getIdResultado(), r.getIdPartido(), r.getGolesSeleccion1(), r.getGolesSeleccion2()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 5. Serialización de Pronósticos (.dat)
    public static String getNombreArchivoPronostico(String idUsuario, String fase) {
        String faseSanitizada = fase.toLowerCase().replace(" ", "_");
        return "pronostico_" + idUsuario + "_" + faseSanitizada + ".dat";
    }

    @SuppressWarnings("unchecked")
    public static List<Pronostico> cargarPronosticos(Context context, String idUsuario, String fase) {
        List<Pronostico> lista = new ArrayList<>();
        File file = new File(context.getFilesDir(), getNombreArchivoPronostico(idUsuario, fase));
        if (!file.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<Pronostico>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void guardarPronostico(Context context, String idUsuario, String fase, Pronostico nuevoPronostico) {
        List<Pronostico> pronosticos = cargarPronosticos(context, idUsuario, fase);
        pronosticos.removeIf(p -> p.getIdPartido().equals(nuevoPronostico.getIdPartido()));
        pronosticos.add(nuevoPronostico);

        File file = new File(context.getFilesDir(), getNombreArchivoPronostico(idUsuario, fase));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(pronosticos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarListaPronosticos(Context context, String idUsuario, String fase, List<Pronostico> lista) {
        File file = new File(context.getFilesDir(), getNombreArchivoPronostico(idUsuario, fase));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 6. Guardar lista de participantes con sus nuevos puntajes
    public static void guardarParticipantesPuntajes(Context context, List<Participante> participantes) {
        File file = new File(context.getFilesDir(), "participantes.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Participante p : participantes) {
                bw.write(p.getId() + "," + p.getPuntajeAcumulado() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 7. Reglas de cálculo de puntajes oficiales
    public static int calcularPuntos(int pGoles1, int pGoles2, int rGoles1, int rGoles2) {
        if (pGoles1 == rGoles1 && pGoles2 == rGoles2) {
            return 3; // Marcador exacto
        }
        int difPronostico = pGoles1 - pGoles2;
        int difReal = rGoles1 - rGoles2;

        if (rGoles1 == rGoles2 && pGoles1 == pGoles2) {
            return 2; // Acertó empate no exacto
        }

        boolean mismoGanador = (difPronostico > 0 && difReal > 0) || (difPronostico < 0 && difReal < 0);
        if (mismoGanador) {
            if (difPronostico == difReal) {
                return 2; // Acertó ganador y diferencia de goles
            }
            return 1; // Solo acertó el ganador
        }
        return 0;
    }
}
