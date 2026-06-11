package estructurasTDA;

public class ColaUsuarios implements IColaUsuarios {
    private NodoCola frente;
    private NodoCola fin;

    public ColaUsuarios() {
        this.frente = null;
        this.fin = null;
    }

    @Override
    public void encolar(int idUsuario) {
        NodoCola nuevo = new NodoCola(idUsuario);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.sig = nuevo;
        }
        fin = nuevo;
    }

    @Override
    public int desencolar() {
        if (estaVacia()) {
            return -1;
        }
        int valor = frente.dato;
        frente = frente.sig;
        if (frente == null) {
            fin = null;
        }
        return valor;
    }

    @Override
    public int frente() {
        if (estaVacia()) return -1;
        return frente.dato;
    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

}
