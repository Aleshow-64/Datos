package tipopila;

public class NodoPila {
    Object dato;
    NodoPila siguiente;
    
    public NodoPila(Object x) {
        dato = x;
        siguiente = null;
    }
}