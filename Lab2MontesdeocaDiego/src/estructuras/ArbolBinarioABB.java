package estructuras;

public class ArbolBinarioABB extends ArbolBinario {

    public ArbolBinarioABB() {
        super();
    }

    public NodoArbol_Montesdeoca buscar(Object buscado) {
        Comparador dato;
        dato = (Comparador) buscado;
        if (raiz == null)
            return null;
        else
            return localizar(raizArbol(), dato);
    }

    protected NodoArbol_Montesdeoca localizar(NodoArbol_Montesdeoca raizSub, Comparador buscado) {
        if (raizSub == null)
            return null;
        else if (buscado.igualQue(raizSub.valorNodo()))
            return raizSub;
        else if (buscado.menorQue(raizSub.valorNodo()))
            return localizar(raizSub.subarbolIzdo(), buscado);
        else
            return localizar(raizSub.subarbolDcho(), buscado);
    }

    public NodoArbol_Montesdeoca buscarIterativo(Object buscado) {
        Comparador dato;
        boolean encontrado = false;
        NodoArbol_Montesdeoca raizSub = raiz;
        dato = (Comparador) buscado;
        while (!encontrado && raizSub != null) {
            if (dato.igualQue(raizSub.valorNodo()))
                encontrado = true;
            else if (dato.menorQue(raizSub.valorNodo()))
                raizSub = raizSub.subarbolIzdo();
            else
                raizSub = raizSub.subarbolDcho();
        }
        return raizSub;
    }

    public void insertar(Object valor) throws Exception {
        Comparador dato;
        dato = (Comparador) valor;
        raiz = insertar(raiz, dato);
    }

    protected NodoArbol_Montesdeoca insertar(NodoArbol_Montesdeoca raizSub, Comparador dato) throws Exception {
        if (raizSub == null)
            raizSub = new NodoArbol_Montesdeoca(dato);
        else if (dato.menorQue(raizSub.valorNodo())) {
            NodoArbol_Montesdeoca iz;
            iz = insertar(raizSub.subarbolIzdo(), dato);
            raizSub.ramaIzdo(iz);
        } else if (dato.mayorQue(raizSub.valorNodo())) {
            NodoArbol_Montesdeoca dr;
            dr = insertar(raizSub.subarbolDcho(), dato);
            raizSub.ramaDcho(dr);
        } else
            throw new Exception("Nodo duplicado");
        return raizSub;
    }

    public void eliminar(Object valor) throws Exception {
        Comparador dato;
        dato = (Comparador) valor;
        raiz = eliminar(raiz, dato);
    }

    protected NodoArbol_Montesdeoca eliminar(NodoArbol_Montesdeoca raizSub, Comparador dato) throws Exception {
        if (raizSub == null)
            throw new Exception("No encontrado el nodo con la clave");
        else if (dato.menorQue(raizSub.valorNodo())) {
            NodoArbol_Montesdeoca iz;
            iz = eliminar(raizSub.subarbolIzdo(), dato);
            raizSub.ramaIzdo(iz);
        } else if (dato.mayorQue(raizSub.valorNodo())) {
            NodoArbol_Montesdeoca dr;
            dr = eliminar(raizSub.subarbolDcho(), dato);
            raizSub.ramaDcho(dr);
        } else {
            NodoArbol_Montesdeoca q;
            q = raizSub;
            if (q.subarbolIzdo() == null)
                raizSub = q.subarbolDcho();
            else if (q.subarbolDcho() == null)
                raizSub = q.subarbolIzdo();
            else {
                q = reemplazar(q);
            }
            q = null;
        }
        return raizSub;
    }

    private NodoArbol_Montesdeoca reemplazar(NodoArbol_Montesdeoca act) {
        NodoArbol_Montesdeoca a, p;
        p = act;
        a = act.subarbolIzdo();
        while (a.subarbolDcho() != null) {
            p = a;
            a = a.subarbolDcho();
        }
        act.nuevoValor(a.valorNodo());
        if (p == act)
            p.ramaIzdo(a.subarbolIzdo());
        else
            p.ramaDcho(a.subarbolIzdo());
        return a;
    }
}