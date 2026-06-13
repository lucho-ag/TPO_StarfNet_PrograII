package estructurasTDA;

public class ConjuntoUsuarios implements IConjuntoUsuarios {
    private int[] datos;
    private int cantidad;
    private final int MAX = 200;

    public ConjuntoUsuarios() {
        this.datos = new int[MAX];
        this.cantidad = 0;
    }

    public void insertar(int elemento) {
        if (cantidad == MAX) {
            System.out.println("Conjunto lleno");
            return;
        }
        if (!pertenece(elemento)) {
            datos[cantidad] = elemento;
            cantidad++;
        }
    }

    public void eliminar(int elemento) {
        int pos = -1;
        for (int i = 0; i < cantidad; i++) {
            if (datos[i] == elemento) {
                pos = i;
                break;
            }
        }
        if (pos != -1) {
            for (int i = pos; i < cantidad - 1; i++) {
                datos[i] = datos[i + 1];
            }
            cantidad--;
        }
    }

    public boolean pertenece(int elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (datos[i] == elemento) {
                return true;
            }
        }
        return false;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    public int[] obtenerDatos() {
        int[] copia = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = datos[i];
        }
        return copia;
    }

    public ConjuntoUsuarios interseccion(ConjuntoUsuarios B) {
        ConjuntoUsuarios C = new ConjuntoUsuarios();
        for (int i = 0; i < this.cantidad; i++) {
            if (B.pertenece(this.datos[i])) {
                C.insertar(this.datos[i]);
            }
        }
        return C;
    }
}
