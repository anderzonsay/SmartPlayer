package interfaz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

import modelos.Cancion;
import reproductor.ReproductorMP3;
import hash.TablaHashArtistas;
import hash.TablaHashGeneros;
import estadisticas.EstadisticasMusicales;
import playlist.PlaylistFavoritos;
import arboles.ABB;
import arboles.AVL;
import pilas.Pila;
import colas.Cola;
import listas.ListaSimple;
import listas.ListaDoble;
import listas.ListaCircular;

public class VentanaPrincipal extends JFrame {

    private JLabel lblTitulo, lblArtista, lblAlbum, lblGenero, lblDuracion;
    private JLabel lblTiempoActual, lblTiempoTotal;
    private JLabel lblComparacionABBAVL;
    private JSlider sliderProgreso;

    private JButton btnAnterior, btnPlay, btnPausa, btnStop, btnSiguiente;
    private JButton btnAleatorio, btnCircular;
    private JButton btnBuscar, btnLimpiar;
    private JButton btnFavorito, btnVerFavoritos, btnEliminarFavorito, btnEliminarPlaylist, btnEliminarCancion, btnModificarCancion;
    private JButton btnHistorial, btnAgregarCola, btnVerCola, btnReproducirCola;
    private JButton btnExportar, btnImportar, btnComprimir, btnDescomprimir;
    private JButton btnVerABB, btnVerAVL, btnRecorridos, btnEstadisticas, btnEficiencia;

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
    private Pila historialReproduccion;
    private Cola colaReproduccion;
    private ABB abbReal;
    private AVL avlReal;

    private ListaSimple listaReal;
    private ListaDoble dobleReal;
    private ListaCircular circularReal;

    private PanelPortada panelPortada;
    private Timer timerProgreso;
    private boolean moviendoSlider = false;
    private boolean modoCircular = false;
    private boolean modoAleatorio = false;

    private String ultimaBusqueda = "";
    private long ultimoTiempoBusquedaABB = 0;
    private long ultimoTiempoBusquedaAVL = 0;
    private long sumaBusquedaABB = 0;
private long sumaBusquedaAVL = 0;
private int cantidadBusquedasABB = 0;
private int cantidadBusquedasAVL = 0;
    private int ultimosResultados = 0;
    
    

