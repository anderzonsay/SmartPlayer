package listas;

import modelos.Cancion;

public class ListaCircular {

    private NodoCircular ultimo;

    public ListaCircular() {
        ultimo = null;
    }

    public void insertar(Cancion cancion) {

        NodoCircular nuevo = new NodoCircular(cancion);

        if (ultimo == null) {

            ultimo = nuevo;
            ultimo.siguiente = ultimo;

        } else {

            nuevo.siguiente = ultimo.siguiente;
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
    }

    public void mostrar() {

        if (ultimo == null) {
            System.out.println("Lista circular vacia");
            return;
        }

        NodoCircular aux = ultimo.siguiente;

        do {
            System.out.println(aux.cancion);
            aux = aux.siguiente;

        } while (aux != ultimo.siguiente);
    }

    public void reproducirInfinito(int vueltas) {

        if (ultimo == null) {
            System.out.println("No hay canciones para reproducir");
            return;
        }

        NodoCircular aux = ultimo.siguiente;

        for (int i = 0; i < vueltas; i++) {

            System.out.println("Reproduciendo:");
            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }
}

