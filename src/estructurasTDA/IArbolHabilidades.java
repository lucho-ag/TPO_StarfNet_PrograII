package estructurasTDA;

import entidades.Habilidad;

public interface IArbolHabilidades {
    void insertar(int idPadre, Habilidad nuevaHabilidad);
    Habilidad buscar(int idHabilidad);
    void mostrarEstructura();
    Habilidad[] obtenerHijosDirectos(String nombreCategoria);
    boolean esDescendienteOIgual(String nombreAncestro, String nombreHijo);
}
