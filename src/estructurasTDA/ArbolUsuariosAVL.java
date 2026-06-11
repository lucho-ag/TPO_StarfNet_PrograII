package estructurasTDA;

import entidades.Usuario;

public class ArbolUsuariosAVL implements IArbolUsuariosAVL {
    private NodoArbolUsuarios raiz;

    public ArbolUsuariosAVL() {
        this.raiz = null;
    }

    @Override
    public void insertar(int id, Usuario perfil) {
        raiz = insertarRecursivo(raiz, id, perfil);
    }

    @Override
    public Usuario buscar(int id) {
        return buscarRecursivo(raiz, id);
    }

    @Override
    public void eliminar(int id) {
        raiz = eliminarRecursivo(raiz, id);
    }

    @Override
    public boolean existe(int id) {
        return buscar(id) != null;
    }

    private Usuario buscarRecursivo(NodoArbolUsuarios nodo, int id) {
        if (nodo == null) {
            return null;
        }
        if (id == nodo.clave) {
            return nodo.valor;
        }

        if (id < nodo.clave) {
            return buscarRecursivo(nodo.izquierdo, id);
        } else {
            return buscarRecursivo(nodo.derecho, id);
        }
    }

    private NodoArbolUsuarios insertarRecursivo(NodoArbolUsuarios nodo, int id, Usuario perfil) {
        if (nodo == null) {
            return new NodoArbolUsuarios(id, perfil);
        }

        if (id < nodo.clave) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, id, perfil);
        } else if (id > nodo.clave) {
            nodo.derecho = insertarRecursivo(nodo.derecho, id, perfil);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        int balance = obtenerBalance(nodo);

        if (balance > 1 && id < nodo.izquierdo.clave) {
            return rotarDerecha(nodo);
        }

        if (balance < -1 && id > nodo.derecho.clave) {
            return rotarIzquierda(nodo);
        }

        if (balance > 1 && id > nodo.izquierdo.clave) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && id < nodo.derecho.clave) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoArbolUsuarios eliminarRecursivo(NodoArbolUsuarios raiz, int id) {
        if (raiz == null) {
            return raiz;
        }

        if (id < raiz.clave) {
            raiz.izquierdo = eliminarRecursivo(raiz.izquierdo, id);
        } else if (id > raiz.clave) {
            raiz.derecho = eliminarRecursivo(raiz.derecho, id);
        } else {
            if ((raiz.izquierdo == null) || (raiz.derecho == null)) {
                NodoArbolUsuarios temp = null;
                if (temp == raiz.izquierdo) {
                    temp = raiz.derecho;
                } else {
                    temp = raiz.izquierdo;
                }

                if (temp == null) {
                    raiz = null;
                } else {
                    raiz = temp;
                }
            } else {
                NodoArbolUsuarios temp = nodoMinimo(raiz.derecho);
                raiz.clave = temp.clave;
                raiz.valor = temp.valor;
                raiz.derecho = eliminarRecursivo(raiz.derecho, temp.clave);
            }
        }

        if (raiz == null) {
            return raiz;
        }

        raiz.altura = Math.max(altura(raiz.izquierdo), altura(raiz.derecho)) + 1;

        int balance = obtenerBalance(raiz);

        if (balance > 1 && obtenerBalance(raiz.izquierdo) >= 0) {
            return rotarDerecha(raiz);
        }
        if (balance > 1 && obtenerBalance(raiz.izquierdo) < 0) {
            raiz.izquierdo = rotarIzquierda(raiz.izquierdo);
            return rotarDerecha(raiz);
        }
        if (balance < -1 && obtenerBalance(raiz.derecho) <= 0) {
            return rotarIzquierda(raiz);
        }
        if (balance < -1 && obtenerBalance(raiz.derecho) > 0) {
            raiz.derecho = rotarDerecha(raiz.derecho);
            return rotarIzquierda(raiz);
        }

        return raiz;
    }

    private int altura(NodoArbolUsuarios nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    private int obtenerBalance(NodoArbolUsuarios nodo) {
        if (nodo == null) return 0;
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private NodoArbolUsuarios rotarDerecha(NodoArbolUsuarios y) {
        NodoArbolUsuarios x = y.izquierdo;
        NodoArbolUsuarios T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    private NodoArbolUsuarios rotarIzquierda(NodoArbolUsuarios x) {
        NodoArbolUsuarios y = x.derecho;
        NodoArbolUsuarios T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    private NodoArbolUsuarios nodoMinimo(NodoArbolUsuarios nodo) {
        NodoArbolUsuarios actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

}
