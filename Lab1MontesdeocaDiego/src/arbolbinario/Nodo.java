package arbolbinario;

public class Nodo {
	protected Object dato;
	protected Nodo izdo;
	protected Nodo dcho;
	
	public Nodo(Object dato) {
		this.dato = dato;
		this.izdo = null;
		this.dcho = null;
	}

	public Nodo(Nodo izdo, Object dato, Nodo dcho) {
		this.dato = dato;
		this.izdo = izdo;
		this.dcho = dcho;
	}

	public Object getDato() {
		return dato;
	}

	public void setDato(Object dato) {
		this.dato = dato;
	}

	public Nodo SubarbolIzdo() {
		return izdo;
	}

	public void ramaIzdo(Nodo izdo) {
		this.izdo = izdo;
	}

	public Nodo SubarbolDcho() {
		return dcho;
	}

	public void ramaDcho(Nodo dcho) {
		this.dcho = dcho;
	}
	
	
}