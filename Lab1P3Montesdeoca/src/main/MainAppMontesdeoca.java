package main;

import controlador.ControladorReinasMontesdeoca;
import javax.swing.SwingUtilities;

public class MainAppMontesdeoca {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ControladorReinasMontesdeoca controlador = new ControladorReinasMontesdeoca();
                controlador.iniciar();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            }
        });
    }
}