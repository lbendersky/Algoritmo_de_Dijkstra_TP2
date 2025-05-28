package imp;

import api.ConjuntoTDA;
import api.GrafoTDA;
import java.util.HashMap;
import java.util.Map;

public class DijkstraHashMap implements GrafoTDA {

    class NodoArista {
        int peso;
        NodoVertice destino;
        NodoArista sigArista;
    }

    class NodoVertice {
        int vertice;
        NodoArista aristas;
    }

    // Mapa para acceso rápido a vértices
    private Map<Integer, NodoVertice> vertices;

    @Override
    public void inicializarGrafo() {
        vertices = new HashMap<>();
    }

    @Override
    public void agregarVertice(int v) {
        if (!vertices.containsKey(v)) {
            NodoVertice nuevo = new NodoVertice();
            nuevo.vertice = v;
            nuevo.aristas = null;
            vertices.put(v, nuevo);
        }
    }

    @Override
    public void eliminarVertice(int v) {
        // Eliminar el vértice
        NodoVertice eliminado = vertices.remove(v);
        if (eliminado == null) return;

        // Eliminar todas las aristas que apuntan a v
        for (NodoVertice nodo : vertices.values()) {
            eliminarAristaEnVertice(nodo, v);
        }
    }

    @Override
    public ConjuntoTDA vertices() {
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.inicializarConjunto();
        for (int v : vertices.keySet()) {
            conjunto.agregar(v);
        }
        return conjunto;
    }

    @Override
    public void agregarArista(int origen, int destino, int peso) {
        // Asegurar que los vértices existen
        agregarVertice(origen);
        agregarVertice(destino);

        NodoVertice o = vertices.get(origen);
        NodoVertice d = vertices.get(destino);

        // Evitar aristas duplicadas
        NodoArista actual = o.aristas;
        while (actual != null) {
            if (actual.destino == d) {
                actual.peso = peso; // Actualizar peso si ya existe
                return;
            }
            actual = actual.sigArista;
        }

        // Agregar nueva arista al principio
        NodoArista nuevo = new NodoArista();
        nuevo.peso = peso;
        nuevo.destino = d;
        nuevo.sigArista = o.aristas;
        o.aristas = nuevo;
    }

    @Override
    public void eliminarArista(int origen, int destino) {
        NodoVertice o = vertices.get(origen);
        if (o != null) {
            eliminarAristaEnVertice(o, destino);
        }
    }

    @Override
    public boolean existeArista(int origen, int destino) {
        NodoVertice o = vertices.get(origen);
        if (o == null) return false;
        NodoArista turista = o.aristas;
        while (turista != null) {
            if (turista.destino.vertice == destino) return true;
            turista = turista.sigArista;
        }
        return false;
    }

    @Override
    public int pesoArista(int origen, int destino) {
        NodoVertice o = vertices.get(origen);
        if (o == null) return -1;
        NodoArista turista = o.aristas;
        while (turista != null) {
            if (turista.destino.vertice == destino) return turista.peso;
            turista = turista.sigArista;
        }
        return -1;
    }

    private void eliminarAristaEnVertice(NodoVertice vertice, int destino) {
        NodoArista turista = vertice.aristas;
        NodoArista anterior = null;
        while (turista != null && turista.destino.vertice != destino) {
            anterior = turista;
            turista = turista.sigArista;
        }
        if (turista != null) {
            if (anterior == null) {
                vertice.aristas = turista.sigArista;
            } else {
                anterior.sigArista = turista.sigArista;
            }
        }
    }

    @Override
    public ConjuntoTDA vecinos(int v) {
        ConjuntoTDA vecinos = new ConjuntoLD();
        vecinos.inicializarConjunto();
        NodoVertice nodo = vertices.get(v);
        if (nodo != null) {
            NodoArista turista = nodo.aristas;
            while (turista != null) {
                vecinos.agregar(turista.destino.vertice);
                turista = turista.sigArista;
            }
        }
        return vecinos;
    }
}