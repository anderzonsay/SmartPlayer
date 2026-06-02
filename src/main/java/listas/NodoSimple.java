
package listas;

import modelos.Cancion;

public class NodoSimple {
    
    // GUARDA LA CANCION
    public Cancion cancion;

    // APUNTA AL SIGUIENTE NODO
    public NodoSimple siguiente;

    // CONSTRUCTOR
    public NodoSimple(Cancion cancion) {

        this.cancion = cancion;

        this.siguiente = null;
    }
}
    

