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
        if (padre == null) {
            System.out.println("[⚠️] No se encontró la categoría padre con ID: " + idPadre);
            return;
        }

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
        if (nodo != null) {
            return nodo.habilidad;
        } else {
            return null;
        }
    }

    @Override
    public void mostrarEstructura() {
        System.out.println("\n--- CATÁLOGO GLOBAL DE HABILIDADES ---");
        mostrarRecursivo(raiz, 0);
    }

    public void mostrarSubEstructura(int idEspecialidad) {
        NodoArbolHabilidades subRaiz = buscarNodo(raiz, idEspecialidad);
        if (subRaiz != null) {
            System.out.println("\n--- ESPECIALIDAD: " + subRaiz.habilidad.getNombre() + " ---");
            mostrarRecursivo(subRaiz, 0);
        } else {
            System.out.println("[❌] Especialidad no encontrada.");
        }
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

    public Habilidad buscarPorNombre(String nombre) {
        NodoArbolHabilidades nodo = buscarNodoPorNombre(raiz, nombre);
        return nodo != null ? nodo.habilidad : null;
    }

    private NodoArbolHabilidades buscarNodoPorNombre(NodoArbolHabilidades nodo, String nombre) {
        if (nodo == null) return null;

        if (nodo.habilidad.getNombre().equalsIgnoreCase(nombre.trim())) {
            return nodo;
        }

        NodoArbolHabilidades encontrado = buscarNodoPorNombre(nodo.primerHijo, nombre);
        if (encontrado != null) return encontrado;

        return buscarNodoPorNombre(nodo.hermano, nombre);
    }
}