    public VentanaPrincipal(ArrayList<Cancion> canciones,
                            TablaHashArtistas hashArtistas,
                            TablaHashGeneros hashGeneros,
                            Pila historialReproduccion,
                            Cola colaReproduccion,
                            ABB abbReal,
                            AVL avlReal,
                            ListaSimple listaReal,
                            ListaDoble dobleReal,
                            ListaCircular circularReal) {

        this.canciones = canciones;
        this.cancionesMostradas = new ArrayList<>(canciones);
        this.hashArtistas = hashArtistas;
        this.hashGeneros = hashGeneros;
        this.reproductor = new ReproductorMP3();
        this.favoritos = new PlaylistFavoritos();
        this.historialReproduccion = historialReproduccion;
        this.colaReproduccion = colaReproduccion;
        this.abbReal = abbReal;
        this.avlReal = avlReal;
        this.listaReal = listaReal;
        this.dobleReal = dobleReal;
        this.circularReal = circularReal;

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

        cmbTipoBusqueda = new JComboBox<>(
                new String[]{"Canción", "Artista", "Álbum", "Género"}
        );
        cmbTipoBusqueda.setMaximumSize(new Dimension(280, 30));

        lblComparacionABBAVL = new JLabel("ABB: 0 ns | AVL: 0 ns | Resultados: 0");
        lblComparacionABBAVL.setForeground(new Color(30, 215, 96));
        lblComparacionABBAVL.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblComparacionABBAVL.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        panelBusqueda.add(Box.createVerticalStrut(5));
        panelBusqueda.add(lblComparacionABBAVL);
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
        panelPrincipal.setMaximumSize(new Dimension(1000, 260));

        JPanel panelReproduccion = new JPanel();
        panelReproduccion.setBackground(new Color(18, 18, 18));

        btnAnterior = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnPausa = new JButton("⏸");
        btnStop = new JButton("⏹");
        btnSiguiente = new JButton("⏭");
        btnAleatorio = new JButton("🔀 Aleatorio OFF");
        btnCircular = new JButton("🔁 Circular OFF");

        panelReproduccion.add(btnAnterior);
        panelReproduccion.add(btnPlay);
        panelReproduccion.add(btnPausa);
        panelReproduccion.add(btnStop);
        panelReproduccion.add(btnSiguiente);
        panelReproduccion.add(btnAleatorio);
        panelReproduccion.add(btnCircular);

        JPanel panelPlaylist = new JPanel();
        panelPlaylist.setBackground(new Color(18, 18, 18));

        btnFavorito = new JButton("⭐ Favorito");
        btnVerFavoritos = new JButton("❤️ Favoritos");
        btnEliminarFavorito = new JButton("❌ Quitar");
        btnEliminarPlaylist = new JButton("🗑 Playlist");
        btnEliminarCancion = new JButton("🗑 Canción");
        btnModificarCancion = new JButton("✏ Modificar");
        btnHistorial = new JButton("📜 Historial");
        btnAgregarCola = new JButton("➕ Cola");
        btnVerCola = new JButton("📋 Ver Cola");
        btnReproducirCola = new JButton("▶ Cola");

        panelPlaylist.add(btnFavorito);
        panelPlaylist.add(btnVerFavoritos);
        panelPlaylist.add(btnEliminarFavorito);
        panelPlaylist.add(btnEliminarPlaylist);
        panelPlaylist.add(btnEliminarCancion);
        panelPlaylist.add(btnModificarCancion);
        panelPlaylist.add(btnHistorial);
        panelPlaylist.add(btnAgregarCola);
        panelPlaylist.add(btnVerCola);
        panelPlaylist.add(btnReproducirCola);

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
        btnRecorridos = new JButton("📄 Recorridos");
        btnEstadisticas = new JButton("📊 Estadísticas");
        btnEficiencia = new JButton("⏱ Eficiencia");

        panelOpciones.add(btnVerABB);
        panelOpciones.add(btnVerAVL);
        panelOpciones.add(btnRecorridos);
        panelOpciones.add(btnEstadisticas);
        panelOpciones.add(btnEficiencia);

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
                    registrarReproduccion(actual);
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
        btnAleatorio.addActionListener(e -> activarDesactivarAleatorio());
        btnCircular.addActionListener(e -> activarDesactivarCircular());

        btnFavorito.addActionListener(e -> agregarFavoritoActual());
        btnVerFavoritos.addActionListener(e -> mostrarFavoritos());
        btnEliminarFavorito.addActionListener(e -> eliminarFavoritoActual());
        btnEliminarPlaylist.addActionListener(e -> eliminarPlaylistCompleta());
        btnEliminarCancion.addActionListener(e -> eliminarCancionGlobal());
        btnModificarCancion.addActionListener(e -> modificarCancionActual());

        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnAgregarCola.addActionListener(e -> agregarActualACola());
        btnVerCola.addActionListener(e -> mostrarCola());
        btnReproducirCola.addActionListener(e -> reproducirDesdeCola());

        btnExportar.addActionListener(e -> exportarFavoritosEncriptados());
        btnImportar.addActionListener(e -> importarFavoritosEncriptados());
        btnComprimir.addActionListener(e -> comprimirFavoritos());
        btnDescomprimir.addActionListener(e -> descomprimirFavoritos());

        btnVerABB.addActionListener(e -> abrirImagen("abb_real.png"));
        btnVerAVL.addActionListener(e -> abrirImagen("avl_real.png"));
        btnRecorridos.addActionListener(e -> mostrarRecorridosArboles());

        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnEficiencia.addActionListener(e -> mostrarEficiencia());
    }

    private void activarDesactivarAleatorio() {

        modoAleatorio = !modoAleatorio;

        if (modoAleatorio) {

            btnAleatorio.setText("🔀 Aleatorio ON");

            JOptionPane.showMessageDialog(
                    this,
                    "Modo aleatorio activado"
            );

        } else {

            btnAleatorio.setText("🔀 Aleatorio OFF");

            JOptionPane.showMessageDialog(
                    this,
                    "Modo aleatorio desactivado"
            );
        }
    }
    
