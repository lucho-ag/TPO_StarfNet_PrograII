package estructurasTDA;

import entidades.Habilidad;

public interface IArbolHabilidades {
    void insertar(int idPadre, Habilidad nuevaHabilidad);
    Habilidad[] obtenerHijosDirectos(String nombreCategoria);
    Habilidad buscarPorNombre(String nombre);
    boolean esDescendienteOIgual(String nombreAncestro, String nombreHijo);
}
