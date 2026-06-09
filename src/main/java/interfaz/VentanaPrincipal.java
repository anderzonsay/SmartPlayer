package interfaz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

import modelos.Cancion;
import reproductor.ReproductorMP3;
import hash.TablaHashArtistas;
import hash.TablaHashGeneros;
import estadisticas.EstadisticasMusicales;
import playlist.PlaylistFavoritos;

public class VentanaPrincipal extends JFrame {

    private JLabel lblTitulo, lblArtista, lblAlbum, lblGenero, lblDuracion;
    private JLabel lblTiempoActual, lblTiempoTotal;
    private JSlider sliderProgreso;

    private JButton btnAnterior, btnPlay, btnPausa, btnStop, btnSiguiente;
    private JButton btnBuscar, btnLimpiar;
    private JButton btnFavorito, btnVerFavoritos, btnEliminarFavorito;
    private JButton btnExportar, btnImportar, btnComprimir, btnDescomprimir;
    private JButton btnVerABB, btnVerAVL, btnEstadisticas;

    private JTextField txtBuscar;
    private JComboBox<String> cmbTipoBusqueda;

    private JList<String> listaCanciones;
    private DefaultListModel<String> modeloLista;

    private ArrayList<Cancion> canciones;
    private ArrayList<Cancion> cancionesMostradas;
    private int indiceActual = 0;

    private TablaHashArtistas hashArtistas;
    private TablaHashGeneros hashGeneros;

    private ReproductorMP3 reproductor;
    private PlaylistFavoritos favoritos;
    private PanelPortada panelPortada;
    private Timer timerProgreso;
    private boolean moviendoSlider = false;

    public VentanaPrincipal(ArrayList<Cancion> canciones,
                            TablaHashArtistas hashArtistas,
                            TablaHashGeneros hashGeneros) {

        this.canciones = canciones;
        this.cancionesMostradas = new ArrayList<>(canciones);
        this.hashArtistas = hashArtistas;
        this.hashGeneros = hashGeneros;
        this.reproductor = new ReproductorMP3();
        this.favoritos = new PlaylistFavoritos();

        setTitle("SmartPlayer");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        crearPanelIzquierdo();
        crearPanelCentral();

        actualizarInformacion();
        conectarBotones();
        iniciarTimerProgreso();

        reproductor.setAccionAlTerminar(() -> {
            SwingUtilities.invokeLater(() -> reproducirSiguiente());
        });
    }

    private void crearPanelIzquierdo() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBackground(new Color(24, 24, 24));

