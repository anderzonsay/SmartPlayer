package arboles;

import modelos.Cancion;

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

}

