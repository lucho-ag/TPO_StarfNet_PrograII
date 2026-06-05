public interface ICola {
    boolean estaVacia();
    boolean estaLlena();
    void encolar(int elemento);
    int desencolar();
    int frente();
    int tamano();
}
