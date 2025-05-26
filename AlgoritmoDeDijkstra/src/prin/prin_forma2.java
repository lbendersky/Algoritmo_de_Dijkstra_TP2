package prin;

import api.ConjuntoTDA;
import api.GrafoTDA;
import imp.ConjuntoLD;
import imp.Dijkstra_forma2;
import imp.Dijkstra_forma2.Dijkstra;

public class prin_forma2 {

	public static int contarVertices(GrafoTDA g) {
		int cant = 0;
		ConjuntoTDA c = g.vertices();
		ConjuntoTDA aux = new ConjuntoLD();
		aux.inicializarConjunto();

		while (!c.conjuntoVacio()) {
			int x = c.elegir();
			c.sacar(x);
			aux.agregar(x);
			cant++;
		}

		while (!aux.conjuntoVacio()) {
			int x = aux.elegir();
			aux.sacar(x);
			c.agregar(x);
		}

		return cant;
	}
		
	public static void mostrarGrafo(GrafoTDA g) {
		ConjuntoTDA v = g.vertices();
		ConjuntoTDA copia = Dijkstra.copiarConjunto(v); // usa el mismo copiarConjunto que usás en Dijkstra

		int cantidad = contarVertices(g);
		int[] vertices = new int[cantidad];
		int inx = 0;

		// Generar cabecera ordenada
		System.out.print("    ");
		while (!copia.conjuntoVacio()) {
			int x = copia.elegir();
			copia.sacar(x);
			vertices[inx++] = x;
		}

		for (int x : vertices)
			System.out.print(x + "   ");
		System.out.println();

		// Mostrar matriz
		for (int i = 0; i < cantidad; i++) {
			System.out.print(vertices[i] + "   ");
			for (int j = 0; j < cantidad; j++) {
				if (g.existeArista(vertices[i], vertices[j]))
					System.out.print(g.pesoArista(vertices[i], vertices[j]) + "   ");
				else
					System.out.print("0   ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		//GrafoTDA a = new GrafoMA();
		GrafoTDA a = new Dijkstra_forma2();
		a.inicializarGrafo();
		a.agregarVertice(1);
		a.agregarVertice(2);
		a.agregarVertice(3);
		a.agregarVertice(4);
		a.agregarVertice(5);
		a.agregarVertice(6);
		a.agregarArista(1, 2, 2);
		a.agregarArista(1, 3, 1);
		a.agregarArista(1, 4, 7);
		a.agregarArista(1, 5, 5);
		a.agregarArista(2, 6, 2);
		a.agregarArista(3, 2, 2);
		a.agregarArista(3, 5, 2);
		a.agregarArista(4, 1, 3);
		a.agregarArista(5, 4, 2);
		a.agregarArista(5, 6, 2);
		a.agregarArista(6, 3, 3);
		System.out.println("Matriz de adyacencia de grafo original: ");
		mostrarGrafo(a);


		GrafoTDA copia = new Dijkstra_forma2();
		copia.inicializarGrafo();
		copia.agregarVertice(1);
		copia.agregarVertice(2);
		copia.agregarVertice(3);
		copia.agregarVertice(4);
		copia.agregarVertice(5);
		copia.agregarVertice(6);
		copia.agregarArista(1, 2, 2);
		copia.agregarArista(1, 3, 1);
		copia.agregarArista(1, 4, 7);
		copia.agregarArista(1, 5, 5);
		copia.agregarArista(2, 6, 2);
		copia.agregarArista(3, 2, 2);
		copia.agregarArista(3, 5, 2);
		copia.agregarArista(4, 1, 3);
		copia.agregarArista(5, 4, 2);
		copia.agregarArista(5, 6, 2);
		copia.agregarArista(6, 3, 3);
		GrafoTDA grafoReducido = Dijkstra.segundoMetodo(1, copia);
		System.out.println(" ");
		System.out.println("Matriz de adyacencia resultante, después de implementar el 2do método de Dijkstra: ");
		mostrarGrafo(grafoReducido);
	}

}
