package hash;

import modelos.Cancion;
import java.util.ArrayList;

public class TablaHashArtistas {

    private ArrayList<Cancion>[] tabla;
    private int tamaño;

    public TablaHashArtistas(int tamaño) {

        this.tamaño = tamaño;
        tabla = new ArrayList[tamaño];

        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new ArrayList<>();
        }
    }

    private int funcionHash(String artista) {

        int suma = 0;

        for (int i = 0; i < artista.length(); i++) {
            suma += artista.charAt(i);
        }

        return suma % tamaño;
    }

    public void insertar(Cancion cancion) {

        int posicion = funcionHash(cancion.getArtista());

        tabla[posicion].add(cancion);
    }

    public void mostrarTabla() {

        for (int i = 0; i < tamaño; i++) {

            System.out.println("Posicion " + i + ":");

            for (Cancion c : tabla[i]) {
                System.out.println("  " + c.getArtista() + " - " + c.getNombre());
            }
        }
    }

    public void buscarPorArtista(String artista) {

        int posicion = funcionHash(artista);

        boolean encontrado = false;

        System.out.println("Canciones del artista: " + artista);

        for (Cancion c : tabla[posicion]) {

            if (c.getArtista().equalsIgnoreCase(artista)) {

                System.out.println(c);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron canciones de ese artista");
        }
    }
}

