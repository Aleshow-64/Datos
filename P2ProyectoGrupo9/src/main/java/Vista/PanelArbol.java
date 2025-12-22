package Vista;

import javax.swing.*;
import java.awt.*;
import Modelo.*;

/**
 * Panel para demostrar el Árbol Binario de Búsqueda
 */
public class PanelArbol extends JPanel {
    private ArbolBinarioBusqueda arbol;
    private JTextArea txtResultados;
    private JTextField txtBuscar;
    private JComboBox<String> cmbRecorrido;
    private JLabel lblEstadisticas;
    private PanelVisualizacionArbol panelVisualizacion;
    
    public PanelArbol() {
        arbol = new ArbolBinarioBusqueda();
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(52, 73, 94));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel superior - Controles con estilo
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelControles.setBackground(new Color(41, 128, 185));
        panelControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        JLabel lblInfo = new JLabel("📊 Búsqueda: O(log n) | Inserción: O(log n)");
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblInfo.setForeground(Color.WHITE);
        panelControles.add(lblInfo);
        
        add(panelControles, BorderLayout.NORTH);
        
        // Panel central - Dividido en visualización y controles
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setOpaque(false);
        
        // Izquierda - Visualización gráfica del árbol
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(new Color(44, 62, 80));
        panelIzq.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTituloVis = new JLabel("📊 Visualización del Árbol");
        lblTituloVis.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTituloVis.setForeground(new Color(52, 152, 219));
        lblTituloVis.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelIzq.add(lblTituloVis, BorderLayout.NORTH);
        
        panelVisualizacion = new PanelVisualizacionArbol();
        JScrollPane scrollVis = new JScrollPane(panelVisualizacion);
        scrollVis.setBorder(null);
        scrollVis.getVerticalScrollBar().setUnitIncrement(16);
        panelIzq.add(scrollVis, BorderLayout.CENTER);
        
        panelCentral.add(panelIzq);
        
        // Derecha - Búsqueda y Recorridos
        JPanel panelDer = new JPanel(new BorderLayout(5, 10));
        panelDer.setBackground(new Color(44, 62, 80));
        panelDer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Panel superior derecha - Búsqueda
        JPanel panelBusquedaContainer = new JPanel(new BorderLayout(5, 10));
        panelBusquedaContainer.setOpaque(false);
        
        JLabel lblTituloOps = new JLabel("⚙️ Operaciones");
        lblTituloOps.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTituloOps.setForeground(new Color(52, 152, 219));
        panelBusquedaContainer.add(lblTituloOps, BorderLayout.NORTH);
        
        JPanel panelBusqueda = new JPanel(new GridLayout(3, 1, 5, 10));
        panelBusqueda.setOpaque(false);
        
        JLabel lblBuscar = new JLabel("Buscar película:");
        lblBuscar.setForeground(new Color(236, 240, 241));
        lblBuscar.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        txtBuscar = new JTextField(15);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtBuscar.setBackground(new Color(52, 73, 94));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(149, 165, 166), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        // Permitir búsqueda con Enter
        txtBuscar.addActionListener(e -> buscarPelicula());
        
        JButton btnBuscar = crearBotonEstilizado("🔍 Buscar", new Color(52, 152, 219));
        btnBuscar.addActionListener(e -> {
            buscarPelicula();
        });
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        panelBusquedaContainer.add(panelBusqueda, BorderLayout.CENTER);
        
        lblEstadisticas = new JLabel("<html><b>Estadísticas:</b><br>Vacío</html>");
        lblEstadisticas.setForeground(new Color(149, 165, 166));
        lblEstadisticas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblEstadisticas.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        panelBusquedaContainer.add(lblEstadisticas, BorderLayout.SOUTH);
        
        panelDer.add(panelBusquedaContainer, BorderLayout.NORTH);
        
        // Panel central derecha - Recorridos
        JPanel panelRecorridosContainer = new JPanel(new BorderLayout(5, 5));
        panelRecorridosContainer.setOpaque(false);
        panelRecorridosContainer.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JLabel lblTituloRec = new JLabel("🔄 Recorridos");
        lblTituloRec.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTituloRec.setForeground(new Color(155, 89, 182));
        panelRecorridosContainer.add(lblTituloRec, BorderLayout.NORTH);
        
        JPanel panelRecorridos = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelRecorridos.setOpaque(false);
        
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(new Color(236, 240, 241));
        lblTipo.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelRecorridos.add(lblTipo);
        
