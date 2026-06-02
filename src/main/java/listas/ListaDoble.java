package listas;

import modelos.Cancion;

public class ListaDoble {

    // INICIO Y FINAL
    private NodoDoble cabeza;
    private NodoDoble cola;

    // CONSTRUCTOR
    public ListaDoble() {

        cabeza = null;
        cola = null;
    }

    // INSERTAR
    public void insertar(Cancion cancion) {

        NodoDoble nuevo = new NodoDoble(cancion);

        // SI ESTA VACIA
        if (cabeza == null) {

            cabeza = nuevo;
            cola = nuevo;

        } else {

            cola.siguiente = nuevo;

            nuevo.anterior = cola;

            cola = nuevo;
        }
    }

    // MOSTRAR HACIA ADELANTE
    public void mostrarAdelante() {

        NodoDoble aux = cabeza;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }

    // MOSTRAR HACIA ATRAS
    public void mostrarAtras() {

        NodoDoble aux = cola;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.anterior;
        }
    }
}

