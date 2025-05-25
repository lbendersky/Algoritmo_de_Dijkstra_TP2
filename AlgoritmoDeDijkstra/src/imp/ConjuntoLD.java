package imp;

import api.ConjuntoTDA;

public class ConjuntoLD implements ConjuntoTDA {

	class Nodo {
		int valor;
		Nodo sig;
	}
	
	Nodo origen;
	
	@Override
	public void inicializarConjunto() {
		origen = null;
	}

	@Override
	public void agregar(int x) {
		if (!this.pertenece(x)) {
			Nodo nuevo = new Nodo();
			nuevo.valor = x;
			nuevo.sig = origen;
			origen = nuevo;
		}
	}

	@Override
	public void sacar(int x) {
		if (origen != null) {
			if(origen.valor == x) {
				origen = origen.sig;
			} else {
				Nodo turista = origen;
				while(turista.sig != null && turista.sig.valor != x)
					turista = turista.sig;
				if(turista.sig != null)
					turista.sig = turista.sig.sig;
			}		
		}
	}

	@Override
	public int elegir() {
		return origen.valor;
	}

	@Override
	public boolean pertenece(int x) {
		Nodo turista = origen;
		while(turista != null && turista.valor != x)
			turista = turista.sig;
		return (turista != null);
	}

	@Override
	public boolean conjuntoVacio() {
		return (origen == null);
	}
}
