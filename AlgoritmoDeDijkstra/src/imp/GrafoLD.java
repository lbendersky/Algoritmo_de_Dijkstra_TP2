package imp;

import api.ConjuntoTDA;
import api.GrafoTDA;

public class GrafoLD implements GrafoTDA {

	class NodoVertice {
		int vertice;
		NodoVertice sigVertice;
		NodoArista aristas;
	}
	
	class NodoArista {
		int peso;
		NodoArista sigArista;
		NodoVertice destino;
	}
	
	NodoVertice origen;
	
	@Override
	public void inicializarGrafo() {
		origen = null;
	}

	@Override
	public void agregarVertice(int v) {
		NodoVertice nuevo = new NodoVertice();
		nuevo.vertice = v;
		nuevo.sigVertice = origen;
		nuevo.aristas = null;
		origen = nuevo;
	}

	@Override
	public void eliminarVertice(int v) {
		if (origen.vertice == v)
			origen = origen.sigVertice;
		NodoVertice turista = origen;
		while(turista != null) {
			eliminarAristaEnVertice(turista, v);
			if(turista.sigVertice != null && turista.sigVertice.vertice == v) {
				turista.sigVertice = turista.sigVertice.sigVertice;
			}
			turista = turista.sigVertice;
		}
	}

	@Override
	public ConjuntoTDA vertices() {
		ConjuntoTDA v = new ConjuntoLD();
		v.inicializarConjunto();
		NodoVertice turista = origen;
		while(turista != null) {
			v.agregar(turista.vertice);
			turista = turista.sigVertice;
		}
		return v;
	}

	@Override
	public void agregarArista(int origen, int destino, int peso) {
		NodoVertice o = vertice2nodo(origen);
		NodoVertice d = vertice2nodo(destino);
		NodoArista nuevo = new NodoArista();
		nuevo.peso = peso;
		nuevo.sigArista = o.aristas;
		o.aristas = nuevo;
		nuevo.destino = d;
	}

	@Override
	public void eliminarArista(int origen, int destino) {
		NodoVertice o = vertice2nodo(origen);
		eliminarAristaEnVertice(o, destino);
	}

	@Override
	public boolean existeArista(int origen, int destino) {
		NodoVertice o = vertice2nodo(origen);
		NodoArista turista = o.aristas;
		while(turista != null && turista.destino.vertice != destino)
			turista = turista.sigArista;
		return (turista != null);
	}

	@Override
	public int pesoArista(int origen, int destino) {
		NodoVertice o = vertice2nodo(origen);
		NodoArista turista = o.aristas;
		while(turista.destino.vertice != destino)
			turista = turista.sigArista;
		return (turista.peso);
	}

	private NodoVertice vertice2nodo(int v) {
		NodoVertice turista = origen;
		while(turista != null && turista.vertice != v)
			turista = turista.sigVertice;
		return turista;
	}
	
	private void eliminarAristaEnVertice(NodoVertice vertice, int destino) {
		NodoArista turista = vertice.aristas;
		if (turista != null) {
			if(turista.destino.vertice == destino)
				vertice.aristas = turista.sigArista;
		} else {
			while(turista.sigArista != null && turista.sigArista.destino.vertice != destino)
				turista = turista.sigArista;
			if(turista.sigArista != null)
				turista.sigArista = turista.sigArista.sigArista;
		}
	}
}
