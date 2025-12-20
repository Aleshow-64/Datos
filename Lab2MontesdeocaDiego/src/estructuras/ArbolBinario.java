package estructuras;

public class ArbolBinario {
    protected NodoArbol_Montesdeoca raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public ArbolBinario(NodoArbol_Montesdeoca raiz) {
        this.raiz = raiz;
    }

    public NodoArbol_Montesdeoca raizArbol() {
        return raiz;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public void preorden(NodoArbol_Montesdeoca r) {
        if (r != null) {
            r.visitar();
            preorden(r.subarbolIzdo());
            preorden(r.subarbolDcho());
        }
    }

    public void inorden(NodoArbol_Montesdeoca r) {
        if (r != null) {
            inorden(r.subarbolIzdo());
            r.visitar();
            inorden(r.subarbolDcho());
        }
    }

    public void postorden(NodoArbol_Montesdeoca r) {
        if (r != null) {
            postorden(r.subarbolIzdo());
            postorden(r.subarbolDcho());
            r.visitar();
        }
    }

    public int contarNodos(NodoArbol_Montesdeoca r) {
        if (r == null) {
            return 0;
        }
        return 1 + contarNodos(r.subarbolIzdo()) + contarNodos(r.subarbolDcho());
    }

    public NodoArbol_Montesdeoca getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbol_Montesdeoca raiz) {
        this.raiz = raiz;
    }

    public static NodoArbol_Montesdeoca nuevoArbol(NodoArbol_Montesdeoca ramaIzqda, Object dato, NodoArbol_Montesdeoca ramaDrcha) {
        return new NodoArbol_Montesdeoca(ramaIzqda, dato, ramaDrcha);
    }
    

}