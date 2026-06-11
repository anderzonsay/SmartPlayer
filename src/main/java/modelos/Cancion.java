package modelos;

public class Cancion {

    private String nombre;
    private String artista;
    private String album;
    private String genero;
    private String ruta;
    private String rutaPortada;

    private double duracion;
    private double tamaño;

    private int año;
    private int reproducciones;

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
        this.rutaPortada = null;
        this.duracion = duracion;
        this.tamaño = tamaño;
        this.año = año;
        this.reproducciones = 0;
    }

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

    public String getRutaPortada() {
        return rutaPortada;
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

    public int getReproducciones() {
        return reproducciones;
    }

    public void aumentarReproduccion() {
        reproducciones++;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getDuracionFormateada() {

        int minutos = (int) duracion;
        int segundos = (int) ((duracion - minutos) * 60);

        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    public String toString() {

        return "Nombre: " + nombre +
               "\nArtista: " + artista +
               "\nAlbum: " + album +
               "\nGenero: " + genero +
               "\nDuracion: " + getDuracionFormateada() +
               "\nReproducciones: " + reproducciones +
               "\n";
    }
}
