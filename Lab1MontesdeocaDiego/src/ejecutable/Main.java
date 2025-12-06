package ejecutable;

import vista.VistaArbol;
import controlador.ControladorArbol;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaArbol vista = new VistaArbol();
                ControladorArbol controlador = new ControladorArbol(vista);
                vista.mostrar();
                
                // Mensaje inicial
                vista.actualizarResultado("=== SISTEMA DE ÁRBOLES BINARIOS ===\n");
                vista.actualizarResultado("Autor: Diego Montesdeoca\n");
                vista.actualizarResultado("===================================\n\n");
                vista.actualizarResultado("INSTRUCCIONES:\n");
                vista.actualizarResultado("1. Use 'Crear Árbol de Joyanes' para generar el ejemplo del libro\n");
                vista.actualizarResultado("2. Use 'Crear Árbol Expresión' para evaluar expresiones matemáticas\n");
                vista.actualizarResultado("3. Ejemplo de expresión: 3 4 + 5 2 - *\n");
                vista.actualizarResultado("===================================\n\n");
            }
        });
    }
}