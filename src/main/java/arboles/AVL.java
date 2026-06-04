package arboles;

import modelos.Cancion;
import java.io.FileWriter;
import java.io.IOException;

public class AVL {

    // RAIZ
    private NodoAVL raiz;

    // CONSTRUCTOR
    public AVL() {

        raiz = null;
    }

    // OBTENER ALTURA
    private int altura(NodoAVL nodo) {

        if (nodo == null) {

            return 0;
        }

        return nodo.altura;
    }

    // MAXIMO
    private int maximo(int a, int b) {

        return Math.max(a, b);
    }

    // FACTOR DE BALANCE
    private int balance(NodoAVL nodo) {

        if (nodo == null) {

            return 0;
        }

        return altura(nodo.izquierda)
                - altura(nodo.derecha);
    }
    // ROTACION DERECHA
private NodoAVL rotacionDerecha(NodoAVL y) {

    NodoAVL x = y.izquierda;

    NodoAVL t2 = x.derecha;

    // ROTAR
    x.derecha = y;

    y.izquierda = t2;

    // ACTUALIZAR ALTURAS
    y.altura = maximo(
            altura(y.izquierda),
            altura(y.derecha)
    ) + 1;

    x.altura = maximo(
            altura(x.izquierda),
            altura(x.derecha)
    ) + 1;

    // NUEVA RAIZ
    return x;
}
// ROTACION IZQUIERDA
private NodoAVL rotacionIzquierda(NodoAVL x) {

    NodoAVL y = x.derecha;

    NodoAVL t2 = y.izquierda;

    // ROTAR
    y.izquierda = x;

    x.derecha = t2;

    // ACTUALIZAR ALTURAS
    x.altura = maximo(
            altura(x.izquierda),
            altura(x.derecha)
    ) + 1;

    y.altura = maximo(
            altura(y.izquierda),
            altura(y.derecha)
    ) + 1;

    // NUEVA RAIZ
    return y;
}
public void insertar(modelos.Cancion cancion) {
    raiz = insertarRecursivo(raiz, cancion);
}

private NodoAVL insertarRecursivo(NodoAVL nodo, modelos.Cancion cancion) {

    if (nodo == null) {
        return new NodoAVL(cancion);
    }

    if (cancion.getNombre().compareToIgnoreCase(nodo.cancion.getNombre()) < 0) {
        nodo.izquierda = insertarRecursivo(nodo.izquierda, cancion);
    } else if (cancion.getNombre().compareToIgnoreCase(nodo.cancion.getNombre()) > 0) {
        nodo.derecha = insertarRecursivo(nodo.derecha, cancion);
    } else {
        return nodo;
    }

    nodo.altura = 1 + maximo(
            altura(nodo.izquierda),
            altura(nodo.derecha)
    );

    int balance = balance(nodo);

    // Caso izquierda izquierda
    if (balance > 1 &&
        cancion.getNombre().compareToIgnoreCase(nodo.izquierda.cancion.getNombre()) < 0) {
        return rotacionDerecha(nodo);
    }

    // Caso derecha derecha
    if (balance < -1 &&
        cancion.getNombre().compareToIgnoreCase(nodo.derecha.cancion.getNombre()) > 0) {
        return rotacionIzquierda(nodo);
    }

    // Caso izquierda derecha
    if (balance > 1 &&
        cancion.getNombre().compareToIgnoreCase(nodo.izquierda.cancion.getNombre()) > 0) {
        nodo.izquierda = rotacionIzquierda(nodo.izquierda);
        return rotacionDerecha(nodo);
    }

    // Caso derecha izquierda
    if (balance < -1 &&
        cancion.getNombre().compareToIgnoreCase(nodo.derecha.cancion.getNombre()) < 0) {
        nodo.derecha = rotacionDerecha(nodo.derecha);
        return rotacionIzquierda(nodo);
    }

    return nodo;
}
public void inorder() {
    inorderRecursivo(raiz);
}

private void inorderRecursivo(NodoAVL nodo) {

    if (nodo != null) {

        inorderRecursivo(nodo.izquierda);

        System.out.println(nodo.cancion);

        inorderRecursivo(nodo.derecha);
    }
}

// RECORRIDO PREORDER AVL
public void preorder() {

    preorderRecursivo(raiz);
}

private void preorderRecursivo(NodoAVL nodo) {

    if (nodo != null) {

        System.out.println(nodo.cancion);

        preorderRecursivo(nodo.izquierda);

        preorderRecursivo(nodo.derecha);
    }
}

// RECORRIDO POSTORDER AVL
public void postorder() {

    postorderRecursivo(raiz);
}

private void postorderRecursivo(NodoAVL nodo) {

    if (nodo != null) {

        postorderRecursivo(nodo.izquierda);

        postorderRecursivo(nodo.derecha);

        System.out.println(nodo.cancion);
    }
}

// BUSCAR CANCION
public Cancion buscar(String nombre) {

    return buscarRecursivo(raiz, nombre);
}

private Cancion buscarRecursivo(NodoAVL nodo, String nombre) {

    if (nodo == null) {

        return null;
    }

    if (nombre.equalsIgnoreCase(
            nodo.cancion.getNombre())) {

        return nodo.cancion;
    }

    if (nombre.compareToIgnoreCase(
            nodo.cancion.getNombre()) < 0) {

        return buscarRecursivo(
                nodo.izquierda,
                nombre);

    } else {

        return buscarRecursivo(
                nodo.derecha,
                nombre);
    }
    
    
}

// MODIFICAR CANCION EN AVL
public boolean modificar(String nombre, String nuevoArtista, String nuevoAlbum,
                         String nuevoGenero, double nuevaDuracion, int nuevoAño) {

    Cancion encontrada = buscar(nombre);

    if (encontrada == null) {

        return false;
    }

    encontrada.setArtista(nuevoArtista);
    encontrada.setAlbum(nuevoAlbum);
    encontrada.setGenero(nuevoGenero);
    encontrada.setDuracion(nuevaDuracion);
    encontrada.setAño(nuevoAño);

    return true;
}

// ELIMINAR CANCION EN AVL
public void eliminar(String nombre) {

    raiz = eliminarRecursivo(raiz, nombre);
}

private NodoAVL eliminarRecursivo(NodoAVL nodo, String nombre) {

    if (nodo == null) {
        return null;
    }

    if (nombre.compareToIgnoreCase(nodo.cancion.getNombre()) < 0) {

        nodo.izquierda = eliminarRecursivo(nodo.izquierda, nombre);

    } else if (nombre.compareToIgnoreCase(nodo.cancion.getNombre()) > 0) {

        nodo.derecha = eliminarRecursivo(nodo.derecha, nombre);

    } else {

        if (nodo.izquierda == null || nodo.derecha == null) {

            NodoAVL temp;

            if (nodo.izquierda != null) {
                temp = nodo.izquierda;
            } else {
                temp = nodo.derecha;
            }

            if (temp == null) {
                nodo = null;
            } else {
                nodo = temp;
            }

        } else {

            NodoAVL temp = encontrarMinimo(nodo.derecha);

            nodo.cancion = temp.cancion;

            nodo.derecha = eliminarRecursivo(
                    nodo.derecha,
                    temp.cancion.getNombre()
            );
        }
    }

    if (nodo == null) {
        return null;
    }

    nodo.altura = maximo(
            altura(nodo.izquierda),
            altura(nodo.derecha)
    ) + 1;

    int balance = balance(nodo);

    // Izquierda izquierda
    if (balance > 1 && balance(nodo.izquierda) >= 0) {
        return rotacionDerecha(nodo);
    }

    // Izquierda derecha
    if (balance > 1 && balance(nodo.izquierda) < 0) {
        nodo.izquierda = rotacionIzquierda(nodo.izquierda);
        return rotacionDerecha(nodo);
    }

    // Derecha derecha
    if (balance < -1 && balance(nodo.derecha) <= 0) {
        return rotacionIzquierda(nodo);
    }

    // Derecha izquierda
    if (balance < -1 && balance(nodo.derecha) > 0) {
        nodo.derecha = rotacionDerecha(nodo.derecha);
        return rotacionIzquierda(nodo);
    }

    return nodo;
}

private NodoAVL encontrarMinimo(NodoAVL nodo) {

    while (nodo.izquierda != null) {
        nodo = nodo.izquierda;
    }

    return nodo;
}
// GENERAR ARCHIVO DOT PARA GRAPHVIZ
public void generarDot(String rutaArchivo) {

    try {

        FileWriter writer = new FileWriter(rutaArchivo);

        writer.write("digraph AVL {\n");
        writer.write("node [shape=circle, style=filled, fillcolor=lightgreen];\n");

        generarDotRecursivo(raiz, writer);

        writer.write("}\n");

        writer.close();

        System.out.println("Archivo DOT del AVL generado correctamente.");

    } catch (IOException e) {

        System.out.println("Error al generar archivo DOT del AVL.");
    }
}

private void generarDotRecursivo(NodoAVL nodo, FileWriter writer) throws IOException {

    if (nodo != null) {

        String nombreNodo = nodo.cancion.getNombre();

        if (nodo.izquierda != null) {

            writer.write("\"" + nombreNodo + "\" -> \"" +
                    nodo.izquierda.cancion.getNombre() + "\";\n");

            generarDotRecursivo(nodo.izquierda, writer);
        }

        if (nodo.derecha != null) {

            writer.write("\"" + nombreNodo + "\" -> \"" +
                    nodo.derecha.cancion.getNombre() + "\";\n");

            generarDotRecursivo(nodo.derecha, writer);
        }

        if (nodo.izquierda == null && nodo.derecha == null) {

            writer.write("\"" + nombreNodo + "\";\n");
        }
    }
}

}


