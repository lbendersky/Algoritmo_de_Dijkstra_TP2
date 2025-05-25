package api;

public interface GrafoTDA {
    void inicializarGrafo();
    void agregarVertice(int v);
    void eliminarVertice(int v);
    ConjuntoTDA vertices();
    void agregarArista(int origen, int destino, int peso);
    void eliminarArista(int oirgen, int destino);
    boolean existeArista(int origen, int destino);
    int pesoArista(int origen, int destino);
}
