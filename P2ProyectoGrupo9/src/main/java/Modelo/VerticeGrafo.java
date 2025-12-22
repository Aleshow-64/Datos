package Modelo;

/**
 * Vértice del Grafo - Representa una película en el grafo
 * Contiene la película y su lista de adyacencia
 */
public class VerticeGrafo {
    private Pelicula pelicula;
    private NodoGrafo listaAdyacencia; // Lista enlazada de películas relacionadas
    private boolean visitado; // Para algoritmos de recorrido
    
    public VerticeGrafo(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula");
        }
        this.pelicula = pelicula;
        this.listaAdyacencia = null;
        this.visitado = false;
    }
    
    public Pelicula getPelicula() {
        return pelicula;
    }
    
    public void setPelicula(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula");
        }
        this.pelicula = pelicula;
    }
    
    public NodoGrafo getListaAdyacencia() {
        return listaAdyacencia;
    }
    
    public void setListaAdyacencia(NodoGrafo listaAdyacencia) {
        this.listaAdyacencia = listaAdyacencia;
    }
    
    public boolean isVisitado() {
        return visitado;
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    
    /**
     * Agrega una relación con otra película
     */
    public void agregarRelacion(Pelicula peliculaRelacionada, String tipoRelacion) {
        NodoGrafo nuevoNodo = new NodoGrafo(peliculaRelacionada, tipoRelacion);
        nuevoNodo.setSiguiente(listaAdyacencia);
        listaAdyacencia = nuevoNodo;
    }
    
    /**
     * Cuenta el número de relaciones (grado del vértice)
     */
    public int contarRelaciones() {
        int contador = 0;
        NodoGrafo actual = listaAdyacencia;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }
    
    /**
     * Verifica si existe una relación con una película específica
     */
    public boolean tieneRelacionCon(String tituloPelicula) {
        NodoGrafo actual = listaAdyacencia;
        while (actual != null) {
            if (actual.getPelicula().getTitulo().equalsIgnoreCase(tituloPelicula)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
