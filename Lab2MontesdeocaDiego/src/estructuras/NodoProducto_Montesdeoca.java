package estructuras;

import modelo.Producto_Montesdeoca;

public class NodoProducto_Montesdeoca {

    public Producto_Montesdeoca producto;
    public NodoProducto_Montesdeoca siguiente;

    public NodoProducto_Montesdeoca(Producto_Montesdeoca producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    public Producto_Montesdeoca getProducto() {
        return producto;
    }

    public void setProducto(Producto_Montesdeoca producto) {
        this.producto = producto;
    }

    public NodoProducto_Montesdeoca getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProducto_Montesdeoca siguiente) {
        this.siguiente = siguiente;
    }
}