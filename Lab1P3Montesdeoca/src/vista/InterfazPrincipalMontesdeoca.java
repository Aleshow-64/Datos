package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import modelo.TableroModeloMontesdeoca;
import controlador.ControladorReinasMontesdeoca;

public class InterfazPrincipalMontesdeoca {
    private JFrame frame;
    private PanelTableroMontesdeoca panelTablero;
    private JSpinner spinnerN;
    private JButton btnResolver;
    private JButton btnTodasSoluciones;
    private JButton btnLimpiar;
    private JLabel lblSoluciones;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private ControladorReinasMontesdeoca controlador;
    private TableroModeloMontesdeoca modelo;

    // Nueva paleta de colores - Tema moderno azul y púrpura
    private static final Color COLOR_FONDO = new Color(18, 18, 30);
    private static final Color COLOR_PANEL = new Color(30, 30, 46);
    private static final Color COLOR_ACCENT = new Color(138, 43, 226); // Violeta
    private static final Color COLOR_SECUNDARIO = new Color(0, 191, 255); // Azul claro
    private static final Color COLOR_TEXTO = new Color(220, 220, 255);
    private static final Color COLOR_TEXTO_SECUNDARIO = new Color(180, 180, 220);
    private static final Color COLOR_EXITO = new Color(50, 205, 50);
    private static final Color COLOR_ERROR = new Color(255, 69, 0);
    private static final Color COLOR_BORDE = new Color(64, 64, 96);

    public InterfazPrincipalMontesdeoca(ControladorReinasMontesdeoca controlador, TableroModeloMontesdeoca modelo) {
        this.controlador = controlador;
        this.modelo = modelo;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        frame = new JFrame("Problema de las N-Reinas - Montesdeoca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(COLOR_FONDO);

        // Panel principal con diseño moderno
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 15));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header con título
        JPanel panelHeader = crearPanelHeader();
        
        // Panel central con controles y tablero
        JPanel panelCentral = crearPanelCentral();

