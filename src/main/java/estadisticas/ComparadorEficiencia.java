package estadisticas;

import arboles.ABB;
import arboles.AVL;
import java.util.ArrayList;
import modelos.Cancion;

public class ComparadorEficiencia {

    private long tiempoCargaABB;
    private long tiempoCargaAVL;
    private long tiempoBusquedaABB;
    private long tiempoBusquedaAVL;

    public void medirCarga(ArrayList<Cancion> canciones) {

        ABB abb = new ABB();
        AVL avl = new AVL();

        long inicioABB = System.nanoTime();

        for (Cancion c : canciones) {
            abb.insertar(c);
        }

        long finABB = System.nanoTime();

        long inicioAVL = System.nanoTime();

        for (Cancion c : canciones) {
            avl.insertar(c);
        }

        long finAVL = System.nanoTime();

        tiempoCargaABB = finABB - inicioABB;
        tiempoCargaAVL = finAVL - inicioAVL;
    }

    public void medirBusqueda(ABB abb, AVL avl, String nombre) {

        long inicioABB = System.nanoTime();
        abb.buscar(nombre);
        long finABB = System.nanoTime();

        long inicioAVL = System.nanoTime();
        avl.buscar(nombre);
        long finAVL = System.nanoTime();

        tiempoBusquedaABB = finABB - inicioABB;
        tiempoBusquedaAVL = finAVL - inicioAVL;
    }

    public long getTiempoCargaABB() {
        return tiempoCargaABB;
    }

    public long getTiempoCargaAVL() {
        return tiempoCargaAVL;
    }

    public long getTiempoBusquedaABB() {
        return tiempoBusquedaABB;
    }

    public long getTiempoBusquedaAVL() {
        return tiempoBusquedaAVL;
    }

    public String resultadoCarga() {

        String masRapido;

        if (tiempoCargaABB < tiempoCargaAVL) {
            masRapido = "ABB fue más rápido en carga";
        } else if (tiempoCargaAVL < tiempoCargaABB) {
            masRapido = "AVL fue más rápido en carga";
        } else {
            masRapido = "Ambos tuvieron el mismo tiempo de carga";
        }

        return "TIEMPO DE CARGA\n\n" +
                "ABB: " + tiempoCargaABB + " ns\n" +
                "AVL: " + tiempoCargaAVL + " ns\n\n" +
                masRapido;
    }

    public String resultadoBusqueda() {

        String masRapido;

        if (tiempoBusquedaABB < tiempoBusquedaAVL) {
            masRapido = "ABB fue más rápido en búsqueda";
        } else if (tiempoBusquedaAVL < tiempoBusquedaABB) {
            masRapido = "AVL fue más rápido en búsqueda";
        } else {
            masRapido = "Ambos tuvieron el mismo tiempo de búsqueda";
        }

        return "TIEMPO DE BÚSQUEDA\n\n" +
                "ABB: " + tiempoBusquedaABB + " ns\n" +
                "AVL: " + tiempoBusquedaAVL + " ns\n\n" +
                masRapido;
    }
}

