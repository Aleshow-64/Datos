package logicarecursividad;

import interfaz.TorresDeHanoiPanel;

public class TorresDeHanoi {
    private TorresDeHanoiPanel panel;
    
    public TorresDeHanoi(TorresDeHanoiPanel panel) {
        this.panel = panel;
    }
    
    public void resolver(int numDiscos, int origen, int auxiliar, int destino, int delay) {
        if (numDiscos > 0) {
            // Paso 1: Mover n-1 discos de origen a auxiliar
            resolver(numDiscos - 1, origen, destino, auxiliar, delay);
            
            // Paso 2: Mover el disco más grande a destino
            panel.moverDisco(origen, destino);
            
            // Pausa para la animación
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Paso 3: Mover n-1 discos de auxiliar a destino
            resolver(numDiscos - 1, auxiliar, origen, destino, delay);
        }
    }
}