        JLabel titulo = new JLabel("  Canciones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(new Color(24, 24, 24));
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));

        txtBuscar = new JTextField();
        txtBuscar.setMaximumSize(new Dimension(280, 30));

        cmbTipoBusqueda = new JComboBox<>(new String[]{"Canción", "Artista", "Género"});
        cmbTipoBusqueda.setMaximumSize(new Dimension(280, 30));

        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        JPanel panelBotonesBusqueda = new JPanel();
        panelBotonesBusqueda.setBackground(new Color(24, 24, 24));
        panelBotonesBusqueda.add(btnBuscar);
        panelBotonesBusqueda.add(btnLimpiar);

        panelBusqueda.add(Box.createVerticalStrut(8));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createVerticalStrut(5));
        panelBusqueda.add(cmbTipoBusqueda);
        panelBusqueda.add(panelBotonesBusqueda);

        modeloLista = new DefaultListModel<>();
        llenarModeloLista(cancionesMostradas);

        listaCanciones = new JList<>(modeloLista);
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

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(24, 24, 24));
        panelSuperior.add(titulo, BorderLayout.NORTH);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);

        panel.add(panelSuperior, BorderLayout.NORTH);
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

        panel.add(Box.createVerticalStrut(20));
        panel.add(panelPortada);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblArtista);
        panel.add(lblAlbum);
        panel.add(lblGenero);
        panel.add(lblDuracion);
        panel.add(Box.createVerticalStrut(20));
        panel.add(crearPanelProgreso());
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearPanelBotones());

        add(panel, BorderLayout.CENTER);
    }

    private JPanel crearPanelProgreso() {

        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(18, 18, 18));
        panel.setMaximumSize(new Dimension(520, 30));

        lblTiempoActual = new JLabel("00:00");
        lblTiempoActual.setForeground(Color.WHITE);
        lblTiempoActual.setFont(new Font("Consolas", Font.BOLD, 14));

        lblTiempoTotal = new JLabel("00:00");
        lblTiempoTotal.setForeground(Color.WHITE);
        lblTiempoTotal.setFont(new Font("Consolas", Font.BOLD, 14));

        sliderProgreso = new JSlider(0, 100, 0);
        sliderProgreso.setBackground(new Color(18, 18, 18));
        sliderProgreso.setForeground(new Color(30, 215, 96));
        sliderProgreso.setPaintTicks(false);
        sliderProgreso.setPaintLabels(false);
        sliderProgreso.setFocusable(false);
        sliderProgreso.setPreferredSize(new Dimension(390, 18));

        sliderProgreso.addChangeListener((ChangeEvent e) -> {

            if (sliderProgreso.getValueIsAdjusting()) {
                moviendoSlider = true;
            } else if (moviendoSlider) {

                double total = reproductor.getDuracionTotalSegundos();

                if (total > 0) {
                    double nuevoTiempo = (sliderProgreso.getValue() / 100.0) * total;
                    reproductor.moverA(nuevoTiempo);
                }

                moviendoSlider = false;
            }
        });

        panel.add(lblTiempoActual, BorderLayout.WEST);
        panel.add(sliderProgreso, BorderLayout.CENTER);
        panel.add(lblTiempoTotal, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelBotones() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(18, 18, 18));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setMaximumSize(new Dimension(900, 180));

        JPanel panelReproduccion = new JPanel();
        panelReproduccion.setBackground(new Color(18, 18, 18));

        btnAnterior = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnPausa = new JButton("⏸");
        btnStop = new JButton("⏹");
        btnSiguiente = new JButton("⏭");

        panelReproduccion.add(btnAnterior);
        panelReproduccion.add(btnPlay);
        panelReproduccion.add(btnPausa);
        panelReproduccion.add(btnStop);
        panelReproduccion.add(btnSiguiente);

        JPanel panelPlaylist = new JPanel();
        panelPlaylist.setBackground(new Color(18, 18, 18));

        btnFavorito = new JButton("⭐ Favorito");
        btnVerFavoritos = new JButton("❤️ Favoritos");
        btnEliminarFavorito = new JButton("❌ Quitar Favorito");

        panelPlaylist.add(btnFavorito);
        panelPlaylist.add(btnVerFavoritos);
        panelPlaylist.add(btnEliminarFavorito);

        JPanel panelSeguridad = new JPanel();
        panelSeguridad.setBackground(new Color(18, 18, 18));

        btnExportar = new JButton("🔒 Exportar");
        btnImportar = new JButton("🔓 Importar");
        btnComprimir = new JButton("📦 Comprimir");
        btnDescomprimir = new JButton("📂 Descomprimir");

        panelSeguridad.add(btnExportar);
        panelSeguridad.add(btnImportar);
        panelSeguridad.add(btnComprimir);
        panelSeguridad.add(btnDescomprimir);

        JPanel panelOpciones = new JPanel();
        panelOpciones.setBackground(new Color(18, 18, 18));

        btnVerABB = new JButton("🌳 ABB");
        btnVerAVL = new JButton("🌳 AVL");
        btnEstadisticas = new JButton("📊 Estadísticas");

        panelOpciones.add(btnVerABB);
        panelOpciones.add(btnVerAVL);
        panelOpciones.add(btnEstadisticas);

        panelPrincipal.add(panelReproduccion);
        panelPrincipal.add(panelPlaylist);
        panelPrincipal.add(panelSeguridad);
        panelPrincipal.add(panelOpciones);

        return panelPrincipal;
    }

    private JLabel crearLabel(String texto, int tamaño, boolean negrita) {

        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", negrita ? Font.BOLD : Font.PLAIN, tamaño));
        return label;
    }

    private void conectarBotones() {

        btnBuscar.addActionListener(e -> buscarCanciones());
        btnLimpiar.addActionListener(e -> limpiarBusqueda());

        btnPlay.addActionListener(e -> {

            if (!cancionesMostradas.isEmpty()) {

                if (reproductor.estaPausado()) {
                    reproductor.continuarReproduccion();
                } else {
                    Cancion actual = cancionesMostradas.get(indiceActual);
                    reproductor.reproducir(actual.getRuta());
                }
            }
        });

        btnPausa.addActionListener(e -> reproductor.pausar());

        btnStop.addActionListener(e -> {
            reproductor.detener();
            sliderProgreso.setValue(0);
            lblTiempoActual.setText("00:00");
        });

        btnSiguiente.addActionListener(e -> reproducirSiguiente());
        btnAnterior.addActionListener(e -> reproducirAnterior());

        btnFavorito.addActionListener(e -> agregarFavoritoActual());
        btnVerFavoritos.addActionListener(e -> mostrarFavoritos());
        btnEliminarFavorito.addActionListener(e -> eliminarFavoritoActual());

        btnExportar.addActionListener(e -> exportarFavoritosEncriptados());
        btnImportar.addActionListener(e -> importarFavoritosEncriptados());
        btnComprimir.addActionListener(e -> comprimirFavoritos());
        btnDescomprimir.addActionListener(e -> descomprimirFavoritos());

        btnVerABB.addActionListener(e -> abrirImagen("abb_real.png"));
        btnVerAVL.addActionListener(e -> abrirImagen("avl_real.png"));

        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
    }

    private void comprimirFavoritos() {

        favoritos.comprimirFavoritos();

        JOptionPane.showMessageDialog(
                this,
                "Favoritos comprimidos en:\nfavoritos.comp"
        );
    }

    private void descomprimirFavoritos() {

        favoritos.descomprimirFavoritos();

        JOptionPane.showMessageDialog(
                this,
                "Favoritos descomprimidos en:\nfavoritos_descomprimidos.txt"
        );
    }

    private void exportarFavoritosEncriptados() {

        favoritos.exportarFavoritosEncriptados();

        JOptionPane.showMessageDialog(
                this,
                "Favoritos exportados a:\nfavoritos_encriptados.txt"
        );
    }

    private void importarFavoritosEncriptados() {

        favoritos.importarFavoritosEncriptados();

        JOptionPane.showMessageDialog(
                this,
                "Favoritos importados correctamente"
        );
    }

    private void agregarFavoritoActual() {

        if (cancionesMostradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay canción seleccionada");
            return;
        }

        Cancion actual = cancionesMostradas.get(indiceActual);

        favoritos.agregarFavorito(actual);

        JOptionPane.showMessageDialog(
                this,
                "Agregada a favoritos:\n" + actual.getNombre()
        );
    }

    private void mostrarFavoritos() {

        ArrayList<Cancion> listaFav = favoritos.obtenerFavoritos(canciones);

        if (listaFav.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay favoritos guardados");
            return;
        }

        cancionesMostradas = listaFav;
        indiceActual = 0;

        llenarModeloLista(cancionesMostradas);
        actualizarInformacion();
    }

    private void eliminarFavoritoActual() {

        if (cancionesMostradas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay canción seleccionada"
            );

            return;
        }

        Cancion actual = cancionesMostradas.get(indiceActual);

        favoritos.eliminarFavorito(actual);

        JOptionPane.showMessageDialog(
                this,
                "Favorito eliminado:\n" + actual.getNombre()
        );

        ArrayList<Cancion> listaFav = favoritos.obtenerFavoritos(canciones);

        cancionesMostradas = listaFav;
        indiceActual = 0;

        llenarModeloLista(cancionesMostradas);
        actualizarInformacion();
    }

    private void mostrarEstadisticas() {

        if (canciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay canciones cargadas");
            return;
        }

        EstadisticasMusicales estadisticas = new EstadisticasMusicales(canciones);

        Cancion masLarga = estadisticas.cancionMasLarga();
        Cancion masCorta = estadisticas.cancionMasCorta();

        String mensaje =
                "ESTADÍSTICAS SMARTPLAYER\n\n" +
                "Canciones cargadas: " + estadisticas.totalCanciones() + "\n\n" +
                "Canción más larga:\n" +
                masLarga.getNombre() + " - " + masLarga.getDuracionFormateada() + "\n\n" +
                "Canción más corta:\n" +
                masCorta.getNombre() + " - " + masCorta.getDuracionFormateada() + "\n\n" +
                "Duración promedio:\n" +
                formatearTiempo(estadisticas.promedioDuracion() * 60) + "\n\n" +
                "Artistas diferentes: " + estadisticas.totalArtistas() + "\n" +
                "Géneros diferentes: " + estadisticas.totalGeneros();

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Estadísticas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void abrirImagen(String nombreArchivo) {

        try {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el archivo: " + nombreArchivo
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir la imagen: " + nombreArchivo
            );
        }
    }

    private void buscarCanciones() {

        String texto = txtBuscar.getText().trim();
        String tipo = cmbTipoBusqueda.getSelectedItem().toString();

        if (texto.isEmpty()) {
            limpiarBusqueda();
            return;
        }

        ArrayList<Cancion> resultados = new ArrayList<>();

        if (tipo.equals("Canción")) {

            for (Cancion c : canciones) {
                if (c.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    resultados.add(c);
                }
            }

        } else if (tipo.equals("Artista")) {

            resultados = hashArtistas.obtenerPorArtista(texto);

        } else if (tipo.equals("Género")) {

            resultados = hashGeneros.obtenerPorGenero(texto);
        }

        cancionesMostradas = resultados;
        indiceActual = 0;

        llenarModeloLista(cancionesMostradas);

        if (cancionesMostradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados");
        }

        actualizarInformacion();
    }

    private void limpiarBusqueda() {

        txtBuscar.setText("");
        cancionesMostradas = new ArrayList<>(canciones);
        indiceActual = 0;
        llenarModeloLista(cancionesMostradas);
        actualizarInformacion();
    }

    private void llenarModeloLista(ArrayList<Cancion> lista) {

        modeloLista.clear();

        for (Cancion c : lista) {
            modeloLista.addElement(c.getNombre());
        }
    }

    private void reproducirSiguiente() {

        if (!cancionesMostradas.isEmpty()) {

            indiceActual++;

            if (indiceActual >= cancionesMostradas.size()) {
                indiceActual = 0;
            }

            listaCanciones.setSelectedIndex(indiceActual);
            actualizarInformacion();

            Cancion actual = cancionesMostradas.get(indiceActual);
            reproductor.reproducir(actual.getRuta());
        }
    }

    private void reproducirAnterior() {

        if (!cancionesMostradas.isEmpty()) {

            indiceActual--;

            if (indiceActual < 0) {
                indiceActual = cancionesMostradas.size() - 1;
            }

            listaCanciones.setSelectedIndex(indiceActual);
            actualizarInformacion();

            Cancion actual = cancionesMostradas.get(indiceActual);
            reproductor.reproducir(actual.getRuta());
        }
    }

    private void actualizarInformacion() {

        if (cancionesMostradas.isEmpty()) {
            lblTitulo.setText("Sin canciones");
            lblArtista.setText("");
            lblAlbum.setText("");
            lblGenero.setText("");
            lblDuracion.setText("");
            lblTiempoActual.setText("00:00");
            lblTiempoTotal.setText("00:00");
            sliderProgreso.setValue(0);
            panelPortada.cargarPortada(null);
            return;
        }

        Cancion actual = cancionesMostradas.get(indiceActual);

        lblTitulo.setText(actual.getNombre());
        lblArtista.setText("Artista: " + actual.getArtista());
        lblAlbum.setText("Álbum: " + actual.getAlbum());
        lblGenero.setText("Género: " + actual.getGenero());
        lblDuracion.setText("Duración: " + actual.getDuracionFormateada());

        lblTiempoActual.setText("00:00");
        lblTiempoTotal.setText(actual.getDuracionFormateada());
        sliderProgreso.setValue(0);

        panelPortada.cargarPortada(actual.getRutaPortada());
    }

    private void iniciarTimerProgreso() {

        timerProgreso = new Timer(1000, e -> {

            if (moviendoSlider) {
                return;
            }

            double actual = reproductor.getTiempoActualSegundos();
            double total = reproductor.getDuracionTotalSegundos();

            if (total > 0) {

                int porcentaje = (int) ((actual / total) * 100);

                sliderProgreso.setValue(porcentaje);
                lblTiempoActual.setText(formatearTiempo(actual));
                lblTiempoTotal.setText(formatearTiempo(total));
            }
        });

        timerProgreso.start();
    }

    private String formatearTiempo(double segundosTotales) {

        int minutos = (int) segundosTotales / 60;
        int segundos = (int) segundosTotales % 60;

        return String.format("%02d:%02d", minutos, segundos);
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
}