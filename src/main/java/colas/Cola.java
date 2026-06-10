
package colas;

import modelos.Cancion;

public class Cola {

    private NodoCola frente;
    private NodoCola fin;

    public Cola() {

        frente = null;
        fin = null;
    }

    public void enqueue(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        NodoCola nuevo = new NodoCola(cancion);

        if (frente == null) {

            frente = nuevo;
            fin = nuevo;

        } else {

            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }

    public Cancion dequeue() {

        if (frente == null) {
            return null;
        }

        Cancion cancion = frente.cancion;

        frente = frente.siguiente;

        if (frente == null) {
            fin = null;
        }

        return cancion;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public String obtenerColaTexto() {

        if (frente == null) {
            return "La cola está vacía.";
        }

        String texto = "";
        NodoCola aux = frente;
        int posicion = 1;

        while (aux != null) {

            texto += posicion + ". " +
                    aux.cancion.getNombre() +
                    " - " +
                    aux.cancion.getArtista() +
                    "\n";

            aux = aux.siguiente;
            posicion++;
        }

        return texto;
    }

    public void mostrar() {

        NodoCola aux = frente;

        while (aux != null) {

            System.out.println(aux.cancion);

            aux = aux.siguiente;
        }
    }
}
