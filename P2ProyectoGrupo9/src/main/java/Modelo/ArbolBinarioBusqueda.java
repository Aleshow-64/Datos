package Modelo;

/**
 * Árbol Binario de Búsqueda para almacenar películas
 * Ordenado por título (comparación lexicográfica)
 * No usa colecciones de Java, implementación pura con nodos
 */
public class ArbolBinarioBusqueda {
    private NodoArbol raiz;
    private int tamanio;
    
    public ArbolBinarioBusqueda() {
        this.raiz = null;
        this.tamanio = 0;
    }
    
    public boolean estaVacio() {
        return raiz == null;
    }
    
    public int getTamanio() {
        return tamanio;
    }
    
    public NodoArbol getRaiz() {
        return raiz;
    }
    
    /**
     * Inserta una película en el árbol
     * Complejidad: O(log n) promedio, O(n) peor caso
     */
    public void insertar(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("No se puede insertar película nula");
        }
        raiz = insertarRecursivo(raiz, pelicula);
        tamanio++;
    }
    
    private NodoArbol insertarRecursivo(NodoArbol nodo, Pelicula pelicula) {
        if (nodo == null) {
            return new NodoArbol(pelicula);
        }
        
        int comparacion = pelicula.getTitulo().compareToIgnoreCase(nodo.getDato().getTitulo());
        
        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), pelicula));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), pelicula));
        }
        // Si comparacion == 0, no insertamos duplicados
        
        return nodo;
    }
    
    /**
     * Busca una película por título
     * Complejidad: O(log n) promedio, O(n) peor caso
     */
    public Pelicula buscar(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        return buscarRecursivo(raiz, titulo.trim());
    }
    
    private Pelicula buscarRecursivo(NodoArbol nodo, String titulo) {
        if (nodo == null) {
            return null;
        }
        
        int comparacion = titulo.compareToIgnoreCase(nodo.getDato().getTitulo());
        
        if (comparacion == 0) {
            return nodo.getDato();
        } else if (comparacion < 0) {
            return buscarRecursivo(nodo.getIzquierdo(), titulo);
        } else {
            return buscarRecursivo(nodo.getDerecho(), titulo);
        }
    }
    
    /**
     * Elimina una película por título
     * Complejidad: O(log n) promedio, O(n) peor caso
     */
    public boolean eliminar(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        
        int tamanioAnterior = tamanio;
        raiz = eliminarRecursivo(raiz, titulo.trim());
        return tamanio < tamanioAnterior;
    }
    
    private NodoArbol eliminarRecursivo(NodoArbol nodo, String titulo) {
        if (nodo == null) {
            return null;
        }
        
        int comparacion = titulo.compareToIgnoreCase(nodo.getDato().getTitulo());
        
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), titulo));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), titulo));
        } else {
            // Encontramos el nodo a eliminar
            tamanio--;
            
            // Caso 1: Nodo hoja
            if (nodo.esHoja()) {
                return null;
            }
            
            // Caso 2: Nodo con un hijo
            if (nodo.tieneUnHijo()) {
                return nodo.getIzquierdo() != null ? nodo.getIzquierdo() : nodo.getDerecho();
            }
            
            // Caso 3: Nodo con dos hijos
            NodoArbol sucesor = encontrarMinimo(nodo.getDerecho());
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), sucesor.getDato().getTitulo()));
        }
        
        return nodo;
    }
    
    private NodoArbol encontrarMinimo(NodoArbol nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }
    
    /**
     * Recorrido Inorden (Izquierdo -> Raíz -> Derecho)
     * Retorna películas ordenadas alfabéticamente por título
     */
    public Pelicula[] recorridoInorden() {
        Pelicula[] resultado = new Pelicula[tamanio];
        ContadorArray contador = new ContadorArray();
        inordenRecursivo(raiz, resultado, contador);
        return resultado;
    }
    
    private void inordenRecursivo(NodoArbol nodo, Pelicula[] array, ContadorArray contador) {
        if (nodo != null) {
            inordenRecursivo(nodo.getIzquierdo(), array, contador);
            array[contador.valor++] = nodo.getDato();
            inordenRecursivo(nodo.getDerecho(), array, contador);
        }
    }
    
    /**
     * Recorrido Preorden (Raíz -> Izquierdo -> Derecho)
     */
    public Pelicula[] recorridoPreorden() {
        Pelicula[] resultado = new Pelicula[tamanio];
        ContadorArray contador = new ContadorArray();
        preordenRecursivo(raiz, resultado, contador);
        return resultado;
    }
    
    private void preordenRecursivo(NodoArbol nodo, Pelicula[] array, ContadorArray contador) {
        if (nodo != null) {
            array[contador.valor++] = nodo.getDato();
            preordenRecursivo(nodo.getIzquierdo(), array, contador);
            preordenRecursivo(nodo.getDerecho(), array, contador);
        }
    }
    
    /**
     * Recorrido Postorden (Izquierdo -> Derecho -> Raíz)
     */
    public Pelicula[] recorridoPostorden() {
        Pelicula[] resultado = new Pelicula[tamanio];
        ContadorArray contador = new ContadorArray();
        postordenRecursivo(raiz, resultado, contador);
        return resultado;
    }
    
    private void postordenRecursivo(NodoArbol nodo, Pelicula[] array, ContadorArray contador) {
        if (nodo != null) {
            postordenRecursivo(nodo.getIzquierdo(), array, contador);
            postordenRecursivo(nodo.getDerecho(), array, contador);
            array[contador.valor++] = nodo.getDato();
        }
    }
    
    /**
     * Calcula la altura del árbol
     */
    public int altura() {
        return alturaRecursiva(raiz);
    }
    
    private int alturaRecursiva(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(alturaRecursiva(nodo.getIzquierdo()), 
                           alturaRecursiva(nodo.getDerecho()));
    }
    
    /**
     * Clase auxiliar para pasar contador por referencia
     */
    private static class ContadorArray {
        int valor = 0;
    }
    
    /**
     * Busca películas por rango de años
     */
    public Pelicula[] buscarPorRangoAnio(int anioMin, int anioMax) {
        ListaEnlazada temp = new ListaEnlazada();
        buscarPorRangoRecursivo(raiz, anioMin, anioMax, temp);
        return temp.obtenerTodasLasPeliculas();
    }
    
    private void buscarPorRangoRecursivo(NodoArbol nodo, int anioMin, int anioMax, ListaEnlazada lista) {
        if (nodo == null) {
            return;
        }
        
        buscarPorRangoRecursivo(nodo.getIzquierdo(), anioMin, anioMax, lista);
        
        if (nodo.getDato().getAnio() >= anioMin && nodo.getDato().getAnio() <= anioMax) {
            lista.insertarAlFinal(nodo.getDato());
        }
        
        buscarPorRangoRecursivo(nodo.getDerecho(), anioMin, anioMax, lista);
    }
}
