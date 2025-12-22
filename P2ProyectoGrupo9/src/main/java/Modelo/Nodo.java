package Modelo;

/** 
 * Clase base para todos los nodos del sistema
 * Proporciona funcionalidad común de almacenamiento de película
 */
public class Nodo {

    protected Pelicula dato;
    protected Nodo siguiente;

    public Nodo(Pelicula dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser null");
        }
        this.dato = dato;
        this.siguiente = null;
    }

    public Pelicula getDato() {
        return dato;
    }

    public void setDato(Pelicula dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser null");
        }
        this.dato = dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
