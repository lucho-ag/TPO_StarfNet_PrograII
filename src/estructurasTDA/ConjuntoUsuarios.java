package estructurasTDA;

public class ConjuntoUsuarios implements IConjuntoUsuarios {
    private int[] elementos;
    private int cantidad;

    public ConjuntoUsuarios() {
        this.elementos = new int[100]; // dinámico si querés
        this.cantidad = 0;
    }

    @Override
    public void insertar(int elemento) {
        if (!pertenece(elemento)) {
            elementos[cantidad++] = elemento;
        }
    }

    @Override
    public void eliminar(int elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == elemento) {
                elementos[i] = elementos[--cantidad];
                return;
            }
        }
    }

    @Override
    public boolean pertenece(int elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == elemento) return true;
        }
        return false;
    }

    @Override
    public boolean estaVacio() { return cantidad == 0; }

    // Método extra que necesitás para RF13 (no está en la interface pero lo usás internamente)
    public int[] obtenerElementos() {
        int[] resultado = new int[cantidad];
        for (int i = 0; i < cantidad; i++) resultado[i] = elementos[i];
        return resultado;
    }
}
