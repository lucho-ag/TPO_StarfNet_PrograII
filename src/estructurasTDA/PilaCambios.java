package estructurasTDA;
import entidades.PerfilEstado;

public class PilaCambios implements IPilaCambios {
    private NodoPila tope;

    public PilaCambios() { this.tope = null; }

    @Override
    public void apilar(PerfilEstado estado) {
        NodoPila nuevo = new NodoPila(estado);
        nuevo.sig = tope;
        tope = nuevo;
    }

    @Override
    public PerfilEstado desapilar() {
        if (estaVacia()) return null;
        PerfilEstado dato = tope.dato;
        tope = tope.sig;
        return dato;
    }

    @Override
    public boolean estaVacia() { return tope == null; }
}