package controlador;

import arbolbinario.ArbolBinario;
import vista.VistaArbol;
import arbolbinario.ArbolExpresion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorArbol {
    private VistaArbol vista;
    private ArbolBinario arbol;
    private ArbolExpresion arbolExpresion;
    
    public ControladorArbol(VistaArbol vista) {
        this.vista = vista;
        this.arbol = null;
        this.arbolExpresion = null;
        
        // Configurar listeners
        configurarListeners();
    }
    
    private void configurarListeners() {
        // Listeners para árbol binario
        vista.getBtnCrearJoyanes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearArbolJoyanes();
            }
        });
        
        vista.getBtnMostrarRecorridos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecorridos();
            }
        });
        
        vista.getBtnMostrarArbol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEstructuraArbol();
            }
        });
        
        vista.getBtnEstadisticas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticas();
            }
        });
        
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarElemento();
            }
        });
        
        // Listeners para árbol de expresión
        vista.getBtnCrearExpresion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearArbolExpresion();
            }
        });
        
        vista.getBtnEvaluar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evaluarExpresion();
            }
        });
        
        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarResultados();
            }
        });
    }
    
    private void crearArbolJoyanes() {
        arbol = ArbolBinario.crearArbolJoyanes();
        vista.actualizarResultado("✓ Árbol de Joyanes Aguilar creado exitosamente!\n");
        vista.actualizarResultado("Estructura familiar: ((Maria Esperanza Rodrigo) Esperanza (Anyora M Jesus Abel))\n");
    }
    
    private void mostrarRecorridos() {
        if (arbol == null || arbol.esVacio()) {
            vista.actualizarResultado("❌ Primero debe crear un árbol\n");
            return;
        }
        
        vista.actualizarResultado("\n=== RECORRIDOS DEL ÁRBOL ===\n");
        vista.actualizarResultado("Preorden: " + arbol.preorden() + "\n");
        vista.actualizarResultado("Inorden: " + arbol.inorden() + "\n");
        vista.actualizarResultado("Postorden: " + arbol.postorden() + "\n");
        vista.actualizarResultado("Preorden Iterativo: " + arbol.preordenIterativo() + "\n");
        vista.actualizarResultado("Inorden Iterativo: " + arbol.inordenIterativo() + "\n");
    }
    
    private void mostrarEstructuraArbol() {
        if (arbol == null || arbol.esVacio()) {
            vista.actualizarResultado("❌ Primero debe crear un árbol\n");
            return;
        }
        
        vista.actualizarResultado("\n=== ESTRUCTURA DEL ÁRBOL ===\n");
        vista.actualizarResultado("Representación vertical (rotada 90°):\n");
        vista.actualizarResultado(arbol.mostrarArbol() + "\n");
        
        vista.actualizarResultado("\nRepresentación con líneas:\n");
        vista.actualizarResultado(arbol.mostrarArbolConLineas() + "\n");
    }
    
    private void mostrarEstadisticas() {
        if (arbol == null || arbol.esVacio()) {
            vista.actualizarResultado("❌ Primero debe crear un árbol\n");
            return;
        }
        
        vista.actualizarResultado("\n=== ESTADÍSTICAS DEL ÁRBOL ===\n");
        vista.actualizarResultado("Número total de nodos: " + arbol.numNodos() + "\n");
        vista.actualizarResultado("Altura del árbol: " + arbol.altura() + "\n");
        vista.actualizarResultado("Número de hojas: " + arbol.contarHojas() + "\n");
        vista.actualizarResultado("¿El árbol está vacío?: " + (arbol.esVacio() ? "Sí" : "No") + "\n");
    }
    
    private void buscarElemento() {
        if (arbol == null || arbol.esVacio()) {
            vista.actualizarResultado("❌ Primero debe crear un árbol\n");
            return;
        }
        
        String elemento = vista.getTextoBusqueda();
        if (elemento.isEmpty()) {
            vista.actualizarResultado("❌ Ingrese un elemento para buscar\n");
            return;
        }
        
        boolean encontrado = arbol.buscar(elemento);
        vista.actualizarResultado("\n=== BÚSQUEDA ===\n");
        vista.actualizarResultado("¿El elemento '" + elemento + "' está en el árbol? " + 
                               (encontrado ? "✓ SÍ" : "✗ NO") + "\n");
    }
    
    private void crearArbolExpresion() {
        String expresion = vista.getTextoExpresion();
        if (expresion.isEmpty()) {
            vista.actualizarResultado("❌ Ingrese una expresión postfija\n");
            vista.actualizarResultado("Ejemplo: 3 4 + 5 2 - *\n");
            return;
        }
        
        try {
            arbolExpresion = new ArbolExpresion();
            arbolExpresion.construirDesdePostfija(expresion);
            vista.actualizarResultado("✓ Árbol de expresión creado exitosamente!\n");
            vista.actualizarResultado("Expresión infija: " + arbolExpresion.expresionInfija() + "\n");
        } catch (Exception e) {
            vista.actualizarResultado("❌ Error al crear el árbol: " + e.getMessage() + "\n");
        }
    }
    
    private void evaluarExpresion() {
        if (arbolExpresion == null) {
            vista.actualizarResultado("❌ Primero debe crear un árbol de expresión\n");
            return;
        }
        
        try {
            double resultado = arbolExpresion.evaluar();
            vista.actualizarResultado("\n=== EVALUACIÓN ===\n");
            vista.actualizarResultado("Resultado: " + resultado + "\n");
            vista.actualizarResultado("Recorrido preorden: " + arbolExpresion.preorden() + "\n");
            vista.actualizarResultado("Recorrido inorden: " + arbolExpresion.inorden() + "\n");
            vista.actualizarResultado("Recorrido postorden: " + arbolExpresion.postorden() + "\n");
            
            vista.actualizarResultado("\n=== ESTADÍSTICAS ===\n");
            vista.actualizarResultado("Número de nodos: " + arbolExpresion.contarNodos() + "\n");
            vista.actualizarResultado("Altura: " + arbolExpresion.altura() + "\n");
            vista.actualizarResultado("Número de hojas: " + arbolExpresion.contarHojas() + "\n");
        } catch (Exception e) {
            vista.actualizarResultado("❌ Error al evaluar: " + e.getMessage() + "\n");
        }
    }
    
    private void limpiarResultados() {
        vista.limpiarResultados();
        vista.actualizarResultado("Resultados limpiados ✓\n");
    }
}