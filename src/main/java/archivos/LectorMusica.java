package archivos;

import java.io.File;
import java.io.FileOutputStream;

import listas.ListaSimple;
import listas.ListaDoble;
import listas.ListaCircular;
import modelos.Cancion;
import arboles.ABB;
import arboles.AVL;
import hash.TablaHashArtistas;
import hash.TablaHashGeneros;
import pilas.Pila;
import colas.Cola;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.images.Artwork;

public class LectorMusica {

    public void cargarCanciones(String rutaCarpeta, ListaSimple lista) {
        File carpeta = new File(rutaCarpeta);
        cargarRecursivoLista(carpeta, lista);
    }

    public void cargarCancionesEstructuras(String rutaCarpeta,
                                           ListaSimple lista,
                                           ListaDoble doble,
                                           ListaCircular circular,
                                           Pila pila,
                                           Cola cola,
                                           ABB abb,
                                           AVL avl,
                                           TablaHashArtistas hashArtistas,
                                           TablaHashGeneros hashGeneros) {

        File carpeta = new File(rutaCarpeta);

        cargarRecursivoEstructuras(
                carpeta,
                lista,
                doble,
                circular,
                pila,
                cola,
                abb,
                avl,
                hashArtistas,
                hashGeneros
        );
    }

    private void cargarRecursivoLista(File archivo, ListaSimple lista) {

        if (archivo == null || !archivo.exists()) {
            return;
        }

        if (archivo.isDirectory()) {

            File[] archivos = archivo.listFiles();

            if (archivos == null) {
                return;
            }

            for (File f : archivos) {
                cargarRecursivoLista(f, lista);
            }

        } else if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".mp3")) {

            Cancion cancion = leerCancionDesdeArchivo(archivo);
            lista.insertar(cancion);
        }
    }

    private void cargarRecursivoEstructuras(File archivo,
                                            ListaSimple lista,
                                            ListaDoble doble,
                                            ListaCircular circular,
                                            Pila pila,
                                            Cola cola,
                                            ABB abb,
                                            AVL avl,
                                            TablaHashArtistas hashArtistas,
                                            TablaHashGeneros hashGeneros) {

        if (archivo == null || !archivo.exists()) {
            return;
        }

        if (archivo.isDirectory()) {

            File[] archivos = archivo.listFiles();

            if (archivos == null) {
                return;
            }

            for (File f : archivos) {
                cargarRecursivoEstructuras(
                        f,
                        lista,
                        doble,
                        circular,
                        pila,
                        cola,
                        abb,
                        avl,
                        hashArtistas,
                        hashGeneros
                );
            }

        } else if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".mp3")) {

            Cancion cancion = leerCancionDesdeArchivo(archivo);

            lista.insertar(cancion);
            doble.insertar(cancion);
            circular.insertar(cancion);
            pila.push(cancion);
            cola.enqueue(cancion);
            abb.insertar(cancion);
            avl.insertar(cancion);
            hashArtistas.insertar(cancion);
            hashGeneros.insertar(cancion);
        }
    }

    private Cancion leerCancionDesdeArchivo(File archivo) {

        String titulo = archivo.getName().replace(".mp3", "");
        String artista = "Desconocido";
        String album = "Desconocido";
        String genero = "Desconocido";
        int año = 0;
        double duracion = 0.0;
        String rutaPortada = null;

        try {

            AudioFile audioFile = AudioFileIO.read(archivo);
            Tag tag = audioFile.getTag();

            duracion = audioFile.getAudioHeader().getTrackLength() / 60.0;

            if (tag != null) {

                String metaTitulo = tag.getFirst(FieldKey.TITLE);
                String metaArtista = tag.getFirst(FieldKey.ARTIST);
                String metaAlbum = tag.getFirst(FieldKey.ALBUM);
                String metaGenero = tag.getFirst(FieldKey.GENRE);
                String metaAño = tag.getFirst(FieldKey.YEAR);

                if (metaTitulo != null && !metaTitulo.isEmpty()) {
                    titulo = metaTitulo;
                }

                if (metaArtista != null && !metaArtista.isEmpty()) {
                    artista = metaArtista;
                }

                if (metaAlbum != null && !metaAlbum.isEmpty()) {
                    album = metaAlbum;
                }

                if (metaGenero != null && !metaGenero.isEmpty()) {
                    genero = metaGenero;
                }

                if (metaAño != null && !metaAño.isEmpty()) {
                    try {
                        año = Integer.parseInt(metaAño);
                    } catch (NumberFormatException e) {
                        año = 0;
                    }
                }

                rutaPortada = extraerPortada(tag, titulo);
            }

        } catch (Exception e) {
            // Se carga con datos básicos si el MP3 no permite leer metadatos.
        }

        double tamañoMB = archivo.length() / (1024.0 * 1024.0);

        Cancion cancion = new Cancion(
                titulo,
                artista,
                album,
                genero,
                archivo.getAbsolutePath(),
                duracion,
                tamañoMB,
                año
        );

        cancion.setRutaPortada(rutaPortada);

        return cancion;
    }

    private String extraerPortada(Tag tag, String titulo) {

        try {

            Artwork artwork = tag.getFirstArtwork();

            if (artwork == null) {
                return null;
            }

            byte[] imagenBytes = artwork.getBinaryData();

            if (imagenBytes == null || imagenBytes.length == 0) {
                return null;
            }

            File carpetaPortadas = new File("portadas");

            if (!carpetaPortadas.exists()) {
                carpetaPortadas.mkdirs();
            }

            String nombreLimpio = titulo.replaceAll("[^a-zA-Z0-9]", "_");

            File archivoPortada = new File(
                    carpetaPortadas,
                    nombreLimpio + ".jpg"
            );

            FileOutputStream fos = new FileOutputStream(archivoPortada);
            fos.write(imagenBytes);
            fos.close();

            return archivoPortada.getAbsolutePath();

        } catch (Exception e) {
            return null;
        }
    }
}
