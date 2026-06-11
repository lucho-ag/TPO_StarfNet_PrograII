package estructurasTDA;

import entidades.Usuario;

public interface IArbolUsuariosAVL {
    void insertar(int id, Usuario perfil);
    Usuario buscar(int id);
    void eliminar(int id);
    boolean existe(int id);
}
