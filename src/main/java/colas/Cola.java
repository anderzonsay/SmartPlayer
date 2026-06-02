
package colas;

import modelos.Cancion;

public class Cola {
    
       // FRENTE Y FINAL
    private NodoCola frente;
    private NodoCola fin;

    // CONSTRUCTOR
    public Cola() {

        frente = null;
        fin = null;
    }

    // ENCOLAR
    public void enqueue(Cancion cancion) {

        NodoCola nuevo = new NodoCola(cancion);

        // SI LA COLA ESTA VACIA
        if (frente == null) {

            frente = nuevo;
            fin = nuevo;

        } else {

            fin.siguiente = nuevo;

            fin = nuevo;
        }
    }

    // DESENCOLAR
    public Cancion dequeue() {

        if (frente == null) {

            return null;
        }

        Cancion cancion = frente.cancion;

        frente = frente.siguiente;

        // SI LA COLA QUEDA VACIA
        if (frente == null) {

            fin = null;
        }

        return cancion;
    }

    // MOSTRAR COLA
    public void mostrar() {

        NodoCola aux = frente;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }
}
    

