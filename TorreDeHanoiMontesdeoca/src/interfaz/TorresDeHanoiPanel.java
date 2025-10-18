package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TorresDeHanoiPanel extends JPanel {
    private Stack<Integer>[] torres;
    private List<Color> discoColores;
    private int movimientos;

    @SuppressWarnings("unchecked")
    public TorresDeHanoiPanel(int numDiscos) {
        torres = new Stack[3];
        discoColores = new ArrayList<>();
        movimientos = 0;

        for (int i = 0; i < 3; i++) {
            torres[i] = new Stack<>();
        }

        // Inicializar la torre 0 con los discos
        for (int i = numDiscos; i > 0; i--) {
            torres[0].push(i);
            discoColores.add(new Color((int) (Math.random() * 0x1000000)));
        }

        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
    }

    public void moverDisco(int origen, int destino) {
        if (!torres[origen].isEmpty()) {
            int disco = torres[origen].pop();
            torres[destino].push(disco);
            movimientos++;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int torreWidth = 20;
        int baseHeight = 30;
        int spacing = width / 4;

        // Dibujar base
        g.setColor(Color.BLACK);
        g.fillRect(0, height - baseHeight, width, baseHeight);

        // Dibujar torres
        for (int i = 0; i < 3; i++) {
            int x = spacing * (i + 1) - torreWidth / 2;
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, height - baseHeight - 200, torreWidth, 200);
            
            // Etiqueta de la torre
            g.setColor(Color.BLACK);
            g.drawString("Torre " + (char)('A' + i), x - 10, height - baseHeight + 20);
        }

        // Dibujar discos
        for (int torreIndex = 0; torreIndex < 3; torreIndex++) {
            int xBase = spacing * (torreIndex + 1);
            int yBase = height - baseHeight;
            
            for (int i = 0; i < torres[torreIndex].size(); i++) {
                int disco = torres[torreIndex].get(i);
                int discoAncho = disco * 25;
                int discoAlto = 20;
                int x = xBase - discoAncho / 2;
                int y = yBase - (i + 1) * discoAlto;
                
                g.setColor(discoColores.get(disco - 1));
                g.fillRect(x, y, discoAncho, discoAlto);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, discoAncho, discoAlto);
            }
        }

        // Mostrar contador de movimientos
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Movimientos: " + movimientos, 20, 30);
    }

    public void reiniciar(int numDiscos) {
        movimientos = 0;
        for (int i = 0; i < 3; i++) {
            torres[i].clear();
        }
        discoColores.clear();
        
        for (int i = numDiscos; i > 0; i--) {
            torres[0].push(i);
            discoColores.add(new Color((int) (Math.random() * 0x1000000)));
        }
        repaint();
    }
}