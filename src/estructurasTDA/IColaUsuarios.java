package estructurasTDA;

public interface IColaUsuarios {
    void encolar(int idUsuario);
    int desencolar();
    int frente();
    boolean estaVacia();
}
