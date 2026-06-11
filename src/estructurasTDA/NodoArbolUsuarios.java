package estructurasTDA;

import entidades.Usuario;

public class NodoArbolUsuarios {
    public int clave;
    public Usuario valor;
    int altura;

    public NodoArbolUsuarios izquierdo;
    public NodoArbolUsuarios derecho;

    public NodoArbolUsuarios(int clave, Usuario valor) {
        this.clave = clave;
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }
}
