package estructuras;

import modelo.Producto_Montesdeoca;

public class ListaProductos_Montesdeoca {
    
    public NodoProducto_Montesdeoca head;
    
    public void insertarAlInicio(Producto_Montesdeoca producto) {
        NodoProducto_Montesdeoca nuevo = new NodoProducto_Montesdeoca(producto);
        nuevo.siguiente = head;
        head = nuevo;
    }

    public void insertarAlFinal(Producto_Montesdeoca producto) {
        NodoProducto_Montesdeoca nuevo = new NodoProducto_Montesdeoca(producto);
        if (head == null) {
            head = nuevo;
            return;
        }
        NodoProducto_Montesdeoca actual = head;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
    }

    public void mostrarProductos() {
        NodoProducto_Montesdeoca actual = head;
        while (actual != null) {
            System.out.println(actual.producto);
            actual = actual.siguiente;
        }
    }

    public Producto_Montesdeoca buscarPorId(int id) {
        NodoProducto_Montesdeoca actual = head;
        while (actual != null) {
            if (actual.producto.getId() == id) {
                return actual.producto;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public void ordenarPorId() {
        head = insertionSort(head);
    }

    private NodoProducto_Montesdeoca insertionSort(NodoProducto_Montesdeoca cabeza) {
        NodoProducto_Montesdeoca sorted = null;
        NodoProducto_Montesdeoca actual = cabeza;
        while (actual != null) {
            NodoProducto_Montesdeoca siguiente = actual.siguiente;
            sorted = insertarOrdenado(sorted, actual);
            actual = siguiente;
        }
        return sorted;
    }

    private NodoProducto_Montesdeoca insertarOrdenado(NodoProducto_Montesdeoca cabeza, NodoProducto_Montesdeoca nuevo) {
        if (cabeza == null || nuevo.producto.getId() < cabeza.producto.getId()) {
            nuevo.siguiente = cabeza;
            return nuevo;
        }
        NodoProducto_Montesdeoca actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.producto.getId() < nuevo.producto.getId()) {
            actual = actual.siguiente;
        }
        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
        return cabeza;
    }
}