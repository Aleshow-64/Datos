package estructuras;

public class NodoArbol_Montesdeoca {
    protected Object producto;
    protected NodoArbol_Montesdeoca izdo;
    protected NodoArbol_Montesdeoca dcho;

    public NodoArbol_Montesdeoca(Object valor) {
        producto = valor;
        izdo = dcho = null;
    }

    public NodoArbol_Montesdeoca(NodoArbol_Montesdeoca ramaIzdo, Object valor, NodoArbol_Montesdeoca ramaDcho) {
        this.producto = valor;
        this.izdo = ramaIzdo;
        this.dcho = ramaDcho;
    }

    public Object valorNodo() {
        return producto;
    }

    public NodoArbol_Montesdeoca subarbolIzdo() {
        return izdo;
    }

    public NodoArbol_Montesdeoca subarbolDcho() {
        return dcho;
    }

    public void nuevoValor(Object d) {
        producto = d;
    }

    public void ramaIzdo(NodoArbol_Montesdeoca n) {
        izdo = n;
    }

    public void ramaDcho(NodoArbol_Montesdeoca n) {
        dcho = n;
    }

    public void visitar() {
        System.out.print(producto + " ");
    }
}