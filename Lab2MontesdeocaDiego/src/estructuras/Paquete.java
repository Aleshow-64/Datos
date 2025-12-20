package estructuras;

public class Paquete implements Comparador {
    public int id;
    public String info;
    
    public Paquete(int id, String info) {
        this.id = id;
        this.info = info;
    }
    
    @Override
    public boolean igualQue(Object q) {
        Paquete p = (Paquete) q;
        return this.id == p.id;
    }
    
    @Override
    public boolean menorQue(Object q) {
        Paquete p = (Paquete) q;
        return this.id < p.id;
    }
    
    @Override
    public boolean menorIgualQue(Object q) {
        Paquete p = (Paquete) q;
        return this.id <= p.id;
    }
    
    @Override
    public boolean mayorQue(Object q) {
        Paquete p = (Paquete) q;
        return this.id > p.id;
    }
    
    @Override
    public boolean mayorIgualQue(Object q) {
        Paquete p = (Paquete) q;
        return this.id >= p.id;
    }
    
    @Override
    public String toString() {
        return "Paquete [ID=" + id + ", Info=" + info + "]";
    }
}