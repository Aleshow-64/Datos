package Vista;

import javax.swing.*;
import java.awt.*;
import Modelo.*;

/**
 * Panel para demostrar algoritmos de ordenamiento
 */
public class PanelOrdenamiento extends JPanel {
    private JTextArea txtResultados;
    private JComboBox<String> cmbAlgoritmo;
    private JComboBox<String> cmbCriterio;
    private JLabel lblTiempo;
    
    public PanelOrdenamiento() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(52, 73, 94));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel superior - Controles con estilo
        JPanel panelControles = new JPanel(new GridLayout(3, 1, 5, 10));
        panelControles.setBackground(new Color(44, 62, 80));
        panelControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 126, 34), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblInfo = new JLabel("📊 Complejidad: QuickSort O(n log n) | MergeSort O(n log n) garantizado");
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblInfo.setForeground(new Color(230, 126, 34));
        panelControles.add(lblInfo);
        
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSeleccion.setOpaque(false);
        
        JLabel lblAlg = new JLabel("Algoritmo:");
        lblAlg.setForeground(new Color(236, 240, 241));
        lblAlg.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelSeleccion.add(lblAlg);
        
        cmbAlgoritmo = new JComboBox<>(new String[]{
            "QuickSort (Rápido)",
            "MergeSort (Estable)"
        });
        cmbAlgoritmo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cmbAlgoritmo.setBackground(new Color(52, 73, 94));
        cmbAlgoritmo.setForeground(Color.WHITE);
        panelSeleccion.add(cmbAlgoritmo);
        
        panelSeleccion.add(Box.createHorizontalStrut(20));
        
        JLabel lblCrit = new JLabel("Ordenar por:");
        lblCrit.setForeground(new Color(236, 240, 241));
        lblCrit.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelSeleccion.add(lblCrit);
        
        cmbCriterio = new JComboBox<>(new String[]{
            "Título (A-Z)",
            "Año (Antiguo-Nuevo)"
        });
        cmbCriterio.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cmbCriterio.setBackground(new Color(52, 73, 94));
        cmbCriterio.setForeground(Color.WHITE);
        panelSeleccion.add(cmbCriterio);
        
        JButton btnOrdenar = crearBotonEstilizado("▶ Ordenar", new Color(230, 126, 34));
        btnOrdenar.addActionListener(e -> ordenarPeliculas());
        panelSeleccion.add(btnOrdenar);
        
        panelControles.add(panelSeleccion);
        
        lblTiempo = new JLabel("⏱️ Tiempo de ejecución: --");
        lblTiempo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTiempo.setForeground(new Color(46, 204, 113));
        panelControles.add(lblTiempo);
        
        add(panelControles, BorderLayout.NORTH);
        
        // Panel central - Resultados
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultados.setBackground(new Color(44, 62, 80));
        txtResultados.setForeground(new Color(236, 240, 241));
        txtResultados.setCaretColor(Color.WHITE);
        txtResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 126, 34), 2));
        scroll.setPreferredSize(new Dimension(0, 400)); // Aumentar altura mínima
        add(scroll, BorderLayout.CENTER);
        
        // Panel inferior - Comparación (más compacto)
        JPanel panelInfo = new JPanel(new GridLayout(3, 1, 2, 2));
        panelInfo.setBackground(new Color(44, 62, 80));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 126, 34), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        JLabel lbl1 = new JLabel("QuickSort: Muy rápido en promedio, utiliza estrategia divide y conquista");
        lbl1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl1.setForeground(new Color(189, 195, 199));
        panelInfo.add(lbl1);
        
        JLabel lbl2 = new JLabel("MergeSort: Garantiza O(n log n), ideal para datos grandes, es estable");
        lbl2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl2.setForeground(new Color(189, 195, 199));
        panelInfo.add(lbl2);
        
        JLabel lbl3 = new JLabel("Búsqueda Binaria: Disponible en array ordenado - O(log n)");
        lbl3.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl3.setForeground(new Color(189, 195, 199));
        panelInfo.add(lbl3);
        
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    private Pelicula[] peliculasActuales;
    
    public void cargarPeliculas(Pelicula[] peliculas) {
        this.peliculasActuales = peliculas;
        txtResultados.setText(String.format("%d películas cargadas. Seleccione algoritmo y criterio, luego presione Ordenar.", 
            peliculas.length));
    }
    
    private void ordenarPeliculas() {
        if (peliculasActuales == null || peliculasActuales.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay películas cargadas", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Copiar array para no modificar el original
        Pelicula[] copia = new Pelicula[peliculasActuales.length];
        System.arraycopy(peliculasActuales, 0, copia, 0, peliculasActuales.length);
        
        int algoritmo = cmbAlgoritmo.getSelectedIndex();
        int criterio = cmbCriterio.getSelectedIndex();
        
        long inicio = System.nanoTime();
        
        // Ejecutar ordenamiento
        if (criterio == 0) { // Por título
            if (algoritmo == 0) {
                AlgoritmosOrdenamiento.quickSortPorTitulo(copia);
            } else {
                AlgoritmosOrdenamiento.mergeSortPorTitulo(copia);
            }
        } else { // Por año
            if (algoritmo == 0) {
                AlgoritmosOrdenamiento.quickSortPorAnio(copia);
            } else {
                AlgoritmosOrdenamiento.mergeSortPorAnio(copia);
            }
        }
        
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        lblTiempo.setText(String.format("⏱ Tiempo de ejecución: %.4f ms", tiempoMs));
        
        // Mostrar resultados
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DEL ORDENAMIENTO ===\n");
        sb.append(String.format("Algoritmo: %s\n", cmbAlgoritmo.getSelectedItem()));
        sb.append(String.format("Criterio: %s\n", cmbCriterio.getSelectedItem()));
        sb.append(String.format("Tiempo: %.4f ms\n", tiempoMs));
        sb.append(String.format("Películas: %d\n\n", copia.length));
        
        for (int i = 0; i < copia.length; i++) {
            if (criterio == 0) { // Por título
                sb.append(String.format("%3d. %-40s (%d) - %s\n", 
                    i + 1, 
                    copia[i].getTitulo(),
                    copia[i].getAnio(),
                    copia[i].getDirector()));
            } else { // Por año
                sb.append(String.format("%3d. %4d - %-40s - %s\n", 
                    i + 1,
                    copia[i].getAnio(),
                    copia[i].getTitulo(),
                    copia[i].getDirector()));
            }
        }
        
        // Demostrar búsqueda binaria si está ordenado por título
        if (criterio == 0) {
            sb.append("\n\n=== BÚSQUEDA BINARIA DISPONIBLE ===\n");
            sb.append("El array está ordenado alfabéticamente.\n");
            sb.append("Pruebe buscar: ").append(copia[copia.length / 2].getTitulo()).append("\n");
            
            String tituloBuscar = copia[copia.length / 2].getTitulo();
            long inicioBusqueda = System.nanoTime();
            Pelicula encontrada = AlgoritmosOrdenamiento.busquedaBinariaPorTitulo(copia, tituloBuscar);
            long finBusqueda = System.nanoTime();
            double tiempoBusqueda = (finBusqueda - inicioBusqueda) / 1_000_000.0;
            
            if (encontrada != null) {
                sb.append(String.format("✅ Encontrada en %.6f ms (Búsqueda Binaria)\n", tiempoBusqueda));
            }
        }
        
        txtResultados.setText(sb.toString());
    }
    
    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 32));
        return boton;
    }
}
