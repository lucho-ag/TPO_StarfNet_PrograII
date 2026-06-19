package interfaz;

import entidades.*;
import estructurasTDA.*;

public class SistemaRedSocial {

    private IArbolUsuariosAVL arbolUsuarios;
    private IGrafoConexiones grafoContactos;
    private ArbolHabilidades arbolHabilidades;

    private int proximoIdUsuario;

    private Oferta[] ofertas;
    private int cantidadOfertas;
    private int proximoIdOferta;

    private Usuario usuarioActual;

    public SistemaRedSocial() {
        this.arbolUsuarios = new ArbolUsuariosAVL();
        this.grafoContactos = new GrafoConexiones();
        this.proximoIdUsuario = 1;
        this.ofertas = new Oferta[200];
        this.cantidadOfertas = 0;
        this.proximoIdOferta = 1;
        this.usuarioActual = null;
        this.arbolHabilidades = new ArbolHabilidades();
        cargarArbolHabilidadesDefault();
    }

    public boolean registrarUsuario(String nombreUsuario, String nombre, String email, String contrasenia, String rol) {
        Usuario[] todos = arbolUsuarios.obtenerTodos();
        for (Usuario u : todos) {
            if (u != null && (u.getEmail().equalsIgnoreCase(email) || u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))) {
                return false;
            }
        }
        int nuevoId = proximoIdUsuario++;
        Usuario nuevo = new Usuario(nuevoId, nombreUsuario, nombre, email, contrasenia, rol);
        arbolUsuarios.insertar(nuevoId, nuevo);
        grafoContactos.agregarVertice(nuevoId);
        return true;
    }

    public boolean iniciarSesion(String email, String contrasenia) {
        Usuario[] todos = arbolUsuarios.obtenerTodos();
        for (Usuario u : todos) {
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

    public Usuario buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        Usuario[] todos = arbolUsuarios.obtenerTodos();
        for (Usuario u : todos) {
            if (u != null && u.getNombreUsuario().equalsIgnoreCase(nombreUsuario) && u.isActivo()) {
                return u;
            }
        }
        return null;
    }

    public void editarPerfilActual(String nombre, String profesion, String ciudad, String resumen) {
        if (this.usuarioActual == null) return;

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
        if (this.usuarioActual == null) return false;

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

    public void enviarSolicitudConexion(String nombreUsuarioDestino) {
        if (this.usuarioActual == null) return;

        Usuario destino = buscarUsuarioPorNombreUsuario(nombreUsuarioDestino);
        if (destino == null) {
            System.out.println("[❌] No se encontró a nadie con el usuario '@" + nombreUsuarioDestino + "'.");
            return;
        }

        int miId = this.usuarioActual.getId();
        int idDestino = destino.getId();

        if (miId == idDestino) {
            System.out.println("[⚠️] No puedes enviarte una solicitud a ti mismo.");
            return;
        }

        if (grafoContactos.existeArista(miId, idDestino)) {
            System.out.println("[⚠️] Ya estás conectado con " + destino.getNombre() + ".");
            return;
        }

        destino.getSolicitudesPendientes().encolar(miId);
        System.out.println("[✅] ¡Solicitud enviada a @" + destino.getNombreUsuario() + "!");
    }

    public int[] obtenerSolicitudesPendientes() {
        if (this.usuarioActual == null) return new int[0];
        IColaUsuarios pendientes = this.usuarioActual.getSolicitudesPendientes();
        IColaUsuarios aux = new ColaUsuarios();
        int cant = 0;

        while (!pendientes.estaVacia()) {
            aux.encolar(pendientes.desencolar());
            cant++;
        }

        int[] array = new int[cant];
        int index = 0;

        while (!aux.estaVacia()) {
            int id = aux.desencolar();
            array[index++] = id;
            pendientes.encolar(id);
        }

        return array;
    }

    public void procesarConexion(int idSolicitante, boolean aceptar) {
        if (this.usuarioActual == null) return;

        IColaUsuarios pendientes = this.usuarioActual.getSolicitudesPendientes();
        IColaUsuarios aux = new ColaUsuarios();
        boolean encontrado = false;

        while (!pendientes.estaVacia()) {
            int id = pendientes.desencolar();
            if (id == idSolicitante) {
                encontrado = true;
            } else {
                aux.encolar(id);
            }
        }

        while (!aux.estaVacia()) {
            pendientes.encolar(aux.desencolar());
        }

        if (!encontrado) return;

        if (aceptar) {
            grafoContactos.agregarArista(this.usuarioActual.getId(), idSolicitante);
            Usuario solicitante = arbolUsuarios.buscar(idSolicitante);
            if (solicitante != null) {
                System.out.println("[✅] ¡Conexión aceptada! Ahora eres contacto de @" + solicitante.getNombreUsuario() + ".");
            }
        } else {
            System.out.println("[ℹ️] Solicitud rechazada.");
        }
    }

    public void mostrarContactosRecomendados() {
        if (this.usuarioActual == null) return;

        int miId = this.usuarioActual.getId();
        int[] misContactos = grafoContactos.obtenerContactos(miId);

        if (misContactos == null || misContactos.length == 0) {
            System.out.println("[ℹ️] Tu red está vacía. Aquí tienes algunos colegas de tu rubro para romper el hielo:");
            sugerirPlanBPorAfinidad();
            return;
        }

        System.out.println("\n--- \uD83D\uDC65 PROFESIONALES RECOMENDADOS ---");
        boolean huboSugerencias = false;

        ConjuntoUsuarios misAmigos = new ConjuntoUsuarios();
        for (int id : misContactos) {
            misAmigos.insertar(id);
        }

        ConjuntoUsuarios yaSugeridos = new ConjuntoUsuarios();

        for (int idAmigo : misContactos) {
            int[] contactosDelAmigo = grafoContactos.obtenerContactos(idAmigo);
            if (contactosDelAmigo == null) continue;

            for (int idSugerido : contactosDelAmigo) {
                if (idSugerido != miId && !misAmigos.pertenece(idSugerido) && !yaSugeridos.pertenece(idSugerido)) {
                    yaSugeridos.insertar(idSugerido);

                    ConjuntoUsuarios amigosDelSugerido = new ConjuntoUsuarios();
                    int[] arrayAmigosSugerido = grafoContactos.obtenerContactos(idSugerido);
                    for (int id : arrayAmigosSugerido) {
                        amigosDelSugerido.insertar(id);
                    }

                    ConjuntoUsuarios mutuos = misAmigos.interseccion(amigosDelSugerido);
                    Usuario usuarioSugerido = arbolUsuarios.buscar(idSugerido);

                    if (usuarioSugerido != null && usuarioSugerido.isActivo()) {
                        System.out.println("-> @" + usuarioSugerido.getNombreUsuario() + " (" + usuarioSugerido.getNombre() + ")" +
                                " | Profesión: " + usuarioSugerido.getProfesion() +
                                " | Amigos en común: " + mutuos.tamanio());
                        huboSugerencias = true;
                    }
                }
            }
        }

        if (!huboSugerencias) {
            System.out.println("Has conectado con toda tu red cercana. ¡Busca nuevas personas por nombre de usuario!");
        }
        System.out.println("-------------------------------------------------------");
    }

    private void sugerirPlanBPorAfinidad() {
        int cantSugeridos = 0;
        String miProfesion = this.usuarioActual.getProfesion();
        Usuario[] todos = arbolUsuarios.obtenerTodos();

        for (Usuario otro : todos) {
            if (otro != null && otro.isActivo() && otro.getId() != this.usuarioActual.getId()) {
                if (otro.getProfesion().equalsIgnoreCase(miProfesion) && !otro.getProfesion().equals("-")) {
                    System.out.println("-> @" + otro.getNombreUsuario() + " (" + otro.getNombre() + ") | Colega en: " + miProfesion);
                    cantSugeridos++;
                }
            }

            if (cantSugeridos == 5) break;
        }

        if (cantSugeridos == 0) {
            System.out.println("No encontramos colegas de tu rubro exacto, ¡explora la red conociendo gente nueva!");
        }
    }

    public int[] obtenerContactos(int idUsuario) {
        Usuario u = arbolUsuarios.buscar(idUsuario);
        if (u == null) {
            return new int[0];
        }
        return grafoContactos.obtenerContactos(idUsuario);
    }

    public boolean crearOferta(int idReclutador, String titulo, String descripcion, String habilidades) {
        Usuario u = arbolUsuarios.buscar(idReclutador);
        if (u == null || !u.getRol().equals("RECLUTADOR")) {
            System.out.println("Solo los reclutadores pueden crear ofertas.");
            return false;
        }
        int nuevoId = proximoIdOferta++;
        ofertas[cantidadOfertas++] = new Oferta(nuevoId, idReclutador, titulo, descripcion, habilidades);
        System.out.println("Oferta creada con éxito.");
        return true;
    }

    public Oferta[] obtenerOfertasDeContactos() {
        if (this.usuarioActual == null) return new Oferta[0];

        int[] misContactos = grafoContactos.obtenerContactos(this.usuarioActual.getId());
        if (misContactos == null || misContactos.length == 0) return new Oferta[0];

        int count = 0;
        for (int i = 0; i < cantidadOfertas; i++) {
            if (ofertas[i].getEstado().equals("ACTIVA")) {
                for (int idContacto : misContactos) {
                    if (ofertas[i].getIdReclutador() == idContacto) {
                        count++;
                        break;
                    }
                }
            }
        }

        Oferta[] resultado = new Oferta[count];
        int index = 0;
        for (int i = 0; i < cantidadOfertas; i++) {
            if (ofertas[i].getEstado().equals("ACTIVA")) {
                for (int idContacto : misContactos) {
                    if (ofertas[i].getIdReclutador() == idContacto) {
                        resultado[index++] = ofertas[i];
                        break;
                    }
                }
            }
        }
        return resultado;
    }

    public boolean postularse(int idUsuario, int idOferta) {
        Usuario u = arbolUsuarios.buscar(idUsuario);
        if (u == null || !u.isActivo()) return false;

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

    public Oferta[] obtenerTodasLasOfertas() {
        return this.ofertas;
    }

    public int getCantidadOfertas() {
        return this.cantidadOfertas;
    }

    public Oferta obtenerOfertaPropia(int idOferta) {
        if (this.usuarioActual == null || !this.usuarioActual.getRol().equals("RECLUTADOR")) {
            return null;
        }
        Oferta o = buscarOferta(idOferta);
        if (o != null && o.getIdReclutador() == this.usuarioActual.getId()) {
            return o;
        }
        return null;
    }

    public void cerrarOferta(int idOferta) {
        Oferta o = obtenerOfertaPropia(idOferta);
        if (o != null) {
            o.cerrar();
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    private void cargarArbolHabilidadesDefault() {
        arbolHabilidades.insertar(0, new Habilidad(1, "Competencias Laborales", "Área Principal"));

        arbolHabilidades.insertar(1, new Habilidad(2, "Desarrollo de Software", "Subárea"));
        arbolHabilidades.insertar(1, new Habilidad(3, "Datos e Inteligencia Artificial", "Subárea"));
        arbolHabilidades.insertar(1, new Habilidad(10, "Diseño y Producto Digital", "Subárea"));
        arbolHabilidades.insertar(1, new Habilidad(11, "Gestión y Negocios", "Subárea"));
        arbolHabilidades.insertar(1, new Habilidad(12, "Ciberseguridad e Infraestructura", "Subárea"));

        arbolHabilidades.insertar(2, new Habilidad(4, "Java", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(5, "Python", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(6, "TypeScript", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(13, "C#", "Tecnología"));
        arbolHabilidades.insertar(2, new Habilidad(14, "React", "Framework"));
        arbolHabilidades.insertar(2, new Habilidad(15, "Node.js", "Entorno"));

        arbolHabilidades.insertar(3, new Habilidad(7, "SQL", "Tecnología"));
        arbolHabilidades.insertar(3, new Habilidad(8, "Machine Learning", "Especialidad"));
        arbolHabilidades.insertar(3, new Habilidad(9, "Power BI", "Herramienta"));
        arbolHabilidades.insertar(3, new Habilidad(16, "Big Data (Hadoop/Spark)", "Especialidad"));
        arbolHabilidades.insertar(3, new Habilidad(17, "NoSQL (MongoDB)", "Tecnología"));

        arbolHabilidades.insertar(10, new Habilidad(18, "Figma", "Herramienta"));
        arbolHabilidades.insertar(10, new Habilidad(19, "Diseño UX/UI", "Metodología"));
        arbolHabilidades.insertar(10, new Habilidad(20, "Prototipado", "Aptitud"));

        arbolHabilidades.insertar(11, new Habilidad(21, "Metodologías Ágiles (Scrum)", "Metodología"));
        arbolHabilidades.insertar(11, new Habilidad(22, "Product Management", "Rol"));
        arbolHabilidades.insertar(11, new Habilidad(23, "Liderazgo de Equipos", "Aptitud"));

        arbolHabilidades.insertar(12, new Habilidad(24, "Docker y Kubernetes", "Herramienta"));
        arbolHabilidades.insertar(12, new Habilidad(25, "Linux", "Sistema Operativo"));
        arbolHabilidades.insertar(12, new Habilidad(26, "Ethical Hacking", "Especialidad"));
        arbolHabilidades.insertar(12, new Habilidad(27, "Cloud Computing (AWS)", "Plataforma"));
    }

    public void agregarHabilidadAlPerfilActual(String nombreHabilidad) {
        if (this.usuarioActual == null) return;

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

    public IArbolUsuariosAVL getArbolUsuarios() {
        return arbolUsuarios;
    }

    public ArbolHabilidades getArbolHabilidades() {
        return arbolHabilidades;
    }

    public Usuario[] buscarProfesionalesPorHabilidad(String nombreHabilidad) {
        Habilidad habBuscada = arbolHabilidades.buscarPorNombre(nombreHabilidad);
        if (habBuscada == null) {
            System.out.println("[❌] No existe la habilidad '" + nombreHabilidad + "' en el catálogo.");
            return new Usuario[0];
        }

        Usuario[] todos = arbolUsuarios.obtenerTodos();
        int count = 0;
        for (Usuario u : todos) {
            if (u != null && u.isActivo() && u.getRol().equalsIgnoreCase("PROFESIONAL")) {
                boolean tieneHabilidad = false;
                Habilidad[] habs = u.getMisHabilidades();
                int totalHabs = u.getCantHabilidades();
                for (int i = 0; i < totalHabs; i++) {
                    if (habs[i] != null && arbolHabilidades.esDescendienteOIgual(nombreHabilidad, habs[i].getNombre())) {
                        tieneHabilidad = true;
                        break;
                    }
                }
                if (tieneHabilidad) {
                    count++;
                }
            }
        }

        Usuario[] resultado = new Usuario[count];
        int index = 0;
        for (Usuario u : todos) {
            if (u != null && u.isActivo() && u.getRol().equalsIgnoreCase("PROFESIONAL")) {
                boolean tieneHabilidad = false;
                Habilidad[] habs = u.getMisHabilidades();
                int totalHabs = u.getCantHabilidades();
                for (int i = 0; i < totalHabs; i++) {
                    if (habs[i] != null && arbolHabilidades.esDescendienteOIgual(nombreHabilidad, habs[i].getNombre())) {
                        tieneHabilidad = true;
                        break;
                    }
                }
                if (tieneHabilidad) {
                    resultado[index++] = u;
                }
            }
        }

        return resultado;
    }
}