package hash;

import modelos.Cancion;
import java.util.ArrayList;

public class TablaHashGeneros {

    private ArrayList<Cancion>[] tabla;
    private int tamaño;

    public TablaHashGeneros(int tamaño) {

        this.tamaño = tamaño;
        tabla = new ArrayList[tamaño];

        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new ArrayList<>();
        }
    }

    private int funcionHash(String genero) {

        if (genero == null || genero.isEmpty()) {
            genero = "Desconocido";
        }

        int suma = 0;

        for (int i = 0; i < genero.length(); i++) {
            suma += genero.charAt(i);
        }

        return suma % tamaño;
    }

    public void insertar(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        int posicion = funcionHash(cancion.getGenero());

        tabla[posicion].add(cancion);
    }

    public void eliminar(String nombreCancion) {

        for (int i = 0; i < tamaño; i++) {

            for (int j = 0; j < tabla[i].size(); j++) {

                if (tabla[i].get(j).getNombre().equalsIgnoreCase(nombreCancion)) {

                    tabla[i].remove(j);

                    System.out.println("Canción eliminada de hash géneros");
                    return;
                }
            }
        }

        System.out.println("Canción no encontrada en hash géneros");
    }

    public void mostrarTabla() {

        for (int i = 0; i < tamaño; i++) {

            System.out.println("Posicion " + i + ":");

            for (Cancion c : tabla[i]) {
                System.out.println("  " + c.getGenero() + " - " + c.getNombre());
            }
        }
    }

    public void buscarPorGenero(String genero) {

        ArrayList<Cancion> resultados = obtenerPorGenero(genero);

        System.out.println("Canciones del genero: " + genero);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron canciones de ese genero");
        } else {
            for (Cancion c : resultados) {
                System.out.println(c);
            }
        }
    }

    public ArrayList<Cancion> obtenerPorGenero(String genero) {

        ArrayList<Cancion> resultados = new ArrayList<>();

        if (genero == null) {
            genero = "";
        }

        for (int i = 0; i < tamaño; i++) {

            for (Cancion c : tabla[i]) {

                if (c.getGenero().toLowerCase().contains(genero.toLowerCase())) {
                    resultados.add(c);
                }
            }
        }

        return resultados;
    }
}
