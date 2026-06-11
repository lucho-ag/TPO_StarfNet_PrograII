package estructurasTDA;

public class NodoArista {
    public int idDestino;
    public NodoArista sig;

    public NodoArista(int idDestino) {
        this.idDestino = idDestino;
        this.sig = null;
    }
}
