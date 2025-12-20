package estructuras;

public class GrafoMatriz {
    private int[][] matriz;
    private String[] ciudades;
    private int numCiudades;
    private static final int NO_HAY_RUTA = -1;

    public GrafoMatriz() {
        this.numCiudades = 5;
        this.matriz = new int[numCiudades][numCiudades];
        this.ciudades = new String[numCiudades];

        for (int i = 0; i < numCiudades; i++) {
            for (int j = 0; j < numCiudades; j++) {
                matriz[i][j] = NO_HAY_RUTA;
            }
        }
    }

    public void definirCiudad(int indice, String nombre) {
        if (indice >= 0 && indice < numCiudades) {
            ciudades[indice] = nombre;
        }
    }

    public void nuevoArco(int a, int b, int peso) {
        if (a >= 0 && a < numCiudades && b >= 0 && b < numCiudades) {
            matriz[a][b] = peso;
            matriz[b][a] = peso; 
        }
    }

    public void imprimirMatriz() {
        System.out.print("     ");
        for (int i = 0; i < numCiudades; i++) {
            System.out.printf("%-12s", ciudades[i].substring(0, Math.min(10, ciudades[i].length())));
        }
        System.out.println();
        
        for (int i = 0; i < numCiudades; i++) {
            System.out.printf("%-5s", ciudades[i].substring(0, Math.min(4, ciudades[i].length())));
            for (int j = 0; j < numCiudades; j++) {
                if (matriz[i][j] == NO_HAY_RUTA) {
                    System.out.printf("%-12s", "-1");
                } else {
                    System.out.printf("%-12d", matriz[i][j]);
                }
            }
            System.out.println();
        }
    }
    
    public void mostrarRuta(String origen, String destino) {
        int idxOrigen = -1, idxDestino = -1;
        
        for (int i = 0; i < numCiudades; i++) {
            if (ciudades[i].equals(origen)) idxOrigen = i;
            if (ciudades[i].equals(destino)) idxDestino = i;
        }
        
        if (idxOrigen != -1 && idxDestino != -1) {
            int distancia = matriz[idxOrigen][idxDestino];
            if (distancia != NO_HAY_RUTA) {
                System.out.println("Ruta: " + origen + " ↔ " + destino + " | Distancia: " + distancia + " km");
            } else {
                System.out.println("No existe ruta directa entre " + origen + " y " + destino);
            }
        } else {
            System.out.println("Una o ambas ciudades no existen en el grafo.");
        }
    }

    public String getNombreCiudad(int indice) {
        if (indice >= 0 && indice < numCiudades) {
            return ciudades[indice];
        }
        return "Índice inválido";
    }

