package estructurasTDA;

public interface IGrafoConexiones {
    void agregarVertice(int idUsuario);
    void eliminarVertice(int idOrigen, int idDestino);
    void agregarArista(int idOrigen, int idDestino);
    void eliminarArista(int idOrigen, int idDestino);
    boolean existeArista(int idOrigen, int idDestino);
    int[] obtenerContactos(int idUsuario);
    int calcularGradosDeSeparacion(int idOrigen, int idDestino);
}
