package reproductor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ReproductorMP3 {

    private MediaPlayer player;
    private static boolean iniciado = false;

    public ReproductorMP3() {

        if (!iniciado) {
            new JFXPanel(); // inicia JavaFX en aplicación de consola
            iniciado = true;
        }
    }

    public void reproducir(String rutaArchivo) {

        Platform.runLater(() -> {

            try {

                if (player != null) {
                    player.stop();
                }

                String ruta = new java.io.File(rutaArchivo).toURI().toString();

                Media media = new Media(ruta);
                player = new MediaPlayer(media);

                player.play();

                System.out.println("Reproduciendo: " + rutaArchivo);

            } catch (Exception e) {

                System.out.println("Error al reproducir: " + rutaArchivo);
                e.printStackTrace();
            }
        });
    }

    public void detener() {

        Platform.runLater(() -> {

            if (player != null) {
                player.stop();
                System.out.println("Reproduccion detenida");
            }
        });
    }
    
    public void pausar() {

    Platform.runLater(() -> {

        if (player != null) {

            player.pause();

            System.out.println("Reproduccion pausada");
        }
    });
}
    
   public void continuarReproduccion() {

    Platform.runLater(() -> {

        if (player != null) {

            player.play();

            System.out.println("Reproduccion reanudada");
        }
    });
}
    
}

