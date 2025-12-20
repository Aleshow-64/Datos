package modelo;

import estructuras.ArbolBinarioABB;

public class Centro_Montesdeoca {

	private String nombre;
	private ArbolBinarioABB inventario;

	public Centro_Montesdeoca(String nombre) {
		this.nombre = nombre;
		this.inventario = new ArbolBinarioABB();
	}

	public String getNombre() {
		return nombre;
	}

	public ArbolBinarioABB getInventario() {
		return inventario;
	}
}