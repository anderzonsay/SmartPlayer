package compresion;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compresor {

    public void comprimirArchivo(String archivoOrigen, String archivoDestino) {

        try {

            FileInputStream entrada = new FileInputStream(archivoOrigen);
            FileOutputStream salida = new FileOutputStream(archivoDestino);
            GZIPOutputStream gzip = new GZIPOutputStream(salida);

            byte[] buffer = new byte[1024];
            int cantidad;

            while ((cantidad = entrada.read(buffer)) != -1) {
                gzip.write(buffer, 0, cantidad);
            }

            gzip.close();
            salida.close();
            entrada.close();

            System.out.println("Archivo comprimido correctamente");

        } catch (IOException e) {

            System.out.println("Error al comprimir archivo");
        }
    }

    public void descomprimirArchivo(String archivoOrigen, String archivoDestino) {

        try {

            FileInputStream entrada = new FileInputStream(archivoOrigen);
            GZIPInputStream gzip = new GZIPInputStream(entrada);
            FileOutputStream salida = new FileOutputStream(archivoDestino);

            byte[] buffer = new byte[1024];
            int cantidad;

            while ((cantidad = gzip.read(buffer)) != -1) {
                salida.write(buffer, 0, cantidad);
            }

            salida.close();
            gzip.close();
            entrada.close();

            System.out.println("Archivo descomprimido correctamente");

        } catch (IOException e) {

            System.out.println("Error al descomprimir archivo");
        }
    }
}
