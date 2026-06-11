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
            System.out.println("Lista circular vacía");
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

    public void eliminar(String nombre) {

        if (ultimo == null) {
            System.out.println("Lista circular vacía");
            return;
        }

        NodoCircular actual = ultimo.siguiente;
        NodoCircular anterior = ultimo;

        do {

            if (actual.cancion.getNombre().equalsIgnoreCase(nombre)) {

                if (actual == ultimo && actual.siguiente == ultimo) {
                    ultimo = null;
                } else {
                    anterior.siguiente = actual.siguiente;

                    if (actual == ultimo) {
                        ultimo = anterior;
                    }
                }

                System.out.println("Canción eliminada de lista circular");
                return;
            }

            anterior = actual;
            actual = actual.siguiente;

        } while (actual != ultimo.siguiente);

        System.out.println("Canción no encontrada en lista circular");
    }
}
