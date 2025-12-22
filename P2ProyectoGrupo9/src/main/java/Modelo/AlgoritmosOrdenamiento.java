package Modelo;

public class AlgoritmosOrdenamiento {
    
    /**
     * QuickSort - Ordenamiento rápido por título
     * Complejidad: O(n log n) promedio, O(n²) peor caso
     */
    public static void quickSortPorTitulo(Pelicula[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        quickSortRecursivo(array, 0, array.length - 1);
    }
    
    private static void quickSortRecursivo(Pelicula[] array, int bajo, int alto) {
        if (bajo < alto) {
            int indicePivote = particionar(array, bajo, alto);
            quickSortRecursivo(array, bajo, indicePivote - 1);
            quickSortRecursivo(array, indicePivote + 1, alto);
        }
    }
    
    private static int particionar(Pelicula[] array, int bajo, int alto) {
        Pelicula pivote = array[alto];
        int i = bajo - 1;
        
        for (int j = bajo; j < alto; j++) {
            if (array[j].getTitulo().compareToIgnoreCase(pivote.getTitulo()) <= 0) {
                i++;
                intercambiar(array, i, j);
            }
        }
        
        intercambiar(array, i + 1, alto);
        return i + 1;
    }
    
    /**
     * QuickSort - Ordenamiento por año
     */
    public static void quickSortPorAnio(Pelicula[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        quickSortPorAnioRecursivo(array, 0, array.length - 1);
    }
    
    private static void quickSortPorAnioRecursivo(Pelicula[] array, int bajo, int alto) {
        if (bajo < alto) {
            int indicePivote = particionarPorAnio(array, bajo, alto);
            quickSortPorAnioRecursivo(array, bajo, indicePivote - 1);
            quickSortPorAnioRecursivo(array, indicePivote + 1, alto);
        }
    }
    
    private static int particionarPorAnio(Pelicula[] array, int bajo, int alto) {
        int pivote = array[alto].getAnio();
        int i = bajo - 1;
        
        for (int j = bajo; j < alto; j++) {
            if (array[j].getAnio() <= pivote) {
                i++;
                intercambiar(array, i, j);
            }
        }
        
        intercambiar(array, i + 1, alto);
        return i + 1;
    }
    
    /**
     * MergeSort - Ordenamiento por título
     * Complejidad: O(n log n) en todos los casos
     */
    public static void mergeSortPorTitulo(Pelicula[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortRecursivo(array, 0, array.length - 1);
    }
    
    private static void mergeSortRecursivo(Pelicula[] array, int izq, int der) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            
            mergeSortRecursivo(array, izq, medio);
            mergeSortRecursivo(array, medio + 1, der);
            
            merge(array, izq, medio, der);
        }
    }
    
    private static void merge(Pelicula[] array, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;
        
        Pelicula[] L = new Pelicula[n1];
        Pelicula[] R = new Pelicula[n2];
        
        for (int i = 0; i < n1; i++) {
            L[i] = array[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[medio + 1 + j];
        }
        
        int i = 0, j = 0, k = izq;
        
        while (i < n1 && j < n2) {
            if (L[i].getTitulo().compareToIgnoreCase(R[j].getTitulo()) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
    
    /**
     * MergeSort - Ordenamiento por año
     */
    public static void mergeSortPorAnio(Pelicula[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortPorAnioRecursivo(array, 0, array.length - 1);
    }
    
    private static void mergeSortPorAnioRecursivo(Pelicula[] array, int izq, int der) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            
            mergeSortPorAnioRecursivo(array, izq, medio);
            mergeSortPorAnioRecursivo(array, medio + 1, der);
            
            mergePorAnio(array, izq, medio, der);
        }
    }
    
    private static void mergePorAnio(Pelicula[] array, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;
        
        Pelicula[] L = new Pelicula[n1];
        Pelicula[] R = new Pelicula[n2];
        
        for (int i = 0; i < n1; i++) {
            L[i] = array[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[medio + 1 + j];
        }
        
        int i = 0, j = 0, k = izq;
        
        while (i < n1 && j < n2) {
            if (L[i].getAnio() <= R[j].getAnio()) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
    
    /**
     * Búsqueda Binaria - Requiere array ordenado por título
     * Complejidad: O(log n)
     */
    public static Pelicula busquedaBinariaPorTitulo(Pelicula[] array, String titulo) {
        if (array == null || array.length == 0 || titulo == null) {
            return null;
        }
        
        int izq = 0;
        int der = array.length - 1;
        
        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            int comparacion = array[medio].getTitulo().compareToIgnoreCase(titulo.trim());
            
            if (comparacion == 0) {
                return array[medio];
            } else if (comparacion < 0) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        
        return null;
    }
    
    private static void intercambiar(Pelicula[] array, int i, int j) {
        Pelicula temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
