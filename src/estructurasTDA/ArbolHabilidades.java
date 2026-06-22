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


    private NodoArbolHabilidades buscarNodo(NodoArbolHabilidades nodo, int id) {
        if (nodo == null) return null;
        if (nodo.habilidad.getId() == id) return nodo;
        NodoArbolHabilidades porHijo = buscarNodo(nodo.primerHijo, id);
        if (porHijo != null) return porHijo;
        return buscarNodo(nodo.hermano, id);
    }

    @Override
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

    @Override
    public Habilidad[] obtenerHijosDirectos(String nombreCategoria) {
        NodoArbolHabilidades nodo = buscarNodoPorNombre(raiz, nombreCategoria);
        if (nodo == null || nodo.primerHijo == null) {
            return new Habilidad[0];
        }

        int count = 0;
        NodoArbolHabilidades aux = nodo.primerHijo;
        while (aux != null) {
            count++;
            aux = aux.hermano;
        }

        Habilidad[] hijos = new Habilidad[count];
        aux = nodo.primerHijo;
        int i = 0;
        while (aux != null) {
            hijos[i++] = aux.habilidad;
            aux = aux.hermano;
        }
        return hijos;
    }

    @Override
    public boolean esDescendienteOIgual(String nombreAncestro, String nombreHijo) {
        if (nombreAncestro == null || nombreHijo == null) return false;
        if (nombreAncestro.trim().equalsIgnoreCase(nombreHijo.trim())) return true;

        NodoArbolHabilidades ancestro = buscarNodoPorNombre(raiz, nombreAncestro);
        if (ancestro == null) return false;

        NodoArbolHabilidades nodoHijo = buscarNodoPorNombre(ancestro.primerHijo, nombreHijo);
        return nodoHijo != null;
    }
}
