package estructurasTDA;

public class NodoVertice {
    public int idUsuario;
    public NodoArista aristas;
    public NodoVertice sig;

    public boolean visitado;
    public int distancia;

    public NodoVertice(int idUsuario) {
        this.idUsuario = idUsuario;
        this.aristas = null;
        this.sig = null;
        this.visitado = false;
        this.distancia = 0;
    }
}
