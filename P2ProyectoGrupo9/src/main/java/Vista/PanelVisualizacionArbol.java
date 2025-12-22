package Vista;

import javax.swing.*;
import java.awt.*;
import Modelo.*;

/**
 * Panel personalizado para visualizar gráficamente un árbol binario de búsqueda
 */
public class PanelVisualizacionArbol extends JPanel {
    private ArbolBinarioBusqueda arbol;
    private NodoArbol raiz;
    private static final int RADIO_NODO = 30;
    private static final int SEPARACION_HORIZONTAL = 50;
    private static final int SEPARACION_VERTICAL = 80;
    private Pelicula[] ordenRecorrido; // Orden de visita según el recorrido
    private String tipoRecorrido; // Tipo de recorrido actual
    private java.util.HashMap<String, Integer> ordenVisita; // Mapa de título -> orden
    
    public PanelVisualizacionArbol() {
        setBackground(new Color(30, 39, 46));
        setPreferredSize(new Dimension(800, 500));
        ordenVisita = new java.util.HashMap<>();
    }
    
    public void setArbol(ArbolBinarioBusqueda arbol) {
        this.arbol = arbol;
        this.ordenRecorrido = null;
        this.tipoRecorrido = null;
        this.ordenVisita.clear();
        repaint();
    }
    
    /**
     * Establece el recorrido actual para visualizar el orden de visita
     */
    public void setRecorrido(Pelicula[] recorrido, String tipo) {
        this.ordenRecorrido = recorrido;
        this.tipoRecorrido = tipo;
        this.ordenVisita.clear();
        
        // Crear mapa de título -> orden de visita
        if (recorrido != null) {
            for (int i = 0; i < recorrido.length; i++) {
                ordenVisita.put(recorrido[i].getTitulo(), i + 1);
            }
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activar antialiasing para mejor calidad visual
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (arbol == null || arbol.estaVacio()) {
            // Mostrar mensaje cuando no hay árbol
            g2d.setColor(new Color(149, 165, 166));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            String mensaje = "Árbol vacío - Agregue películas para visualizar";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(mensaje, x, y);
            return;
        }
        
        // Obtener todas las películas en inorden para calcular el ancho necesario
        Pelicula[] peliculas = arbol.recorridoInorden();
        if (peliculas.length == 0) return;
        
        // Calcular posición inicial centrada
        int centroX = getWidth() / 2;
        int inicioY = 50;
        
        // Mostrar tipo de recorrido si está activo
        if (tipoRecorrido != null) {
            g2d.setColor(new Color(46, 204, 113));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
            String info = "🔢 Recorrido: " + tipoRecorrido;
            g2d.drawString(info, 10, 25);
            
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2d.setColor(new Color(189, 195, 199));
            g2d.drawString("Los números verdes muestran el orden de visita", 10, 42);
        }
        
        // Dibujar el árbol recursivamente
        dibujarArbolRecursivo(g2d, peliculas, 0, peliculas.length - 1, centroX, inicioY, getWidth() / 4);
    }
    
    private int dibujarArbolRecursivo(Graphics2D g2d, Pelicula[] inorden, int inicio, int fin, int x, int y, int separacion) {
        if (inicio > fin) return -1;
        
        // El nodo central es la raíz de este subárbol (para ABB en inorden)
        int medio = (inicio + fin) / 2;
        Pelicula pelicula = inorden[medio];
        
        // Calcular posiciones de los hijos
        int separacionHijos = Math.max(separacion / 2, SEPARACION_HORIZONTAL);
        
        // Dibujar hijo izquierdo
        if (inicio < medio) {
            int hijoIzqX = x - separacion;
            int hijoIzqY = y + SEPARACION_VERTICAL;
            
            int posHijoIzq = dibujarArbolRecursivo(g2d, inorden, inicio, medio - 1, hijoIzqX, hijoIzqY, separacionHijos);
            
            // Dibujar línea al hijo izquierdo
            g2d.setColor(new Color(52, 152, 219));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y + RADIO_NODO, hijoIzqX, hijoIzqY - RADIO_NODO);
        }
        
        // Dibujar hijo derecho
        if (medio < fin) {
            int hijoDerX = x + separacion;
            int hijoDerY = y + SEPARACION_VERTICAL;
            
            int posHijoDer = dibujarArbolRecursivo(g2d, inorden, medio + 1, fin, hijoDerX, hijoDerY, separacionHijos);
            
            // Dibujar línea al hijo derecho
            g2d.setColor(new Color(52, 152, 219));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x, y + RADIO_NODO, hijoDerX, hijoDerY - RADIO_NODO);
        }
        
        // Dibujar el nodo actual
        dibujarNodo(g2d, pelicula, x, y);
        
        return medio;
    }
    
    private void dibujarNodo(Graphics2D g2d, Pelicula pelicula, int x, int y) {
        // Determinar si este nodo tiene orden de visita
        Integer orden = ordenVisita.get(pelicula.getTitulo());
        boolean tieneOrden = orden != null;
        
        // Sombra del nodo
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillOval(x - RADIO_NODO + 3, y - RADIO_NODO + 3, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Círculo del nodo con gradiente (cambiar color si tiene orden)
        GradientPaint gradient;
        if (tieneOrden) {
            gradient = new GradientPaint(
                x - RADIO_NODO, y - RADIO_NODO, new Color(46, 204, 113),
                x + RADIO_NODO, y + RADIO_NODO, new Color(39, 174, 96)
            );
        } else {
            gradient = new GradientPaint(
                x - RADIO_NODO, y - RADIO_NODO, new Color(52, 152, 219),
                x + RADIO_NODO, y + RADIO_NODO, new Color(41, 128, 185)
            );
        }
        g2d.setPaint(gradient);
        g2d.fillOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Borde del nodo
        g2d.setColor(new Color(236, 240, 241));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        
        // Texto del año o número de orden
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, tieneOrden ? 16 : 12));
        String texto = tieneOrden ? String.valueOf(orden) : String.valueOf(pelicula.getAnio());
        FontMetrics fm = g2d.getFontMetrics();
        int textoX = x - fm.stringWidth(texto) / 2;
        int textoY = y + fm.getAscent() / 2;
        g2d.drawString(texto, textoX, textoY);
        
        // Título debajo del nodo (truncado si es muy largo)
        String titulo = pelicula.getTitulo();
        if (titulo.length() > 15) {
            titulo = titulo.substring(0, 12) + "...";
        }
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2d.setColor(new Color(189, 195, 199));
        fm = g2d.getFontMetrics();
        int tituloX = x - fm.stringWidth(titulo) / 2;
        int tituloY = y + RADIO_NODO + 15;
        g2d.drawString(titulo, tituloX, tituloY);
    }
}
