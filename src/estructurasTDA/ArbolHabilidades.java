package estructurasTDA;
import entidades.Habilidad;

public class ArbolHabilidades implements IArbolHabilidades {
    private NodoArbolHabilidades raiz;

    public ArbolHabilidades() {
        this.raiz = null;
    }

    @Override
    public void insertar(int idPadre, Habilidad nuevaHabilidad) {
        NodoArbolHabilidades nuevoNodo = new NodoArbolHabilidades(nuevaHabilidad);
        if (raiz == null) {
            raiz = nuevoNodo;
            return;
        }
        NodoArbolHabilidades padre = buscarNodo(raiz, idPadre);
        if (padre == null) return;

        if (padre.primerHijo == null) {
            padre.primerHijo = nuevoNodo;
        } else {
            NodoArbolHabilidades hermano = padre.primerHijo;
            while (hermano.hermano != null) {
                hermano = hermano.hermano;
            }
            hermano.hermano = nuevoNodo;
        }
    }

    @Override
    public Habilidad buscar(int idHabilidad) {
        NodoArbolHabilidades nodo = buscarNodo(raiz, idHabilidad);
        return nodo != null ? nodo.habilidad : null;
    }

    @Override
    public void mostrarEstructura() {
        mostrarRecursivo(raiz, 0);
    }

    private void mostrarRecursivo(NodoArbolHabilidades nodo, int nivel) {
        if (nodo == null) return;
        String indentacion = "  ".repeat(nivel);
        System.out.println(indentacion + "- " + nodo.habilidad.getNombre()
                + " [" + nodo.habilidad.getCategoria() + "]");
        mostrarRecursivo(nodo.primerHijo, nivel + 1);
        mostrarRecursivo(nodo.hermano, nivel);
    }

    private NodoArbolHabilidades buscarNodo(NodoArbolHabilidades nodo, int id) {
        if (nodo == null) return null;
        if (nodo.habilidad.getId() == id) return nodo;
        NodoArbolHabilidades porHijo = buscarNodo(nodo.primerHijo, id);
        if (porHijo != null) return porHijo;
        return buscarNodo(nodo.hermano, id);
    }
}
