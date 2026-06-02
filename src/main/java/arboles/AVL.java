package arboles;

import modelos.Cancion;

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

}


