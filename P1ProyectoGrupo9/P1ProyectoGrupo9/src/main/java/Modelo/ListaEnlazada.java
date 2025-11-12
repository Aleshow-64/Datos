package Modelo;

/** Lista enlazada simple para almacenar películas */
public class ListaEnlazada {
    private Nodo cabeza;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    // Insertar al inicio - O(1)
    public void insertarAlInicio(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("No se puede insertar película nula");
        }
        Nodo nuevoNodo = new Nodo(pelicula);
        nuevoNodo.setSiguiente(this.cabeza);
        this.cabeza = nuevoNodo;
        tamanio++;
    }

    // Insertar al final - O(n)
    public void insertarAlFinal(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("No se puede insertar película nula");
        }
        Nodo nuevoNodo = new Nodo(pelicula);
        if (estaVacia()) {
            this.cabeza = nuevoNodo;
        } else {
            Nodo actual = this.cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamanio++;
    }

    /** Elimina la primera película con el título dado (case-insensitive) */
    public boolean eliminarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        
        if (estaVacia()) {
            return false;
        }

        if (cabeza.getDato().getTitulo().equalsIgnoreCase(titulo.trim())) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return true;
        }
        
        Nodo anterior = cabeza;
        Nodo actual = cabeza.getSiguiente();
        while (actual != null) {
            if (actual.getDato().getTitulo().equalsIgnoreCase(titulo.trim())) {
                anterior.setSiguiente(actual.getSiguiente());
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        
        return false;
    }    /** Busca y retorna la primera película con el título dado (case-insensitive) */
    public Pelicula buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getDato().getTitulo().equalsIgnoreCase(titulo.trim())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /** Retorna todas las películas en un array ordenado de inicio a fin */
    public Pelicula[] obtenerTodasLasPeliculas() {
        if (estaVacia()) {
            return new Pelicula[0];
        }
        
        Pelicula[] peliculas = new Pelicula[tamanio];
        Nodo actual = cabeza;
        int indice = 0;
        
        while (actual != null && indice < tamanio) {
            peliculas[indice++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        
        return peliculas;
    }

    /** Limpia todas las películas de la lista */
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }

    /** Verifica si existe una película con el título dado */
    public boolean contiene(String titulo) {
        return buscarPorTitulo(titulo) != null;
    }

    @Override
    public String toString() {
        if (estaVacia()) {
            return "ListaEnlazada[]";
        }
        
        StringBuilder sb = new StringBuilder("ListaEnlazada[");
        Nodo actual = cabeza;
        while (actual != null) {
            sb.append(actual.getDato().toString());
            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }
}
