package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import Modelo.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Panel personalizado para visualizar gráficamente un grafo de películas
 */
public class PanelVisualizacionGrafo extends JPanel {
    private GrafoPeliculas grafo;
    private Map<String, Point> posicionesNodos;
    private static final int RADIO_NODO = 35;
    private String peliculaSeleccionada = null;
    
    public PanelVisualizacionGrafo() {
        setBackground(new Color(30, 39, 46));
        setPreferredSize(new Dimension(800, 500));
        posicionesNodos = new HashMap<>();
    }
    
    public void setGrafo(GrafoPeliculas grafo) {
        this.grafo = grafo;
        calcularPosiciones();
        repaint();
    }
    
    public void setPeliculaSeleccionada(String titulo) {
        this.peliculaSeleccionada = titulo;
        repaint();
    }
    
    private void calcularPosiciones() {
        if (grafo == null || grafo.getTamanio() == 0) return;
        
        posicionesNodos.clear();
        Pelicula[] peliculas = grafo.obtenerTodasPeliculas();
        
        // Distribuir nodos en un círculo
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = Math.min(getWidth(), getHeight()) / 3;
        
        double anguloIncremento = 2 * Math.PI / peliculas.length;
        
        for (int i = 0; i < peliculas.length; i++) {
            double angulo = i * anguloIncremento - Math.PI / 2; // Comenzar desde arriba
            int x = centroX + (int) (radio * Math.cos(angulo));
            int y = centroY + (int) (radio * Math.sin(angulo));
            posicionesNodos.put(peliculas[i].getTitulo(), new Point(x, y));
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activar antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (grafo == null || grafo.getTamanio() == 0) {
            // Mostrar mensaje cuando no hay grafo
            g2d.setColor(new Color(149, 165, 166));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            String mensaje = "Grafo vacío - Construya el grafo para visualizar";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(mensaje, x, y);
            return;
        }
        
        // Redibujar posiciones si el tamaño cambió
        if (posicionesNodos.isEmpty()) {
            calcularPosiciones();
        }
        
        // Dibujar todas las aristas primero
        dibujarAristas(g2d);
        
        // Dibujar todos los nodos encima
        dibujarNodos(g2d);
        
        // Dibujar leyenda
        dibujarLeyenda(g2d);
    }
    
    private void dibujarAristas(Graphics2D g2d) {
        Pelicula[] peliculas = grafo.obtenerTodasPeliculas();
        
        // Para evitar dibujar aristas duplicadas
        for (int i = 0; i < peliculas.length; i++) {
            Pelicula p1 = peliculas[i];
            Point pos1 = posicionesNodos.get(p1.getTitulo());
            if (pos1 == null) continue;
            
            Pelicula[] relacionadas = grafo.obtenerRelacionadas(p1.getTitulo());
            
            for (Pelicula p2 : relacionadas) {
                // Solo dibujar una vez (de menor a mayor índice)
                int j = buscarIndice(peliculas, p2.getTitulo());
                if (j <= i) continue;
                
                Point pos2 = posicionesNodos.get(p2.getTitulo());
                if (pos2 == null) continue;
                
                // Obtener tipo de relación para color
                String tipoRelacion = grafo.obtenerTipoRelacion(p1.getTitulo(), p2.getTitulo());
                Color colorLinea = obtenerColorRelacion(tipoRelacion);
                
                // Determinar si resaltar la arista
                boolean resaltar = (peliculaSeleccionada != null && 
                                   (p1.getTitulo().equals(peliculaSeleccionada) || 
                                    p2.getTitulo().equals(peliculaSeleccionada)));
                
                if (resaltar) {
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(colorLinea);
                } else {
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.setColor(new Color(colorLinea.getRed(), colorLinea.getGreen(), colorLinea.getBlue(), 100));
                }
                
                g2d.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
            }
        }
    }
    
    private void dibujarNodos(Graphics2D g2d) {
        Pelicula[] peliculas = grafo.obtenerTodasPeliculas();
        
        for (Pelicula p : peliculas) {
            Point pos = posicionesNodos.get(p.getTitulo());
            if (pos == null) continue;
            
            boolean esSeleccionada = (peliculaSeleccionada != null && 
                                     p.getTitulo().equals(peliculaSeleccionada));
            
            dibujarNodo(g2d, p, pos.x, pos.y, esSeleccionada);
        }
    }
    
    private void dibujarNodo(Graphics2D g2d, Pelicula pelicula, int x, int y, boolean seleccionada) {
        // Sombra
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillOval(x - RADIO_NODO + 4, y - RADIO_NODO + 4, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Nodo con gradiente
        Color color1, color2;
        if (seleccionada) {
            color1 = new Color(231, 76, 60);  // Rojo brillante
            color2 = new Color(192, 57, 43);
        } else {
            color1 = new Color(26, 188, 156); // Turquesa
            color2 = new Color(22, 160, 133);
        }
        
        GradientPaint gradient = new GradientPaint(
            x - RADIO_NODO, y - RADIO_NODO, color1,
            x + RADIO_NODO, y + RADIO_NODO, color2
        );
        g2d.setPaint(gradient);
        g2d.fillOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Borde
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(seleccionada ? 3 : 2));
        g2d.drawOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Icono de película
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        String icono = "🎬";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(icono, x - fm.stringWidth(icono) / 2, y + fm.getAscent() / 2);
        
        // Título truncado debajo
        String titulo = pelicula.getTitulo();
        if (titulo.length() > 12) {
            titulo = titulo.substring(0, 10) + "..";
        }
        g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2d.setColor(seleccionada ? new Color(231, 76, 60) : new Color(236, 240, 241));
        fm = g2d.getFontMetrics();
        int tituloX = x - fm.stringWidth(titulo) / 2;
        int tituloY = y + RADIO_NODO + 15;
        g2d.drawString(titulo, tituloX, tituloY);
        
        // Número de relaciones
        if (seleccionada) {
            int numRelaciones = grafo.contarRelaciones(pelicula.getTitulo());
            String textoRel = numRelaciones + " rel.";
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2d.setColor(new Color(189, 195, 199));
            fm = g2d.getFontMetrics();
            int relX = x - fm.stringWidth(textoRel) / 2;
            int relY = y + RADIO_NODO + 28;
            g2d.drawString(textoRel, relX, relY);
        }
    }
    
    private void dibujarLeyenda(Graphics2D g2d) {
        int x = 15;
        int y = getHeight() - 90;
        
        // Fondo de la leyenda
        g2d.setColor(new Color(44, 62, 80, 200));
        g2d.fillRoundRect(x - 5, y - 20, 180, 85, 10, 10);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2d.drawString("RELACIONES:", x, y);
        
        y += 18;
        
        // Mismo Director
        g2d.setColor(new Color(155, 89, 182));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y, x + 25, y);
        g2d.setColor(new Color(236, 240, 241));
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2d.drawString("Mismo Director", x + 30, y + 4);
        
        y += 15;
        
        // Mismo Género
        g2d.setColor(new Color(52, 152, 219));
        g2d.drawLine(x, y, x + 25, y);
        g2d.setColor(new Color(236, 240, 241));
        g2d.drawString("Mismo Género", x + 30, y + 4);
        
        y += 15;
        
        // Mismo Año
        g2d.setColor(new Color(46, 204, 113));
        g2d.drawLine(x, y, x + 25, y);
        g2d.setColor(new Color(236, 240, 241));
        g2d.drawString("Mismo Año", x + 30, y + 4);
    }
    
    private Color obtenerColorRelacion(String tipoRelacion) {
        if (tipoRelacion == null) return new Color(149, 165, 166);
        
        switch (tipoRelacion) {
            case "Mismo Director":
                return new Color(155, 89, 182); // Púrpura
            case "Mismo Género":
                return new Color(52, 152, 219); // Azul
            case "Mismo Año":
                return new Color(46, 204, 113); // Verde
            default:
                return new Color(149, 165, 166); // Gris
        }
    }
    
    private int buscarIndice(Pelicula[] peliculas, String titulo) {
        for (int i = 0; i < peliculas.length; i++) {
            if (peliculas[i].getTitulo().equals(titulo)) {
                return i;
            }
        }
        return -1;
    }
}
