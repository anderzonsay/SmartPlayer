package listas;

import modelos.Cancion;

public class ListaDoble {

    private NodoDoble cabeza;
    private NodoDoble cola;
    private NodoDoble actual;

    public ListaDoble() {

        cabeza = null;
        cola = null;
        actual = null;
    }

    public void insertar(Cancion cancion) {

        NodoDoble nuevo = new NodoDoble(cancion);

        if (cabeza == null) {

            cabeza = nuevo;
            cola = nuevo;
            actual = nuevo;

        } else {

            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
    }

    public Cancion obtenerActual() {

        if (actual == null) {
            return null;
        }

        return actual.cancion;
    }

    public Cancion avanzar() {

        if (actual == null) {
            return null;
        }

        if (actual.siguiente != null) {
            actual = actual.siguiente;
        } else {
            actual = cabeza;
        }

        return actual.cancion;
    }

    public Cancion retroceder() {

        if (actual == null) {
            return null;
        }

        if (actual.anterior != null) {
            actual = actual.anterior;
        } else {
            actual = cola;
        }

        return actual.cancion;
    }

    public void posicionarEn(String nombre) {

        NodoDoble aux = cabeza;

        while (aux != null) {

            if (aux.cancion.getNombre().equalsIgnoreCase(nombre)) {
                actual = aux;
                return;
            }

            aux = aux.siguiente;
        }
    }

    public void reiniciar() {

        actual = cabeza;
    }

    public void mostrarAdelante() {

        NodoDoble aux = cabeza;

        while (aux != null) {
            System.out.println(aux.cancion);
            aux = aux.siguiente;
        }
    }

    public void mostrarAtras() {

        NodoDoble aux = cola;

        while (aux != null) {
            System.out.println(aux.cancion);
            aux = aux.anterior;
        }
    }

    public void eliminar(String nombre) {

        if (cabeza == null) {
            System.out.println("Lista doble vacía");
            return;
        }

        NodoDoble nodoEliminar = cabeza;

        while (nodoEliminar != null &&
                !nodoEliminar.cancion.getNombre().equalsIgnoreCase(nombre)) {

            nodoEliminar = nodoEliminar.siguiente;
        }

        if (nodoEliminar == null) {
            System.out.println("Canción no encontrada en lista doble");
            return;
        }

        if (nodoEliminar == actual) {

            if (nodoEliminar.siguiente != null) {
                actual = nodoEliminar.siguiente;
            } else if (nodoEliminar.anterior != null) {
                actual = nodoEliminar.anterior;
            } else {
                actual = null;
            }
        }

        if (nodoEliminar == cabeza && nodoEliminar == cola) {

            cabeza = null;
            cola = null;
            actual = null;

        } else if (nodoEliminar == cabeza) {

            cabeza = cabeza.siguiente;
            cabeza.anterior = null;

        } else if (nodoEliminar == cola) {

            cola = cola.anterior;
            cola.siguiente = null;

        } else {

            nodoEliminar.anterior.siguiente = nodoEliminar.siguiente;
            nodoEliminar.siguiente.anterior = nodoEliminar.anterior;
        }

        System.out.println("Canción eliminada de lista doble");
    }
}
