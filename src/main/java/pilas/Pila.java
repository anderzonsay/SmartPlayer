
package pilas;

import modelos.Cancion;

public class Pila {
    
     // TOPE DE LA PILA
    private NodoPila cima;

    // CONSTRUCTOR
    public Pila() {

        cima = null;
    }

    // APILAR
    public void push(Cancion cancion) {

        NodoPila nuevo = new NodoPila(cancion);

        nuevo.siguiente = cima;

        cima = nuevo;
    }

    // DESAPILAR
    public Cancion pop() {

        if (cima == null) {

            return null;
        }

        Cancion cancion = cima.cancion;

        cima = cima.siguiente;

        return cancion;
    }

    // MOSTRAR PILA
    public void mostrar() {

        NodoPila aux = cima;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }
}
    

