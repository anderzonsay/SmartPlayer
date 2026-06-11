package playlist;

import java.io.*;
import java.util.ArrayList;
import modelos.Cancion;
import encriptacion.Encriptador;
import compresion.Compresor;

public class PlaylistFavoritos {

    private ArrayList<String> nombresFavoritos;
    private final String archivoFavoritos = "favoritos.txt";
    private final String archivoEncriptado = "favoritos_encriptados.txt";

    public PlaylistFavoritos() {

        nombresFavoritos = new ArrayList<>();
        cargarFavoritos();
    }

    public void agregarFavorito(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        String nombre = cancion.getNombre();

        if (!nombresFavoritos.contains(nombre)) {

            nombresFavoritos.add(nombre);
            guardarFavoritos();

            System.out.println("Cancion agregada a favoritos: " + nombre);

        } else {

            System.out.println("La cancion ya estaba en favoritos");
        }
    }

    public ArrayList<Cancion> obtenerFavoritos(ArrayList<Cancion> canciones) {

        ArrayList<Cancion> favoritos = new ArrayList<>();

        for (Cancion c : canciones) {

            if (nombresFavoritos.contains(c.getNombre())) {
                favoritos.add(c);
            }
        }

        return favoritos;
    }

    private void guardarFavoritos() {

        try {

            FileWriter writer = new FileWriter(archivoFavoritos);

            for (String nombre : nombresFavoritos) {
                writer.write(nombre + "\n");
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error al guardar favoritos");
        }
    }

    private void cargarFavoritos() {

        File archivo = new File(archivoFavoritos);

        if (!archivo.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(archivo));

            String linea;

            while ((linea = reader.readLine()) != null) {

                if (!linea.trim().isEmpty()) {
                    nombresFavoritos.add(linea.trim());
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Error al cargar favoritos");
        }
    }

    public void eliminarFavorito(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        nombresFavoritos.remove(cancion.getNombre());

        guardarFavoritos();

        System.out.println("Favorito eliminado: " + cancion.getNombre());
    }

    public void eliminarPlaylistCompleta() {

        nombresFavoritos.clear();
        guardarFavoritos();

        System.out.println("Playlist de favoritos eliminada completamente");
    }

    public void exportarFavoritosEncriptados(arboles.ABB abbReal) {

        try {

            Encriptador encriptador = new Encriptador();

            FileWriter writer = new FileWriter(archivoEncriptado);

            writer.write("===== FAVORITOS ENCRIPTADOS =====\n");

            for (String nombre : nombresFavoritos) {
                writer.write(encriptador.encriptar(nombre) + "\n");
            }

            writer.write("===== FIN FAVORITOS =====\n\n");

            writer.write("===== ABB INORDEN ENCRIPTADO =====\n");
            writer.write(encriptador.encriptar(abbReal.obtenerInOrdenTexto()));
            writer.write("\n===== FIN ABB INORDEN =====\n\n");

            writer.write("===== ABB PREORDEN ENCRIPTADO =====\n");
            writer.write(encriptador.encriptar(abbReal.obtenerPreOrdenTexto()));
            writer.write("\n===== FIN ABB PREORDEN =====\n\n");

            writer.write("===== ABB POSTORDEN ENCRIPTADO =====\n");
            writer.write(encriptador.encriptar(abbReal.obtenerPostOrdenTexto()));
            writer.write("\n===== FIN ABB POSTORDEN =====\n");

            writer.close();

            System.out.println("Favoritos y recorridos ABB exportados encriptados correctamente");

        } catch (IOException e) {

            System.out.println("Error al exportar favoritos y recorridos ABB encriptados");
        }
    }

    public void importarFavoritosEncriptados() {

        try {

            Encriptador encriptador = new Encriptador();

            File archivo = new File(archivoEncriptado);

            if (!archivo.exists()) {
                System.out.println("No existe " + archivoEncriptado);
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));

            String linea;
            boolean leyendoFavoritos = false;

            while ((linea = reader.readLine()) != null) {

                if (linea.equals("===== FAVORITOS ENCRIPTADOS =====")) {
                    leyendoFavoritos = true;
                    continue;
                }

                if (linea.equals("===== FIN FAVORITOS =====")) {
                    leyendoFavoritos = false;
                    break;
                }

                if (leyendoFavoritos && !linea.trim().isEmpty()) {

                    String desencriptado = encriptador.desencriptar(linea);

                    if (!nombresFavoritos.contains(desencriptado)) {
                        nombresFavoritos.add(desencriptado);
                    }
                }
            }

            reader.close();

            guardarFavoritos();

            System.out.println("Favoritos desencriptados e importados correctamente");

        } catch (IOException e) {

            System.out.println("Error al importar favoritos encriptados");
        }
    }

    public void comprimirFavoritos() {

        Compresor compresor = new Compresor();

        compresor.comprimirArchivo(
                archivoFavoritos,
                "favoritos.comp"
        );
    }

    public void descomprimirFavoritos() {

        Compresor compresor = new Compresor();

        compresor.descomprimirArchivo(
                "favoritos.comp",
                "favoritos_descomprimidos.txt"
        );
    }
}
