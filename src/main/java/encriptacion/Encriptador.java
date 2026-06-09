package encriptacion;

public class Encriptador {

    private int clave = 3;

    public String encriptar(String texto) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {

            char caracter = texto.charAt(i);

            caracter += clave;

            resultado.append(caracter);
        }

        return resultado.toString();
    }

    public String desencriptar(String texto) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {

            char caracter = texto.charAt(i);

            caracter -= clave;

            resultado.append(caracter);
        }

        return resultado.toString();
    }
}

