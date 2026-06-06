 package estadisticas;

import java.util.ArrayList;
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
}

