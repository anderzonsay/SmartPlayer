
package modelos;


public class Cancion {
    
     // ATRIBUTOS
    private String nombre;
    private String artista;
    private String album;
    private String genero;
    private String ruta;

    private double duracion;
    private double tamaño;

    private int año;

    // CONSTRUCTOR
    public Cancion(String nombre,
                   String artista,
                   String album,
                   String genero,
                   String ruta,
                   double duracion,
                   double tamaño,
                   int año) {

        this.nombre = nombre;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.ruta = ruta;
        this.duracion = duracion;
        this.tamaño = tamaño;
        this.año = año;
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenero() {
        return genero;
    }

    public String getRuta() {
        return ruta;
    }

    public double getDuracion() {
        return duracion;
    }

    public double getTamaño() {
        return tamaño;
    }

    public int getAño() {
        return año;
    }

    // MOSTRAR CANCION
    @Override
    public String toString() {

        return "Nombre: " + nombre +
               "\nArtista: " + artista +
               "\nAlbum: " + album +
               "\nGenero: " + genero +
               "\nDuracion: " + duracion +
               "\n";
    }
}
    

