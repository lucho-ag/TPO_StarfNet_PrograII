package estructurasTDA;

public class ConjuntoUsuarios implements IConjuntoUsuarios {
    private NodoConjunto cabeza;

    public ConjuntoUsuarios() {
        this.cabeza = null;
    }

    @Override
    public void insertar(int elemento) {
        if (!pertenece(elemento)) {
            NodoConjunto nuevo = new NodoConjunto(elemento);
            nuevo.sig = cabeza;
            cabeza = nuevo;
        }
    }

    @Override
    public void eliminar(int elemento) {
        if (cabeza == null) return;
        if (cabeza.dato == elemento) {
            cabeza = cabeza.sig;
            return;
        }
        NodoConjunto actual = cabeza;
        while (actual.sig != null) {
            if (actual.sig.dato == elemento) {
                actual.sig = actual.sig.sig;
                return;
            }
            actual = actual.sig;
        }
    }

    @Override
    public boolean pertenece(int elemento) {
        NodoConjunto actual = cabeza;
        while (actual != null) {
            if (actual.dato == elemento) {
                return true;
            }
            actual = actual.sig;
        }
        return false;
    }

    @Override
    public boolean estaVacio() {
        return cabeza == null;
    }

    public int tamanio() {
        int cant = 0;
        NodoConjunto actual = cabeza;
        while (actual != null) {
            cant++;
            actual = actual.sig;
        }
        return cant;
    }

    public int[] obtenerDatos() {
        int cant = tamanio();
        int[] copia = new int[cant];
        NodoConjunto actual = cabeza;
        int i = 0;
        while (actual != null) {
            copia[i++] = actual.dato;
            actual = actual.sig;
        }
        return copia;
    }

    public ConjuntoUsuarios interseccion(ConjuntoUsuarios B) {
        ConjuntoUsuarios C = new ConjuntoUsuarios();
        NodoConjunto actual = this.cabeza;
        while (actual != null) {
            if (B.pertenece(actual.dato)) {
                C.insertar(actual.dato);
            }
            actual = actual.sig;
        }
        return C;
    }
}
