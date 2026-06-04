
package listas;

import modelos.Cancion;

public class ListaSimple {
    
     // PRIMER NODO DE LA LISTA
    private NodoSimple cabeza;

    // CONSTRUCTOR
    public ListaSimple() {

        cabeza = null;
    }

    // INSERTAR CANCION
    public void insertar(Cancion cancion) {

        NodoSimple nuevo = new NodoSimple(cancion);

        // SI LA LISTA ESTA VACIA
        if (cabeza == null) {

            cabeza = nuevo;

        } else {

            NodoSimple aux = cabeza;

            // RECORRER HASTA EL FINAL
            while (aux.siguiente != null) {

                aux = aux.siguiente;
            }

            // INSERTAR AL FINAL
            aux.siguiente = nuevo;
        }
    }

    // MOSTRAR LISTA
    public void mostrar() {

        NodoSimple aux = cabeza;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }
    
    public Cancion buscar(String nombre) {

    NodoSimple aux = cabeza;

    while (aux != null) {

        if (aux.cancion.getNombre().equalsIgnoreCase(nombre)) {

            return aux.cancion;
        }

        aux = aux.siguiente;
    }

    return null;
}
    public int contarCancionesArtista(String artista) {

    NodoSimple aux = cabeza;

    int contador = 0;

    while (aux != null) {

        if (aux.cancion.getArtista().equalsIgnoreCase(artista)) {

            contador++;
        }

        aux = aux.siguiente;
    }

    return contador;
}
    public Cancion cancionMasLarga() {

    if (cabeza == null) {

        return null;
    }

    NodoSimple aux = cabeza;

    Cancion mayor = cabeza.cancion;

    while (aux != null) {

        if (aux.cancion.getDuracion() > mayor.getDuracion()) {

            mayor = aux.cancion;
        }

        aux = aux.siguiente;
    }

    return mayor;
}
   public Cancion cancionMasCorta() {

    if (cabeza == null) {

        return null;
    }

    NodoSimple aux = cabeza;

    Cancion menor = cabeza.cancion;

    while (aux != null) {

        if (aux.cancion.getDuracion() < menor.getDuracion()) {

            menor = aux.cancion;
        }

        aux = aux.siguiente;
    }

    return menor;
}
   public void eliminar(String nombre) {

    // SI LA LISTA ESTA VACIA
    if (cabeza == null) {

        System.out.println("Lista vacia");
        return;
    }

    // SI EL PRIMERO ES EL QUE SE ELIMINA
    if (cabeza.cancion.getNombre().equalsIgnoreCase(nombre)) {

        cabeza = cabeza.siguiente;

        System.out.println("Cancion eliminada");
        return;
    }

    NodoSimple actual = cabeza;
    NodoSimple anterior = null;

    while (actual != null &&
           !actual.cancion.getNombre().equalsIgnoreCase(nombre)) {

        anterior = actual;
        actual = actual.siguiente;
    }

    // SI NO EXISTE
    if (actual == null) {

        System.out.println("Cancion no encontrada");
        return;
    }

    // ELIMINAR NODO
    anterior.siguiente = actual.siguiente;

    System.out.println("Cancion eliminada");
}
   
   public Cancion obtenerPrimera() {

    if (cabeza == null) {
        return null;
    }

    return cabeza.cancion;
}
   
}
    

