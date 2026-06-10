package estructurasTDA;

public interface IGrafoConexiones {
    void agregarUsuario(int idUsuario);
    void eliminarUsuario(int idOrigen, int idDestino);
    void agregarConexion(int idOrigen, int idDestino);
    void eliminarConexion(int idOrigen, int idDestino);
    boolean existeConexion(int idOrigen, int idDestino);
}
