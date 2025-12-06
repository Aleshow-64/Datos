package arbolbinario;

import tipopila.PilaLista;

public class ArbolExpresion extends ArbolBinario {
    
    public ArbolExpresion() {
        super();
    }

    public void construirDesdePostfija(String expresion) {
        crearDesdePostfija(expresion);
    }
    
    // Construir árbol de expresión desde notación postfija
    public void crearDesdePostfija(String expresion) {
        PilaLista pila = new PilaLista();
        String[] tokens = expresion.split(" ");
        
        for (String token : tokens) {
            if (esOperador(token)) {
                // Es operador, necesita dos operandos
                Nodo dcho = (Nodo) pila.quitar();
                Nodo izdo = (Nodo) pila.quitar();
                
                if (dcho == null || izdo == null) {
                    System.out.println("Error: Expresión postfija incorrecta");
                    return;
                }
                
                Nodo nuevo = nuevoArbol(izdo, token, dcho);
                pila.insertar(nuevo);
            } else {
                // Es operando
                pila.insertar(new Nodo(token));
            }
        }
        
        // La raíz queda en la pila
        this.raiz = (Nodo) pila.quitar();
    }
    
    // Evaluar expresión aritmética
    public double evaluar() {
        return evaluar(raiz);
    }
    
    private double evaluar(Nodo r) {
        if (r == null) {
            return 0;
        }
        
        String dato = r.getDato().toString();
        
        // Si es hoja (operando)
        if (r.SubarbolIzdo() == null && r.SubarbolDcho() == null) {
            try {
                return Double.parseDouble(dato);
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + dato + "' no es un número válido");
                return 0;
            }
        }
        
        // Evaluar subárboles
        double izq = evaluar(r.SubarbolIzdo());
        double der = evaluar(r.SubarbolDcho());
        
        // Aplicar operador
        switch (dato) {
            case "+":
                return izq + der;
            case "-":
                return izq - der;
            case "*":
                return izq * der;
            case "/":
                if (der == 0) {
                    System.out.println("Error: División por cero");
                    return 0;
                }
                return izq / der;
            case "^": // Añadir operador potencia
                return Math.pow(izq, der);
            default:
                System.out.println("Operador no válido: " + dato);
                return 0;
        }
    }
    
    // Verificar si un token es operador
    private boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/") || token.equals("^");
    }
    
    // Mostrar expresión en notación infija
    public String expresionInfija() {
        StringBuilder sb = new StringBuilder();
        expresionInfija(raiz, sb);
        return sb.toString();
    }
    
    private void expresionInfija(Nodo r, StringBuilder sb) {
        if (r != null) {
            if (esOperador(r.getDato().toString())) {
                sb.append("(");
            }
            
            expresionInfija(r.SubarbolIzdo(), sb);
            sb.append(r.getDato()).append(" ");
            expresionInfija(r.SubarbolDcho(), sb);
            
            if (esOperador(r.getDato().toString())) {
                sb.append(")");
            }
        }
    }
    
    // Métodos faltantes requeridos por Principal.java
    public String preorden() {
        return obtenerRecorrido(1);
    }
    
    public String inorden() {
        return obtenerRecorrido(2);
    }
    
    public String postorden() {
        return obtenerRecorrido(3);
    }
    
    private String obtenerRecorrido(int tipo) {
        StringBuilder sb = new StringBuilder();
        
        switch (tipo) {
            case 1: // preorden
                preordenString(raiz, sb);
                break;
            case 2: // inorden
                inordenString(raiz, sb);
                break;
            case 3: // postorden
                postordenString(raiz, sb);
                break;
        }
        
        return sb.toString().trim();
    }
    
    private void preordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            sb.append(r.getDato()).append(" ");
            preordenString(r.SubarbolIzdo(), sb);
            preordenString(r.SubarbolDcho(), sb);
        }
    }
    
    private void inordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            inordenString(r.SubarbolIzdo(), sb);
            sb.append(r.getDato()).append(" ");
            inordenString(r.SubarbolDcho(), sb);
        }
    }
    
    private void postordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            postordenString(r.SubarbolIzdo(), sb);
            postordenString(r.SubarbolDcho(), sb);
            sb.append(r.getDato()).append(" ");
        }
    }
    
    public int contarNodos() {
        return numNodos();
    }
    
    public int contarHojas() {
        return contarHojas(raiz);
    }
    
    private int contarHojas(Nodo r) {
        if (r == null) {
            return 0;
        }
        if (r.SubarbolIzdo() == null && r.SubarbolDcho() == null) {
            return 1;
        }
        return contarHojas(r.SubarbolIzdo()) + contarHojas(r.SubarbolDcho());
    }
    
    // Métodos para mostrar el árbol
    public void imprimirArbol() {
        imprimirArbol(raiz, 0);
    }

    private void imprimirArbol(Nodo r, int nivel) {
        if (r != null) {
            imprimirArbol(r.SubarbolDcho(), nivel + 1);
            
            for (int i = 0; i < nivel; i++) {
                System.out.print("    ");
            }
            
            System.out.println(r.getDato());
            
            imprimirArbol(r.SubarbolIzdo(), nivel + 1);
        }
    }
}