package interfaz;

import entidades.*;
import estructurasTDA.ArbolHabilidades;

public class Consola {

    private final SistemaRedSocial sistema;
    private final java.util.Scanner scanner;

    public Consola() {
        this.sistema = new SistemaRedSocial();
        this.scanner = new java.util.Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("=================================================");
            System.out.println("│          SISTEMA DE RED SOCIAL                │");
            System.out.println("=================================================");
            System.out.println("│  1. Iniciar sesión                            │");
            System.out.println("│  2. Crear cuenta nueva                        │");
            System.out.println("│  3. Iniciar prueba automática                 │");
            System.out.println("│  0. Salir del Programa                        │");
            System.out.println("=================================================");
            opcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    if (ejecutarIniciarSesion()) {
                        enrutarMenuPorRol();
                    }
                    break;
                case 2:
                    ejecutarCrearCuenta();
                    break;
                case 3:
                    ejecutarPruebaAuto();
                    break;
                case 0:
                    imprimirEncabezado("SALIENDO DEL SISTEMA");
                    System.out.println("¡Gracias por utilizar la aplicación!");
                    System.out.println("=================================================");
                    break;
                default:
                    System.out.println("[⚠️] Opción inválida. Intente nuevamente.");
                    esperarEnter();
            }
        } while (opcion != 0);
    }

    private void ejecutarPruebaAuto() {
        TesterAutomatizado tester = new TesterAutomatizado(this.sistema);
        tester.ejecutarPruebaAuto();
    }

    private void enrutarMenuPorRol() {
        Usuario actual = sistema.getUsuarioActual();
        if (actual == null) return;

        if (actual.getRol().equalsIgnoreCase("RECLUTADOR")) {
            menuReclutador();
        } else {
            menuProfesional();
        }
    }

    private void menuProfesional() {
        int opcion;
        do {
            imprimirEncabezado("MENÚ PROFESIONAL: @" + sistema.getUsuarioActual().getNombreUsuario());
            System.out.println("1. Buscar Perfil de Usuario");
            System.out.println("2. Ver mi Perfil");
            System.out.println("3. Editar mi Perfil");
            System.out.println("4. Agregar Habilidad a mi Perfil");
            System.out.println("5. Deshacer último cambio (Historial)");
            System.out.println("6. Ver mis Contactos");
            System.out.println("7. Ver mis Contactos Recomendados");
            System.out.println("8. Enviar Solicitud de Conexión");
            System.out.println("9. Procesar Solicitudes Pendientes");
            System.out.println("10. Postularse a una Oferta de Trabajo");
            System.out.println("11. Cerrar Sesión");
            System.out.println("12. Eliminar Cuenta");
            System.out.println("13. Buscar Profesionales por Habilidad");
            System.out.println("-------------------------------------------------");
            opcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch(opcion) {
                case 1: ejecutarBuscarUsuario(); break;
                case 2: ejecutarVerMiPerfil(); break;
                case 3: ejecutarEditarPerfil(); break;
                case 4: ejecutarAgregarHabilidad(); break;
                case 5: ejecutarDeshacerCambio(); break;
                case 6: ejecutarVerMisContactos(); break;
                case 7: ejecutarContactosRecomendados(); break;
                case 8: ejecutarEnviarSolicitud(); break;
                case 9: ejecutarProcesarSolicitud(); break;
                case 10: ejecutarPostularse(); break;
                case 11:
                    if (pedirConfirmacionSi("¿Seguro que desea cerrar sesión?")) {
                        ejecutarCerrarSesion();
                        return;
                    }
                    break;
                case 12:
                    if (pedirConfirmacionSi("¿Seguro que desea ELIMINAR su cuenta de forma permanente?")) {
                        ejecutarEliminarCuenta();
                        return;
                    }
                    break;
                case 13: ejecutarBuscarProfesionalesPorHabilidad(); break;
                default:
                    System.out.println("[⚠️] Opción no válida.");
                    esperarEnter();
            }
        } while(sistema.getUsuarioActual() != null);
    }

    private void menuReclutador() {
        int opcion;
        do {
            imprimirEncabezado("MENÚ RECLUTADOR: @" + sistema.getUsuarioActual().getNombreUsuario());
            System.out.println("1. Buscar Perfil de Usuario");
            System.out.println("2. Ver mi Perfil");
            System.out.println("3. Editar mi Perfil");
            System.out.println("4. Deshacer último cambio (Historial)");
            System.out.println("5. Ver mis Contactos");
            System.out.println("6. Enviar Solicitud de Conexión");
            System.out.println("7. Procesar Solicitudes Pendientes");
            System.out.println("8. Crear Oferta de Trabajo");
            System.out.println("9. Evaluar Postulantes Oferta");
            System.out.println("10. Cerrar Sesión");
            System.out.println("11. Eliminar Cuenta");
            System.out.println("12. Buscar Profesionales por Habilidad");
            System.out.println("-------------------------------------------------");
            opcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch(opcion) {
                case 1: ejecutarBuscarUsuario(); break;
                case 2: ejecutarVerMiPerfil(); break;
                case 3: ejecutarEditarPerfil(); break;
                case 4: ejecutarDeshacerCambio(); break;
                case 5: ejecutarVerMisContactos(); break;
                case 6: ejecutarEnviarSolicitud(); break;
                case 7: ejecutarProcesarSolicitud(); break;
                case 8: ejecutarCrearOferta(); break;
                case 9: ejecutarEvaluarCandidatos(); break;
                case 10:
                    if (pedirConfirmacionSi("¿Seguro que desea cerrar sesión?")) {
                        ejecutarCerrarSesion();
                        return;
                    }
                    break;
                case 11:
                    if (pedirConfirmacionSi("¿Seguro que desea ELIMINAR su cuenta de forma permanente?")) {
                        ejecutarEliminarCuenta();
                        return;
                    }
                    break;
                case 12: ejecutarBuscarProfesionalesPorHabilidad(); break;
                default:
                    System.out.println("[⚠️] Opción no válida.");
                    esperarEnter();
            }
        } while(sistema.getUsuarioActual() != null);
    }

    private void ejecutarCrearCuenta() {
        imprimirEncabezado("CREAR CUENTA NUEVA");
        String nombreUsuario;
        while (true) {
            nombreUsuario = pedirNombreUsuario();
            if (sistema.buscarUsuarioPorNombreUsuario(nombreUsuario) != null) {
                System.out.println("[❌] Error: El usuario '" + nombreUsuario + "' ya está registrado. Por favor elija otro.");
            } else {
                break;
            }
        }
        String nombre = pedirTextoObligatorio("Nombre personal completo: ");
        String email = pedirEmail();
        String contrasenia = pedirTextoObligatorio("Contraseña: ");

        int tipo = 0;
        do {
            System.out.println("\nSeleccione su tipo de cuenta:");
            System.out.println("1. PROFESIONAL");
            System.out.println("2. RECLUTADOR");
            tipo = pedirEntero("Opción (1 o 2): ");

            if (tipo != 1 && tipo != 2) {
                System.out.println("[⚠️] Error: Por favor, ingrese exactamente 1 o 2.");
            }
        } while (tipo != 1 && tipo != 2);

        String rol = (tipo == 2) ? "RECLUTADOR" : "PROFESIONAL";

        if (sistema.registrarUsuario(nombreUsuario, nombre, email, contrasenia, rol)) {
            System.out.println("[✅] ¡Registro exitoso! Ya puedes iniciar sesión.");
            esperarEnter();
        } else {
            System.out.println("[❌] Falló el registro. Intenta nuevamente.");
            esperarEnter();
        }
    }

    private boolean ejecutarIniciarSesion() {
        imprimirEncabezado("INICIAR SESIÓN");
        String email = pedirEmail();
        String contrasenia = pedirTextoObligatorio("Contraseña: ");

        if (sistema.iniciarSesion(email, contrasenia)) {
            if (!sistema.getUsuarioActual().isActivo()) {
                System.out.println("\n[❌] Esta cuenta ha sido eliminada y no puede utilizarse.");
                sistema.cerrarSesion();
                esperarEnter();
                return false;
            }
            System.out.println("\n[✅] ¡Sesión iniciada con éxito!");
            esperarEnter();
            return true;
        }
        esperarEnter();
        return false;
    }

    private void ejecutarCerrarSesion() {
        sistema.cerrarSesion();
        System.out.println("[✅] Sesión cerrada correctamente.");
        esperarEnter();
    }

    private void ejecutarEliminarCuenta() {
        sistema.getUsuarioActual().setActivo(false);
        sistema.cerrarSesion();
        System.out.println("[✅] Tu cuenta ha sido eliminada por completo.");
        esperarEnter();
    }

    private void ejecutarBuscarUsuario() {
        imprimirEncabezado("BUSCAR PERFIL DE USUARIO");
        String nombreUsuario = pedirNombreUsuario("Ingrese el nombre de usuario exacto a buscar (sin @): ");
        Usuario usuario = sistema.buscarUsuarioPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            System.out.println("\n>> PERFIL ENCONTRADO <<");
            System.out.println(usuario.toString());
        } else {
            System.out.println("\n[❌] No se encontró ningún usuario activo con el nombre de usuario @" + nombreUsuario + ".");
        }
        esperarEnter();
    }

    private void ejecutarBuscarProfesionalesPorHabilidad() {
        imprimirEncabezado("BUSCAR PROFESIONALES POR HABILIDAD");
        String habilidadBuscada = pedirTextoObligatorio("Ingrese la habilidad a buscar (se incluirán especialidades y subcategorías): ");

        Usuario[] resultados = sistema.buscarProfesionalesPorHabilidad(habilidadBuscada);

        if (resultados.length == 0) {
            System.out.println("\n[ℹ️] No se encontraron profesionales activos con la habilidad '" + habilidadBuscada + "' o sus derivadas.");
        } else {
            System.out.println("\n>> PROFESIONALES ENCONTRADOS (" + resultados.length + ") <<");
            System.out.println("-------------------------------------------------");
            for (Usuario u : resultados) {
                System.out.println(" • @" + u.getNombreUsuario() + " - " + u.getNombre());
                System.out.println("   Profesión: " + u.getProfesion());
                System.out.println("   Habilidades: " + u.obtenerHabilidadesString());
                System.out.println("-------------------------------------------------");
            }
        }
        esperarEnter();
    }

    private void ejecutarVerMiPerfil() {
        System.out.println(sistema.getUsuarioActual().toString());
        esperarEnter();
    }

    private void ejecutarEditarPerfil() {
        Usuario u = sistema.getUsuarioActual();

        String tempNombre = u.getNombre();
        String tempProfesion = u.getProfesion().equals("-") ? "" : u.getProfesion();
        String tempCiudad = u.getCiudad().equals("-") ? "" : u.getCiudad();
        String tempResumen = u.getResumen().equals("-") ? "" : u.getResumen();

        int opcion;
        do {
            imprimirEncabezado("EDITAR PERFIL");
            System.out.println("Seleccione el campo que desea modificar:");
            System.out.println("1. Nombre completo : " + tempNombre);
            System.out.println("2. Profesión       : " + (tempProfesion.isEmpty() ? "[Vacío]" : tempProfesion));
            System.out.println("3. Ciudad          : " + (tempCiudad.isEmpty() ? "[Vacío]" : tempCiudad));
            System.out.println("4. Resumen         : " + (tempResumen.isEmpty() ? "[Vacío]" : tempResumen));
            System.out.println("5. [💾] GUARDAR CAMBIOS Y SALIR");
            System.out.println("0. [❌] Cancelar y descartar cambios");
            System.out.println("-------------------------------------------------");

            opcion = pedirEntero("Opción: ");

            switch (opcion) {
                case 1:
                    tempNombre = pedirTextoObligatorio("Nuevo nombre completo: ");
                    break;
                case 2:
                    System.out.print("Nueva profesión (Enter para dejar vacío): ");
                    tempProfesion = scanner.nextLine().trim();
                    break;
                case 3:
                    System.out.print("Nueva ciudad (Enter para dejar vacío): ");
                    tempCiudad = scanner.nextLine().trim();
                    break;
                case 4:
                    System.out.print("Nuevo resumen (Enter para dejar vacío): ");
                    tempResumen = scanner.nextLine().trim();
                    break;
                case 5:
                    sistema.editarPerfilActual(tempNombre, tempProfesion, tempCiudad, tempResumen);
                    System.out.println("\n[✅] Perfil actualizado. El estado anterior fue resguardado en el Historial.");
                    esperarEnter();
                    return;
                case 0:
                    System.out.println("\n[ℹ️] Edición cancelada. Se descartó el borrador.");
                    esperarEnter();
                    return;
                default:
                    System.out.println("[⚠️] Opción inválida.");
            }
        } while (true);
    }

    private void ejecutarDeshacerCambio() {
        sistema.deshacerUltimoCambio();
        esperarEnter();
    }

    private void ejecutarAgregarHabilidad() {
        ArbolHabilidades arbol = sistema.getArbolHabilidades();
        String categoriaActual = "Competencias Laborales";

        while (true) {
            imprimirEncabezado("AGREGAR HABILIDAD - NAVEGACIÓN JERÁRQUICA");
            System.out.println("Categoría actual: " + categoriaActual);
            System.out.println("-------------------------------------------------");

            Habilidad[] hijos = arbol.obtenerHijosDirectos(categoriaActual);

            int indice = 1;
            for (Habilidad hijo : hijos) {
                System.out.println(indice + ". Ir a -> " + hijo.getNombre() + " [" + hijo.getCategoria() + "]");
                indice++;
            }

            boolean esRaiz = categoriaActual.equalsIgnoreCase("Competencias Laborales");
            if (!esRaiz) {
                System.out.println(indice + ". [SELECCIONAR] Cargar '" + categoriaActual + "' a mi perfil");
            }
            System.out.println("0. Cancelar / Volver");
            System.out.println("-------------------------------------------------");

            int opcion = pedirEntero("Seleccione una opción: ");

            if (opcion == 0) {
                if (esRaiz) {
                    System.out.println("[ℹ️] Operación cancelada.");
                    esperarEnter();
                    return;
                } else {
                    categoriaActual = "Competencias Laborales";
                    continue;
                }
            }

            if (opcion > 0 && opcion < indice) {
                categoriaActual = hijos[opcion - 1].getNombre();
            } else if (opcion == indice && !esRaiz) {
                sistema.agregarHabilidadAlPerfilActual(categoriaActual);
                esperarEnter();
                return;
            } else {
                System.out.println("[❌] Opción inválida.");
                esperarEnter();
            }
        }
    }

    private void ejecutarEnviarSolicitud() {
        imprimirEncabezado("ENVIAR SOLICITUD DE CONEXIÓN");
        String nombreUsuario = pedirNombreUsuario("Ingrese el nombre de usuario exacto a conectar (sin @): ");
        sistema.enviarSolicitudConexion(nombreUsuario);
        esperarEnter();
    }

    private void ejecutarProcesarSolicitud() {
        imprimirEncabezado("PROCESAR SOLICITUDES");
        int[] pendientes = sistema.obtenerSolicitudesPendientes();

        if (pendientes.length == 0) {
            System.out.println("[ℹ️] No tienes solicitudes de conexión pendientes.");
            esperarEnter();
            return;
        }

        for (int idSolicitante : pendientes) {
            Usuario solicitante = sistema.getArbolUsuarios().buscar(idSolicitante);
            if (solicitante != null) {
                System.out.println("-------------------------------------------------");
                System.out.println("Solicitud de: @" + solicitante.getNombreUsuario() + " (" + solicitante.getNombre() + ")");
                System.out.print("¿Aceptar solicitud? (S/N): ");
                String respuesta = scanner.nextLine();
                boolean aceptar = respuesta.equalsIgnoreCase("S");
                sistema.procesarConexion(idSolicitante, aceptar);
            }
        }
        esperarEnter();
    }

    private void ejecutarVerMisContactos() {
        imprimirEncabezado("MIS CONTACTOS");
        int idUsuario = sistema.getUsuarioActual().getId();
        int[] contactos = sistema.obtenerContactos(idUsuario);

        if (contactos != null && contactos.length > 0) {
            System.out.println("\nMis contactos");
            System.out.println("-------------------------------------------------");
            for (int id : contactos) {
                Usuario amigo = sistema.getArbolUsuarios().buscar(id);
                if (amigo != null && amigo.isActivo()) {
                    System.out.println(" • @" + amigo.getNombreUsuario() + " - " + amigo.getNombre() + " (" + amigo.getRol() + ")");
                }
            }
            System.out.println("-------------------------------------------------");
        } else {
            System.out.println("[ℹ️] Aún no tienes contactos.");
        }
        esperarEnter();
    }

    private void ejecutarContactosRecomendados() {
        sistema.mostrarContactosRecomendados();
        esperarEnter();
    }

    private int obtenerCantidadHabilidadesRequeridas(Oferta o) {
        if (o.getHabilidadesRequeridas() == null || o.getHabilidadesRequeridas().trim().isEmpty()) {
            return 0;
        }
        return o.getHabilidadesRequeridas().split(",").length;
    }

    private void ejecutarCrearOferta() {
        imprimirEncabezado("CREAR OFERTA DE TRABAJO");
        String titulo = pedirTextoObligatorio("Título de la oferta: ");
        String descripcion = pedirTextoObligatorio("Descripción: ");
        
        String habilidadesValidadas = "";
        while (true) {
            System.out.println("Ingrese las habilidades requeridas separadas por coma (ej: Java, SQL, Python):");
            String habilidadesInput = pedirTextoObligatorio("Habilidades: ");
            
            String[] partes = habilidadesInput.split(",");
            int validasCount = 0;
            int invalidasCount = 0;
            String invalidasStr = "";
            String validasStr = "";
            
            for (String p : partes) {
                String clean = p.trim();
                if (clean.isEmpty()) continue;
                if (sistema.getArbolHabilidades().buscarPorNombre(clean) != null) {
                    validasCount++;
                    if (!validasStr.isEmpty()) validasStr += ", ";
                    validasStr += clean;
                } else {
                    invalidasCount++;
                    if (!invalidasStr.isEmpty()) invalidasStr += ", ";
                    invalidasStr += clean;
                }
            }
            
            if (invalidasCount > 0) {
                System.out.println("[⚠️] Las siguientes habilidades no están registradas en el catálogo: " + invalidasStr);
                if (validasCount > 0) {
                    System.out.println("Habilidades reconocidas: " + validasStr);
                    System.out.print("¿Desea crear la oferta sólo con las habilidades reconocidas? (S/N) o 'C' para cancelar: ");
                    String resp = scanner.nextLine().trim();
                    if (resp.equalsIgnoreCase("S")) {
                        habilidadesValidadas = validasStr;
                        break;
                    } else if (resp.equalsIgnoreCase("C")) {
                        System.out.println("[ℹ️] Creación de oferta cancelada.");
                        esperarEnter();
                        return;
                    }
                } else {
                    System.out.println("[❌] Ninguna de las habilidades ingresadas es válida.");
                    System.out.print("¿Desea intentar ingresarlas de nuevo? (S/N): ");
                    String resp = scanner.nextLine().trim();
                    if (!resp.equalsIgnoreCase("S")) {
                        System.out.println("[ℹ️] Creación de oferta cancelada.");
                        esperarEnter();
                        return;
                    }
                }
            } else {
                habilidadesValidadas = validasStr;
                break;
            }
        }
        
        boolean exito = sistema.crearOferta(sistema.getUsuarioActual().getId(), titulo, descripcion, habilidadesValidadas);
        if (exito) {
            System.out.println("[✅] Oferta de trabajo creada exitosamente con las habilidades: [" + habilidadesValidadas + "]");
        }
        esperarEnter();
    }

    private void ejecutarPostularse() {
        imprimirEncabezado("POSTULARSE A OFERTA");
        Oferta[] ofertasDisponibles = sistema.obtenerOfertasGlobalesActivas();

        if (ofertasDisponibles.length == 0) {
            System.out.println("[ℹ️] No hay ofertas laborales activas en la bolsa de trabajo.");
            esperarEnter();
            return;
        }

        sistema.ordenarOfertasPorCoincidencia(sistema.getUsuarioActual(), ofertasDisponibles);

        System.out.println("Ofertas de trabajo activas en el sistema (ordenadas por coincidencia con tu perfil):");
        for (int i = 0; i < ofertasDisponibles.length; i++) {
            Usuario reclutador = sistema.getArbolUsuarios().buscar(ofertasDisponibles[i].getIdReclutador());
            String nombreRec = reclutador != null ? reclutador.getNombreUsuario() : "Desconocido";
            
            int coincidencia = sistema.calcularCoincidenciaHabilidades(sistema.getUsuarioActual(), ofertasDisponibles[i]);
            int totalRequeridas = obtenerCantidadHabilidadesRequeridas(ofertasDisponibles[i]);
            
            System.out.println((i + 1) + ". " + ofertasDisponibles[i].getTitulo() + " (@" + nombreRec + ")" +
                    " | Coincidencia: " + coincidencia + "/" + totalRequeridas + " (" + 
                    (totalRequeridas > 0 ? (coincidencia * 100 / totalRequeridas) : 0) + "%)");
        }
        System.out.println("0. Cancelar");
        System.out.println("-------------------------------------------------");

        int opcion = pedirEntero("Seleccione el número de la oferta para ver detalles: ");
        if (opcion == 0 || opcion > ofertasDisponibles.length) {
            return;
        }

        Oferta seleccionada = ofertasDisponibles[opcion - 1];
        System.out.println("\n>> DETALLES DE LA OFERTA <<");
        Usuario reclutadorInfo = sistema.getArbolUsuarios().buscar(seleccionada.getIdReclutador());
        String infoRec = reclutadorInfo != null ? reclutadorInfo.getNombreUsuario() : "Desconocido";
        System.out.println("Publicado por: @" + infoRec);
        System.out.println("Título: " + seleccionada.getTitulo());
        System.out.println("Descripción: " + seleccionada.getDescripcion());
        System.out.println("Habilidades requeridas: " + seleccionada.getHabilidadesRequeridas());
        
        int coincidencia = sistema.calcularCoincidenciaHabilidades(sistema.getUsuarioActual(), seleccionada);
        int totalRequeridas = obtenerCantidadHabilidadesRequeridas(seleccionada);
        System.out.println("Tu Coincidencia: " + coincidencia + "/" + totalRequeridas + " (" + 
                (totalRequeridas > 0 ? (coincidencia * 100 / totalRequeridas) : 0) + "%)");
        System.out.println("-------------------------------------------------");

        System.out.print("¿Desea postularse a esta oferta? (S/N): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("S")) {
            sistema.postularse(sistema.getUsuarioActual().getId(), seleccionada.getId());
        }
        esperarEnter();
    }

    private void ejecutarEvaluarCandidatos() {
        imprimirEncabezado("EVALUAR POSTULANTES");

        Oferta[] listaOfertas = sistema.obtenerTodasLasOfertas();
        int totalOfertas = sistema.getCantidadOfertas();
        int idReclutadorLogueado = sistema.getUsuarioActual().getId();

        System.out.println("📋 TUS OFERTAS DE EMPLEO ACTIVAS:");
        System.out.println("-------------------------------------------------");
        boolean tieneOfertasActivas = false;

        for (int i = 0; i < totalOfertas; i++) {
            Oferta o = listaOfertas[i];
            if (o != null && o.getIdReclutador() == idReclutadorLogueado && o.getEstado().equalsIgnoreCase("ACTIVA")) {
                System.out.println("▶️ [ID: " + o.getId() + "] " + o.getTitulo());
                tieneOfertasActivas = true;
            }
        }

        if (!tieneOfertasActivas) {
            System.out.println("[ℹ️] No posees ninguna oferta de empleo activa en este momento.");
            System.out.println("-------------------------------------------------");
            esperarEnter();
            return;
        }
        System.out.println("-------------------------------------------------");

        int idOferta = pedirEntero("Ingrese el [ID] de la oferta que desea evaluar: ");
        Oferta ofertaSeleccionada = sistema.obtenerOfertaPropia(idOferta);

        if (ofertaSeleccionada == null || !ofertaSeleccionada.getEstado().equalsIgnoreCase("ACTIVA")) {
            System.out.println("[❌] Error: ID inválido o la oferta seleccionada no te pertenece.");
            esperarEnter();
            return;
        }

        if (ofertaSeleccionada.getColaPostulantes().estaVacia()) {
            System.out.println("[ℹ️] No hay postulantes para esta oferta laboral por ahora.");
            esperarEnter();
            return;
        }

        System.out.println("\n[⏳] Procesando postulantes por estricto orden de llegada ...");

        while (!ofertaSeleccionada.getColaPostulantes().estaVacia()) {

            int idCandidato = ofertaSeleccionada.getColaPostulantes().desencolar();

            Usuario candidato = sistema.getArbolUsuarios().buscar(idCandidato);

            if (candidato != null) {
                imprimirEncabezado("POSTULANTE EN TURNO");
                System.out.println(candidato.toString());
                
                int coincidencia = sistema.calcularCoincidenciaHabilidades(candidato, ofertaSeleccionada);
                int totalRequeridas = obtenerCantidadHabilidadesRequeridas(ofertaSeleccionada);
                System.out.println("  Coincidencia con Oferta: " + coincidencia + "/" + totalRequeridas + 
                        " (" + (totalRequeridas > 0 ? (coincidencia * 100 / totalRequeridas) : 0) + "%)\n");

                System.out.println("¿Qué decisión desea tomar con este profesional?");
                System.out.println("1. ACEPTAR (Contratar y CERRAR de forma definitiva la oferta)");
                System.out.println("2. RECHAZAR (Descartar y evaluar al siguiente postulante)");

                int decision;
                do {
                    decision = pedirEntero("Opción (1 o 2): ");
                } while (decision != 1 && decision != 2);

                if (decision == 1) {
                    boolean nuevaConexion = sistema.contratarCandidato(ofertaSeleccionada.getId(), candidato.getId());
                    System.out.println("\n[🎉] ¡Contratación exitosa! Has incorporado a " + candidato.getNombre() + ".");
                    if (nuevaConexion) {
                        System.out.println("[🤝] ¡Nueva conexión establecida! Se ha agregado a @" + candidato.getNombreUsuario() + " a tus contactos directos.");
                    } else {
                        System.out.println("[ℹ️] Ya estabas conectado con @" + candidato.getNombreUsuario() + ".");
                    }
                    System.out.println("[ℹ️] La búsqueda finalizó y la oferta se encuentra CERRADA.");
                    esperarEnter();

                    return;
                } else {
                    System.out.println("\n[➡️] Postulante descartado. Removiendo de la fila y llamando al siguiente...\n");
                }
            }
        }

        System.out.println("\n[ℹ️] Se ha procesado toda la fila. La oferta sigue ACTIVA esperando nuevos candidatos.");
        esperarEnter();
    }

    private boolean pedirConfirmacionSi(String pregunta) {
        System.out.print(pregunta + " Escriba 'Si' para confirmar: ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("Si");
    }

    private int pedirEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("[❌ ERROR] Entrada inválida. Por favor, introduzca un número entero.");
            }
        }
    }

    private String pedirTextoObligatorio(String mensaje) {
        String input;
        while (true) {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[⚠️] Error: Este campo es obligatorio y no puede estar vacío.");
        }
    }

    private String pedirNombreUsuario() {
        return pedirNombreUsuario("Nombre de usuario (único, sin espacios ni @): ");
    }

    private String pedirNombreUsuario(String mensaje) {
        String input;
        while (true) {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("[⚠️] Error: El nombre de usuario no puede estar vacío.");
            } else if (input.contains(" ") || input.contains("@")) {
                System.out.println("[⚠️] Error: El nombre de usuario no debe contener espacios ni el símbolo '@'.");
            } else {
                return input;
            }
        }
    }

    private String pedirEmail() {
        return pedirEmail("Email: ");
    }

    private String pedirEmail(String mensaje) {
        String input;
        while (true) {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();

            if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return input;
            } else {
                System.out.println("[⚠️] Error: Formato de email inválido. Ejemplo esperado: usuario@dominio.com");
            }
        }
    }

    private void imprimirEncabezado(String titulo) {
        System.out.println("\n>>> " + titulo + " <<<");
        System.out.println("-------------------------------------------------");
    }

    private void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
        System.out.println();
    }
}