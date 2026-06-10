package estructurasTDA;

import entidades.Usuario;

public interface IDiccionarioUsuarios {
    void insertar(int id, Usuario perfil);
    Usuario buscar(int id);
    void eliminar(int id);
    boolean existe(int id);
}
