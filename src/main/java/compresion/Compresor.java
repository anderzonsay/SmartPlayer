package compresion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Compresor {

    public void comprimirArchivo(String archivoOrigen, String archivoDestino) {

        try {

            File archivo = new File(archivoOrigen);

            if (!archivo.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            FileWriter writer = new FileWriter(archivoDestino);

            String linea;

            while ((linea = reader.readLine()) != null) {

                if (!linea.trim().isEmpty()) {

                    String comprimido = comprimirLinea(linea);

                    writer.write(comprimido + "\n");
                }
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al comprimir archivo manualmente");
        }
    }

    public void descomprimirArchivo(String archivoOrigen, String archivoDestino) {

        try {

            File archivo = new File(archivoOrigen);

            if (!archivo.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            FileWriter writer = new FileWriter(archivoDestino);

            String linea;

            while ((linea = reader.readLine()) != null) {

                if (!linea.trim().isEmpty()) {

                    String descomprimido = descomprimirLinea(linea);

                    writer.write(descomprimido + "\n");
                }
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al descomprimir archivo manualmente");
        }
    }

    private String comprimirLinea(String texto) {

        if (texto == null || texto.isEmpty()) {
            return "";
        }

        String resultado = "";
        int contador = 1;

        for (int i = 1; i < texto.length(); i++) {

            if (texto.charAt(i) == texto.charAt(i - 1)) {
                contador++;
            } else {

                resultado += contador + "#" + texto.charAt(i - 1) + "|";
                contador = 1;
            }
        }

        resultado += contador + "#" + texto.charAt(texto.length() - 1);

        return resultado;
    }

    private String descomprimirLinea(String textoComprimido) {

        if (textoComprimido == null || textoComprimido.isEmpty()) {
            return "";
        }

        String resultado = "";

        String[] partes = textoComprimido.split("\\|");

        for (String parte : partes) {

            String[] datos = parte.split("#", 2);

            if (datos.length == 2) {

                int cantidad = Integer.parseInt(datos[0]);
                char caracter = datos[1].charAt(0);

                for (int i = 0; i < cantidad; i++) {
                    resultado += caracter;
                }
            }
        }

        return resultado;
    }
}
