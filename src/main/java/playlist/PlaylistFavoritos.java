package playlist;

import java.io.*;
import java.util.ArrayList;
import modelos.Cancion;
import encriptacion.Encriptador;

public class PlaylistFavoritos {

    private ArrayList<String> nombresFavoritos;
    private final String archivoFavoritos = "favoritos.txt";

    public PlaylistFavoritos() {

        nombresFavoritos = new ArrayList<>();
        cargarFavoritos();
    }

    public void agregarFavorito(Cancion cancion) {

        if (cancion == null) {
            return;
        }

        String nombre = cancion.getNombre();

        if (!nombresFavoritos.contains(nombre)) {

            nombresFavoritos.add(nombre);
            guardarFavoritos();

            System.out.println("Cancion agregada a favoritos: " + nombre);

        } else {

            System.out.println("La cancion ya estaba en favoritos");
        }
    }

    public ArrayList<Cancion> obtenerFavoritos(ArrayList<Cancion> canciones) {

        ArrayList<Cancion> favoritos = new ArrayList<>();

        for (Cancion c : canciones) {

            if (nombresFavoritos.contains(c.getNombre())) {
                favoritos.add(c);
            }
        }

        return favoritos;
    }

    private void guardarFavoritos() {

        try {

            FileWriter writer = new FileWriter(archivoFavoritos);

            for (String nombre : nombresFavoritos) {
                writer.write(nombre + "\n");
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error al guardar favoritos");
        }
    }

    private void cargarFavoritos() {

        File archivo = new File(archivoFavoritos);

        if (!archivo.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(archivo));

            String linea;

            while ((linea = reader.readLine()) != null) {

                if (!linea.trim().isEmpty()) {
                    nombresFavoritos.add(linea.trim());
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Error al cargar favoritos");
        }
    }
    
    public void eliminarFavorito(Cancion cancion) {

    if (cancion == null) {
        return;
    }

    nombresFavoritos.remove(cancion.getNombre());

    guardarFavoritos();

    System.out.println("Favorito eliminado: " + cancion.getNombre());
}
   
    public void exportarFavoritosEncriptados() {

    try {

        Encriptador encriptador = new Encriptador();

        FileWriter writer = new FileWriter("favoritos_encriptados.txt");

        for (String nombre : nombresFavoritos) {

            String textoEncriptado = encriptador.encriptar(nombre);

            writer.write(textoEncriptado + "\n");
        }

        writer.close();

        System.out.println("Favoritos encriptados exportados correctamente");

    } catch (IOException e) {

        System.out.println("Error al exportar favoritos encriptados");
    }
}

public void importarFavoritosEncriptados() {

    try {

        Encriptador encriptador = new Encriptador();

        File archivo = new File("favoritos_encriptados.txt");

        if (!archivo.exists()) {
            System.out.println("No existe favoritos_encriptados.txt");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        String linea;

        while ((linea = reader.readLine()) != null) {

            String desencriptado = encriptador.desencriptar(linea);

            if (!nombresFavoritos.contains(desencriptado)) {
                nombresFavoritos.add(desencriptado);
            }
        }

        reader.close();

        guardarFavoritos();

        System.out.println("Favoritos encriptados importados correctamente");

    } catch (IOException e) {

        System.out.println("Error al importar favoritos encriptados");
    }
}
    
}

