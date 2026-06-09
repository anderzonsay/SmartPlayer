package estadisticas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import modelos.Cancion;

public class EstadisticasMusicales {

    private ArrayList<Cancion> canciones;

    public EstadisticasMusicales(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    public int totalCanciones() {
        return canciones.size();
    }

    public Cancion cancionMasLarga() {

        Cancion mayor = canciones.get(0);

        for (Cancion c : canciones) {
            if (c.getDuracion() > mayor.getDuracion()) {
                mayor = c;
            }
        }

        return mayor;
    }

    public Cancion cancionMasCorta() {

        Cancion menor = canciones.get(0);

        for (Cancion c : canciones) {
            if (c.getDuracion() < menor.getDuracion()) {
                menor = c;
            }
        }

        return menor;
    }

    public double promedioDuracion() {

        double suma = 0;

        for (Cancion c : canciones) {
            suma += c.getDuracion();
        }

        return suma / canciones.size();
    }

    public double duracionTotal() {

        double suma = 0;

        for (Cancion c : canciones) {
            suma += c.getDuracion();
        }

        return suma;
    }

    public double tamañoTotalMB() {

        double suma = 0;

        for (Cancion c : canciones) {
            suma += c.getTamaño();
        }

        return suma;
    }

    public int totalArtistas() {

        HashSet<String> artistas = new HashSet<>();

        for (Cancion c : canciones) {
            artistas.add(c.getArtista());
        }

        return artistas.size();
    }

    public int totalGeneros() {

        HashSet<String> generos = new HashSet<>();

        for (Cancion c : canciones) {

            if (c.getGenero() != null &&
                !c.getGenero().equalsIgnoreCase("Desconocido")) {

                generos.add(c.getGenero());
            }
        }

        return generos.size();
    }

    public String artistaConMasCanciones() {

        HashMap<String, Integer> contador = new HashMap<>();

        for (Cancion c : canciones) {
            String artista = c.getArtista();
            contador.put(artista, contador.getOrDefault(artista, 0) + 1);
        }

        String mayorArtista = "Desconocido";
        int mayorCantidad = 0;

        for (String artista : contador.keySet()) {

            if (contador.get(artista) > mayorCantidad) {
                mayorCantidad = contador.get(artista);
                mayorArtista = artista;
            }
        }

        return mayorArtista + " (" + mayorCantidad + " canciones)";
    }

    public String generoMasFrecuente() {

        HashMap<String, Integer> contador = new HashMap<>();

        for (Cancion c : canciones) {

            String genero = c.getGenero();

            if (genero != null && !genero.equalsIgnoreCase("Desconocido")) {
                contador.put(genero, contador.getOrDefault(genero, 0) + 1);
            }
        }

        if (contador.isEmpty()) {
            return "No disponible";
        }

        String mayorGenero = "Desconocido";
        int mayorCantidad = 0;

        for (String genero : contador.keySet()) {

            if (contador.get(genero) > mayorCantidad) {
                mayorCantidad = contador.get(genero);
                mayorGenero = genero;
            }
        }

        return mayorGenero + " (" + mayorCantidad + " canciones)";
    }

    public int cantidadDuplicados() {

        HashSet<String> nombres = new HashSet<>();
        int duplicados = 0;

        for (Cancion c : canciones) {

            String clave = c.getNombre().toLowerCase() + "-" +
                           c.getArtista().toLowerCase();

            if (nombres.contains(clave)) {
                duplicados++;
            } else {
                nombres.add(clave);
            }
        }

        return duplicados;
    }

    public String listarDuplicados() {

        HashSet<String> vistos = new HashSet<>();
        HashSet<String> duplicados = new HashSet<>();

        for (Cancion c : canciones) {

            String clave = c.getNombre().toLowerCase() + "-" +
                           c.getArtista().toLowerCase();

            if (vistos.contains(clave)) {
                duplicados.add(c.getNombre() + " - " + c.getArtista() +
                        " (" + String.format("%.2f MB", c.getTamaño()) + ")");
            } else {
                vistos.add(clave);
            }
        }

        if (duplicados.isEmpty()) {
            return "No se encontraron duplicados";
        }

        String texto = "";

        for (String d : duplicados) {
            texto += d + "\n";
        }

        return texto;
    }
}


