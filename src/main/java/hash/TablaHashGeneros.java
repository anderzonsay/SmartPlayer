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

        int suma = 0;

        for (int i = 0; i < genero.length(); i++) {
            suma += genero.charAt(i);
        }

        return suma % tamaño;
    }

    public void insertar(Cancion cancion) {

        int posicion = funcionHash(cancion.getGenero());

        tabla[posicion].add(cancion);
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

        int posicion = funcionHash(genero);

        boolean encontrado = false;

        System.out.println("Canciones del genero: " + genero);

        for (Cancion c : tabla[posicion]) {

            if (c.getGenero().equalsIgnoreCase(genero)) {

                System.out.println(c);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron canciones de ese genero");
        }
    }
}

