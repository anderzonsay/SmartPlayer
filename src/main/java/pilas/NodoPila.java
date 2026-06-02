
package pilas;

import modelos.Cancion;

public class NodoPila {
    
      public Cancion cancion;

    public NodoPila siguiente;

    public NodoPila(Cancion cancion) {

        this.cancion = cancion;

        this.siguiente = null;
    }
}
    

