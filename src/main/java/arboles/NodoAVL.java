package arboles;

import modelos.Cancion;

public class NodoAVL {

    public Cancion cancion;

    public NodoAVL izquierda;
    public NodoAVL derecha;

    public int altura;

    public NodoAVL(Cancion cancion) {

        this.cancion = cancion;

        this.izquierda = null;
        this.derecha = null;

        this.altura = 1;
    }
}

