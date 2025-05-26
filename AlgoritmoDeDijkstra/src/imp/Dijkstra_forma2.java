package imp;

import api.ConjuntoTDA;
import api.GrafoTDA;

public class Dijkstra_forma2 implements GrafoTDA {

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
		NodoArista turista = o.aristas;
		while(turista != null && turista.destino.vertice != destino)
			turista = turista.sigArista;
		return (turista != null);
	}

	@Override
	public int pesoArista(int origen, int destino) {
		NodoVertice o = vertice2nodo(origen);

        if(o == null) {
            agregarVertice(origen);
            o = vertice2nodo(origen);
        }

		NodoArista turista = o.aristas;

        if (turista == null) {
            agregarArista(origen, destino, 1);
        }

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

    public class Dijkstra {
            public static Dijkstra_forma2 segundoMetodo(int origen, GrafoTDA grafo) {
                ConjuntoTDA vertices = grafo.vertices(); //Obtengo los vertices del grafo
                
                int[] distancias = new int[10];
                boolean[] visitado = new boolean[10];
                int[] predecesores = new int[100];
                
                for (int i = 0; i < 10; i++) {
                    distancias[i] = 11;
                    visitado[i] = false;
                    predecesores[i] = -1;
                }
                distancias[origen] = 0;

                while(!todosVisitados(vertices, visitado)) {
                    int u = verticeMinDistNoVisitado(vertices, distancias, visitado);

                    if(u == -1) break;
                    visitado[u] = true;

                    ConjuntoTDA vecinos = grafo.vertices();

                    while(!vecinos.conjuntoVacio()) {
                        int b = vecinos.elegir();
                        vecinos.sacar(b);

                        if(grafo.existeArista(u, b)) {
                            int peso = grafo.pesoArista(u, b);

                            if(!visitado[b] && distancias[u] + peso < distancias[b]) {
                                distancias[b] = distancias[u] + peso;
                                predecesores[b] = u;
                            }
                        }   
                    }
                }

                System.out.println("Distancias minimas desde el nodo:  " + origen + ":");
                for (int i = 0; i < distancias.length; i++) {
                    if(distancias[i] != 11) {
                        System.out.println("Hasta: " + i + ": " + distancias[i]);
                    }
                }

                Dijkstra_forma2 grafoResultado = new Dijkstra_forma2();
                grafoResultado.inicializarGrafo();

                ConjuntoTDA versos = copiarConjunto(vertices);

                while(!versos.conjuntoVacio()) {
                    int v = versos.elegir();
                    versos.sacar(v);
                    grafoResultado.agregarVertice(v);
                }
                
                grafoResultado.agregarVertice(origen);

                for(int i = 0; i < 100; i++) { 
                    if(predecesores[i] != -1) {
                        grafoResultado.agregarVertice(i);
                        grafoResultado.agregarVertice(predecesores[i]);

                        int desde = predecesores[i];
                        int hasta = i;
                        int peso = grafo.pesoArista(desde, hasta);
                        grafoResultado.agregarArista(desde, hasta, peso);
                    }
                }
                return grafoResultado;
            }

            private static boolean todosVisitados(ConjuntoTDA vertices, boolean[] visitado) {
                ConjuntoTDA copia = copiarConjunto(vertices);

                while(!copia.conjuntoVacio()) {
                    int x = copia.elegir();
                    copia.sacar(x);
                    if (!visitado[x]) 
                        return false;
                }

                return true;
            }

            private static ConjuntoTDA copiarConjunto(ConjuntoTDA original) {
                ConjuntoTDA copia = new ConjuntoLD();
                copia.inicializarConjunto();
                ConjuntoTDA aux = new ConjuntoLD();
                aux.inicializarConjunto();

                while(!original.conjuntoVacio()) {
                    int elemento = original.elegir();
                    original.sacar(elemento);
                    aux.agregar(elemento);
                    copia.agregar(elemento);
                }

                while(!aux.conjuntoVacio()) {
                    int elemento = aux.elegir();
                    aux.sacar(elemento);
                    original.agregar(elemento);
                }

                return copia;
            }

            private static int verticeMinDistNoVisitado(ConjuntoTDA vertices, int[] distancias,boolean[] visitado) {
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

                return minimoVertice;
            }
        }

}
