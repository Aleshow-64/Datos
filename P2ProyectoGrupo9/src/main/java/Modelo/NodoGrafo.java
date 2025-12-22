package Modelo;

/**
 * Nodo para el Grafo de Películas
 * Extiende Nodo base y agrega información de relación
 */
public class NodoGrafo extends Nodo {
    private String tipoRelacion; // "mismo_director", "mismo_genero", "mismo_anio"
    
    public NodoGrafo(Pelicula pelicula, String tipoRelacion) {
        super(pelicula);
        this.tipoRelacion = tipoRelacion != null ? tipoRelacion : "relacionada";
    }
    
    public String getTipoRelacion() {
        return tipoRelacion;
    }
    
    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }
    
    // Sobrescribimos para retornar el tipo correcto
    @Override
    public NodoGrafo getSiguiente() {
        return (NodoGrafo) siguiente;
    }
    
    public void setSiguiente(NodoGrafo siguiente) {
        this.siguiente = siguiente;
    }
    
    // Métodos de conveniencia heredados de Nodo
    public Pelicula getPelicula() {
        return getDato();
    }
    
    public void setPelicula(Pelicula pelicula) {
        setDato(pelicula);
    }
}

