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
        if (nombre == null) return null;
        NodoArbolHabilidades nodo = buscarNodoPorNombreRecursivo(raiz, nombre.trim());
        return nodo != null ? nodo.habilidad : null;
    }

    private NodoArbolHabilidades buscarNodoPorNombreRecursivo(NodoArbolHabilidades nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nodo.habilidad.getNombre().equalsIgnoreCase(nombre)) {
            return nodo;
        }

        NodoArbolHabilidades encontrado = buscarNodoPorNombreRecursivo(nodo.primerHijo, nombre);
        if (encontrado != null) {
            return encontrado;
        }

        return buscarNodoPorNombreRecursivo(nodo.hermano, nombre);
    }

    @Override
    public Habilidad[] obtenerHijosDirectos(String nombreCategoria) {
        if (nombreCategoria == null) return new Habilidad[0];
        NodoArbolHabilidades nodo = buscarNodoPorNombreRecursivo(raiz, nombreCategoria.trim());
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

        String ancestroTrim = nombreAncestro.trim();
        String hijoTrim = nombreHijo.trim();

        if (ancestroTrim.equalsIgnoreCase(hijoTrim)) return true;

        NodoArbolHabilidades ancestro = buscarNodoPorNombreRecursivo(raiz, ancestroTrim);
        if (ancestro == null) return false;

        NodoArbolHabilidades nodoHijo = buscarNodoPorNombreRecursivo(ancestro.primerHijo, hijoTrim);
        return nodoHijo != null;
    }
}