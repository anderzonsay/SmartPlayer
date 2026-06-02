
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
System.out.println("BUSQUEDA AVL:");

Cancion resultadoAVL = avl.buscar("Shape of You");

if (resultadoAVL != null) {

    System.out.println("Encontrada en AVL:");
    System.out.println(resultadoAVL);

} else {

    System.out.println("No encontrada en AVL");
}

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
    }
}
    

