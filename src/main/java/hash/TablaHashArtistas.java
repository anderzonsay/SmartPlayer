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

        ArrayList<Cancion> resultados = obtenerPorArtista(artista);

        System.out.println("Canciones del artista: " + artista);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron canciones de ese artista");
        } else {
            for (Cancion c : resultados) {
                System.out.println(c);
            }
        }
    }

    public ArrayList<Cancion> obtenerPorArtista(String artista) {

    ArrayList<Cancion> resultados = new ArrayList<>();

    for (int i = 0; i < tamaño; i++) {

        for (Cancion c : tabla[i]) {

            if (c.getArtista().toLowerCase().contains(artista.toLowerCase())) {
                resultados.add(c);
            }
        }
    }

    return resultados;
}
}
