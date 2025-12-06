package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaArbol extends JFrame {
    // Paneles principales
    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JPanel panelResultados;
    
    // Componentes para árbol binario
    private JButton btnCrearJoyanes;
    private JButton btnMostrarRecorridos;
    private JButton btnMostrarArbol;
    private JButton btnEstadisticas;
    private JButton btnBuscar;
    private JTextField txtBusqueda;
    
    // Componentes para árbol de expresión
    private JButton btnCrearExpresion;
    private JButton btnEvaluar;
    private JTextField txtExpresion;
    
    // Componente común
    private JButton btnLimpiar;
    private JTextArea txtResultados;
    private JScrollPane scrollResultados;
    
    // Colores personalizados
    private final Color COLOR_FONDO = new Color(15, 23, 42);    // Azul oscuro
    private final Color COLOR_BOTON = new Color(94, 234, 212);  // Turquesa
    private final Color COLOR_BOTON_HOVER = new Color(45, 212, 191);
    private final Color COLOR_TEXTO = new Color(226, 232, 240); // Gris claro
    private final Color COLOR_PANEL = new Color(30, 41, 59);    // Azul medio
    private final Color COLOR_BORDE = new Color(51, 65, 85);    // Azul grisáceo
    private final Color COLOR_AREA_TEXTO = new Color(30, 41, 59);
    private final Color COLOR_TITULO = new Color(148, 163, 184); // Gris azulado
    
    public VistaArbol() {
        inicializarComponentes();
        configurarVentana();
        aplicarEstilos();
    }
    
    private void inicializarComponentes() {
        // Crear componentes
        btnCrearJoyanes = new JButton("Árbol Joyanes");
        btnMostrarRecorridos = new JButton("Recorridos");
        btnMostrarArbol = new JButton("Estructura");
        btnEstadisticas = new JButton("Estadísticas");
        btnBuscar = new JButton("Buscar");
        txtBusqueda = new JTextField(10); 
        
        btnCrearExpresion = new JButton("Crear Expresión");
        btnEvaluar = new JButton("Evaluar");
        txtExpresion = new JTextField(15); 
        
        btnLimpiar = new JButton("Limpiar");
        txtResultados = new JTextArea(20, 50);
        scrollResultados = new JScrollPane(txtResultados);
        
        // Crear paneles con layout 
        panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelBotones = new JPanel();
        panelResultados = new JPanel(new BorderLayout());
        
        // Configurar área de resultados
        txtResultados.setEditable(false);
        txtResultados.setLineWrap(true);
        txtResultados.setWrapStyleWord(true);
        
        // Organizar componentes
        organizarInterfaz();
    }
    
    private void organizarInterfaz() {
        // Panel de botones (izquierda) GridBagLayout para más control
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Sección Árbol Binario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        JLabel lblArbol = new JLabel("ÁRBOL BINARIO");
        lblArbol.setFont(new Font("Arial", Font.BOLD, 14));
        lblArbol.setHorizontalAlignment(SwingConstants.CENTER);
        panelBotones.add(lblArbol, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Fila 1
        gbc.gridy = 1;
        gbc.gridx = 0;
        panelBotones.add(btnCrearJoyanes, gbc);
        
        gbc.gridx = 1;
        panelBotones.add(btnMostrarRecorridos, gbc);
        
        // Fila 2
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelBotones.add(btnMostrarArbol, gbc);
        
        gbc.gridx = 1;
        panelBotones.add(btnEstadisticas, gbc);
        
        // Fila 3 - Búsqueda
        gbc.gridy = 3;
        gbc.gridx = 0;
        panelBotones.add(new JLabel("Buscar:"), gbc);
        
        gbc.gridx = 1;
        panelBotones.add(txtBusqueda, gbc);
        
        // Fila 4 - Botón buscar
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panelBotones.add(btnBuscar, gbc);
        
        // Separador
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelBotones.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
        
        // Sección Árbol de Expresión
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 5, 5, 5);
        JLabel lblExpresion = new JLabel("ÁRBOL EXPRESIÓN");
        lblExpresion.setFont(new Font("Arial", Font.BOLD, 14));
        lblExpresion.setHorizontalAlignment(SwingConstants.CENTER);
        panelBotones.add(lblExpresion, gbc);
        
        // Fila 7 - Campo expresión
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        JPanel panelExpresion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelExpresion.add(new JLabel("Expr:"));
        panelExpresion.add(txtExpresion);
        panelBotones.add(panelExpresion, gbc);
        
        // Fila 8 - Botones expresión
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panelBotones.add(btnCrearExpresion, gbc);
        
        gbc.gridx = 1;
        panelBotones.add(btnEvaluar, gbc);
        
        // Separador
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelBotones.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
        
        // Fila 10 - Botón limpiar
        gbc.gridy = 10;
        gbc.insets = new Insets(10, 5, 10, 5);
        panelBotones.add(btnLimpiar, gbc);
        
        // Panel de resultados (centro)
        JLabel lblResultados = new JLabel("RESULTADOS");
        lblResultados.setFont(new Font("Arial", Font.BOLD, 14));
        lblResultados.setHorizontalAlignment(SwingConstants.CENTER);
        panelResultados.add(lblResultados, BorderLayout.NORTH);
        panelResultados.add(scrollResultados, BorderLayout.CENTER);
        
        // Añadir paneles al principal
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(panelResultados, BorderLayout.CENTER);
        
        // Añadir un poco de espacio alrededor
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.add(panelPrincipal);
    }
    
    private void aplicarEstilos() {
        // Configurar colores de fondo
        panelPrincipal.setBackground(COLOR_FONDO);
        panelBotones.setBackground(COLOR_FONDO);
        panelResultados.setBackground(COLOR_FONDO);
        
        // Configurar área de texto
        txtResultados.setBackground(COLOR_AREA_TEXTO);
        txtResultados.setForeground(COLOR_TEXTO);
        txtResultados.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultados.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Configurar todos los botones
        JButton[] botones = {
            btnCrearJoyanes, btnMostrarRecorridos, btnMostrarArbol,
            btnEstadisticas, btnBuscar, btnCrearExpresion,
            btnEvaluar, btnLimpiar
        };
        
        for (JButton boton : botones) {
            estilizarBoton(boton);
        }
        
        // Configurar campos de texto
        JTextField[] campos = {txtBusqueda, txtExpresion};
        for (JTextField campo : campos) {
            campo.setBackground(COLOR_PANEL);
            campo.setForeground(COLOR_TEXTO);
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            campo.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        
        // Configurar etiquetas en el panel de botones
        for (Component comp : panelBotones.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setForeground(COLOR_TITULO);
                if (label.getText().equals("ÁRBOL BINARIO") || label.getText().equals("ÁRBOL EXPRESIÓN")) {
                    label.setFont(new Font("Arial", Font.BOLD, 12));
                } else {
                    label.setFont(new Font("Arial", Font.PLAIN, 11));
                }
            } else if (comp instanceof JSeparator) {
                JSeparator sep = (JSeparator) comp;
                sep.setForeground(COLOR_BORDE);
                sep.setBackground(COLOR_BORDE);
            }
        }
        
        // Configurar el label de resultados
        Component[] comps = panelResultados.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setForeground(COLOR_TITULO);
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            }
        }
        
        // Configurar scroll pane
        scrollResultados.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        scrollResultados.getViewport().setBackground(COLOR_AREA_TEXTO);
        
        // Aplicar estilo a los paneles de expresión
        for (Component comp : panelBotones.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setBackground(COLOR_FONDO);
                for (Component subComp : panel.getComponents()) {
                    if (subComp instanceof JLabel) {
                        subComp.setForeground(COLOR_TEXTO);
                        ((JLabel) subComp).setFont(new Font("Arial", Font.PLAIN, 11));
                    }
                }
            }
        }
    }
    
    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_FONDO);
        boton.setFont(new Font("Arial", Font.BOLD, 11)); // Fuente más pequeña
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) // Padding más pequeño
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMinimumSize(new Dimension(100, 35)); // Tamaño mínimo
        boton.setPreferredSize(new Dimension(120, 35)); // Tamaño preferido
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON);
            }
        });
    }
    
    private void configurarVentana() {
        setTitle("Árboles Binarios - Sistema Integrado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600)); // Tamaño mínimo
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    // Getters para el controlador
    public JButton getBtnCrearJoyanes() { return btnCrearJoyanes; }
    public JButton getBtnMostrarRecorridos() { return btnMostrarRecorridos; }
    public JButton getBtnMostrarArbol() { return btnMostrarArbol; }
    public JButton getBtnEstadisticas() { return btnEstadisticas; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnCrearExpresion() { return btnCrearExpresion; }
    public JButton getBtnEvaluar() { return btnEvaluar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    
    public String getTextoBusqueda() { return txtBusqueda.getText(); }
    public String getTextoExpresion() { return txtExpresion.getText(); }
    
    public void actualizarResultado(String texto) {
        txtResultados.append(texto);
        txtResultados.setCaretPosition(txtResultados.getDocument().getLength());
    }
    
    public void limpiarResultados() {
        txtResultados.setText("");
        txtBusqueda.setText("");
        txtExpresion.setText("");
    }
    
    public void mostrar() {
        setVisible(true);
    }
}