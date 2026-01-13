package main;

public class SolucionNReinas {
    static int N = 8; // Tamaño del tablero

    public static void main(String[] args) {
        int[][] tablero = new int[N][N];
        
        System.out.println("=== SOLUCIÓN DE N-REINAS ===");
        System.out.println("Tamaño del tablero: " + N + "x" + N);
        
        // Solución 1: Encontrar primera solución
        if (resolverNReinas(tablero, 0)) {
            System.out.println("\n¡Solución encontrada para N = " + N + "!");
            imprimirSolucion(tablero);
        } else {
            System.out.println("\nNo se encontró una solución.");
        }
        
        // Solución 2: Contar todas las soluciones
        System.out.println("\n=== CONTANDO TODAS LAS SOLUCIONES ===");
        contarTodasSoluciones();
        
        // Solución 3: Probar con diferentes valores de N
        System.out.println("\n=== PRUEBAS CON DIFERENTES VALORES DE N ===");
        probarDiferentesN();
    }
    
    static boolean resolverNReinas(int[][] tablero, int col) {
        boolean exito = false; // Bandera de estado
        
        // CASO BASE: Si todas las reinas están colocadas
        if (col >= N) {
            exito = true;
        } else {
            // PASO RECURSIVO
            int i = 0;
            // Iteramos mientras haya filas y NO hayamos encontrado el éxito aún
            while (i < N && !exito) {
                // Comprobar si es seguro colocar una reina
                if (esSeguro(tablero, i, col)) {
                    // 1. Colocar la reina (línea donde se coloca)
                    tablero[i][col] = 1;
                    
                    // 2. Llamada recursiva: actualizamos 'exito' con el resultado
                    exito = resolverNReinas(tablero, col + 1);
                    
                    // 3. BACKTRACK: Si la llamada recursiva devolvió false, 
                    //    deshacemos el movimiento (línea específica del backtracking)
                    if (!exito) {
                        tablero[i][col] = 0; // ← AQUÍ OCURRE EL BACKTRACKING
                    }
                }
                i++;
            }
        }
        return exito;
    }

    static boolean esSeguro(int[][] tablero, int fila, int col) {
        boolean esSegura = true; // Asumimos que es segura hasta demostrar lo contrario
        int i, j;
        
        // 1. Verificar la fila hacia la izquierda
        for (i = 0; i < col && esSegura; i++) {
            if (tablero[fila][i] == 1) {
                esSegura = false;
            }
        }
        
        // 2. Verificar la diagonal superior izquierda
        for (i = fila, j = col; i >= 0 && j >= 0 && esSegura; i--, j--) {
            if (tablero[i][j] == 1) {
                esSegura = false;
            }
        }
        
        // 3. Verificar la diagonal inferior izquierda
        for (i = fila, j = col; j >= 0 && i < N; i++, j--) {
            // Nota: Agregamos '&& esSegura' en el loop para detener la búsqueda si ya falló
            if (esSegura && tablero[i][j] == 1) {
                esSegura = false;
            }
        }
        
        return esSegura;
    }
    
    static void imprimirSolucion(int[][] tablero) {
        System.out.println("\nRepresentación del tablero:");
        System.out.print("   ");
        for (int j = 0; j < N; j++) {
            System.out.print(" " + (char)('A' + j) + " ");
        }
        System.out.println();
        
        for (int i = 0; i < N; i++) {
            System.out.printf("%2d ", (i + 1));
            for (int j = 0; j < N; j++) {
                System.out.print(" " + (tablero[i][j] == 1 ? "Q" : ".") + " ");
            }
            System.out.println();
        }
    }
    
    static void contarTodasSoluciones() {
        int[][] tablero = new int[N][N];
        int[] contador = {0};
        
        contarTodasSolucionesRec(tablero, 0, contador);
        System.out.println("Total de soluciones para N = " + N + ": " + contador[0]);
    }
    
    static void contarTodasSolucionesRec(int[][] tablero, int col, int[] contador) {
        if (col >= N) {
            contador[0]++;
            return;
        }
        
        for (int i = 0; i < N; i++) {
            if (esSeguro(tablero, i, col)) {
                tablero[i][col] = 1;
                contarTodasSolucionesRec(tablero, col + 1, contador);
                tablero[i][col] = 0; // Backtracking
            }
        }
    }
    
    static void probarDiferentesN() {
        int[] valoresN = {1, 2, 3, 4, 5, 6, 7, 8};
        
        for (int n : valoresN) {
            N = n;
            int[][] tablero = new int[N][N];
            int[] contador = {0};
            
            contarTodasSolucionesRec(tablero, 0, contador);
            
            System.out.println("N = " + n + ": " + contador[0] + " solución(es)");
            
            if (n <= 4 && contador[0] > 0) {
                // Mostrar primera solución para N pequeños
                N = n;
                tablero = new int[N][N];
                if (resolverNReinas(tablero, 0)) {
                    imprimirSolucion(tablero);
                }
            }
            System.out.println();
        }
        
        // Restaurar N original
        N = 8;
    }
}