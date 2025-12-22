package Vista;

import javax.swing.*;
import java.awt.*;
import Modelo.*;

/**
 * Diálogo para demostrar las estructuras de datos avanzadas
 */
public class DialogoEstructurasAvanzadas extends JDialog {
    private PanelArbol panelArbol;
    private PanelGrafo panelGrafo;
    private Pelicula[] peliculasCargadas;
    
    public DialogoEstructurasAvanzadas(JFrame parent) {
        super(parent, "🎓 Estructuras de Datos Avanzadas", true);
        setSize(1000, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(45, 45, 45));
        
        // Panel superior - Título compacto
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        panelSuperior.setBackground(new Color(41, 128, 185));
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(52, 152, 219)),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        
        JLabel lblTitulo = new JLabel("🎓 Estructuras de Datos Avanzadas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        JLabel lblExplicacion = new JLabel(
            "<html>ABB: Búsqueda O(log n) • Grafo: Relaciones entre películas</html>"
        );
        lblExplicacion.setForeground(new Color(236, 240, 241));
        lblExplicacion.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblExplicacion.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panelSuperior.add(lblExplicacion, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central - Pestañas con estilo
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("SansSerif", Font.BOLD, 13));
        pestanas.setBackground(new Color(52, 73, 94));
        pestanas.setForeground(new Color(236, 240, 241));
        
        panelArbol = new PanelArbol();
        panelGrafo = new PanelGrafo();
        
        pestanas.addTab("🌳 Árbol Binario", panelArbol);
        pestanas.addTab("🕸️ Grafo", panelGrafo);
        
        add(pestanas, BorderLayout.CENTER);
        
        // Panel inferior - Botones con estilo
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelInferior.setBackground(new Color(45, 45, 45));
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    public void setPeliculas(Pelicula[] peliculas) {
        this.peliculasCargadas = peliculas;
        // Cargar automáticamente en todas las estructuras
        cargarEnTodasEstructuras();
    }
    
    private void cargarEnTodasEstructuras() {
        if (peliculasCargadas == null || peliculasCargadas.length == 0) {
            return;
        }
        
        panelArbol.cargarPeliculas(peliculasCargadas);
        panelGrafo.cargarPeliculas(peliculasCargadas);
    }
    
    public PanelArbol getPanelArbol() {
        return panelArbol;
    }
    
    public PanelGrafo getPanelGrafo() {
        return panelGrafo;
    }
}
