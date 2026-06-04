package graphviz;

import java.io.IOException;

public class GeneradorImagenGraphviz {

    public void generarImagen(String rutaDot, String rutaPng) {

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Program Files\\Graphviz\\bin\\dot.exe",
                    "-Tpng",
                    rutaDot,
                    "-o",
                    rutaPng
            );

            pb.start();

            System.out.println("Imagen PNG generada correctamente: " + rutaPng);

        } catch (IOException e) {

            System.out.println("Error al generar imagen con Graphviz");
        }
    }
}

