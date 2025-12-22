package Vista;

import javax.swing.*;
import java.awt.*;
import Modelo.*;

/**
 * Panel para demostrar el Grafo de Relaciones entre Películas
 */
public class PanelGrafo extends JPanel {
    private GrafoPeliculas grafo;
    private JTextArea txtResultados;
    private JTextField txtPeliculaConsulta;
    private JComboBox<String> cmbOperacion;
    private JLabel lblEstadisticas;
    private PanelVisualizacionGrafo panelVisualizacion;
    
    public PanelGrafo() {
        grafo = new GrafoPeliculas(100);
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(52, 73, 94));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel superior - Controles con estilo
        JPanel panelControles = new JPanel(new BorderLayout(10, 10));
        panelControles.setBackground(new Color(44, 62, 80));
        panelControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(26, 188, 156), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblInfo = new JLabel("📊 Representa conexiones: Mismo Director, Mismo Género, Mismo Año");
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblInfo.setForeground(new Color(26, 188, 156));
        panelControles.add(lblInfo, BorderLayout.NORTH);
        
        JPanel panelOps = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelOps.setOpaque(false);
        
        JButton btnCargar = crearBotonEstilizado("📥 Construir Grafo", new Color(26, 188, 156));
        btnCargar.addActionListener(e -> construirGrafo());
        panelOps.add(btnCargar);
        
        panelOps.add(Box.createHorizontalStrut(20));
        
        JLabel lblOp = new JLabel("Operación:");
        lblOp.setForeground(new Color(236, 240, 241));
        lblOp.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelOps.add(lblOp);
        
        cmbOperacion = new JComboBox<>(new String[]{
            "Ver Películas Relacionadas",
            "Ver Estadísticas"
        });
        cmbOperacion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cmbOperacion.setBackground(new Color(52, 73, 94));
        cmbOperacion.setForeground(Color.WHITE);
        panelOps.add(cmbOperacion);
        
        JLabel lblPeli = new JLabel("  Película:");
        lblPeli.setForeground(new Color(236, 240, 241));
        lblPeli.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelOps.add(lblPeli);
        
        txtPeliculaConsulta = new JTextField(15);
        txtPeliculaConsulta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtPeliculaConsulta.setBackground(new Color(52, 73, 94));
        txtPeliculaConsulta.setForeground(Color.WHITE);
        txtPeliculaConsulta.setCaretColor(Color.WHITE);
        txtPeliculaConsulta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(149, 165, 166), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panelOps.add(txtPeliculaConsulta);
        
        JButton btnEjecutar = crearBotonEstilizado("▶ Ejecutar", new Color(52, 152, 219));
        btnEjecutar.addActionListener(e -> ejecutarOperacion());
        panelOps.add(btnEjecutar);
        
        panelControles.add(panelOps, BorderLayout.CENTER);
        
        add(panelControles, BorderLayout.NORTH);
        
        // Panel central - Dividido
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setOpaque(false);
        
        // Izquierda - Visualización gráfica del grafo
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(new Color(44, 62, 80));
        panelIzq.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(26, 188, 156), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTituloVis = new JLabel("🕸️ Visualización del Grafo");
        lblTituloVis.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTituloVis.setForeground(new Color(26, 188, 156));
        lblTituloVis.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelIzq.add(lblTituloVis, BorderLayout.NORTH);
        
        panelVisualizacion = new PanelVisualizacionGrafo();
        JScrollPane scrollVis = new JScrollPane(panelVisualizacion);
        scrollVis.setBorder(null);
        scrollVis.getVerticalScrollBar().setUnitIncrement(16);
        panelIzq.add(scrollVis, BorderLayout.CENTER);
        
        panelCentral.add(panelIzq);
        
