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

    public void conectarConContacto(int idDestino) {
        if (this.usuarioActual == null) {
            System.out.println("[❌] Error: Debes iniciar sesión primero.");
            return;
        }

        Usuario destino = arbolUsuarios.buscar(idDestino);
        if (destino == null) {
            System.out.println("[❌] No se encontró ningún profesional con el ID " + idDestino + ".");
            return;
        }

        int miId = this.usuarioActual.getId();

        if (miId == idDestino) {
            System.out.println("[⚠️] No puedes agregarte a ti mismo como contacto.");
            return;
        }

        if (grafoContactos.existeArista(miId, idDestino)) {
            System.out.println("[⚠️] Ya estás conectado con " + destino.getNombre() + ".");
            return;
        }

        grafoContactos.agregarArista(miId, idDestino);
        System.out.println("[✅] ¡Conexión exitosa! Ahora estás conectado con " + destino.getNombre() + ".");
    }

    public void mostrarGradosDeSeparacionCon(int idDestino) {
        if (this.usuarioActual == null) return;

        Usuario destino = arbolUsuarios.buscar(idDestino);
        if (destino == null) {
            System.out.println("[❌] No se encontró al profesional con ID " + idDestino + ".");
            return;
        }

        int pasos = grafoContactos.calcularGradosDeSeparacion(this.usuarioActual.getId(), idDestino);

        if (pasos == -1) {
            System.out.println("[ℹ️] Distancia Infinita: No hay caminos que te conecten con " + destino.getNombre() + ".");
        } else if (pasos == 1) {
            System.out.println("[ℹ️] " + destino.getNombre() + " es tu contacto directo (1er grado).");
        } else {
            System.out.println("[ℹ️] Hay " + pasos + " grados de separación entre tú y " + destino.getNombre() + ".");
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

        System.out.println("\n--- 👥 PROFESIONALES RECOMENDADOS ---");
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
                    if (usuarioSugerido != null) {
                        System.out.println("-> [ID: " + usuarioSugerido.getId() + "] " + usuarioSugerido.getNombre() +
                                " | Profesión: " + usuarioSugerido.getProfesion() +
                                " | Amigos en común: " + mutuos.tamanio());
                        huboSugerencias = true;
                    }
                }
            }
        }

        if (!huboSugerencias) {
            System.out.println("Has conectado con toda tu red cercana. ¡Busca nuevas personas por ID!");
        }
        System.out.println("-------------------------------------------------------");
    }

    private void sugerirPlanBPorAfinidad() {
        int cantSugeridos = 0;
        String miProfesion = this.usuarioActual.getProfesion();

        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario otro = arbolUsuarios.buscar(todosLosIds[i]);

            if (otro != null && otro.getId() != this.usuarioActual.getId()) {
                if (otro.getProfesion().equalsIgnoreCase(miProfesion) && !otro.getProfesion().equals("-")) {
                    System.out.println("-> [ID: " + otro.getId() + "] " + otro.getNombre() + " | Colega en: " + miProfesion);
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


/*
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

 */

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

    public IArbolUsuariosAVL getArbolUsuarios() {
        return arbolUsuarios;
    }

}