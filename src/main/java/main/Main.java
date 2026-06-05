
package main;

import listas.ListaSimple;
import modelos.Cancion;

public class Main {
    
     public static void main(String[] args) {

        // CREAR LISTA
        ListaSimple lista = new ListaSimple();

        // CREAR CANCIONES
        Cancion c1 = new Cancion(
                "Believer",
                "Imagine Dragons",
                "Evolve",
                "Rock",
                "C:/music/believer.mp3",
                3.5,
                5.2,
                2017
        );

        Cancion c2 = new Cancion(
                "Thunder",
                "Imagine Dragons",
                "Evolve",
                "Rock",
                "C:/music/thunder.mp3",
                2.8,
                4.8,
                2017
        );

        Cancion c3 = new Cancion(
                "Shape of You",
                "Ed Sheeran",
                "Divide",
                "Pop",
                "C:/music/shape.mp3",
                4.1,
                6.0,
                2018
        );

        // INSERTAR EN LISTA
        lista.insertar(c1);
        lista.insertar(c2);
        lista.insertar(c3);

        // MOSTRAR LISTA
        lista.mostrar();
        
        System.out.println("BUSQUEDA:");

Cancion encontrada = lista.buscar("Believer");

if (encontrada != null) {

    System.out.println(encontrada);

} else {

    System.out.println("Cancion no encontrada");
}
System.out.println("CANCIONES DE IMAGINE DRAGONS:");

int cantidad = lista.contarCancionesArtista("Imagine Dragons");

System.out.println(cantidad);

System.out.println("CANCION MAS LARGA:");

System.out.println(lista.cancionMasLarga());

System.out.println("CANCION MAS CORTA:");

System.out.println(lista.cancionMasCorta());

System.out.println("ELIMINAR THUNDER");

lista.eliminar("Thunder");

System.out.println("LISTA ACTUALIZADA:");

lista.mostrar();

System.out.println("========== PILA ==========");

pilas.Pila pila = new pilas.Pila();

pila.push(c1);
pila.push(c2);
pila.push(c3);

System.out.println("PILA ACTUAL:");

pila.mostrar();

System.out.println("POP:");

System.out.println(pila.pop());

System.out.println("PILA DESPUES DEL POP:");

pila.mostrar();

System.out.println("========== COLA ==========");

colas.Cola cola = new colas.Cola();

cola.enqueue(c1);
cola.enqueue(c2);
cola.enqueue(c3);

System.out.println("COLA ACTUAL:");

cola.mostrar();

System.out.println("DEQUEUE:");

System.out.println(cola.dequeue());

System.out.println("COLA DESPUES DEL DEQUEUE:");

cola.mostrar();

System.out.println("========== LISTA DOBLE ==========");

listas.ListaDoble doble = new listas.ListaDoble();

doble.insertar(c1);
doble.insertar(c2);
doble.insertar(c3);

System.out.println("RECORRIDO HACIA ADELANTE:");

doble.mostrarAdelante();

System.out.println("RECORRIDO HACIA ATRAS:");

doble.mostrarAtras();

System.out.println("========== ABB ==========");

arboles.ABB abb = new arboles.ABB();

abb.insertar(c1);
abb.insertar(c2);
abb.insertar(c3);

System.out.println("RECORRIDO INORDER:");

abb.inorder();

System.out.println("PREORDER ABB:");
abb.preorder();

System.out.println("POSTORDER ABB:");
abb.postorder();

System.out.println("========== AVL ==========");

arboles.AVL avl = new arboles.AVL();

avl.insertar(c1);
avl.insertar(c2);
avl.insertar(c3);

System.out.println("RECORRIDO INORDER AVL:");

avl.inorder();

System.out.println("PREORDER AVL:");
avl.preorder();

System.out.println("POSTORDER AVL:");
avl.postorder();

System.out.println("BUSQUEDA ABB:");

Cancion resultadoABB = abb.buscar("Shape of You");

if (resultadoABB != null) {

    System.out.println("Encontrada en ABB:");
    System.out.println(resultadoABB);

} else {
    
    System.out.println("No encontrada en ABB");
}

System.out.println("MODIFICAR EN ABB:");

boolean modificado = abb.modificar(
        "Believer",
        "Imagine Dragons",
        "Evolve",
        "Rock Alternativo",
        3.8,
        2017
);

if (modificado) {

    System.out.println("Cancion modificada correctamente:");
    System.out.println(abb.buscar("Believer"));

} else {

    System.out.println("No se encontro la cancion para modificar");
}

System.out.println("ELIMINAR EN ABB:");

abb.eliminar("Thunder");

System.out.println("ABB DESPUES DE ELIMINAR THUNDER:");

abb.inorder();

System.out.println("BUSQUEDA AVL:");

Cancion resultadoAVL = avl.buscar("Shape of You");

if (resultadoAVL != null) {

    System.out.println("Encontrada en AVL:");
    System.out.println(resultadoAVL);

} else {

    System.out.println("No encontrada en AVL");
}

System.out.println("MODIFICAR EN AVL:");

boolean modificadoAVL = avl.modificar(
        "Shape of You",
        "Ed Sheeran",
        "Divide",
        "Pop Latino",
        4.5,
        2018
);

if (modificadoAVL) {

    System.out.println("Cancion modificada correctamente en AVL:");
    System.out.println(avl.buscar("Shape of You"));

} else {

    System.out.println("No se encontro la cancion para modificar en AVL");
}

System.out.println("ELIMINAR EN AVL:");

avl.eliminar("Thunder");

System.out.println("AVL DESPUES DE ELIMINAR THUNDER:");

avl.inorder();

System.out.println("========== COMPARACION DE TIEMPOS ==========");

long inicioABB = System.nanoTime();

Cancion busquedaABB = abb.buscar("Shape of You");

long finABB = System.nanoTime();

long tiempoABB = finABB - inicioABB;


long inicioAVL = System.nanoTime();

Cancion busquedaAVL = avl.buscar("Shape of You");

long finAVL = System.nanoTime();

long tiempoAVL = finAVL - inicioAVL;


System.out.println("Tiempo busqueda ABB: " + tiempoABB + " nanosegundos");
System.out.println("Tiempo busqueda AVL: " + tiempoAVL + " nanosegundos");



System.out.println("========== LISTA CIRCULAR ==========");

listas.ListaCircular circular = new listas.ListaCircular();

circular.insertar(c1);
circular.insertar(c2);
circular.insertar(c3);

System.out.println("LISTA CIRCULAR:");

circular.mostrar();

System.out.println("REPRODUCCION INFINITA SIMULADA:");

circular.reproducirInfinito(6);


System.out.println("========== TABLA HASH ARTISTAS ==========");

hash.TablaHashArtistas hashArtistas = new hash.TablaHashArtistas(10);

hashArtistas.insertar(c1);
hashArtistas.insertar(c2);
hashArtistas.insertar(c3);

hashArtistas.mostrarTabla();

System.out.println("BUSCAR POR ARTISTA EN HASH:");

hashArtistas.buscarPorArtista("Imagine Dragons");

System.out.println("========== TABLA HASH GENEROS ==========");

hash.TablaHashGeneros hashGeneros = new hash.TablaHashGeneros(10);

hashGeneros.insertar(c1);
hashGeneros.insertar(c2);
hashGeneros.insertar(c3);

hashGeneros.mostrarTabla();

System.out.println("BUSCAR POR GENERO EN HASH:");

hashGeneros.buscarPorGenero("Rock");

System.out.println("========== CARGA DE MUSICA REAL ==========");

listas.ListaSimple listaReal = new listas.ListaSimple();

archivos.LectorMusica lector = new archivos.LectorMusica();

lector.cargarCanciones(
        "C:\\Users\\Aderson\\Desktop\\Musica",
        listaReal
);

System.out.println("CANCIONES CARGADAS DESDE CARPETA:");

listaReal.mostrar();

System.out.println("========== CARGA REAL EN ESTRUCTURAS ==========");

listas.ListaSimple listaReal2 = new listas.ListaSimple();
listas.ListaDoble dobleReal = new listas.ListaDoble();
listas.ListaCircular circularReal = new listas.ListaCircular();
pilas.Pila pilaReal = new pilas.Pila();
colas.Cola colaReal = new colas.Cola();
arboles.ABB abbReal = new arboles.ABB();
arboles.AVL avlReal = new arboles.AVL();
hash.TablaHashArtistas hashArtistasReal = new hash.TablaHashArtistas(20);
hash.TablaHashGeneros hashGenerosReal = new hash.TablaHashGeneros(20);

archivos.LectorMusica lector2 = new archivos.LectorMusica();

lector2.cargarCancionesEstructuras(
        "C:\\Users\\Aderson\\Desktop\\Musica",
        listaReal2,
        dobleReal,
        circularReal,
        pilaReal,
        colaReal,
        abbReal,
        avlReal,
        hashArtistasReal,
        hashGenerosReal
);

System.out.println("========== PRUEBA REPRODUCTOR ==========");

Cancion cancionPrueba = listaReal2.obtenerPrimera();

if (cancionPrueba != null) {

    try {

        reproductor.ReproductorMP3 reproductor = new reproductor.ReproductorMP3();

        reproductor.reproducir(cancionPrueba.getRuta());

        Thread.sleep(5000);

        reproductor.pausar();

        Thread.sleep(3000);

        reproductor.continuarReproduccion();

        Thread.sleep(5000);

        reproductor.detener();

    } catch (InterruptedException e) {

        System.out.println("Error en la pausa de la prueba del reproductor");
    }

} else {

    System.out.println("No hay canciones reales para reproducir");
}

System.out.println("LISTA REAL:");
listaReal2.mostrar();

System.out.println("LISTA DOBLE REAL HACIA ADELANTE:");
dobleReal.mostrarAdelante();

System.out.println("LISTA CIRCULAR REAL:");
circularReal.mostrar();

System.out.println("REPRODUCCION CIRCULAR REAL SIMULADA:");
circularReal.reproducirInfinito(6);

System.out.println("PILA REAL / HISTORIAL:");
pilaReal.mostrar();

System.out.println("COLA REAL / REPRODUCCION:");
colaReal.mostrar();

System.out.println("ABB REAL INORDER:");
abbReal.inorder();

abbReal.generarDot("abb_real.dot");

graphviz.GeneradorImagenGraphviz generador = new graphviz.GeneradorImagenGraphviz();

generador.generarImagen(
        "abb_real.dot",
        "abb_real.png"
);

System.out.println("AVL REAL INORDER:");
avlReal.inorder();

avlReal.generarDot("avl_real.dot");

generador.generarImagen(
        "avl_real.dot",
        "avl_real.png"
);

System.out.println("BUSQUEDA REAL EN ABB:");

Cancion cancionABBReal = abbReal.buscar("Off The Record");

if (cancionABBReal != null) {

    System.out.println("Cancion encontrada en ABB REAL:");
    System.out.println(cancionABBReal);

} else {

    System.out.println("Cancion no encontrada en ABB REAL");
}

System.out.println("BUSQUEDA REAL EN AVL:");

Cancion cancionAVLReal = avlReal.buscar("Off The Record");

if (cancionAVLReal != null) {

    System.out.println("Cancion encontrada en AVL REAL:");
    System.out.println(cancionAVLReal);

} else {

    System.out.println("Cancion no encontrada en AVL REAL");
}

System.out.println("HASH ARTISTAS REAL:");
hashArtistasReal.buscarPorArtista("My Morning Jacket");

    javax.swing.SwingUtilities.invokeLater(() -> {
    new interfaz.VentanaPrincipal(listaReal2.convertirAArrayList()).setVisible(true);
});

    }
}
    