    private void activarDesactivarCircular() {

        modoCircular = !modoCircular;

        if (modoCircular) {
            btnCircular.setText("🔁 Circular ON");

            JOptionPane.showMessageDialog(
                    this,
                    "Modo circular activado.\nCuando llegue al final, volverá al inicio."
            );

        } else {
            btnCircular.setText("🔁 Circular OFF");

            JOptionPane.showMessageDialog(
                    this,
                    "Modo circular desactivado."
            );
        }
    }

    private void eliminarPlaylistCompleta() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar toda la playlist de favoritos?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            favoritos.eliminarPlaylistCompleta();

            cancionesMostradas = new ArrayList<>(canciones);
            indiceActual = 0;

            llenarModeloLista(cancionesMostradas);
            actualizarInformacion();

            JOptionPane.showMessageDialog(
                    this,
                    "Playlist de favoritos eliminada completamente."
            );
        }
    }

    private void eliminarCancionGlobal() {

        if (cancionesMostradas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay canción seleccionada"
            );
            return;
        }

        Cancion actual = cancionesMostradas.get(indiceActual);
        String nombre = actual.getNombre();

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar completamente esta canción?\n\n" + nombre,
                "Eliminar canción global",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        reproductor.detener();

        listaReal.eliminar(nombre);
        dobleReal.eliminar(nombre);
        circularReal.eliminar(nombre);

        abbReal.eliminar(nombre);
        avlReal.eliminar(nombre);

        hashArtistas.eliminar(nombre);
        hashGeneros.eliminar(nombre);

        favoritos.eliminarFavorito(actual);

        canciones.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
        cancionesMostradas.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));

        if (indiceActual >= cancionesMostradas.size()) {
            indiceActual = 0;
        }

        llenarModeloLista(cancionesMostradas);

        try {

            abbReal.generarDot("abb_real.dot");
            avlReal.generarDot("avl_real.dot");

            graphviz.GeneradorImagenGraphviz generador =
                    new graphviz.GeneradorImagenGraphviz();

            generador.generarImagen("abb_real.dot", "abb_real.png");
            generador.generarImagen("avl_real.dot", "avl_real.png");

        } catch (Exception e) {

            System.out.println("No se pudieron regenerar las imágenes de los árboles");
        }

        limpiarBusqueda();
        actualizarInformacion();

        JOptionPane.showMessageDialog(
                this,
                "Canción eliminada de todas las estructuras:\n" + nombre
        );
    }
    
    private void modificarCancionActual() {

    if (cancionesMostradas.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay canción seleccionada");
        return;
    }

    Cancion actual = cancionesMostradas.get(indiceActual);

    JTextField txtArtista = new JTextField(actual.getArtista());
    JTextField txtAlbum = new JTextField(actual.getAlbum());
    JTextField txtGenero = new JTextField(actual.getGenero());
    JTextField txtDuracion = new JTextField(String.valueOf(actual.getDuracion()));
    JTextField txtAño = new JTextField(String.valueOf(actual.getAño()));

    JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

    panel.add(new JLabel("Artista:"));
    panel.add(txtArtista);

    panel.add(new JLabel("Álbum:"));
    panel.add(txtAlbum);

    panel.add(new JLabel("Género:"));
    panel.add(txtGenero);

    panel.add(new JLabel("Duración (minutos):"));
    panel.add(txtDuracion);

    panel.add(new JLabel("Año:"));
    panel.add(txtAño);

    int opcion = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Modificar canción: " + actual.getNombre(),
            JOptionPane.OK_CANCEL_OPTION
    );

    if (opcion != JOptionPane.OK_OPTION) {
        return;
    }

    try {

        String nuevoArtista = txtArtista.getText().trim();
        String nuevoAlbum = txtAlbum.getText().trim();
        String nuevoGenero = txtGenero.getText().trim();
        double nuevaDuracion = Double.parseDouble(txtDuracion.getText().trim());
        int nuevoAño = Integer.parseInt(txtAño.getText().trim());

        actual.setArtista(nuevoArtista);
        actual.setAlbum(nuevoAlbum);
        actual.setGenero(nuevoGenero);
        actual.setDuracion(nuevaDuracion);
        actual.setAño(nuevoAño);

        abbReal.modificar(
                actual.getNombre(),
                nuevoArtista,
                nuevoAlbum,
                nuevoGenero,
                nuevaDuracion,
                nuevoAño
        );

        avlReal.modificar(
                actual.getNombre(),
                nuevoArtista,
                nuevoAlbum,
                nuevoGenero,
                nuevaDuracion,
                nuevoAño
        );

        actualizarInformacion();

        JOptionPane.showMessageDialog(
                this,
                "Canción modificada correctamente"
        );

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this,
                "Error: duración y año deben ser números válidos"
        );
    }
}
    

    private void mostrarHistorial() {

        JOptionPane.showMessageDialog(
                this,
                historialReproduccion.obtenerHistorialTexto(),
                "Historial de Reproducción - Pila",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void agregarActualACola() {

        if (cancionesMostradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay canción seleccionada");
            return;
        }

        Cancion actual = cancionesMostradas.get(indiceActual);

        colaReproduccion.enqueue(actual);

        JOptionPane.showMessageDialog(
                this,
                "Agregada a la cola:\n" + actual.getNombre()
        );
    }

    private void mostrarCola() {

        JOptionPane.showMessageDialog(
                this,
                colaReproduccion.obtenerColaTexto(),
                "Cola de Reproducción",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void reproducirDesdeCola() {

        Cancion siguiente = colaReproduccion.dequeue();

        if (siguiente == null) {
            JOptionPane.showMessageDialog(this, "La cola está vacía");
            return;
        }

        reproductor.reproducir(siguiente.getRuta());
        registrarReproduccion(siguiente);
        mostrarCancionEnPantalla(siguiente);
    }

    private void registrarReproduccion(Cancion cancion) {

        if (cancion != null) {
            cancion.aumentarReproduccion();
            historialReproduccion.push(cancion);
        }
    }

    private void mostrarCancionEnPantalla(Cancion cancion) {

        lblTitulo.setText(cancion.getNombre());
        lblArtista.setText("Artista: " + cancion.getArtista());
        lblAlbum.setText("Álbum: " + cancion.getAlbum());
        lblGenero.setText("Género: " + cancion.getGenero());
        lblDuracion.setText("Duración: " + cancion.getDuracionFormateada());

        lblTiempoActual.setText("00:00");
        lblTiempoTotal.setText(cancion.getDuracionFormateada());
        sliderProgreso.setValue(0);

        panelPortada.cargarPortada(cancion.getRutaPortada());
    }

    private void actualizarComparacionBusqueda(String texto) {

        long inicioABB = System.nanoTime();
        abbReal.buscarParcial(texto);
        long finABB = System.nanoTime();

        long inicioAVL = System.nanoTime();
        avlReal.buscarParcial(texto);
        long finAVL = System.nanoTime();

        ultimoTiempoBusquedaABB = finABB - inicioABB;
        ultimoTiempoBusquedaAVL = finAVL - inicioAVL;
        sumaBusquedaABB += ultimoTiempoBusquedaABB;
sumaBusquedaAVL += ultimoTiempoBusquedaAVL;

cantidadBusquedasABB++;
cantidadBusquedasAVL++;
        ultimosResultados = cancionesMostradas.size();

        lblComparacionABBAVL.setText(
                "ABB: " + ultimoTiempoBusquedaABB +
                " ns | AVL: " + ultimoTiempoBusquedaAVL +
                " ns | Resultados: " + ultimosResultados
        );
    }

    private void mostrarEficiencia() {

        if (canciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay canciones cargadas");
            return;
        }

        String nombre = ultimaBusqueda;

        if (nombre == null || nombre.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Primero realiza una búsqueda normal."
            );

            return;
        }

        ABB abbPrueba = new ABB();
        AVL avlPrueba = new AVL();

        long inicioCargaABB = System.nanoTime();

        for (Cancion c : canciones) {
            abbPrueba.insertar(c);
        }

        long finCargaABB = System.nanoTime();

        long inicioCargaAVL = System.nanoTime();

        for (Cancion c : canciones) {
            avlPrueba.insertar(c);
        }

        long finCargaAVL = System.nanoTime();

        String ganadorCarga;

        if ((finCargaABB - inicioCargaABB) < (finCargaAVL - inicioCargaAVL)) {
            ganadorCarga = "ABB fue más rápido cargando.";
        } else if ((finCargaAVL - inicioCargaAVL) < (finCargaABB - inicioCargaABB)) {
            ganadorCarga = "AVL fue más rápido cargando.";
        } else {
            ganadorCarga = "Ambos tuvieron el mismo tiempo de carga.";
        }

        String ganadorBusqueda;

        if (ultimoTiempoBusquedaABB < ultimoTiempoBusquedaAVL) {
            ganadorBusqueda = "ABB fue más rápido en búsqueda.";
        } else if (ultimoTiempoBusquedaAVL < ultimoTiempoBusquedaABB) {
            ganadorBusqueda = "AVL fue más rápido en búsqueda.";
        } else {
            ganadorBusqueda = "Ambos tuvieron el mismo tiempo de búsqueda.";
        }
        
        double promedioABB = 0;
double promedioAVL = 0;

if (cantidadBusquedasABB > 0) {
    promedioABB =
            (double) sumaBusquedaABB /
            cantidadBusquedasABB;
}

if (cantidadBusquedasAVL > 0) {
    promedioAVL =
            (double) sumaBusquedaAVL /
            cantidadBusquedasAVL;
}

        String mensaje =
                "COMPARACIÓN ABB VS AVL\n\n" +

                "Búsqueda realizada:\n" +
                nombre + "\n\n" +

                "Resultados encontrados: " + ultimosResultados + "\n\n" +

                "TIEMPO DE CARGA\n" +
                "ABB: " + (finCargaABB - inicioCargaABB) + " ns\n" +
                "AVL: " + (finCargaAVL - inicioCargaAVL) + " ns\n" +
                ganadorCarga + "\n\n" +

                "TIEMPO DE BÚSQUEDA PARCIAL\n" +
"ABB: " + ultimoTiempoBusquedaABB + " ns\n" +
"AVL: " + ultimoTiempoBusquedaAVL + " ns\n\n" +

"PROMEDIO DE BÚSQUEDA\n" +
"ABB: " + String.format("%.2f", promedioABB) + " ns\n" +
"AVL: " + String.format("%.2f", promedioAVL) + " ns\n\n" +

ganadorBusqueda;

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Eficiencia ABB / AVL",
                JOptionPane.INFORMATION_MESSAGE
        );
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

    private void mostrarRecorridosArboles() {

        String texto =
                abbReal.obtenerRecorridosTexto() +
                "\n\n============================\n\n" +
                avlReal.obtenerRecorridosTexto();

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Recorridos de Árboles",
                JOptionPane.INFORMATION_MESSAGE
        );
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

                "Duración total:\n" +
                formatearTiempo(estadisticas.duracionTotal() * 60) + "\n\n" +

                "Tamaño total:\n" +
                String.format("%.2f MB", estadisticas.tamañoTotalMB()) + "\n\n" +

                "Artistas diferentes: " + estadisticas.totalArtistas() + "\n" +
                "Géneros diferentes: " + estadisticas.totalGeneros() + "\n\n" +

                "Artista con más canciones:\n" +
                estadisticas.artistaConMasCanciones() + "\n\n" +

                "Canción más reproducida:\n" +
                estadisticas.cancionMasReproducida() + "\n\n" +

                "Artista más escuchado:\n" +
                estadisticas.artistaMasEscuchado() + "\n\n" +

                "Álbum con más canciones:\n" +
                estadisticas.albumConMasCanciones() + "\n\n" +

                "Género más frecuente:\n" +
                estadisticas.generoMasFrecuente() + "\n\n" +

                "Archivos duplicados:\n" +
                estadisticas.cantidadDuplicados() + "\n\n" +

                "Detalle duplicados:\n" +
                estadisticas.listarDuplicados();

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

        ultimaBusqueda = texto;

        if (texto.isEmpty()) {
            limpiarBusqueda();
            return;
        }

        ArrayList<Cancion> resultados = new ArrayList<>();

        if (tipo.equals("Canción")) {

            ArrayList<Cancion> resultadosABB = abbReal.buscarParcial(texto);
            ArrayList<Cancion> resultadosAVL = avlReal.buscarParcial(texto);

            for (Cancion c : resultadosABB) {
                resultados.add(c);
            }

            for (Cancion c : resultadosAVL) {

                boolean existe = false;

                for (Cancion agregada : resultados) {

                    if (agregada.getNombre().equalsIgnoreCase(c.getNombre())) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    resultados.add(c);
                }
            }

        } else if (tipo.equals("Artista")) {

            resultados = hashArtistas.obtenerPorArtista(texto);

        } else if (tipo.equals("Álbum")) {

            for (Cancion c : canciones) {

                if (c.getAlbum().toLowerCase().contains(texto.toLowerCase())) {

                    resultados.add(c);
                }
            }

        } else if (tipo.equals("Género")) {

            resultados = hashGeneros.obtenerPorGenero(texto);
        }

        cancionesMostradas = resultados;
        indiceActual = 0;

        llenarModeloLista(cancionesMostradas);

        actualizarComparacionBusqueda(texto);

        if (cancionesMostradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados");
        }

        actualizarInformacion();
    }

    private void limpiarBusqueda() {

        txtBuscar.setText("");
        ultimaBusqueda = "";
        cancionesMostradas = new ArrayList<>(canciones);
        indiceActual = 0;
        llenarModeloLista(cancionesMostradas);

        ultimoTiempoBusquedaABB = 0;
        ultimoTiempoBusquedaAVL = 0;
        ultimosResultados = 0;

        if (lblComparacionABBAVL != null) {
            lblComparacionABBAVL.setText("ABB: 0 ns | AVL: 0 ns | Resultados: 0");
        }

        actualizarInformacion();
    }

    private void llenarModeloLista(ArrayList<Cancion> lista) {

        modeloLista.clear();

        for (Cancion c : lista) {
            modeloLista.addElement(c.getNombre());
        }
    }

   private void reproducirSiguiente() {

    if (cancionesMostradas.isEmpty()) {
        return;
    }

    if (modoAleatorio) {

        Random random = new Random();

        indiceActual = random.nextInt(cancionesMostradas.size());

        listaCanciones.setSelectedIndex(indiceActual);
        actualizarInformacion();

        Cancion actual = cancionesMostradas.get(indiceActual);
        dobleReal.posicionarEn(actual.getNombre());

        reproductor.reproducir(actual.getRuta());
        registrarReproduccion(actual);

        return;
    }

    Cancion actualListaDoble = cancionesMostradas.get(indiceActual);
    dobleReal.posicionarEn(actualListaDoble.getNombre());

    Cancion siguiente = dobleReal.avanzar();

    if (siguiente == null) {
        return;
    }

    for (int i = 0; i < cancionesMostradas.size(); i++) {

        if (cancionesMostradas.get(i).getNombre().equalsIgnoreCase(siguiente.getNombre())) {
            indiceActual = i;
            break;
        }
    }

    if (!modoCircular && indiceActual == 0) {
        reproductor.detener();
        sliderProgreso.setValue(0);
        lblTiempoActual.setText("00:00");
        return;
    }

    listaCanciones.setSelectedIndex(indiceActual);
    actualizarInformacion();

    reproductor.reproducir(siguiente.getRuta());
    registrarReproduccion(siguiente);
}

    private void reproducirAnterior() {

    if (cancionesMostradas.isEmpty()) {
        return;
    }

    if (modoAleatorio) {

        Random random = new Random();

        indiceActual = random.nextInt(cancionesMostradas.size());

        listaCanciones.setSelectedIndex(indiceActual);
        actualizarInformacion();

        Cancion actual = cancionesMostradas.get(indiceActual);
        dobleReal.posicionarEn(actual.getNombre());

        reproductor.reproducir(actual.getRuta());
        registrarReproduccion(actual);

        return;
    }

    Cancion actualListaDoble = cancionesMostradas.get(indiceActual);
    dobleReal.posicionarEn(actualListaDoble.getNombre());

    Cancion anterior = dobleReal.retroceder();

    if (anterior == null) {
        return;
    }

    for (int i = 0; i < cancionesMostradas.size(); i++) {

        if (cancionesMostradas.get(i).getNombre().equalsIgnoreCase(anterior.getNombre())) {
            indiceActual = i;
            break;
        }
    }

    if (!modoCircular && indiceActual == cancionesMostradas.size() - 1) {
        indiceActual = 0;
        listaCanciones.setSelectedIndex(indiceActual);
        actualizarInformacion();
        return;
    }

    listaCanciones.setSelectedIndex(indiceActual);
    actualizarInformacion();

    reproductor.reproducir(anterior.getRuta());
    registrarReproduccion(anterior);
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
