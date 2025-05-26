package prin;

import api.ConjuntoTDA;
import api.GrafoTDA;
import imp.Dijkstra_forma2;
import imp.GrafoLD;
import imp.Dijkstra_forma2.Dijkstra;

public class prin {

	public static int contarVertices(GrafoTDA g) {
		int cant = 0;
		int x;
		ConjuntoTDA c = g.vertices();
		while(!c.conjuntoVacio()) {
			x = c.elegir();
			c.sacar(x);
			cant++;
		}
		return cant;
	}
		
	public static void mostrarGrafo(GrafoTDA g) {
		String cadena = "";
		ConjuntoTDA v = g.vertices();
		int cantidad = contarVertices(g);
		int[] vertices = new int[cantidad];
		cadena = cadena + "    ";
		int inx = 0;
		while(!v.conjuntoVacio()) {
			int x = v.elegir();
			v.sacar(x);
			vertices[inx] = x;
			cadena = cadena + x + "   ";
			inx++;
		}
		System.out.println(cadena);
		for (int i = 0; i < cantidad; i++) {
			cadena = "";
			cadena = cadena + vertices[i] + "   ";
			for (int j = 0; j < cantidad; j++) 
				if(g.existeArista(vertices[i], vertices[j]))
					cadena = cadena + g.pesoArista(vertices[i], vertices[j]) + "   ";
				else
					cadena = cadena + "0   ";
			System.out.println(cadena);
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
		Dijkstra_forma2 grafoReducido = Dijkstra.segundoMetodo(1, copia);
		mostrarGrafo(grafoReducido);
	}

}
