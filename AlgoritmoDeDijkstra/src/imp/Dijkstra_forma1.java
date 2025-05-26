package imp;

import api.ConjuntoTDA;
import api.GrafoTDA;

public class Dijkstra_forma1 implements GrafoTDA {

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
        if (vertice2nodo(v) != null) return;
		NodoVertice nuevo = new NodoVertice();
		nuevo.vertice = v;
		nuevo.sigVertice = origen;
		nuevo.aristas = null;
		origen = nuevo;
	}

	@Override
	public void eliminarVertice(int v) {
		if (origen.vertice == v && origen != null)
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

        if(o == null) {
            agregarVertice(origen);
            o = vertice2nodo(origen);
        }

		NodoVertice d = vertice2nodo(destino);

        if(d == null) {
            agregarVertice(destino);
            d = vertice2nodo(destino);
        }

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
        if (o == null) return false;
		NodoArista turista = o.aristas;
		while(turista != null && turista.destino.vertice != destino)
			turista = turista.sigArista;
		return (turista != null);
	}

	@Override
	public int pesoArista(int origen, int destino) {
		NodoVertice o = vertice2nodo(origen);
        if (o == null) return -1;

		NodoArista turista = o.aristas;
        while (turista != null && turista.destino.vertice != destino)
            turista = turista.sigArista;
        if (turista == null) return -1;

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
        NodoArista anterior = null;
        while (turista != null && turista.destino.vertice != destino) {
            anterior = turista;
            turista = turista.sigArista;
        }
        if (turista != null) {
            if (anterior == null)
                vertice.aristas = turista.sigArista;
            else
                anterior.sigArista = turista.sigArista;
        }
    }
	

    public ConjuntoTDA vecinos(int v) {
        ConjuntoTDA vecinos = new ConjuntoLD();     // Crea un conjunto para guardar los vecinos del vértice v
        vecinos.inicializarConjunto();
    
        NodoVertice nodoOrigen = vertice2nodo(v);     // Busca el nodo del vértice v en la lista de vértices
        
        if (nodoOrigen != null) {
            NodoArista turista = nodoOrigen.aristas;  // Recorre todas las aristas (vecinos) del nodo origen y los agrega al conjunto vecinos
            while (turista != null) {
                vecinos.agregar(turista.destino.vertice);
                turista = turista.sigArista;
            }
        }
        return vecinos; // Devuelve el conjunto de vecinos
    }


    public class Dijkstra {            
        public static ConjuntoTDA copiarConjunto(ConjuntoTDA original) {
            // Crea una copia exacta del conjunto original sin modificarlo
                ConjuntoTDA copia = new ConjuntoLD();
                copia.inicializarConjunto();
                ConjuntoTDA aux = new ConjuntoLD();
                aux.inicializarConjunto();

            // Extrae cada elemento del conjunto original y lo agrega tanto a aux como a copia
                while(!original.conjuntoVacio()) {
                    int elemento = original.elegir();
                    original.sacar(elemento);
                    aux.agregar(elemento);
                    copia.agregar(elemento);
                }
        // Vuelve a agregar todos los elementos de aux al conjunto original para no modificarlo
                while(!aux.conjuntoVacio()) {
                    int elemen = aux.elegir();
                    aux.sacar(elemen);
                    original.agregar(elemen);
                }

                return copia; // Devuelve la copia creada
            }

            public static Dijkstra_forma2 primerMetodo(int origen, GrafoTDA grafo) {
                ConjuntoTDA vertices = grafo.vertices(); //Obtengo los vertices del grafo
                
                
            // Inicializa los arreglos para distancias, visitados
                int[] distancias = new int[10];
                boolean[] visitado = new boolean[10];
                
            // Inicializa distancias con un valor grande (11), visitado en falso y predecesores en -1
                for (int i = 0; i < 10; i++) {
                    distancias[i] = 11;
                    visitado[i] = false; //Si aun no se visitó.

                }
                distancias[origen] = 0;

        // Mientras no se hayan visitado todos los vértices
                while(!todosVisitados(vertices, visitado)) {
                    // Obtiene el vértice no visitado con distancia mínima
                    int u = verticeMinDistNoVisitado(vertices, distancias, visitado);

                    if(u == -1) break; // Si no quedan vértices alcanzables, termina
                    visitado[u] = true; // Marca u como visitado

                    // Obtiene los vecinos de u
                    ConjuntoTDA vecinos = grafo.vecinos(u);

                    // Para cada vecino b, si no está visitado y mejora la distancia, actualiza distancia.
                    while(!vecinos.conjuntoVacio()) {
                        int b = vecinos.elegir();
                        vecinos.sacar(b);

                        if (!visitado[b] && distancias[u] + grafo.pesoArista(u, b) < distancias[b]) {
                            distancias[b] = distancias[u] + grafo.pesoArista(u, b);
                        } 
                    }
                }
                // Crea un nuevo grafo para almacenar el árbol de caminos mínimos resultante
                Dijkstra_forma2 resultado = new Dijkstra_forma2();
                resultado.inicializarGrafo();

                // Copia los vértices para agregarlos al grafo resultado
                ConjuntoTDA copiaVertices = copiarConjunto(vertices);
                while (!copiaVertices.conjuntoVacio()) {
                    int x = copiaVertices.elegir();
                    copiaVertices.sacar(x);
                    resultado.agregarVertice(x);
                }

               // Agregamos solo aristas desde el origen a los demás
                for (int i = 0; i < 10; i++) {
                    if (i != origen && distancias[i] < 11){
                        resultado.agregarArista(origen, i, distancias[i]);
                    }
                }

            return resultado; // Devuelve el grafo resultado con los caminos mínimos
        }
            private static boolean todosVisitados(ConjuntoTDA vertices, boolean[] visitado) {
                // Verifica si todos los vértices del conjunto han sido visitados
                ConjuntoTDA copia = copiarConjunto(vertices);

                while(!copia.conjuntoVacio()) {
                    int x = copia.elegir();
                    copia.sacar(x);
                    if (!visitado[x]) 
                        return false; // Si encuentra alguno no visitado, retorna falso
                }

                return true;  // Todos visitados
            }


            private static int verticeMinDistNoVisitado(ConjuntoTDA vertices, int[] distancias,boolean[] visitado) {
                // Busca el vértice no visitado con la distancia mínima
                ConjuntoTDA copia = copiarConjunto(vertices);
                int minimaDistancia = 10;
                int minimoVertice = -1;
                
                while(!copia.conjuntoVacio()) {
                    int x = copia.elegir();
                    copia.sacar(x);
                    if(!visitado[x] && distancias[x] < minimaDistancia) {
                        minimaDistancia = distancias[x];
                        minimoVertice = x;
                    }
                }

                return minimoVertice; // Retorna el vértice con distancia mínima o -1 si no hay ninguno
            }
        }

}
