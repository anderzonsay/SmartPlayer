package interfaz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private JLabel lblTitulo;
    private JLabel lblArtista;
    private JLabel lblAlbum;
    private JLabel lblGenero;
    private JLabel lblDuracion;

    private JButton btnAnterior;
    private JButton btnPlay;
    private JButton btnPausa;
    private JButton btnStop;
    private JButton btnSiguiente;

    public VentanaPrincipal() {

        setTitle("SmartPlayer");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        crearPanelIzquierdo();
        crearPanelCentral();
        crearPanelInferior();
    }

    private void crearPanelIzquierdo() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(new Color(24, 24, 24));

        JLabel titulo = new JLabel("  Canciones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JList<String> lista = new JList<>(new String[]{
            "Wordless Chorus",
            "Off The Record",
            "Outta My System"
        });

        lista.setBackground(new Color(30, 30, 30));
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font("Arial", Font.PLAIN, 20));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);

        add(panel, BorderLayout.WEST);
    }

    private void crearPanelCentral() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        PanelPortada portada = new PanelPortada();
        portada.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitulo = crearLabel("Wordless Chorus", 30, true);
        lblArtista = crearLabel("Artista: My Morning Jacket", 20, false);
        lblAlbum = crearLabel("Álbum: Red Rocks - Aug. 3, 2019", 20, false);
        lblGenero = crearLabel("Género: Desconocido", 20, false);
        lblDuracion = crearLabel("Duración: 04:33", 20, false);

        panel.add(Box.createVerticalStrut(25));
        panel.add(portada);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblArtista);
        panel.add(lblAlbum);
        panel.add(lblGenero);
        panel.add(lblDuracion);

        add(panel, BorderLayout.CENTER);
    }

    private JLabel crearLabel(String texto, int tamaño, boolean negrita) {

        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", negrita ? Font.BOLD : Font.PLAIN, tamaño));
        return label;
    }

    private void crearPanelInferior() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));

        btnAnterior = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnPausa = new JButton("⏸");
        btnStop = new JButton("⏹");
        btnSiguiente = new JButton("⏭");

        panel.add(btnAnterior);
        panel.add(btnPlay);
        panel.add(btnPausa);
        panel.add(btnStop);
        panel.add(btnSiguiente);

        add(panel, BorderLayout.SOUTH);
    }

    private class PanelPortada extends JPanel {

        private BufferedImage imagen;

        public PanelPortada() {

            setPreferredSize(new Dimension(280, 280));
            setMaximumSize(new Dimension(280, 280));
            setMinimumSize(new Dimension(280, 280));
            setBackground(new Color(18, 18, 18));

            cargarImagen();
        }

        private void cargarImagen() {

    try {

        URL url = getClass().getResource("/imagenes/portada_default.png");

        if (url != null) {

            imagen = ImageIO.read(url);

        } else {

            imagen = ImageIO.read(
                new File("src/main/resources/imagenes/portada_default.png")
            );
        }

        System.out.println("Portada cargada correctamente");

    } catch (Exception e) {

        System.out.println("No se pudo cargar la portada");
        e.printStackTrace();
    }
}

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (imagen != null) {

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR
                );

                g2.drawImage(imagen, 15, 15, 250, 250, null);

            } else {

                g.setColor(Color.WHITE);
                g.drawRect(15, 15, 250, 250);
                g.drawString("Sin portada", 100, 140);
            }
        }
    }
}


