package estructurasTDA;
import entidades.Habilidad;

public class NodoArbolHabilidades {
    public Habilidad habilidad;
    public NodoArbolHabilidades primerHijo;
    public NodoArbolHabilidades hermano;

    public NodoArbolHabilidades(Habilidad habilidad) {
        this.habilidad = habilidad;
        this.primerHijo = null;
        this.hermano = null;
    }
}
