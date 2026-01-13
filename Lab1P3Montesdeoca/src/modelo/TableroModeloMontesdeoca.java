package modelo;

public class TableroModeloMontesdeoca {
    private int[][] tablero;
    private int N;
    private int solucionesEncontradas;

    public TableroModeloMontesdeoca(int N) {
        this.N = N;
        this.tablero = new int[N][N];
        this.solucionesEncontradas = 0;
    }

    public boolean resolverNReinas() {
        return resolverNReinas(0);
    }

    private boolean resolverNReinas(int col) {
        if (col >= N) {
            solucionesEncontradas++;
            return true;
        }

        for (int fila = 0; fila < N; fila++) {
            if (esSeguro(fila, col)) {
                tablero[fila][col] = 1;

                if (resolverNReinas(col + 1)) {
                    return true;
                }

                tablero[fila][col] = 0;
            }
        }
        return false;
    }

    public void encontrarTodasLasSoluciones() {
        solucionesEncontradas = 0;
        encontrarTodasLasSoluciones(0);
    }

    private void encontrarTodasLasSoluciones(int col) {
        if (col >= N) {
            solucionesEncontradas++;
            return;
        }

        for (int fila = 0; fila < N; fila++) {
            if (esSeguro(fila, col)) {
                tablero[fila][col] = 1;
                encontrarTodasLasSoluciones(col + 1);
                tablero[fila][col] = 0;
            }
        } 	
    }

    private boolean esSeguro(int fila, int col) {
        // Verificar misma fila a la izquierda
        for (int i = 0; i < col; i++) {
            if (tablero[fila][i] == 1) {
                return false;
            }
        }

        // Diagonal superior izquierda
        for (int i = fila, j = col; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }

        // Diagonal inferior izquierda
        for (int i = fila, j = col; j >= 0 && i < N; i++, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public int getN() {
        return N;
    }

    public int getSolucionesEncontradas() {
        return solucionesEncontradas;
    }

    public void reiniciarTablero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tablero[i][j] = 0;
            }
        }
        solucionesEncontradas = 0;
    }

    public void setN(int n) {
        this.N = n;
        this.tablero = new int[n][n];
        reiniciarTablero();
    }
}