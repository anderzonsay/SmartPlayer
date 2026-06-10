package pilas;

import modelos.Cancion;

public class Pila {

    private NodoPila cima;

    public Pila() {
        cima = null;
    }

    public void push(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        NodoPila nuevo = new NodoPila(cancion);
        nuevo.siguiente = cima;
        cima = nuevo;
    }

    public Cancion pop() {

        if (cima == null) {
            return null;
        }

        Cancion cancion = cima.cancion;
        cima = cima.siguiente;

        return cancion;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public String obtenerHistorialTexto() {

        if (cima == null) {
            return "No hay canciones en el historial.";
        }

        String texto = "";
        NodoPila aux = cima;
        int contador = 1;

        while (aux != null) {

            texto += contador + ". " +
                    aux.cancion.getNombre() +
                    " - " +
                    aux.cancion.getArtista() +
                    "\n";

            aux = aux.siguiente;
            contador++;
        }

        return texto;
    }

    public void mostrar() {

        NodoPila aux = cima;

        while (aux != null) {
            System.out.println(aux.cancion);
            aux = aux.siguiente;
        }
    }
}
