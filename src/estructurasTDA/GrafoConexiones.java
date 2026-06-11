package estructurasTDA;

public class GrafoConexiones implements IGrafoConexiones{
    private NodoVertice cabeza;

    public GrafoConexiones() {
        this.cabeza = null;
    }

    @Override
    public void agregarVertice(int idUsuario) {
        if (buscarVertice(idUsuario) != null) return;

        NodoVertice nuevo = new NodoVertice(idUsuario);
        nuevo.sig = cabeza;
        cabeza = nuevo;
    }

    @Override
    public void agregarArista(int idOrigen, int idDestino) {
        NodoVertice origen = buscarVertice(idOrigen);
        NodoVertice destino = buscarVertice(idDestino);

        if (origen == null || destino == null) return;
        if (existeArista(idOrigen, idDestino)) return;

        NodoArista nuevaArista1 = new NodoArista(idDestino);
        nuevaArista1.sig = origen.aristas;
        origen.aristas = nuevaArista1;

        NodoArista nuevaArista2 = new NodoArista(idOrigen);
        nuevaArista2.sig = destino.aristas;
        destino.aristas = nuevaArista2;
    }

    @Override
    public void eliminarVertice(int idUsuario) {
        if (cabeza == null) return; // Grafo vacío

        if (cabeza.idUsuario == idUsuario) {
            limpiarAristasEntrantes(cabeza);
            cabeza = cabeza.sig;
            return;
        }

        NodoVertice actual = cabeza;
        while (actual.sig != null) {
            if (actual.sig.idUsuario == idUsuario) {
                limpiarAristasEntrantes(actual.sig);
                actual.sig = actual.sig.sig;
                return;
            }
            actual = actual.sig;
        }
    }

    private void limpiarAristasEntrantes(NodoVertice verticeAEliminar) {
        NodoArista amigo = verticeAEliminar.aristas;

        while (amigo != null) {
            eliminarAristaUnidireccional(amigo.idDestino, verticeAEliminar.idUsuario);
            amigo = amigo.sig;
        }
    }

    @Override
    public void eliminarArista(int idOrigen, int idDestino) {
        eliminarAristaUnidireccional(idOrigen, idDestino);
        eliminarAristaUnidireccional(idDestino, idOrigen);
    }

    @Override
    public boolean existeArista(int idOrigen, int idDestino) {
        NodoVertice origen = buscarVertice(idOrigen);
        if (origen == null) return false;

        NodoArista actual = origen.aristas;
        while (actual != null) {
            if (actual.idDestino == idDestino) return true;
            actual = actual.sig;
        }
        return false;
    }

    @Override
    public int[] obtenerContactos(int idUsuario) {
        NodoVertice vertice = buscarVertice(idUsuario);
        if (vertice == null || vertice.aristas == null) return new int[0];

        int cantidad = 0;
        NodoArista aux = vertice.aristas;
        while (aux != null) {
            cantidad++;
            aux = aux.sig;
        }

        int[] contactos = new int[cantidad];
        aux = vertice.aristas;
        int i = 0;
        while (aux != null) {
            contactos[i++] = aux.idDestino;
            aux = aux.sig;
        }

        return contactos;
    }

    @Override
    public int calcularGradosDeSeparacion(int idOrigen, int idDestino) {
        if (idOrigen == idDestino) return 0;

        NodoVertice origen = buscarVertice(idOrigen);
        NodoVertice destino = buscarVertice(idDestino);
        if (origen == null || destino == null) return -1;

        NodoVertice aux = cabeza;
        while (aux != null) {
            aux.visitado = false;
            aux.distancia = -1;
            aux = aux.sig;
        }

        IColaUsuarios colaBFS = new ColaUsuarios();

        origen.visitado = true;
        origen.distancia = 0;
        colaBFS.encolar(idOrigen);

        while (!colaBFS.estaVacia()) {
            int idActual = colaBFS.desencolar();
            NodoVertice actual = buscarVertice(idActual);

            if (actual.idUsuario == idDestino) {
                return actual.distancia;
            }

            NodoArista aristaActual = actual.aristas;
            while (aristaActual != null) {
                NodoVertice vecino = buscarVertice(aristaActual.idDestino);

                if (vecino != null && !vecino.visitado) {
                    vecino.visitado = true;
                    vecino.distancia = actual.distancia + 1;
                    colaBFS.encolar(vecino.idUsuario);
                }
                aristaActual = aristaActual.sig;
            }
        }

        return -1;
    }

    private NodoVertice buscarVertice(int idUsuario) {
        NodoVertice actual = cabeza;
        while (actual != null) {
            if (actual.idUsuario == idUsuario) return actual;
            actual = actual.sig;
        }
        return null;
    }

    private void eliminarAristaUnidireccional(int origen, int destino) {
        NodoVertice vertOrigen = buscarVertice(origen);
        if (vertOrigen == null || vertOrigen.aristas == null) return;

        if (vertOrigen.aristas.idDestino == destino) {
            vertOrigen.aristas = vertOrigen.aristas.sig; // Era el primero
            return;
        }

        NodoArista actual = vertOrigen.aristas;
        while (actual.sig != null) {
            if (actual.sig.idDestino == destino) {
                actual.sig = actual.sig.sig; // Lo puenteamos
                return;
            }
            actual = actual.sig;
        }
    }
}
