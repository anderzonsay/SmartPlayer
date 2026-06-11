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

        if (artista == null || artista.isEmpty()) {
            artista = "Desconocido";
        }

        int suma = 0;

        for (int i = 0; i < artista.length(); i++) {
            suma += artista.charAt(i);
        }

        return suma % tamaño;
    }

    public void insertar(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        int posicion = funcionHash(cancion.getArtista());

        tabla[posicion].add(cancion);
    }

    public void eliminar(String nombreCancion) {

        for (int i = 0; i < tamaño; i++) {

            for (int j = 0; j < tabla[i].size(); j++) {

                if (tabla[i].get(j).getNombre().equalsIgnoreCase(nombreCancion)) {

                    tabla[i].remove(j);

                    System.out.println("Canción eliminada de hash artistas");
                    return;
                }
            }
        }

        System.out.println("Canción no encontrada en hash artistas");
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

        if (artista == null) {
            artista = "";
        }

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