        // Panel inferior con estadísticas
        JPanel panelInferior = crearPanelInferior();

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 700));
    }

    private JPanel crearPanelHeader() {
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_PANEL);
        panelHeader.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 2, true),
            new EmptyBorder(20, 30, 20, 30)
        ));

        // Título principal con efecto gradiente
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(COLOR_PANEL);
        
        lblTitulo = new JLabel("♛ N-REINAS MONTESDEOCA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_ACCENT);
        
        lblSubtitulo = new JLabel("Algoritmo de Backtracking con Interfaz Moderna");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblSubtitulo.setBorder(new EmptyBorder(5, 0, 0, 0));

        panelTitulo.add(lblTitulo);
        
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(COLOR_PANEL);
        panelContenido.add(panelTitulo);
        panelContenido.add(lblSubtitulo);

        panelHeader.add(panelContenido, BorderLayout.CENTER);

        return panelHeader;
    }

    private JPanel crearPanelCentral() {
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Panel de controles a la izquierda
        JPanel panelControles = crearPanelControles();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelCentral.add(panelControles, gbc);

        // Panel del tablero a la derecha
        JPanel panelTableroContainer = crearPanelTablero();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panelCentral.add(panelTableroContainer, gbc);

        return panelCentral;
    }

    private JPanel crearPanelControles() {
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
        panelControles.setBackground(COLOR_PANEL);
        panelControles.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 2, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        // Título de controles
        JLabel lblTituloControles = new JLabel("CONTROLES");
        lblTituloControles.setFont(new Font("Arial", Font.BOLD, 18));
        lblTituloControles.setForeground(COLOR_SECUNDARIO);
        lblTituloControles.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelControles.add(lblTituloControles);
        panelControles.add(Box.createVerticalStrut(25));

        // Configuración del tamaño del tablero
        JPanel panelTamaño = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelTamaño.setBackground(COLOR_PANEL);
        
        JLabel lblTamaño = new JLabel("Tamaño (N):");
        lblTamaño.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTamaño.setForeground(COLOR_TEXTO);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(8, 1, 20, 1);
        spinnerN = new JSpinner(spinnerModel);
        spinnerN.setFont(new Font("Arial", Font.BOLD, 14));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerN);
        editor.getTextField().setBackground(new Color(40, 40, 60));
        editor.getTextField().setForeground(COLOR_TEXTO);
        editor.getTextField().setCaretColor(COLOR_ACCENT);
        spinnerN.setEditor(editor);
        spinnerN.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));

        panelTamaño.add(lblTamaño);
        panelTamaño.add(spinnerN);
        panelTamaño.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelControles.add(panelTamaño);
        panelControles.add(Box.createVerticalStrut(20));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_PANEL);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnResolver = crearBotonModerno("🔍 ENCONTRAR UNA SOLUCIÓN", COLOR_SECUNDARIO);
        btnTodasSoluciones = crearBotonModerno("📊 CONTAR TODAS LAS SOLUCIONES", COLOR_ACCENT);
        btnLimpiar = crearBotonModerno("🗑️ LIMPIAR TABLERO", new Color(220, 20, 60));
        
        panelBotones.add(btnResolver);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnTodasSoluciones);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnLimpiar);
        
        panelControles.add(panelBotones);
        panelControles.add(Box.createVerticalGlue());

        // Configurar listeners
        btnResolver.addActionListener(e -> controlador.resolverUnaSolucion());
        btnTodasSoluciones.addActionListener(e -> controlador.contarTodasSoluciones());
        btnLimpiar.addActionListener(e -> controlador.limpiarTablero());

        return panelControles;
    }

    private JButton crearBotonModerno(String texto, Color colorBase) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo con gradiente
                GradientPaint gradient = new GradientPaint(
                    0, 0, colorBase,
                    0, getHeight(), colorBase.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Borde
                g2.setColor(colorBase.brighter());
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(250, 45));
        boton.setMaximumSize(new Dimension(250, 45));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setForeground(Color.YELLOW);
                boton.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setForeground(Color.WHITE);
                boton.repaint();
            }
        });

        return boton;
    }

    private JPanel crearPanelTablero() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(COLOR_PANEL);
        container.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        panelTablero = new PanelTableroMontesdeoca(modelo);
        JScrollPane scrollPane = new JScrollPane(panelTablero);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_PANEL);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        // Título del tablero
        JLabel lblTituloTablero = new JLabel("TABLERO DE REINAS", SwingConstants.CENTER);
        lblTituloTablero.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloTablero.setForeground(COLOR_SECUNDARIO);
        lblTituloTablero.setBorder(new EmptyBorder(0, 0, 10, 0));

        container.add(lblTituloTablero, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(COLOR_PANEL);
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 2, true),
            new EmptyBorder(15, 20, 15, 20)
        ));

        // Contador de soluciones
        JPanel panelSoluciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSoluciones.setBackground(COLOR_PANEL);
        
        JLabel lblIcono = new JLabel("📈 ");
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 20));
        
        lblSoluciones = new JLabel("Soluciones encontradas: 0");
        lblSoluciones.setFont(new Font("Arial", Font.BOLD, 16));
        lblSoluciones.setForeground(COLOR_EXITO);
        
        panelSoluciones.add(lblIcono);
        panelSoluciones.add(lblSoluciones);

        // Información del autor
        JLabel lblAutor = new JLabel("Desarrollado por Montesdeoca • N-Reinas Solver v2.0");
        lblAutor.setFont(new Font("Arial", Font.ITALIC, 12));
        lblAutor.setForeground(COLOR_TEXTO_SECUNDARIO);

        panelInferior.add(panelSoluciones, BorderLayout.WEST);
        panelInferior.add(lblAutor, BorderLayout.EAST);

        return panelInferior;
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public int getN() {
        return (int) spinnerN.getValue();
    }

    public void setN(int n) {
        spinnerN.setValue(n);
    }

    public void actualizarTablero() {
        panelTablero.repaint();
    }

    public void actualizarContadorSoluciones(int cantidad) {
        lblSoluciones.setText("Soluciones encontradas: " + cantidad);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "N-Reinas Montesdeoca", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public PanelTableroMontesdeoca getPanelTablero() {
        return panelTablero;
    }
}