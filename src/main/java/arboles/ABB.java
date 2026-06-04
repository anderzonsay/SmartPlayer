package arboles;

import modelos.Cancion;
import java.io.FileWriter;
import java.io.IOException;

public class ABB {

    // RAIZ DEL ARBOL
    private NodoABB raiz;

    // CONSTRUCTOR
    public ABB() {

        raiz = null;
    }

    // INSERTAR
    public void insertar(Cancion cancion) {

        raiz = insertarRecursivo(raiz, cancion);
    }

    // METODO RECURSIVO
    private NodoABB insertarRecursivo(NodoABB nodo, Cancion cancion) {

        // SI EL NODO ESTA VACIO
        if (nodo == null) {

            return new NodoABB(cancion);
        }

        // COMPARAR NOMBRES
        if (cancion.getNombre().compareToIgnoreCase(
                nodo.cancion.getNombre()) < 0) {

            nodo.izquierda = insertarRecursivo(
                    nodo.izquierda, cancion);

        } else {

            nodo.derecha = insertarRecursivo(
                    nodo.derecha, cancion);
        }

        return nodo;
    }

    // RECORRIDO INORDER
    public void inorder() {

        inorderRecursivo(raiz);
    }

    private void inorderRecursivo(NodoABB nodo) {

        if (nodo != null) {

            inorderRecursivo(nodo.izquierda);

            System.out.println(nodo.cancion);

            inorderRecursivo(nodo.derecha);
        }
    }
    // RECORRIDO PREORDER
public void preorder() {

    preorderRecursivo(raiz);
}

private void preorderRecursivo(NodoABB nodo) {

    if (nodo != null) {

        System.out.println(nodo.cancion);

        preorderRecursivo(nodo.izquierda);

        preorderRecursivo(nodo.derecha);
    }
}

// RECORRIDO POSTORDER
public void postorder() {

    postorderRecursivo(raiz);
}

private void postorderRecursivo(NodoABB nodo) {

    if (nodo != null) {

        postorderRecursivo(nodo.izquierda);

        postorderRecursivo(nodo.derecha);

        System.out.println(nodo.cancion);
    }
}

    // BUSCAR CANCION POR NOMBRE
public Cancion buscar(String nombre) {

    return buscarRecursivo(raiz, nombre);
}

private Cancion buscarRecursivo(NodoABB nodo, String nombre) {

    if (nodo == null) {

        return null;
    }

    if (nombre.equalsIgnoreCase(nodo.cancion.getNombre())) {

        return nodo.cancion;
    }

    if (nombre.compareToIgnoreCase(nodo.cancion.getNombre()) < 0) {

        return buscarRecursivo(nodo.izquierda, nombre);

    } else {

        return buscarRecursivo(nodo.derecha, nombre);
    }
}

// MODIFICAR CANCION
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

// ELIMINAR CANCION
public void eliminar(String nombre) {

    raiz = eliminarRecursivo(raiz, nombre);
}

private NodoABB eliminarRecursivo(NodoABB nodo, String nombre) {

    if (nodo == null) {

        return null;
    }

    if (nombre.compareToIgnoreCase(nodo.cancion.getNombre()) < 0) {

        nodo.izquierda = eliminarRecursivo(nodo.izquierda, nombre);

    } else if (nombre.compareToIgnoreCase(nodo.cancion.getNombre()) > 0) {

        nodo.derecha = eliminarRecursivo(nodo.derecha, nombre);

    } else {

        // CASO 1: SIN HIJOS
        if (nodo.izquierda == null && nodo.derecha == null) {

            return null;
        }

        // CASO 2: SOLO HIJO DERECHO
        if (nodo.izquierda == null) {

            return nodo.derecha;
        }

        // CASO 2: SOLO HIJO IZQUIERDO
        if (nodo.derecha == null) {

            return nodo.izquierda;
        }

        // CASO 3: DOS HIJOS
        NodoABB sucesor = encontrarMinimo(nodo.derecha);

        nodo.cancion = sucesor.cancion;

        nodo.derecha = eliminarRecursivo(
                nodo.derecha,
                sucesor.cancion.getNombre()
        );
    }

    return nodo;
}

private NodoABB encontrarMinimo(NodoABB nodo) {

    while (nodo.izquierda != null) {

        nodo = nodo.izquierda;
    }

    return nodo;
}
// GENERAR ARCHIVO DOT PARA GRAPHVIZ
public void generarDot(String rutaArchivo) {

    try {

        FileWriter writer = new FileWriter(rutaArchivo);

        writer.write("digraph ABB {\n");
        writer.write("node [shape=circle, style=filled, fillcolor=lightblue];\n");

        generarDotRecursivo(raiz, writer);

        writer.write("}\n");

        writer.close();

        System.out.println("Archivo DOT del ABB generado correctamente.");

    } catch (IOException e) {

        System.out.println("Error al generar archivo DOT del ABB.");
    }
}

private void generarDotRecursivo(NodoABB nodo, FileWriter writer) throws IOException {

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

