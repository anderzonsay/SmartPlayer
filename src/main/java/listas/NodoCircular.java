
package listas;

import modelos.Cancion;

public class NodoCircular {

    public Cancion cancion;

    public NodoCircular siguiente;

    public NodoCircular(Cancion cancion) {

        this.cancion = cancion;

        this.siguiente = null;
    }
}

