package ejecutable;

import interfaz.TorresDeHanoiPanel;
import logicarecursividad.TorresDeHanoi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TorresDeHanoiApp {
    public static void main(String[] args) {
        // Usar SwingUtilities para asegurar que la GUI se cree en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                crearYMostrarGUI();
            }
        });
    }
    
    private static void crearYMostrarGUI() {
        // Crear el panel con 3 discos por defecto
        TorresDeHanoiPanel panel = new TorresDeHanoiPanel(3);

        // Crear la ventana principal
        JFrame frame = new JFrame("Torres de Hanoi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel de controles
        JPanel controlPanel = new JPanel();
        JLabel label = new JLabel("Número de discos:");
        JTextField numDiscosField = new JTextField("3", 5);
        JButton startButton = new JButton("Iniciar");
        JButton resetButton = new JButton("Reiniciar");

        JLabel velocidadLabel = new JLabel("Velocidad:");
        JSlider velocidadSlider = new JSlider(1, 10, 5);
        velocidadSlider.setMajorTickSpacing(1);
        velocidadSlider.setPaintTicks(true);
        velocidadSlider.setPaintLabels(true);

        // Agregar componentes al panel de control
        controlPanel.add(label);
        controlPanel.add(numDiscosField);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        controlPanel.add(velocidadLabel);
        controlPanel.add(velocidadSlider);

        // Configurar la ventana
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);

        // ActionListener para el botón Iniciar
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numDiscos = Integer.parseInt(numDiscosField.getText());
                    if (numDiscos < 1 || numDiscos > 10) {
                        JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número entre 1 y 10.",
                                "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    panel.reiniciar(numDiscos);
                    
                    TorresDeHanoi hanoi = new TorresDeHanoi(panel);
                    int delay = 1000 / velocidadSlider.getValue();
                    
                    // Ejecutar en un hilo separado para no bloquear la interfaz
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            hanoi.resolver(numDiscos, 0, 1, 2, delay);
                        }
                    }).start();
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido de discos.",
                            "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener para el botón Reiniciar
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numDiscos = Integer.parseInt(numDiscosField.getText());
                    if (numDiscos < 1 || numDiscos > 10) {
                        JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número entre 1 y 10.",
                                "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    panel.reiniciar(numDiscos);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido de discos.",
                            "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}