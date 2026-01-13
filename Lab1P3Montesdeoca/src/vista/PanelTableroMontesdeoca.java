package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import modelo.TableroModeloMontesdeoca;

public class PanelTableroMontesdeoca extends JPanel {
    private TableroModeloMontesdeoca modelo;
    private int tamanoCelda = 60;
    
    // Nueva paleta de colores - Tema oscuro moderno
    private static final Color COLOR_CLARO = new Color(50, 50, 70);
    private static final Color COLOR_OSCURO = new Color(30, 30, 50);
    private static final Color COLOR_REINA_GRADIENTE1 = new Color(138, 43, 226);
    private static final Color COLOR_REINA_GRADIENTE2 = new Color(75, 0, 130);
    private static final Color COLOR_BORDE_CELDA = new Color(80, 80, 110);
    private static final Color COLOR_HIGHLIGHT = new Color(0, 191, 255, 50);
    private static final Color COLOR_SOMBRA = new Color(0, 0, 0, 100);
    private static final Color COLOR_TEXTO = new Color(220, 220, 255);

    public PanelTableroMontesdeoca(TableroModeloMontesdeoca modelo) {
        this.modelo = modelo;
        setPreferredSize(new Dimension(600, 600));
        setBackground(new Color(30, 30, 46));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Configuración de calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (modelo == null) return;

        int N = modelo.getN();
        int[][] tablero = modelo.getTablero();

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        tamanoCelda = Math.min((panelWidth - 60) / N, (panelHeight - 60) / N);

        int tableroWidth = N * tamanoCelda;
        int tableroHeight = N * tamanoCelda;
        int offsetX = (panelWidth - tableroWidth) / 2;
        int offsetY = (panelHeight - tableroHeight) / 2;

        // Dibujar fondo del tablero con efecto de profundidad
        g2d.setColor(COLOR_SOMBRA);
        g2d.fillRoundRect(offsetX + 5, offsetY + 5, tableroWidth, tableroHeight, 15, 15);

        // Dibujar tablero con estilo moderno
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                int x = offsetX + col * tamanoCelda;
                int y = offsetY + fila * tamanoCelda;

                // Patrón de ajedrez
                Color celdaColor = ((fila + col) % 2 == 0) ? COLOR_CLARO : COLOR_OSCURO;
                
                // Efecto de celda con gradiente
                GradientPaint gradient = new GradientPaint(
                    x, y, celdaColor,
                    x + tamanoCelda, y + tamanoCelda, celdaColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(x, y, tamanoCelda, tamanoCelda, 5, 5);

                // Borde de celda sutil
                g2d.setColor(COLOR_BORDE_CELDA);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(x, y, tamanoCelda, tamanoCelda, 5, 5);

                // Dibujar reina si existe
                if (tablero[fila][col] == 1) {
                    dibujarReinaModerno(g2d, x, y, tamanoCelda);
                }

                // Resaltar posición con efecto hover
                if (tamanoCelda > 40) {
                    g2d.setColor(COLOR_HIGHLIGHT);
                    g2d.setStroke(new BasicStroke(2));
                    if (fila == 0) g2d.drawLine(x, y, x + tamanoCelda, y);
                    if (col == 0) g2d.drawLine(x, y, x, y + tamanoCelda);
                }
            }
        }

        // Dibujar borde exterior con efecto neón
        g2d.setColor(new Color(0, 191, 255));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(offsetX - 1, offsetY - 1, tableroWidth + 2, tableroHeight + 2, 15, 15);

        // Dibujar coordenadas si el tablero no es muy grande
        if (N <= 12 && tamanoCelda > 30) {
            dibujarCoordenadas(g2d, offsetX, offsetY, N);
        }
    }

    private void dibujarReinaModerno(Graphics2D g2d, int x, int y, int size) {
        int padding = size / 8;
        int diametro = size - (padding * 2);
        int centerX = x + size / 2;
        int centerY = y + size / 2;

        // Efecto de luz/glow
        for (int i = 0; i < 3; i++) {
            int glowSize = diametro + i * 4;
            int glowX = centerX - glowSize / 2;
            int glowY = centerY - glowSize / 2;
            g2d.setColor(new Color(138, 43, 226, 30 - i * 10));
            g2d.fillOval(glowX, glowY, glowSize, glowSize);
        }

        // Cuerpo de la reina con gradiente radial
        RadialGradientPaint gradient = new RadialGradientPaint(
            centerX - size/10, centerY - size/10,
            diametro/2,
            new float[]{0.0f, 1.0f},
            new Color[]{COLOR_REINA_GRADIENTE1, COLOR_REINA_GRADIENTE2}
        );
        g2d.setPaint(gradient);
        g2d.fillOval(x + padding, y + padding, diametro, diametro);

        // Borde con efecto metálico
        g2d.setColor(new Color(200, 200, 255));
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawOval(x + padding, y + padding, diametro, diametro);

        // Símbolo de corona
        g2d.setColor(Color.WHITE);
        Font font = new Font("Segoe UI Emoji", Font.BOLD, Math.max(size / 3, 16));
        g2d.setFont(font);
        
        String[] simbolos = {"♛", "♕", "👑"};
        String simbolo = simbolos[0];
        
        FontMetrics fm = g2d.getFontMetrics();
        int textoX = centerX - fm.stringWidth(simbolo) / 2;
        int textoY = centerY + fm.getAscent() / 2 - 3;
        
        // Sombra del símbolo
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(simbolo, textoX + 1, textoY + 1);
        
        // Símbolo principal
        g2d.setColor(Color.WHITE);
        g2d.drawString(simbolo, textoX, textoY);
        
        // Destello
        g2d.setColor(new Color(255, 255, 255, 150));
        g2d.fillOval(centerX - size/8, centerY - size/3, size/8, size/8);
    }

    private void dibujarCoordenadas(Graphics2D g2d, int offsetX, int offsetY, int N) {
        g2d.setColor(COLOR_TEXTO);
        g2d.setFont(new Font("Arial", Font.BOLD, Math.max(tamanoCelda/6, 10)));

        // Coordenadas de filas (izquierda)
        for (int i = 0; i < N; i++) {
            String coord = String.valueOf(i + 1);
            int y = offsetY + i * tamanoCelda + tamanoCelda/2 + 5;
            g2d.drawString(coord, offsetX - 25, y);
        }

        // Coordenadas de columnas (arriba)
        for (int i = 0; i < N; i++) {
            String coord = String.valueOf((char)('A' + i));
            int x = offsetX + i * tamanoCelda + tamanoCelda/2 - 5;
            g2d.drawString(coord, x, offsetY - 10);
        }
    }

    public void setModelo(TableroModeloMontesdeoca modelo) {
        this.modelo = modelo;
        repaint();
    }
}