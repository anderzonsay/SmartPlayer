package reproductor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ReproductorMP3 {

    private MediaPlayer player;
    private static boolean iniciado = false;
    private Runnable accionAlTerminar;
    private boolean pausado = false;

    public ReproductorMP3() {

        if (!iniciado) {
            new JFXPanel();
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

                player.setOnEndOfMedia(() -> {

                    if (accionAlTerminar != null) {
                        accionAlTerminar.run();
                    }
                });

                player.play();
                pausado = false;

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
                pausado = false;
                System.out.println("Reproduccion detenida");
            }
        });
    }

    public void pausar() {

        Platform.runLater(() -> {

            if (player != null) {
                player.pause();
                pausado = true;
                System.out.println("Reproduccion pausada");
            }
        });
    }

    public void continuarReproduccion() {

        Platform.runLater(() -> {

            if (player != null) {
                player.play();
                pausado = false;
                System.out.println("Reproduccion reanudada");
            }
        });
    }

    public double getTiempoActualSegundos() {

        if (player == null) {
            return 0;
        }

        return player.getCurrentTime().toSeconds();
    }

    public double getDuracionTotalSegundos() {

        if (player == null || player.getTotalDuration() == null) {
            return 0;
        }

        return player.getTotalDuration().toSeconds();
    }

    public boolean estaPausado() {
        return pausado;
    }

    public void setAccionAlTerminar(Runnable accionAlTerminar) {
        this.accionAlTerminar = accionAlTerminar;
    }
    
    public void moverA(double segundos) {

    Platform.runLater(() -> {

        if (player != null) {
            player.seek(javafx.util.Duration.seconds(segundos));
        }
    });
}
    
}