package estructurasTDA;

public interface IConjuntoUsuarios {
    void insertar(int elemento);
    void eliminar(int elemento);
    boolean pertenece(int elemento);
    boolean estaVacio();
}
