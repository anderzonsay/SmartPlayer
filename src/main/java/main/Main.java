package main;

public class Main {

    public static void main(String[] args) {

        listas.ListaSimple listaReal = new listas.ListaSimple();
        listas.ListaDoble dobleReal = new listas.ListaDoble();
        listas.ListaCircular circularReal = new listas.ListaCircular();
        pilas.Pila pilaReal = new pilas.Pila();
        colas.Cola colaReal = new colas.Cola();

        arboles.ABB abbReal = new arboles.ABB();
        arboles.AVL avlReal = new arboles.AVL();

        hash.TablaHashArtistas hashArtistasReal =
                new hash.TablaHashArtistas(20);

        hash.TablaHashGeneros hashGenerosReal =
                new hash.TablaHashGeneros(20);

        archivos.LectorMusica lector = new archivos.LectorMusica();

        lector.cargarCancionesEstructuras(
                "C:\\Users\\Aderson\\Desktop\\Musica",
                listaReal,
                dobleReal,
                circularReal,
                pilaReal,
                colaReal,
                abbReal,
                avlReal,
                hashArtistasReal,
                hashGenerosReal
        );

        abbReal.generarDot("abb_real.dot");
        avlReal.generarDot("avl_real.dot");

        graphviz.GeneradorImagenGraphviz generador =
                new graphviz.GeneradorImagenGraphviz();

        generador.generarImagen(
                "abb_real.dot",
                "abb_real.png"
        );

        generador.generarImagen(
                "avl_real.dot",
                "avl_real.png"
        );

        javax.swing.SwingUtilities.invokeLater(() -> {

            new interfaz.VentanaPrincipal(
                    listaReal.convertirAArrayList(),
                    hashArtistasReal,
                    hashGenerosReal
            ).setVisible(true);
        });
    }
}