    private int minDistancia(int[] distancias, boolean[] visitado) {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < numCiudades; v++) {
            // Si no ha sido visitado Y su distancia es menor o igual al mínimo actual
            if (!visitado[v] && distancias[v] <= min) {
                min = distancias[v];
                min_index = v;
            }
        }
        return min_index;
    }

    // Método principal Dijkstra
    public void dijkstra(int nodoOrigen) {
        // 1. VALIDACIÓN INICIAL
        if (nodoOrigen < 0 || nodoOrigen >= numCiudades) {
            System.out.println("Error: El nodo origen no existe en el grafo.");
            return;
        }

        // 2. INICIALIZACIÓN
        int[] distancias = new int[numCiudades];
        boolean[] visitado = new boolean[numCiudades];
        int[] padres = new int[numCiudades]; // Para almacenar el predecesor de cada nodo

        for (int i = 0; i < numCiudades; i++) {
            distancias[i] = Integer.MAX_VALUE;
            visitado[i] = false;
            padres[i] = -1; // Inicialmente sin padre
        }

        distancias[nodoOrigen] = 0;
        padres[nodoOrigen] = nodoOrigen; // El origen es su propio padre

        // 3. BUCLE PRINCIPAL
        for (int count = 0; count < numCiudades - 1; count++) {
            // A. ELEGIR el nodo no visitado con menor distancia
            int u = minDistancia(distancias, visitado);
            
            // Si no hay más nodos alcanzables, salir
            if (u == -1) {
                break;
            }
            
            // B. MARCAR como visitado
            visitado[u] = true;

            // C. RELAJAR (Actualizar vecinos)
            for (int v = 0; v < numCiudades; v++) {
                // Extraemos las condiciones para claridad
                boolean esAdyacente = (matriz[u][v] != NO_HAY_RUTA);
                boolean noVisitado = (!visitado[v]);
                boolean origenAlcanzable = (distancias[u] != Integer.MAX_VALUE);
                
                // Validación para evitar overflow
                boolean esCaminoMasCorto = false;
                if (origenAlcanzable && esAdyacente) {
                    long suma = (long)distancias[u] + (long)matriz[u][v];
                    esCaminoMasCorto = (suma < distancias[v] && suma < Integer.MAX_VALUE);
                }

                // Si se cumplen todas las condiciones, actualizamos
                if (esAdyacente && noVisitado && origenAlcanzable && esCaminoMasCorto) {
                    distancias[v] = distancias[u] + matriz[u][v];
                    padres[v] = u; // Guardamos el predecesor
                }
            }
        }

        // 4. MOSTRAR RESULTADOS
        imprimirSolucion(distancias, padres, nodoOrigen);
    }

    // Método para imprimir la solución completa
    private void imprimirSolucion(int[] distancias, int[] padres, int origen) {
        System.out.println("\n=== RUTAS ÓPTIMAS DESDE " + ciudades[origen] + " ===");
        System.out.printf("%-20s %-25s %s\n", "Destino", "Distancia Mínima (Km)", "Ruta");
        System.out.println("---------------------------------------------------------------");
        
        for (int i = 0; i < numCiudades; i++) {
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.printf("%-20s %-25s\n", ciudades[i], "No alcanzable");
            } else {
                System.out.printf("%-20s %-25d %s\n", 
                    ciudades[i], 
                    distancias[i],
                    obtenerRutaCompleta(padres, i, origen));
            }
        }
    }

    // Método para reconstruir la ruta completa desde el origen hasta un nodo
    private String obtenerRutaCompleta(int[] padres, int destino, int origen) {
        if (padres[destino] == -1) {
            return "Sin ruta";
        }
        
        StringBuilder ruta = new StringBuilder();
        int actual = destino;
        java.util.Stack<String> pila = new java.util.Stack<>();
        
        // Reconstruimos la ruta desde el destino hacia el origen
        while (actual != origen) {
            pila.push(ciudades[actual]);
            actual = padres[actual];
        }
        pila.push(ciudades[origen]);
        
        // Construimos la cadena en orden correcto
        while (!pila.isEmpty()) {
            ruta.append(pila.pop());
            if (!pila.isEmpty()) {
                ruta.append(" → ");
            }
        }
        
        return ruta.toString();
    }

    // Dijkstra ejemplo del PDF
    public void pruebaDijkstraEjemplo() {
        definirCiudad(0, "Central");
        definirCiudad(1, "Norte");
        definirCiudad(2, "Este");
        definirCiudad(3, "Sur");
        definirCiudad(4, "Oeste");
        
        // Limpiar matriz
        for (int i = 0; i < numCiudades; i++) {
            for (int j = 0; j < numCiudades; j++) {
                matriz[i][j] = NO_HAY_RUTA;
            }
        }
        
        // Definir rutas según el ejemplo del PDF
        nuevoArco(0, 1, 10);  // Central -> Norte: 10 km
        nuevoArco(0, 4, 5);   // Central -> Oeste: 5 km
        nuevoArco(1, 2, 1);   // Norte -> Este: 1 km
        nuevoArco(4, 1, 3);   // Oeste -> Norte: 3 km (Atajo)
        nuevoArco(4, 2, 9);   // Oeste -> Este: 9 km
        nuevoArco(2, 3, 4);   // Este -> Sur: 4 km
        nuevoArco(4, 3, 2);   // Oeste -> Sur: 2 km
        
        System.out.println("1. Visualización de la Red:");
        imprimirMatriz();
        System.out.println("\n2. Calculando rutas óptimas...");
        dijkstra(0);
    }
}