package interfaz;

import entidades.*;
import estructurasTDA.*;

public class SistemaRedSocial {

    private IArbolUsuariosAVL arbolUsuarios;
    private IGrafoConexiones grafoContactos;
    private ArbolHabilidades arbolHabilidades;

    private int[] todosLosIds;
    private int cantidadUsuarios;
    private int proximoIdUsuario;

    private Oferta[] ofertas;
    private int cantidadOfertas;
    private int proximoIdOferta;

    private Usuario usuarioActual;

    public SistemaRedSocial() {
        this.arbolUsuarios = new ArbolUsuariosAVL();
        this.grafoContactos = new GrafoConexiones();
        this.todosLosIds = new int[500];
        this.cantidadUsuarios = 0;
        this.proximoIdUsuario = 1;
        this.ofertas = new Oferta[200];
        this.cantidadOfertas = 0;
        this.proximoIdOferta = 1;
        this.usuarioActual = null;
        this.arbolHabilidades = new ArbolHabilidades();
        cargarArbolHabilidadesDefault();
    }

    public boolean registrarUsuario(String nombre, String email, String contrasenia, String rol) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario u = arbolUsuarios.buscar(todosLosIds[i]);
            if (u != null && u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Error: ya existe una cuenta con ese email.");
                return false;
            }
        }
        int nuevoId = proximoIdUsuario++;
        Usuario nuevo = new Usuario(nuevoId, nombre, email, contrasenia, rol);
        arbolUsuarios.insertar(nuevoId, nuevo);
        grafoContactos.agregarVertice(nuevoId);
        todosLosIds[cantidadUsuarios++] = nuevoId;
        System.out.println("Usuario registrado con ID: " + nuevoId);
        return true;
    }

    public boolean iniciarSesion(String email, String contrasenia) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario u = arbolUsuarios.buscar(todosLosIds[i]);
            if (u != null && u.getEmail().equalsIgnoreCase(email)) {
                if (u.validarContrasenia(contrasenia)) {
                    this.usuarioActual = u;
                    return true;
                } else {
                    System.out.println("[❌] Error: Contraseña incorrecta.");
                    return false;
                }
            }
        }
        System.out.println("[❌] Error: No existe ninguna cuenta con ese correo.");
        return false;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public void editarPerfilActual(String nombre, String profesion, String ciudad, String resumen) {
        if (this.usuarioActual == null) {
            System.out.println("[❌] Error de seguridad: No hay sesión activa.");
            return;
        }

        PerfilEstado estadoAnterior = new PerfilEstado(
                this.usuarioActual.getNombre(),
                this.usuarioActual.getProfesion(),
                this.usuarioActual.getCiudad(),
                this.usuarioActual.getResumen()
        );

        this.usuarioActual.getHistorial().apilar(estadoAnterior);

        this.usuarioActual.setNombre(nombre);
        this.usuarioActual.setProfesion(profesion);
        this.usuarioActual.setCiudad(ciudad);
        this.usuarioActual.setResumen(resumen);

        System.out.println("[✅] Perfil actualizado con éxito. Cambio respaldado en el historial.");
    }

    public boolean deshacerUltimoCambio() {
        if (this.usuarioActual == null) {
            System.out.println("[❌] Error: No hay sesión activa.");
            return false;
        }

        if (this.usuarioActual.getHistorial().estaVacia()) {
            System.out.println("[⚠️] No hay más cambios para deshacer en esta sesión.");
            return false;
        }

        PerfilEstado ultimoEstado = this.usuarioActual.getHistorial().desapilar();

        this.usuarioActual.setNombre(ultimoEstado.getNombre());
        this.usuarioActual.setProfesion(ultimoEstado.getProfesion());
        this.usuarioActual.setCiudad(ultimoEstado.getCiudad());
        this.usuarioActual.setResumen(ultimoEstado.getResumen());

        System.out.println("[⏪] ¡Cambio deshecho! Se ha restaurado la versión anterior de tu perfil.");
        return true;
    }

    public Usuario buscarUsuario(int id) {
        Usuario u = arbolUsuarios.buscar(id);
        if (u == null || !u.isActivo()) {
            System.out.println("Perfil no encontrado.");
            return null;
        }
        return u;
    }

    public boolean enviarSolicitudConexion(int idOrigen, int idDestino) {
        if (!arbolUsuarios.existe(idOrigen) || !arbolUsuarios.existe(idDestino)) {
            System.out.println("Uno o ambos usuarios no existen.");
            return false;
        }
        if (idOrigen == idDestino) {
            System.out.println("No podés enviarte una solicitud a vos mismo.");
            return false;
        }
        if (grafoContactos.existeArista(idOrigen, idDestino)) {
            System.out.println("Ya son contactos.");
            return false;
        }
        Usuario destino = arbolUsuarios.buscar(idDestino);
        destino.getSolicitudesPendientes().encolar(idOrigen);
        System.out.println("Solicitud enviada correctamente.");
        return true;
    }

    public boolean procesarConexion(int idReceptor, int idSolicitante, boolean aceptar) {
        Usuario receptor = arbolUsuarios.buscar(idReceptor);
        if (receptor == null) {
            System.out.println("Usuario no encontrado.");
            return false;
        }
        IColaUsuarios aux = new ColaUsuarios();
        boolean encontrado = false;
        while (!receptor.getSolicitudesPendientes().estaVacia()) {
            int id = receptor.getSolicitudesPendientes().desencolar();
            if (id == idSolicitante) {
                encontrado = true;
            } else {
                aux.encolar(id);
            }
        }
        while (!aux.estaVacia()) {
            receptor.getSolicitudesPendientes().encolar(aux.desencolar());
        }
        if (!encontrado) {
            System.out.println("No existe esa solicitud pendiente.");
            return false;
        }
        if (aceptar) {
            grafoContactos.agregarArista(idReceptor, idSolicitante);
            System.out.println("Conexión aceptada. ¡Ahora son contactos!");
        } else {
            System.out.println("Solicitud rechazada.");
        }
        return true;
    }

    public boolean crearOferta(int idReclutador, String titulo, String descripcion, String habilidades) {
        Usuario u = arbolUsuarios.buscar(idReclutador);
        if (u == null || !u.getRol().equals("RECLUTADOR")) {
            System.out.println("Solo los reclutadores pueden crear ofertas.");
            return false;
        }
        int nuevoId = proximoIdOferta++;
        ofertas[cantidadOfertas++] = new Oferta(nuevoId, idReclutador, titulo, descripcion, habilidades);
        System.out.println("Oferta creada con ID: " + nuevoId);
        return true;
    }

    public boolean postularse(int idUsuario, int idOferta) {
        Usuario u = arbolUsuarios.buscar(idUsuario);
        if (u == null || !u.isActivo()) {
            System.out.println("Usuario no válido.");
            return false;
        }
        Oferta oferta = buscarOferta(idOferta);
        if (oferta == null || !oferta.getEstado().equals("ACTIVA")) {
            System.out.println("Oferta no encontrada o inactiva.");
            return false;
        }
        oferta.getColaPostulantes().encolar(idUsuario);
        System.out.println("Postulación registrada correctamente.");
        return true;
    }

    private Oferta buscarOferta(int idOferta) {
        for (int i = 0; i < cantidadOfertas; i++) {
            if (ofertas[i].getId() == idOferta) return ofertas[i];
        }
        return null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    private void cargarArbolHabilidadesDefault() {
        arbolHabilidades.insertar(0, new Habilidad(1, "Tecnología y Sistemas", "Raíz"));

        arbolHabilidades.insertar(1, new Habilidad(2, "Desarrollo de Software", "Rama"));
        arbolHabilidades.insertar(1, new Habilidad(3, "Datos e IA", "Rama"));

        arbolHabilidades.insertar(2, new Habilidad(4, "Java", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(5, "Python", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(6, "Desarrollo Web (HTML/CSS)", "Tecnología"));

        arbolHabilidades.insertar(3, new Habilidad(7, "SQL", "Tecnología"));
        arbolHabilidades.insertar(3, new Habilidad(8, "Machine Learning", "Tecnología"));
    }

    public void agregarHabilidadAlPerfilActual(String nombreHabilidad) {
        if (this.usuarioActual == null) {
            System.out.println("[❌] Inicia sesión primero.");
            return;
        }

        Habilidad habEncontrada = arbolHabilidades.buscarPorNombre(nombreHabilidad);

        if (habEncontrada == null) {
            System.out.println("[❌] No existe la habilidad '" + nombreHabilidad + "' en el catálogo.");
            return;
        }

        boolean exito = this.usuarioActual.agregarHabilidad(habEncontrada);
        if (exito) {
            System.out.println("[✅] ¡Habilidad '" + habEncontrada.getNombre() + "' agregada a tu perfil!");
        } else {
            System.out.println("[⚠️] Ya posees esta habilidad o llegaste al límite.");
        }
    }

    public ArbolHabilidades getArbolHabilidades() {
        return arbolHabilidades;
    }

    public int[] obtenerContactos(int idUsuario) {
        if (buscarUsuario(idUsuario) == null) return null;
        return grafoContactos.obtenerContactos(idUsuario);
    }

    public int calcularGradosDeSeparacion(int idOrigen, int idDestino) {
        if (buscarUsuario(idOrigen) == null || buscarUsuario(idDestino) == null) return -2;
        return grafoContactos.calcularGradosDeSeparacion(idOrigen, idDestino);
    }
}