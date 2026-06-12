package estructurasTDA;
import entidades.PerfilEstado;

public class NodoPila {
    public PerfilEstado dato;
    public NodoPila sig;

    public NodoPila(PerfilEstado dato) {
        this.dato = dato;
        this.sig = null;
    }
}