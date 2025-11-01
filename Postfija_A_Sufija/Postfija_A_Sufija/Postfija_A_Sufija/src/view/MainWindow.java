package view;

import controller.NotationConverter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame {
    private NotationConverter controller;
    private JTextArea resultArea;
    private JTextField expressionField;
    
    // Colores en tonalidades azul y gris oscuro
    private final Color DARK_BLUE = new Color(30, 40, 60);
    private final Color MEDIUM_BLUE = new Color(50, 70, 100);
    private final Color LIGHT_BLUE = new Color(70, 100, 140);
    private final Color DARK_GRAY = new Color(45, 45, 55);
    private final Color LIGHT_GRAY = new Color(200, 200, 210);
    private final Color ACCENT_BLUE = new Color(0, 120, 215);
    private final Color BUTTON_TEXT = Color.BLACK;
    
    public MainWindow() {
        // Inicializar controlador con tamaño de pila
        controller = new NotationConverter(50); // Tamaño por defecto 50
        initializeComponents();
        applyDarkTheme();
    }
    
    private void initializeComponents() {
        setTitle("🧮 Expression Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Main panel with dark background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(DARK_BLUE);
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Input panel
        JPanel inputPanel = createInputPanel();
        
        // Buttons panel
        JPanel buttonPanel = createButtonPanel();
        
        // Result area
        resultArea = new JTextArea(20, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollResult = new JScrollPane(resultArea);
        scrollResult.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT_BLUE, 1), 
            "Results", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 12), 
            Color.WHITE
        ));
        
        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(DARK_BLUE);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Main container
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setBackground(DARK_BLUE);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(contentPanel, BorderLayout.NORTH);
        container.add(scrollResult, BorderLayout.CENTER);
        
        add(container);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("Expression Converter", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Infix to Postfix/Prefix Conversion and Evaluation", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(DARK_BLUE);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(LIGHT_BLUE, 1), 
            "Input Expression", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 12), 
            Color.WHITE
        ));
        
        JLabel inputLabel = new JLabel("Enter Infix Expression:");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        inputLabel.setForeground(Color.WHITE);
        
        expressionField = new JTextField();
        expressionField.setFont(new Font("Consolas", Font.PLAIN, 14));
        expressionField.setToolTipText("Enter expression like: ( A + B ) * C / D");
        expressionField.setBackground(DARK_GRAY);
        expressionField.setForeground(Color.WHITE);
        expressionField.setCaretColor(Color.WHITE);
        expressionField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Instructions panel
        JTextArea instructions = new JTextArea(3, 50);
        instructions.setText("📋 Instructions:\n" +
                           "• Use spaces between elements: ( A + B ) * C\n" +
                           "• Supported operators: + - * / ^\n" +
                           "• Examples: 4 * (5 + 6) , A * (B + C) / D , 3 + 4 * 2 / (1 - 5) ^ 2");
        instructions.setEditable(false);
        instructions.setBackground(MEDIUM_BLUE);
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        instructions.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(expressionField, BorderLayout.CENTER);
        inputPanel.add(instructions, BorderLayout.SOUTH);
        
        return inputPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(DARK_BLUE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        
        JButton postfixButton = createStyledButton("🧮 Convert to Postfix");
        JButton evaluateButton = createStyledButton("🔍 Evaluate Step by Step");
        JButton prefixButton = createStyledButton("🔄 Convert to Prefix");
        JButton clearButton = createStyledButton("🗑️ Clear Results");
        
        buttonPanel.add(postfixButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(prefixButton);
        buttonPanel.add(clearButton);
        
        // Button listeners
        postfixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertToPostfix();
            }
        });
        
        evaluateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evaluatePostfix();
            }
        });
        
        prefixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertToPrefix();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                expressionField.setText("");
            }
        });
        
        return buttonPanel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(ACCENT_BLUE);
        button.setForeground(BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BLUE);
                button.setForeground(BUTTON_TEXT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_BLUE);
                button.setForeground(BUTTON_TEXT);
            }
        });
        
        return button;
    }
    
    private void applyDarkTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            // Apply dark theme to scroll panes
            UIManager.put("ScrollPane.background", DARK_GRAY);
            UIManager.put("ScrollPane.foreground", Color.WHITE);
            UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(LIGHT_BLUE, 1));
            
            // Apply to text area
            resultArea.setBackground(DARK_GRAY);
            resultArea.setForeground(Color.WHITE);
            resultArea.setCaretColor(Color.WHITE);
            resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void convertToPostfix() {
        String expression = expressionField.getText().trim();
        if (expression.isEmpty()) {
            showError("Please enter an expression");
            return;
        }
        
        try {
            String result = controller.convertToPostfix(expression);
            resultArea.setText("🎯 CONVERSION TO POSTFIX:\n");
            resultArea.append("═".repeat(50) + "\n\n");
            resultArea.append("📥 Infix expression: " + expression + "\n");
            resultArea.append("📤 Postfix expression: " + result + "\n");
        } catch (Exception ex) {
            showError("Conversion Error: " + ex.getMessage());
        }
    }
    
    private void evaluatePostfix() {
        String expression = expressionField.getText().trim();
        if (expression.isEmpty()) {
            showError("Please enter an expression");
            return;
        }
        
        try {
            // First convert to postfix
            String postfix = controller.convertToPostfix(expression);
            
            // Now step-by-step evaluation is delegated to the model through controller
            List<String> steps = controller.evaluatePostfixStepByStep(postfix);
            
            resultArea.setText("🔍 STEP BY STEP EVALUATION:\n");
            resultArea.append("═".repeat(50) + "\n\n");
            resultArea.append("📥 Infix expression: " + expression + "\n");
            resultArea.append("📤 Postfix expression: " + postfix + "\n\n");
            resultArea.append("📊 Evaluation Steps:\n");
            resultArea.append("─".repeat(30) + "\n");
            
            for (String step : steps) {
                if (step.isEmpty()) {
                    resultArea.append("\n");
                } else {
                    resultArea.append("• " + step + "\n");
                }
            }
        } catch (Exception ex) {
            showError("Evaluation Error: " + ex.getMessage());
        }
    }
    
    private void convertToPrefix() {
        String expression = expressionField.getText().trim();
        if (expression.isEmpty()) {
            showError("Please enter an expression");
            return;
        }
        
        try {
            String result = controller.convertToPrefix(expression);
            resultArea.setText("🔄 CONVERSION TO PREFIX:\n");
            resultArea.append("═".repeat(50) + "\n\n");
            resultArea.append("📥 Infix expression: " + expression + "\n");
            resultArea.append("📤 Prefix expression: " + result + "\n");
        } catch (Exception ex) {
            showError("Conversion Error: " + ex.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                new MainWindow().setVisible(true);
            }
        });
    }
}