        // Derecha - Resultados y estadísticas
        JPanel panelDer = new JPanel(new BorderLayout(5, 5));
        panelDer.setBackground(new Color(44, 62, 80));
        panelDer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        lblEstadisticas = new JLabel("<html><b>Grafo vacío</b><br>Use 'Construir Grafo' primero</html>");
        lblEstadisticas.setForeground(new Color(236, 240, 241));
        lblEstadisticas.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstadisticas.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelDer.add(lblEstadisticas, BorderLayout.NORTH);
        
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtResultados.setBackground(new Color(52, 73, 94));
        txtResultados.setForeground(new Color(236, 240, 241));
        txtResultados.setCaretColor(Color.WHITE);
        txtResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtResultados.setText(
            "CÓMO SE RELACIONAN LAS PELÍCULAS:\n\n" +
            "El grafo conecta automáticamente\n" +
            "películas que comparten características\n" +
            "comunes.\n\n" +
            "TIPOS DE RELACIONES:\n\n" +
            "🎬 Mismo Director:\n" +
            "   Películas del mismo director\n\n" +
            "🎭 Mismo Género:\n" +
            "   Películas del mismo género\n\n" +
            "📅 Mismo Año:\n" +
            "   Películas del mismo año\n\n" +
            "PRIORIDAD:\n" +
            "Director > Género > Año"
        );
        
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(149, 165, 166), 1));
        panelDer.add(scroll, BorderLayout.CENTER);
        
        panelCentral.add(panelDer);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDesc = new JLabel("<html><i>El grafo conecta películas según sus características comunes.</i></html>");
        panelInferior.add(lblDesc);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private Pelicula[] peliculasActuales;
    
    public void cargarPeliculas(Pelicula[] peliculas) {
        this.peliculasActuales = peliculas;
        txtResultados.setText(String.format(
            "%d películas cargadas.\n\n" +
            "Presione 'Construir Grafo' para crear las relaciones automáticamente.",
            peliculas.length
        ));
    }
    
    private void construirGrafo() {
        if (peliculasActuales == null || peliculasActuales.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay películas cargadas", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        grafo = new GrafoPeliculas(peliculasActuales.length + 10);
        
        long inicio = System.nanoTime();
        
        // Agregar vértices
        for (Pelicula p : peliculasActuales) {
            grafo.agregarVertice(p);
        }
        
        // Construir relaciones automáticas
        grafo.construirRelacionesAutomaticas();
        
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        actualizarEstadisticas();
        panelVisualizacion.setGrafo(grafo); // Actualizar visualización gráfica
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== GRAFO CONSTRUIDO ===\n\n");
        sb.append(String.format("Tiempo de construcción: %.4f ms\n\n", tiempoMs));
        sb.append(grafo.obtenerEstadisticas());
        sb.append("\n\nAhora puede explorar las relaciones usando las operaciones disponibles.");
        
        txtResultados.setText(sb.toString());
        
        JOptionPane.showMessageDialog(this, 
            "Grafo construido exitosamente",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ejecutarOperacion() {
        if (grafo.getTamanio() == 0) {
            JOptionPane.showMessageDialog(this, "Debe construir el grafo primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int operacion = cmbOperacion.getSelectedIndex();
        
        if (operacion == 1) { // Ver estadísticas
            txtResultados.setText(grafo.obtenerEstadisticas());
            return;
        }
        
        // operacion == 0: Ver películas relacionadas
        String titulo = txtPeliculaConsulta.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el título de una película", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        long inicio = System.nanoTime();
        mostrarRelacionadas(titulo);
        panelVisualizacion.setPeliculaSeleccionada(titulo); // Resaltar en la visualización
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        txtResultados.append(String.format("\n\nTiempo de ejecución: %.4f ms", tiempoMs));
    }
    
    private void mostrarRelacionadas(String titulo) {
        Pelicula[] relacionadas = grafo.obtenerRelacionadas(titulo);
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== PELÍCULAS RELACIONADAS ===\n\n");
        sb.append("Película consultada: ").append(titulo).append("\n");
        sb.append("Relaciones encontradas: ").append(relacionadas.length).append("\n\n");
        
        if (relacionadas.length == 0) {
            sb.append("❌ No se encontraron relaciones\n");
            sb.append("(La película no existe o no tiene conexiones)");
        } else {
            for (int i = 0; i < relacionadas.length; i++) {
                Pelicula p = relacionadas[i];
                String tipoRelacion = grafo.obtenerTipoRelacion(titulo, p.getTitulo());
                sb.append(String.format("%2d. %-35s (%d)\n", 
                    i + 1, p.getTitulo(), p.getAnio()));
                sb.append(String.format("    📌 %s - Dir: %s - Género: %s\n\n",
                    tipoRelacion, p.getDirector(), p.getGenero()));
            }
        }
        
        txtResultados.setText(sb.toString());
    }
    
    private void actualizarEstadisticas() {
        if (grafo.getTamanio() == 0) {
            lblEstadisticas.setText("<html><b>Grafo vacío</b><br>Use 'Construir Grafo' primero</html>");
        } else {
            Pelicula masConectada = grafo.peliculaMasConectada();
            String nombreMasConectada = masConectada != null ? masConectada.getTitulo() : "N/A";
            int relacionesMasConectada = masConectada != null ? grafo.contarRelaciones(nombreMasConectada) : 0;
            
            lblEstadisticas.setText(String.format(
                "<html><b>Estadísticas del Grafo:</b><br><br>" +
                "🎬 Vértices (Películas): %d<br>" +
                "🔗 Película más conectada:<br>" +
                "    %s<br>" +
                "    (%d relaciones)</html>",
                grafo.getTamanio(),
                nombreMasConectada,
                relacionesMasConectada
            ));
        }
    }
    
    public GrafoPeliculas getGrafo() {
        return grafo;
    }
    
    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(160, 32));
        return boton;
    }
}
