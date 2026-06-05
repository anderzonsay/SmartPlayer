package interfaz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import modelos.Cancion;
import reproductor.ReproductorMP3;

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

    private JList<String> listaCanciones;

    private ArrayList<Cancion> canciones;
    private int indiceActual = 0;
    private ReproductorMP3 reproductor;

    private PanelPortada panelPortada;

    public VentanaPrincipal(ArrayList<Cancion> canciones) {

        this.canciones = canciones;
        this.reproductor = new ReproductorMP3();

        setTitle("SmartPlayer");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        crearPanelIzquierdo();
        crearPanelCentral();
        crearPanelInferior();

        actualizarInformacion();
        conectarBotones();
        
        reproductor.setAccionAlTerminar(() -> {
    SwingUtilities.invokeLater(() -> reproducirSiguiente());
});
        
    }

    private void crearPanelIzquierdo() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(new Color(24, 24, 24));

        JLabel titulo = new JLabel("  Canciones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        DefaultListModel<String> modelo = new DefaultListModel<>();

        for (Cancion c : canciones) {
            modelo.addElement(c.getNombre());
        }

        listaCanciones = new JList<>(modelo);
        listaCanciones.setBackground(new Color(30, 30, 30));
        listaCanciones.setForeground(Color.WHITE);
        listaCanciones.setFont(new Font("Arial", Font.PLAIN, 20));

        listaCanciones.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int seleccion = listaCanciones.getSelectedIndex();

                if (seleccion >= 0) {
                    indiceActual = seleccion;
                    actualizarInformacion();
                }
            }
        });

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(listaCanciones), BorderLayout.CENTER);

        add(panel, BorderLayout.WEST);
    }

    private void crearPanelCentral() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panelPortada = new PanelPortada();
        panelPortada.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitulo = crearLabel("", 30, true);
        lblArtista = crearLabel("", 20, false);
        lblAlbum = crearLabel("", 20, false);
        lblGenero = crearLabel("", 20, false);
        lblDuracion = crearLabel("", 20, false);

        panel.add(Box.createVerticalStrut(25));
        panel.add(panelPortada);
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

    private void conectarBotones() {

        btnPlay.addActionListener(e -> {

            if (!canciones.isEmpty()) {
                Cancion actual = canciones.get(indiceActual);
                reproductor.reproducir(actual.getRuta());
            }
        });

        btnPausa.addActionListener(e -> reproductor.pausar());

        btnStop.addActionListener(e -> reproductor.detener());

        btnSiguiente.addActionListener(e -> {

            if (!canciones.isEmpty()) {

                indiceActual++;

                if (indiceActual >= canciones.size()) {
                    indiceActual = 0;
                }

                listaCanciones.setSelectedIndex(indiceActual);
                actualizarInformacion();

                Cancion actual = canciones.get(indiceActual);
                reproductor.reproducir(actual.getRuta());
            }
        });

        btnAnterior.addActionListener(e -> {

            if (!canciones.isEmpty()) {

                indiceActual--;

                if (indiceActual < 0) {
                    indiceActual = canciones.size() - 1;
                }

                listaCanciones.setSelectedIndex(indiceActual);
                actualizarInformacion();

                Cancion actual = canciones.get(indiceActual);
                reproductor.reproducir(actual.getRuta());
            }
        });
    }

    private void actualizarInformacion() {

        if (canciones.isEmpty()) {
            lblTitulo.setText("Sin canciones");
            lblArtista.setText("");
            lblAlbum.setText("");
            lblGenero.setText("");
            lblDuracion.setText("");
            panelPortada.cargarPortada(null);
            return;
        }

        Cancion actual = canciones.get(indiceActual);

        lblTitulo.setText(actual.getNombre());
        lblArtista.setText("Artista: " + actual.getArtista());
        lblAlbum.setText("Álbum: " + actual.getAlbum());
        lblGenero.setText("Género: " + actual.getGenero());
        lblDuracion.setText("Duración: " + actual.getDuracionFormateada());

        panelPortada.cargarPortada(actual.getRutaPortada());
    }

    private class PanelPortada extends JPanel {

        private BufferedImage imagen;

        public PanelPortada() {

            setPreferredSize(new Dimension(280, 280));
            setMaximumSize(new Dimension(280, 280));
            setMinimumSize(new Dimension(280, 280));
            setBackground(new Color(18, 18, 18));
        }

        public void cargarPortada(String rutaPortada) {

            try {

                imagen = null;

                if (rutaPortada != null && !rutaPortada.isEmpty()) {

                    File archivoPortada = new File(rutaPortada);

                    if (archivoPortada.exists()) {
                        imagen = ImageIO.read(archivoPortada);
                    }
                }

                if (imagen == null) {

                    URL url = getClass().getResource("/imagenes/portada_default.png");

                    if (url != null) {
                        imagen = ImageIO.read(url);
                    } else {
                        imagen = ImageIO.read(new File("src/main/resources/imagenes/portada_default.png"));
                    }
                }

                repaint();

            } catch (Exception e) {

                imagen = null;
                repaint();
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
    
    private void reproducirSiguiente() {

    if (!canciones.isEmpty()) {

        indiceActual++;

        if (indiceActual >= canciones.size()) {
            indiceActual = 0;
        }

        listaCanciones.setSelectedIndex(indiceActual);
        actualizarInformacion();

        Cancion actual = canciones.get(indiceActual);
        reproductor.reproducir(actual.getRuta());
    }
}
}




