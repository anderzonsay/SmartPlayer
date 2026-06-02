package arboles;

import modelos.Cancion;

public class NodoABB {

    public Cancion cancion;

    public NodoABB izquierda;
    public NodoABB derecha;

    public NodoABB(Cancion cancion) {

        this.cancion = cancion;

        this.izquierda = null;
        this.derecha = null;
    }
}
