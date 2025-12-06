package tipopila;

public class PilaLista {
    private NodoPila cima;
    
    public PilaLista() {
        cima = null;
    }
    
    // operación pilaVacia
    public boolean pilaVacia() {
        return cima == null;
    }
    
    // operación insertar (push)
    public void insertar(Object elemento) {
        NodoPila nuevo = new NodoPila(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
    }
    
    // operación quitar (pop)
    public Object quitar() {
        if (pilaVacia()) {
            System.out.println("Pila vacía, no se puede extraer.");
            return null;
        }
        Object aux = cima.dato;
        cima = cima.siguiente;
        return aux;
    }
    
    // operación cimaPila (peek)
    public Object cimaPila() {
        if (pilaVacia()) {
            System.out.println("Pila vacía");
            return null;
        }
        return cima.dato;
    }
    
    // operación limpiarPila
    public void limpiarPila() {
        while (!pilaVacia()) {
            quitar();
        }
    }
    
    // operación mostrar (para depuración)
    public void mostrar() {
        NodoPila aux = cima;
        System.out.print("Cima -> ");
        while (aux != null) {
            System.out.print(aux.dato + " -> ");
            aux = aux.siguiente;
        }
        System.out.println("null");
    }

    public void pushPila(Object elemento) {
        insertar(elemento);
    }
    
    public Object popPila() {
        return quitar();
    }
    
    public void mostrarPila() {
        mostrar();
    }
}