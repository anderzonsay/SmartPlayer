
package colas;

import modelos.Cancion;

public class NodoCola {
   
     public Cancion cancion;

    public NodoCola siguiente;

    public NodoCola(Cancion cancion) {

        this.cancion = cancion;

        this.siguiente = null;
    }
}
    

