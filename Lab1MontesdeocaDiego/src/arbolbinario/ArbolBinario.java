package arbolbinario;

import tipopila.PilaLista;
import java.util.Scanner;

public class ArbolBinario {
    protected Nodo raiz;

    public ArbolBinario() {
        this.raiz = null;
    }
    
    public ArbolBinario(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo raizArbol() {
        return raiz;
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public static Nodo nuevoArbol(Nodo ramaIzdo, Object dato, Nodo ramaDcho) {
        return new Nodo(ramaIzdo, dato, ramaDcho);
    }
    
    // RECORRIDOS RECURSIVOS
    public static void preorden(Nodo r) {
        if (r != null) {
            System.out.print(r.getDato() + " ");
            preorden(r.SubarbolIzdo());
            preorden(r.SubarbolDcho());
        }
    }
    
    public static void inorden(Nodo r) {
        if (r != null) {
            inorden(r.SubarbolIzdo());
            System.out.print(r.getDato() + " ");
            inorden(r.SubarbolDcho());
        }
    }
    
    public static void postorden(Nodo r) {
        if (r != null) {
            postorden(r.SubarbolIzdo());
            postorden(r.SubarbolDcho());
            System.out.print(r.getDato() + " ");
        }
    }
    
    public String preorden() {
        StringBuilder sb = new StringBuilder();
        preordenString(raiz, sb);
        return sb.toString().trim();
    }
    
    private void preordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            sb.append(r.getDato()).append(" ");
            preordenString(r.SubarbolIzdo(), sb);
            preordenString(r.SubarbolDcho(), sb);
        }
    }
    
    public String inorden() {
        StringBuilder sb = new StringBuilder();
        inordenString(raiz, sb);
        return sb.toString().trim();
    }
    
    private void inordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            inordenString(r.SubarbolIzdo(), sb);
            sb.append(r.getDato()).append(" ");
            inordenString(r.SubarbolDcho(), sb);
        }
    }
    
    public String postorden() {
        StringBuilder sb = new StringBuilder();
        postordenString(raiz, sb);
        return sb.toString().trim();
    }
    
    private void postordenString(Nodo r, StringBuilder sb) {
        if (r != null) {
            postordenString(r.SubarbolIzdo(), sb);
            postordenString(r.SubarbolDcho(), sb);
            sb.append(r.getDato()).append(" ");
        }
    }
    
    // RECORRIDOS ITERATIVOS
    public String preordenIterativo() {
        if (raiz == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        PilaLista pila = new PilaLista();
        pila.insertar(raiz);
        
        while (!pila.pilaVacia()) {
            Nodo nodo = (Nodo) pila.quitar();
            sb.append(nodo.getDato()).append(" ");
            
            if (nodo.SubarbolDcho() != null) {
                pila.insertar(nodo.SubarbolDcho());
            }
            if (nodo.SubarbolIzdo() != null) {
                pila.insertar(nodo.SubarbolIzdo());
            }
        }
        return sb.toString().trim();
    }
    
    public String inordenIterativo() {
        StringBuilder sb = new StringBuilder();
        PilaLista pila = new PilaLista();
        Nodo actual = raiz;
        
        while (actual != null || !pila.pilaVacia()) {
            while (actual != null) {
                pila.insertar(actual);
                actual = actual.SubarbolIzdo();
            }
            
            actual = (Nodo) pila.quitar();
            sb.append(actual.getDato()).append(" ");
            actual = actual.SubarbolDcho();
        }
        return sb.toString().trim();
    }
    
    // MÉTODOS AUXILIARES
    public boolean buscar(Object dato) {
        return buscarRec(raiz, dato);
    }
    
    private boolean buscarRec(Nodo r, Object dato) {
        if (r == null) {
            return false;
        }
        if (r.getDato().equals(dato)) {
            return true;
        }
        return buscarRec(r.SubarbolIzdo(), dato) || 
               buscarRec(r.SubarbolDcho(), dato);
    }
    
    public int numNodos() {
        return contarNodos(raiz);
    }
    
    private int contarNodos(Nodo r) {
        if (r == null) {
            return 0;
        }
        return 1 + contarNodos(r.SubarbolIzdo()) + contarNodos(r.SubarbolDcho());
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
    
    public int altura() {
        return altura(raiz);
    }
    
    private int altura(Nodo r) {
        if (r == null) {
            return -1;
        }
        int alturaIzq = altura(r.SubarbolIzdo());
        int alturaDer = altura(r.SubarbolDcho());
        return Math.max(alturaIzq, alturaDer) + 1;
    }
    
    public static ArbolBinario crearArbol() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Creación de árbol binario (ingrese 'null' para nodo vacío)");
        Nodo raiz = crearNodo(sc);
        return new ArbolBinario(raiz);
    }
    
    private static Nodo crearNodo(Scanner sc) {
        System.out.print("Valor del nodo (o 'null'): ");
        String valor = sc.nextLine();
        
        if (valor.equalsIgnoreCase("null")) {
            return null;
        }
        
        System.out.println("Creando subárbol izquierdo de " + valor);
        Nodo izdo = crearNodo(sc);
        
        System.out.println("Creando subárbol derecho de " + valor);
        Nodo dcho = crearNodo(sc);
        
        return new Nodo(izdo, valor, dcho);
    }
    
    public String mostrarArbol() {
        StringBuilder sb = new StringBuilder();
        mostrarArbol(raiz, 0, sb);
        return sb.toString();
    }
    
    private void mostrarArbol(Nodo r, int nivel, StringBuilder sb) {
        if (r != null) {
            mostrarArbol(r.SubarbolDcho(), nivel + 1, sb);
            
            for (int i = 0; i < nivel; i++) {
                sb.append("    ");
            }
            
            sb.append(r.getDato()).append("\n");
            
            mostrarArbol(r.SubarbolIzdo(), nivel + 1, sb);
        }
    }
    
    public String mostrarArbolConLineas() {
        StringBuilder sb = new StringBuilder();
        mostrarArbolConLineas(raiz, "", true, sb);
        return sb.toString();
    }
    
    private void mostrarArbolConLineas(Nodo r, String prefijo, boolean esIzquierdo, StringBuilder sb) {
        if (r != null) {
            sb.append(prefijo).append(esIzquierdo ? "├── " : "└── ").append(r.getDato()).append("\n");
            
            String nuevoPrefijo = prefijo + (esIzquierdo ? "│   " : "    ");
            
            mostrarArbolConLineas(r.SubarbolIzdo(), nuevoPrefijo, true, sb);
            mostrarArbolConLineas(r.SubarbolDcho(), nuevoPrefijo, false, sb);
        }
    }
    
    // Método específico para el ejemplo de Joyanes Aguilar
    public static ArbolBinario crearArbolJoyanes() {
        // Crear nodos hoja
        Nodo maria = nuevoArbol(null, "Maria", null);
        Nodo rodrigo = nuevoArbol(null, "Rodrigo", null);
        Nodo anyora = nuevoArbol(null, "Anyora", null);
        Nodo abel = nuevoArbol(null, "Abel", null);
        
        // Crear subárboles
        Nodo subArbolIzdo = nuevoArbol(maria, "Esperanza", rodrigo);
        Nodo subArbolDcho = nuevoArbol(anyora, "M Jesus", abel);
        
        // Crear raíz
        Nodo raiz = nuevoArbol(subArbolIzdo, "Esperanza", subArbolDcho);
        
        return new ArbolBinario(raiz);
    }
}