package archivos;

import java.io.File;
import listas.ListaSimple;
import modelos.Cancion;

public class LectorMusica {

    public void cargarCanciones(String rutaCarpeta, ListaSimple lista) {

        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La ruta no existe o no es una carpeta valida");
            return;
        }

        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            System.out.println("No se pudieron leer los archivos");
            return;
        }

        for (File archivo : archivos) {

            if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".mp3")) {

                String nombre = archivo.getName().replace(".mp3", "");

                double tamañoMB = archivo.length() / (1024.0 * 1024.0);

                Cancion cancion = new Cancion(
                        nombre,
                        "Desconocido",
                        "Desconocido",
                        "Desconocido",
                        archivo.getAbsolutePath(),
                        0.0,
                        tamañoMB,
                        0
                );

                lista.insertar(cancion);
            }
        }
    }
}

