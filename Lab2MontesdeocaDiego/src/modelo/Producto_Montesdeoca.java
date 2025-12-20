package modelo;

import estructuras.Comparador;

public class Producto_Montesdeoca implements Comparador {
	private int id;
	private String nombre;
	private int stock;

	public Producto_Montesdeoca(int id, String nombre, int stock) {
		this.id = id;
		this.nombre = nombre;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Nombre: " + nombre + ", Stock: " + stock;
	}

	@Override
	public boolean igualQue(Object q) {
		Producto_Montesdeoca producto = (Producto_Montesdeoca) q;
		return this.nombre.equalsIgnoreCase(producto.getNombre());
	}

	@Override
	public boolean menorQue(Object q) {
		Producto_Montesdeoca producto = (Producto_Montesdeoca) q;
		return this.nombre.compareToIgnoreCase(producto.getNombre()) < 0;
	}

	@Override
	public boolean menorIgualQue(Object q) {
		Producto_Montesdeoca producto = (Producto_Montesdeoca) q;
		return this.nombre.compareToIgnoreCase(producto.getNombre()) <= 0;
	}

	@Override
	public boolean mayorQue(Object q) {
		Producto_Montesdeoca producto = (Producto_Montesdeoca) q;
		return this.nombre.compareToIgnoreCase(producto.getNombre()) > 0;
	}

	@Override
	public boolean mayorIgualQue(Object q) {
		Producto_Montesdeoca producto = (Producto_Montesdeoca) q;
		return this.nombre.compareToIgnoreCase(producto.getNombre()) >= 0;
	}
}