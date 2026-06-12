package interfaz;

import entidades.Oferta;
import entidades.PerfilEstado;
import entidades.Usuario;
import estructurasTDA.*;

public class SistemaRedSocial {

    private IArbolUsuariosAVL arbolUsuarios;
    private IGrafoConexiones grafoContactos;

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
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        registrarUsuario("Ana Gomez", "ana@mail.com", "1234", "PROFESIONAL");
        registrarUsuario("Luis Perez", "luis@mail.com", "1234", "PROFESIONAL");
        registrarUsuario("TechCorp SA", "tech@corp.com", "1234", "RECLUTADOR");
        crearOferta(3, "Dev Java Senior", "Desarrollo de microservicios", "Java, Spring");
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

    public Usuario iniciarSesion(String email, String contrasenia) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario u = arbolUsuarios.buscar(todosLosIds[i]);
            if (u != null && u.getEmail().equalsIgnoreCase(email) && u.validarContrasenia(contrasenia) && u.isActivo()) {
                usuarioActual = u;
                System.out.println("Bienvenido/a, " + u.getNombre() + "!");
                return u;
            }
        }
        System.out.println("Credenciales incorrectas o cuenta inactiva.");
        return null;
    }

    public Usuario buscarUsuario(int id) {
        Usuario u = arbolUsuarios.buscar(id);
        if (u == null || !u.isActivo()) {
            System.out.println("Perfil no encontrado.");
            return null;
        }
        return u;
    }

    public boolean editarPerfil(int idUsuario, String campo, String nuevoValor) {
        Usuario u = arbolUsuarios.buscar(idUsuario);
        if (u == null || !u.isActivo()) {
            System.out.println("Usuario no encontrado.");
            return false;
        }
        PerfilEstado estadoActual = new PerfilEstado(u.getNombre(), u.getProfesion(), u.getCiudad(), u.getResumen());
        u.getHistorial().apilar(estadoActual);

        switch (campo.toLowerCase()) {
            case "nombre": u.setNombre(nuevoValor); break;
            case "profesion": u.setProfesion(nuevoValor); break;
            case "ciudad": u.setCiudad(nuevoValor); break;
            case "resumen": u.setResumen(nuevoValor); break;
            default:
                System.out.println("Campo no válido. Opciones: nombre, profesion, ciudad, resumen");
                return false;
        }
        System.out.println("Perfil actualizado correctamente.");
        return true;
    }

    public boolean deshacerCambioPerfil(int idUsuario) {
        Usuario u = arbolUsuarios.buscar(idUsuario);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return false;
        }
        if (u.getHistorial().estaVacia()) {
            System.out.println("No hay cambios para deshacer.");
            return false;
        }
        PerfilEstado anterior = u.getHistorial().desapilar();
        u.setNombre(anterior.getNombre());
        u.setProfesion(anterior.getProfesion());
        u.setCiudad(anterior.getCiudad());
        u.setResumen(anterior.getResumen());
        System.out.println("Último cambio revertido exitosamente.");
        return true;
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

    public Usuario getUsuarioActual() { return usuarioActual; }

    public boolean agregarHabilidadUsuario(int idUsuario, String nombreHab, String categoriaHab) {
        Usuario u = buscarUsuario(idUsuario);
        if (u == null) return false;
        return true;
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