        cmbRecorrido = new JComboBox<>(new String[]{
            "Inorden (Ordenado A-Z)",
            "Preorden (Raíz-Izq-Der)",
            "Postorden (Izq-Der-Raíz)"
        });
        cmbRecorrido.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cmbRecorrido.setBackground(new Color(52, 73, 94));
        cmbRecorrido.setForeground(Color.WHITE);
        panelRecorridos.add(cmbRecorrido);
        
        JButton btnRecorrer = crearBotonEstilizado("▶ Ejecutar", new Color(155, 89, 182));
        btnRecorrer.addActionListener(e -> ejecutarRecorrido());
        panelRecorridos.add(btnRecorrer);
        
        panelRecorridosContainer.add(panelRecorridos, BorderLayout.NORTH);
        
        txtResultados = new JTextArea(10, 30);
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtResultados.setBackground(new Color(52, 73, 94));
        txtResultados.setForeground(new Color(236, 240, 241));
        txtResultados.setCaretColor(Color.WHITE);
        txtResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(149, 165, 166), 1));
        scroll.getVerticalScrollBar().setBackground(new Color(52, 73, 94));
        panelRecorridosContainer.add(scroll, BorderLayout.CENTER);
        
        panelDer.add(panelRecorridosContainer, BorderLayout.CENTER);
        
        panelCentral.add(panelDer);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior - Información
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDesc = new JLabel("<html><i>El ABB mantiene las películas ordenadas por título. Ideal para búsquedas rápidas.</i></html>");
        panelInfo.add(lblDesc);
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    public void cargarPeliculas(Pelicula[] peliculas) {
        arbol = new ArbolBinarioBusqueda();
        for (Pelicula p : peliculas) {
            arbol.insertar(p);
        }
        
        actualizarEstadisticas();
        panelVisualizacion.setArbol(arbol);
        
        // Mostrar las primeras películas para verificación
        Pelicula[] inorden = arbol.recorridoInorden();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "✅ CATÁLOGO CARGADO AUTOMÁTICAMENTE\n\n" +
            "📊 Películas en el árbol: %d\n" +
            "📏 Altura: %d\n\n" +
            "🎬 Primeras películas (ordenadas A-Z):\n",
            peliculas.length,
            arbol.altura()
        ));
        
        int mostrar = Math.min(15, inorden.length);
        for (int i = 0; i < mostrar; i++) {
            sb.append(String.format("  %2d. %s (%d)\n", i + 1, inorden[i].getTitulo(), inorden[i].getAnio()));
        }
        if (inorden.length > 15) {
            sb.append(String.format("  ... y %d más\n", inorden.length - 15));
        }
        
        sb.append("\n💡 Escribe el título exacto en el campo de búsqueda.");
        txtResultados.setText(sb.toString());
    }
    
    private void buscarPelicula() {
        String titulo = txtBuscar.getText().trim();
        
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un título para buscar", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (arbol == null || arbol.estaVacio()) {
            JOptionPane.showMessageDialog(this, 
                "El árbol está vacío.\nNo hay películas cargadas.", 
                "Árbol vacío", 
                JOptionPane.WARNING_MESSAGE);
            txtResultados.setText("⚠️ EL ÁRBOL ESTÁ VACÍO\n\nNo hay películas cargadas.");
            return;
        }
        
        long inicio = System.nanoTime();
        Pelicula encontrada = arbol.buscar(titulo);
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        if (encontrada != null) {
            String mensaje = String.format(
                "✅ ENCONTRADA en %.4f ms\n\n" +
                "Título: %s\n" +
                "Director: %s\n" +
                "Año: %d\n" +
                "Género: %s\n\n" +
                "Sinopsis:\n%s",
                tiempoMs,
                encontrada.getTitulo(),
                encontrada.getDirector(),
                encontrada.getAnio(),
                encontrada.getGenero(),
                encontrada.getSinopsis()
            );
            
            txtResultados.setText(mensaje);
            txtResultados.setCaretPosition(0);
            
            // Mostrar también un diálogo
            JOptionPane.showMessageDialog(this,
                String.format("✅ Película encontrada:\n%s (%d)\nDirector: %s",
                    encontrada.getTitulo(),
                    encontrada.getAnio(),
                    encontrada.getDirector()),
                "Búsqueda exitosa",
                JOptionPane.INFORMATION_MESSAGE);
            
            panelVisualizacion.repaint();
        } else {
            // Mostrar sugerencias
            Pelicula[] todasPeliculas = arbol.recorridoInorden();
            StringBuilder sugerencias = new StringBuilder();
            sugerencias.append(String.format("❌ NO ENCONTRADA (tiempo: %.4f ms)\n\n", tiempoMs));
            sugerencias.append(String.format("'%s' no está en el árbol.\n\n", titulo));
            sugerencias.append("💡 Películas disponibles (en orden alfabético):\n\n");
            
            for (int i = 0; i < Math.min(15, todasPeliculas.length); i++) {
                sugerencias.append(String.format("%2d. %s\n", i + 1, todasPeliculas[i].getTitulo()));
            }
            if (todasPeliculas.length > 15) {
                sugerencias.append(String.format("\n... y %d más", todasPeliculas.length - 15));
            }
            
            txtResultados.setText(sugerencias.toString());
            txtResultados.setCaretPosition(0);
            
            // Mostrar diálogo con las primeras opciones
            StringBuilder dialogMsg = new StringBuilder();
            dialogMsg.append("No se encontró: '" + titulo + "'\n\n");
            dialogMsg.append("Primeras películas disponibles:\n");
            for (int i = 0; i < Math.min(5, todasPeliculas.length); i++) {
                dialogMsg.append("• " + todasPeliculas[i].getTitulo() + "\n");
            }
            
            JOptionPane.showMessageDialog(this,
                dialogMsg.toString(),
                "No encontrada",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void ejecutarRecorrido() {
        if (arbol == null || arbol.estaVacio()) {
            JOptionPane.showMessageDialog(this, 
                "El árbol está vacío.\nNo hay películas para recorrer.", 
                "Árbol vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int seleccion = cmbRecorrido.getSelectedIndex();
        Pelicula[] resultado;
        String tipo;
        String explicacion;
        
        long inicio = System.nanoTime();
        switch (seleccion) {
            case 0: // Inorden
                resultado = arbol.recorridoInorden();
                tipo = "INORDEN";
                explicacion = "Visita: Izquierda → Raíz → Derecha\nResultado: Películas en orden alfabético";
                break;
            case 1: // Preorden
                resultado = arbol.recorridoPreorden();
                tipo = "PREORDEN";
                explicacion = "Visita: Raíz → Izquierda → Derecha\nResultado: Raíz primero, luego subárboles";
                break;
            case 2: // Postorden
                resultado = arbol.recorridoPostorden();
                tipo = "POSTORDEN";
                explicacion = "Visita: Izquierda → Derecha → Raíz\nResultado: Subárboles primero, raíz al final";
                break;
            default:
                return;
        }
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        // ACTUALIZAR VISUALIZACIÓN CON EL ORDEN DE VISITA
        panelVisualizacion.setRecorrido(resultado, tipo);
        
        // Mostrar en área de resultados
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════╗\n");
        sb.append(String.format("   RECORRIDO %s\n", tipo));
        sb.append("╚══════════════════════════════════════════╝\n\n");
        sb.append(explicacion).append("\n\n");
        sb.append(String.format("⏱️  Tiempo: %.4f ms\n", tiempoMs));
        sb.append(String.format("📊 Películas: %d\n\n", resultado.length));
        sb.append("Orden de visita:\n");
        sb.append("─────────────────────────────────────────\n");
        
        for (int i = 0; i < resultado.length; i++) {
            sb.append(String.format("%2d. %-40s (%d)\n", 
                i + 1, resultado[i].getTitulo(), resultado[i].getAnio()));
        }
        
        sb.append("\n💡 Los nodos del árbol ahora muestran\n");
        sb.append("   números VERDES con el orden de visita.");
        
        txtResultados.setText(sb.toString());
        txtResultados.setCaretPosition(0);
    }
    
    private void actualizarEstadisticas() {
        if (arbol.estaVacio()) {
            lblEstadisticas.setText("<html><b>Estadísticas:</b><br>Vacío</html>");
        } else {
            lblEstadisticas.setText(String.format(
                "<html><b>Estadísticas:</b><br>" +
                "Películas: %d<br>" +
                "Altura: %d<br>" +
                "Balance: %s</html>",
                arbol.getTamanio(),
                arbol.altura(),
                arbol.altura() <= Math.log(arbol.getTamanio()) / Math.log(2) + 1 ? "Bueno" : "Desbalanceado"
            ));
        }
    }
    
    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(180, 32));
        return boton;
    }
    
    public ArbolBinarioBusqueda getArbol() {
        return arbol;
    }
}
