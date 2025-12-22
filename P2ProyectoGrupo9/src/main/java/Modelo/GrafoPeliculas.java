package Modelo;

/**
 * Grafo No Dirigido para representar relaciones entre películas
 * Usa lista de adyacencia (sin usar colecciones de Java)
 * Las relaciones pueden ser: mismo director, mismo género, mismo año, etc.
 */
public class GrafoPeliculas {
    private VerticeGrafo[] vertices; // Array de vértices
    private int capacidad;
    private int tamanio;
    
    public GrafoPeliculas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        this.capacidad = capacidad;
        this.vertices = new VerticeGrafo[capacidad];
        this.tamanio = 0;
    }
    
    public int getTamanio() {
        return tamanio;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    /**
     * Agrega una película como vértice al grafo
     */
    public boolean agregarVertice(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula");
        }
        
        if (tamanio >= capacidad) {
            return false; // Grafo lleno
        }
        
        // Verificar si ya existe
        if (buscarIndiceVertice(pelicula.getTitulo()) != -1) {
            return false; // Ya existe
        }
        
        vertices[tamanio] = new VerticeGrafo(pelicula);
        tamanio++;
        return true;
    }
    
    /**
     * Agrega una arista (relación) entre dos películas
     */
    public boolean agregarArista(String titulo1, String titulo2, String tipoRelacion) {
        int indice1 = buscarIndiceVertice(titulo1);
        int indice2 = buscarIndiceVertice(titulo2);
        
        if (indice1 == -1 || indice2 == -1 || indice1 == indice2) {
            return false;
        }
        
        // Grafo no dirigido: agregar en ambas direcciones
        vertices[indice1].agregarRelacion(vertices[indice2].getPelicula(), tipoRelacion);
        vertices[indice2].agregarRelacion(vertices[indice1].getPelicula(), tipoRelacion);
        
        return true;
    }
    
    /**
     * Construye automáticamente relaciones entre películas basadas en sus atributos
     */
    public void construirRelacionesAutomaticas() {
        for (int i = 0; i < tamanio; i++) {
            for (int j = i + 1; j < tamanio; j++) {
                Pelicula p1 = vertices[i].getPelicula();
                Pelicula p2 = vertices[j].getPelicula();
                
                // Mismo director
                if (p1.getDirector().equalsIgnoreCase(p2.getDirector())) {
                    agregarArista(p1.getTitulo(), p2.getTitulo(), "Mismo Director");
                }
                // Mismo género
                else if (p1.getGenero().equalsIgnoreCase(p2.getGenero())) {
                    agregarArista(p1.getTitulo(), p2.getTitulo(), "Mismo Género");
                }
                // Mismo año
                else if (p1.getAnio() == p2.getAnio()) {
                    agregarArista(p1.getTitulo(), p2.getTitulo(), "Mismo Año");
                }
            }
        }
    }
    
    /**
     * Obtiene todas las películas relacionadas con una película específica
     */
    public Pelicula[] obtenerRelacionadas(String titulo) {
        int indice = buscarIndiceVertice(titulo);
        if (indice == -1) {
            return new Pelicula[0];
        }
        
        ListaEnlazada resultado = new ListaEnlazada();
        NodoGrafo actual = vertices[indice].getListaAdyacencia();
        
        while (actual != null) {
            resultado.insertarAlFinal(actual.getPelicula());
            actual = actual.getSiguiente();
        }
        
        return resultado.obtenerTodasLasPeliculas();
    }
    
    /**
     * Obtiene el tipo de relación entre dos películas
     */
    public String obtenerTipoRelacion(String titulo1, String titulo2) {
        int indice1 = buscarIndiceVertice(titulo1);
        if (indice1 == -1) {
            return null;
        }
        
        NodoGrafo actual = vertices[indice1].getListaAdyacencia();
        while (actual != null) {
            if (actual.getPelicula().getTitulo().equalsIgnoreCase(titulo2)) {
                return actual.getTipoRelacion();
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }
    
    /**
     * Cuenta el número de relaciones de una película (grado del vértice)
     */
    public int contarRelaciones(String titulo) {
        int indice = buscarIndiceVertice(titulo);
        if (indice == -1) {
            return 0;
        }
        return vertices[indice].contarRelaciones();
    }
    
    /**
     * Encuentra la película con más relaciones
     */
    public Pelicula peliculaMasConectada() {
        if (tamanio == 0) {
            return null;
        }
        
        int maxRelaciones = 0;
        int indiceMax = 0;
        
        for (int i = 0; i < tamanio; i++) {
            int relaciones = vertices[i].contarRelaciones();
            if (relaciones > maxRelaciones) {
                maxRelaciones = relaciones;
                indiceMax = i;
            }
        }
        
        return vertices[indiceMax].getPelicula();
    }
    
    /**
     * Obtiene todas las películas del grafo
     */
    public Pelicula[] obtenerTodasPeliculas() {
        Pelicula[] resultado = new Pelicula[tamanio];
        for (int i = 0; i < tamanio; i++) {
            resultado[i] = vertices[i].getPelicula();
        }
        return resultado;
    }
    
    /**
     * Busca el índice de un vértice por título de película
     */
    private int buscarIndiceVertice(String titulo) {
        for (int i = 0; i < tamanio; i++) {
            if (vertices[i].getPelicula().getTitulo().equalsIgnoreCase(titulo)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Obtiene información del grafo en formato texto
     */
    public String obtenerEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS DEL GRAFO ===\n");
        sb.append("Total de películas: ").append(tamanio).append("\n");
        
        int totalAristas = 0;
        for (int i = 0; i < tamanio; i++) {
            totalAristas += vertices[i].contarRelaciones();
        }
        sb.append("Total de relaciones: ").append(totalAristas / 2).append("\n");
        
        Pelicula masConectada = peliculaMasConectada();
        if (masConectada != null) {
            sb.append("Película más conectada: ").append(masConectada.getTitulo());
            sb.append(" (").append(contarRelaciones(masConectada.getTitulo())).append(" relaciones)\n");
        }
        
        return sb.toString();
    }
}
