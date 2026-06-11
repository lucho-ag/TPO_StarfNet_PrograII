package estructurasTDA;

import entidades.PerfilEstado;

public interface IPilaCambios {
    void apilar(PerfilEstado estado);
    PerfilEstado desapilar();
    boolean estaVacia();
}
