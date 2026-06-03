package archivos;

import java.io.File;
import listas.ListaSimple;
import modelos.Cancion;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;

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

                String titulo = archivo.getName().replace(".mp3", "");
                String artista = "Desconocido";
                String album = "Desconocido";
                String genero = "Desconocido";
                int año = 0;
                double duracion = 0.0;

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
                    }

                } catch (Exception e) {

                    System.out.println("No se pudieron leer metadatos de: " + archivo.getName());
                    System.out.println("Se cargara usando nombre de archivo.");
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

                lista.insertar(cancion);
            }
        }
    }
}