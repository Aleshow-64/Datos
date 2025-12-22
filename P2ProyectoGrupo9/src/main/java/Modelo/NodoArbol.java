package Modelo;

/**
 * Nodo para el Árbol Binario de Búsqueda
 * Extiende Nodo base y agrega referencias izquierda/derecha
 */
public class NodoArbol extends Nodo {
    private NodoArbol izquierdo;
    private NodoArbol derecho;
    
    public NodoArbol(Pelicula dato) {
        super(dato);
        this.izquierdo = null;
        this.derecho = null;
    }
    
    public NodoArbol getIzquierdo() {
        return izquierdo;
    }
    
    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }
    
    public NodoArbol getDerecho() {
        return derecho;
    }
    
    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
    
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
    
    public boolean tieneUnHijo() {
        return (izquierdo != null && derecho == null) || 
               (izquierdo == null && derecho != null);
    }
    
    public boolean tieneDosHijos() {
        return izquierdo != null && derecho != null;
    }
}
