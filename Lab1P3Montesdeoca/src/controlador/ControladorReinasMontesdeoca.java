package controlador;

import modelo.TableroModeloMontesdeoca;
import vista.InterfazPrincipalMontesdeoca;

public class ControladorReinasMontesdeoca {
    private TableroModeloMontesdeoca modelo;
    private InterfazPrincipalMontesdeoca vista;

    public ControladorReinasMontesdeoca() {
        this.modelo = new TableroModeloMontesdeoca(8);
    }

    public void iniciar() {
        this.vista = new InterfazPrincipalMontesdeoca(this, modelo);
        vista.mostrar();
    }
    
    public void resolverUnaSolucion() {
        int n = vista.getN();
        modelo.setN(n);
        
        if (modelo.resolverNReinas()) {
            vista.actualizarTablero();
            vista.actualizarContadorSoluciones(modelo.getSolucionesEncontradas());
            vista.mostrarMensaje("✓ Solución encontrada para N = " + n);
        } else {
            vista.mostrarMensaje("✗ No hay solución para N = " + n);
        }
    }

    public void contarTodasSoluciones() {
        int n = vista.getN();
        modelo.setN(n);
        modelo.encontrarTodasLasSoluciones();
        
        vista.actualizarTablero();
        vista.actualizarContadorSoluciones(modelo.getSolucionesEncontradas());
        vista.mostrarMensaje("🔍 Se encontraron " + modelo.getSolucionesEncontradas() + " soluciones para N = " + n);
    }

    public void limpiarTablero() {
        modelo.reiniciarTablero();
        vista.actualizarTablero();
        vista.actualizarContadorSoluciones(0);
    }

    public TableroModeloMontesdeoca getModelo() {
        return modelo;
    }
}