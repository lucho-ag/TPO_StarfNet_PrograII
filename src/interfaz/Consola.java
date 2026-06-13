package interfaz;

import entidades.*;

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
        imprimirEncabezado("INICIANDO PRUEBA AUTOMÁTICA EN ORDEN SECUENCIAL");
        System.out.println("Recreando el comportamiento del sistema paso a paso...\n");

        sistema.registrarUsuario("luciano_agostino", "Luciano Agostino", "lucho@gmail", "1234", "RECLUTADOR");
        sistema.registrarUsuario("facu_ielpi", "Facundo Ielpi", "facu@gmail", "1234", "PROFESIONAL");

        System.out.println("\n>>> [LOGIN] Luciano inicia sesión como Reclutador...");
        sistema.iniciarSesion("lucho@gmail", "1234");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("1");
        imprimirEncabezado("BUSCAR PERFIL DE USUARIO");
        System.out.print("Ingrese el nombre de usuario exacto a buscar (sin @): "); System.out.println("facu_ielpi");
        Usuario uBuscado1 = sistema.buscarUsuarioPorNombreUsuario("facu_ielpi");
        if (uBuscado1 != null) {
            System.out.println("\n>> PERFIL ENCONTRADO <<\n" + uBuscado1.toString());
        }

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("2");
        System.out.println(sistema.getUsuarioActual().toString());

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("3");
        imprimirEncabezado("EDITAR MI PERFIL");
        System.out.print("Nombre personal completo: "); System.out.println("Luciano Agostino");
        System.out.print("Profesión: "); System.out.println("IT Talent Acquisition");
        System.out.print("Ciudad: "); System.out.println("Buenos Aires");
        System.out.print("Resumen: "); System.out.println("Buscando especialistas en infraestructura y desarrollo.");
        sistema.editarPerfilActual("Luciano Agostino", "IT Talent Acquisition", "Buenos Aires", "Buscando especialistas en infraestructura y desarrollo.");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("4");
        sistema.deshacerUltimoCambio();

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("5");
        imprimirEncabezado("MIS CONTACTOS");
        mostrarContactosInterno(sistema.getUsuarioActual().getId());

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("6");
        imprimirEncabezado("ENVIAR SOLICITUD DE CONEXIÓN");
        System.out.print("Ingrese el nombre de usuario exacto a conectar (sin @): "); System.out.println("facu_ielpi");
        sistema.enviarSolicitudConexion("facu_ielpi");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("7");
        imprimirEncabezado("PROCESAR SOLICITUDES");
        System.out.println("[ℹ️] No tienes solicitudes de conexión pendientes.");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("8");
        imprimirEncabezado("CREAR OFERTA DE TRABAJO");
        System.out.print("Título de la oferta: "); System.out.println("DevOps & Cloud Engineer");
        System.out.print("Descripción: "); System.out.println("Buscamos experto en automatización, bases de datos y entornos Linux.");
        System.out.print("Habilidades requeridas: "); System.out.println("Java, SQL");
        sistema.crearOferta(sistema.getUsuarioActual().getId(), "DevOps & Cloud Engineer", "Buscamos experto en automatización, bases de datos y entornos Linux.", "Java, SQL");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("9");
        System.out.print("¿Seguro que desea cerrar sesión? Escriba 'Si' para confirmar: "); System.out.println("Si");
        sistema.cerrarSesion();
        System.out.println("[✅] Sesión cerrada correctamente.");

        System.out.println("\n>>> [LOGIN] Facundo inicia sesión como Profesional...");
        sistema.iniciarSesion("facu@gmail", "1234");

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("1");
        imprimirEncabezado("BUSCAR PERFIL DE USUARIO");
        System.out.print("Ingrese el nombre de usuario exacto a buscar (sin @): "); System.out.println("luciano_agostino");
        Usuario uBuscado2 = sistema.buscarUsuarioPorNombreUsuario("luciano_agostino");
        if (uBuscado2 != null) {
            System.out.println("\n>> PERFIL ENCONTRADO <<\n" + uBuscado2.toString());
        }

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("2");
        System.out.println(sistema.getUsuarioActual().toString());

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("3");
        imprimirEncabezado("EDITAR MI PERFIL");
        System.out.print("Nombre personal completo: "); System.out.println("Facundo Ielpi");
        System.out.print("Profesión: "); System.out.println("Cloud Infrastructure & Automation Engineer");
        System.out.print("Ciudad: "); System.out.println("General Rodríguez");
        System.out.print("Resumen: "); System.out.println("Especialista en flujos de automatización n8n, manejo de Redis Cloud e integraciones de API.");
        sistema.editarPerfilActual("Facundo Ielpi", "Cloud Infrastructure & Automation Engineer", "General Rodríguez", "Especialista en flujos de automatización n8n, manejo de Redis Cloud e integraciones de API.");

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("4");
        imprimirEncabezado("AGREGAR HABILIDAD");
        System.out.println("Catálogo de Habilidades Disponibles:\n1. Tecnología y Sistemas\n2. Desarrollo de Software\n3. Datos e IA\n4. Java\n5. Python\n6. Desarrollo Web (HTML/CSS)\n7. SQL\n8. Machine Learning\n0. Cancelar");
        System.out.print("Seleccione el número de la habilidad: "); System.out.println("4");
        sistema.agregarHabilidadAlPerfilActual("Java");

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("5");
        sistema.deshacerUltimoCambio();

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("6");
        imprimirEncabezado("MIS CONTACTOS");
        mostrarContactosInterno(sistema.getUsuarioActual().getId());

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("7");
        sistema.mostrarContactosRecomendados();

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("8");
        imprimirEncabezado("ENVIAR SOLICITUD DE CONEXIÓN");
        System.out.print("Ingrese el nombre de usuario exacto a conectar (sin @): "); System.out.println("luciano_agostino");
        sistema.enviarSolicitudConexion("luciano_agostino");

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("9");
        imprimirEncabezado("PROCESAR SOLICITUDES");
        int[] pendientes = sistema.obtenerSolicitudesPendientes();
        for (int idSolicitante : pendientes) {
            Usuario solicitante = sistema.getArbolUsuarios().buscar(idSolicitante);
            if (solicitante != null) {
                System.out.println("-------------------------------------------------");
                System.out.println("Solicitud de: @" + solicitante.getNombreUsuario() + " (" + solicitante.getNombre() + ")");
                System.out.print("¿Aceptar solicitud? (S/N): "); System.out.println("S");
                sistema.procesarConexion(idSolicitante, true);
            }
        }

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("10");
        imprimirEncabezado("POSTULARSE A OFERTA");
        Oferta[] ofertasDisponibles = sistema.obtenerOfertasDeContactos();
        System.out.println("Ofertas publicadas por tus contactos:");
        for (int i = 0; i < ofertasDisponibles.length; i++) {
            Usuario reclutador = sistema.getArbolUsuarios().buscar(ofertasDisponibles[i].getIdReclutador());
            String nombreRec = reclutador != null ? reclutador.getNombreUsuario() : "Desconocido";
            System.out.println((i + 1) + ". " + ofertasDisponibles[i].getTitulo() + " (@" + nombreRec + ")");
        }
        System.out.println("0. Cancelar\n-------------------------------------------------");
        System.out.print("Seleccione el número de la oferta para ver detalles: "); System.out.println("1");
        if (ofertasDisponibles.length > 0) {
            Oferta seleccionada = ofertasDisponibles[0];
            System.out.println("\n>> DETALLES DE LA OFERTA <<");
            System.out.println("Publicado por: @luciano_agostino");
            System.out.println("Título: " + seleccionada.getTitulo());
            System.out.println("Descripción: " + seleccionada.getDescripcion());
            System.out.println("Habilidades: " + seleccionada.getHabilidadesRequeridas());
            System.out.println("-------------------------------------------------");
            System.out.print("¿Desea postularse a esta oferta? (S/N): "); System.out.println("S");
            sistema.postularse(sistema.getUsuarioActual().getId(), seleccionada.getId());
        }

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("11");
        System.out.print("¿Seguro que desea cerrar sesión? Escriba 'Si' para confirmar: "); System.out.println("Si");
        System.out.println("[✅] Sesión cerrada correctamente.");

        imprimirMenuProfesionalSimulado("facu_ielpi");
        System.out.print("Seleccione una opción: "); System.out.println("12");
        System.out.print("¿Seguro que desea ELIMINAR su cuenta de forma permanente? Escriba 'Si' para confirmar: "); System.out.println("Si");
        sistema.getUsuarioActual().setActivo(false);
        sistema.cerrarSesion();
        System.out.println("[✅] Tu cuenta ha sido eliminada por completo.");

        System.out.println("\n>>> [LOGIN] Luciano vuelve a ingresar para completar su última opción disponible...");
        sistema.iniciarSesion("lucho@gmail", "1234");

        imprimirMenuReclutadorSimulado("luciano_agostino");
        System.out.print("Seleccione una opción: "); System.out.println("10");
        System.out.print("¿Seguro que desea ELIMINAR su cuenta de forma permanente? Escriba 'Si' para confirmar: "); System.out.println("Si");
        sistema.getUsuarioActual().setActivo(false);
        sistema.cerrarSesion();
        System.out.println("[✅] Tu cuenta ha sido eliminada por completo.");

        System.out.println("\n=================================================================");
        System.out.println("│ PRUEBA AUTOMÁTICA FINALIZADA: TODOS LOS MENÚS EJECUTADOS EN ORDEN │");
        System.out.println("=================================================================");
        esperarEnter();
    }

    private void mostrarContactosInterno(int idUsuario) {
        int[] contactos = sistema.obtenerContactos(idUsuario);
        if (contactos != null && contactos.length > 0) {
            System.out.println("\nMis contactos\n-------------------------------------------------");
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
    }

    private void imprimirMenuProfesionalSimulado(String usuario) {
        imprimirEncabezado("MENÚ PROFESIONAL: @" + usuario);
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
        System.out.println("-------------------------------------------------");
    }

    private void imprimirMenuReclutadorSimulado(String usuario) {
        imprimirEncabezado("MENÚ RECLUTADOR: @" + usuario);
        System.out.println("1. Buscar Perfil de Usuario");
        System.out.println("2. Ver mi Perfil");
        System.out.println("3. Editar mi Perfil");
        System.out.println("4. Deshacer último cambio (Historial)");
        System.out.println("5. Ver mis Contactos");
        System.out.println("6. Enviar Solicitud de Conexión");
        System.out.println("7. Procesar Solicitudes Pendientes");
        System.out.println("8. Crear Oferta de Trabajo");
        System.out.println("9. Cerrar Sesión");
        System.out.println("10. Eliminar Cuenta");
        System.out.println("-------------------------------------------------");
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
            System.out.println("9. Cerrar Sesión");
            System.out.println("10. Eliminar Cuenta");
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
                case 9:
                    if (pedirConfirmacionSi("¿Seguro que desea cerrar sesión?")) {
                        ejecutarCerrarSesion();
                        return;
                    }
                    break;
                case 10:
                    if (pedirConfirmacionSi("¿Seguro que desea ELIMINAR su cuenta de forma permanente?")) {
                        ejecutarEliminarCuenta();
                        return;
                    }
                    break;
                default:
                    System.out.println("[⚠️] Opción no válida.");
                    esperarEnter();
            }
        } while(sistema.getUsuarioActual() != null);
    }

    private void ejecutarCrearCuenta() {
        imprimirEncabezado("CREAR CUENTA NUEVA");
        System.out.print("Nombre de usuario (único, sin @): ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Nombre personal completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenia = scanner.nextLine();

        System.out.println("\nSeleccione su tipo de cuenta:");
        System.out.println("1. PROFESIONAL");
        System.out.println("2. RECLUTADOR");
        int tipo = pedirEntero("Opción: ");
        String rol = (tipo == 2) ? "RECLUTADOR" : "PROFESIONAL";

        if (sistema.registrarUsuario(nombreUsuario, nombre, email, contrasenia, rol)) {
            esperarEnter();
        }
    }

    private boolean ejecutarIniciarSesion() {
        imprimirEncabezado("INICIAR SESIÓN");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenia = scanner.nextLine();

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
        System.out.print("Ingrese el nombre de usuario exacto a buscar (sin @): ");
        String nombreUsuario = scanner.nextLine();
        Usuario usuario = sistema.buscarUsuarioPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            System.out.println("\n>> PERFIL ENCONTRADO <<");
            System.out.println(usuario.toString());
        } else {
            System.out.println("\n[❌] No se encontró ningún usuario activo con el nombre de usuario @" + nombreUsuario + ".");
        }
        esperarEnter();
    }

    private void ejecutarVerMiPerfil() {
        System.out.println(sistema.getUsuarioActual().toString());
        esperarEnter();
    }

    private void ejecutarEditarPerfil() {
        imprimirEncabezado("EDITAR MI PERFIL");
        System.out.print("Nombre personal completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Resumen: ");
        String resumen = scanner.nextLine();

        sistema.editarPerfilActual(nombre, profesion, ciudad, resumen);
        esperarEnter();
    }

    private void ejecutarDeshacerCambio() {
        sistema.deshacerUltimoCambio();
        esperarEnter();
    }

    private void ejecutarAgregarHabilidad() {
        imprimirEncabezado("AGREGAR HABILIDAD");
        System.out.println("Catálogo de Habilidades Disponibles:");
        System.out.println("1. Tecnología y Sistemas");
        System.out.println("2. Desarrollo de Software");
        System.out.println("3. Datos e IA");
        System.out.println("4. Java");
        System.out.println("5. Python");
        System.out.println("6. Desarrollo Web (HTML/CSS)");
        System.out.println("7. SQL");
        System.out.println("8. Machine Learning");
        System.out.println("0. Cancelar");
        System.out.println("-------------------------------------------------");

        int opcion = pedirEntero("Seleccione el número de la habilidad: ");
        String habilidadSeleccionada = "";

        switch (opcion) {
            case 1: habilidadSeleccionada = "Tecnología y Sistemas"; break;
            case 2: habilidadSeleccionada = "Desarrollo de Software"; break;
            case 3: habilidadSeleccionada = "Datos e IA"; break;
            case 4: habilidadSeleccionada = "Java"; break;
            case 5: habilidadSeleccionada = "Python"; break;
            case 6: habilidadSeleccionada = "Desarrollo Web (HTML/CSS)"; break;
            case 7: habilidadSeleccionada = "SQL"; break;
            case 8: habilidadSeleccionada = "Machine Learning"; break;
            case 0: return;
            default:
                System.out.println("[❌] Opción inválida.");
                esperarEnter();
                return;
        }

        sistema.agregarHabilidadAlPerfilActual(habilidadSeleccionada);
        esperarEnter();
    }

    private void ejecutarEnviarSolicitud() {
        imprimirEncabezado("ENVIAR SOLICITUD DE CONEXIÓN");
        System.out.print("Ingrese el nombre de usuario exacto a conectar (sin @): ");
        String nombreUsuario = scanner.nextLine();
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

    private void ejecutarCrearOferta() {
        imprimirEncabezado("CREAR OFERTA DE TRABAJO");
        System.out.print("Título de la oferta: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Habilidades requeridas: ");
        String habilidades = scanner.nextLine();

        sistema.crearOferta(sistema.getUsuarioActual().getId(), titulo, descripcion, habilidades);
        esperarEnter();
    }

    private void ejecutarPostularse() {
        imprimirEncabezado("POSTULARSE A OFERTA");
        Oferta[] ofertasDisponibles = sistema.obtenerOfertasDeContactos();

        if (ofertasDisponibles.length == 0) {
            System.out.println("[ℹ️] No hay ofertas laborales activas publicadas por tus contactos.");
            esperarEnter();
            return;
        }

        System.out.println("Ofertas publicadas por tus contactos:");
        for (int i = 0; i < ofertasDisponibles.length; i++) {
            Usuario reclutador = sistema.getArbolUsuarios().buscar(ofertasDisponibles[i].getIdReclutador());
            String nombreRec = reclutador != null ? reclutador.getNombreUsuario() : "Desconocido";
            System.out.println((i + 1) + ". " + ofertasDisponibles[i].getTitulo() + " (@" + nombreRec + ")");
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
        System.out.println("Habilidades: " + seleccionada.getHabilidadesRequeridas());
        System.out.println("-------------------------------------------------");

        System.out.print("¿Desea postularse a esta oferta? (S/N): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("S")) {
            sistema.postularse(sistema.getUsuarioActual().getId(), seleccionada.getId());
        }
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