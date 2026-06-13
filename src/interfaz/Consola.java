package interfaz;

import entidades.Usuario;

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
            mostrarMenuPrincipal();
            opcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    ejecutarLogin(); break;
                case 2:
                    ejecutarRegistro(); break;
                case 3:
                    // ejecutarCargaDeDatos();
                    break;
                case 0:
                    imprimirEncabezado("SALIENDO DEL SISTEMA");
                    System.out.println("¡Gracias por utilizar la aplicación!");
                    System.out.println("=================================================");
                    break;
                default: System.out.println("[⚠️] Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("=================================================");
        System.out.println("│         SISTEMA DE RED SOCIAL STARFNET        │");
        System.out.println("=================================================");
        System.out.println("│  1. Iniciar Sesión                            │");
        System.out.println("│  2. Registrar Nueva Cuenta                    │");
        System.out.println("│  3. Cargar Datos de Prueba                    │");
        System.out.println("│  0. Salir del Programa                        │");
        System.out.println("=================================================");
    }

    private void ejecutarLogin() {
        imprimirEncabezado("INICIAR SESIÓN");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        if (sistema.iniciarSesion(email, pass)) {
            String rol = sistema.getUsuarioActual().getRol();
            System.out.println("\n[✅] ¡Bienvenido/a, " + sistema.getUsuarioActual().getNombre() + "!");

            if (rol.equalsIgnoreCase("Profesional")) {
                menuProfesional();
            } else if (rol.equalsIgnoreCase("Reclutador")) {
                menuReclutador();
            }
        } else {
            esperarEnter();
        }
    }

    private void menuProfesional() {
        int opcion;
        do {
            imprimirEncabezado("PANEL DE PROFESIONAL");
            System.out.println("1. Ver mi Perfil");
            System.out.println("2. Editar mi Perfil");
            System.out.println("3. Deshacer Último Cambio de Perfil (Pila)");
            System.out.println("4. Buscar Empleos");
            System.out.println("5. Mi Red de Contactos y Networking");
            System.out.println("6. Explorar Catálogo de Habilidades y Agregar");
            System.out.println("0. Cerrar Sesión");
            opcion = pedirEntero("Opción: ");

            switch (opcion) {
                case 1:
                    System.out.println(sistema.getUsuarioActual().toString());
                    esperarEnter();
                    break;
                case 2:
                    ejecutarEditarMiPerfil();
                    break;
                case 3:
                    sistema.deshacerUltimoCambio();
                    esperarEnter();
                    break;
                case 4:
                    System.out.println("Próximamente: Postulación a ofertas...");
                    esperarEnter();
                    break;
                case 5:
                    ejecutarMiRedDeContactos();
                    break;
                case 6:
                    ejecutarAgregarHabilidad();
                    break;
                case 0:
                    sistema.cerrarSesion();
                    System.out.println("[ℹ️] Sesión cerrada.");
                    break;
                default:
                    System.out.println("[⚠️] Opción inválida.");
                    esperarEnter();
            }
        } while (opcion != 0);
    }

    private void menuReclutador() {
        int opcion;
        do {
            imprimirEncabezado("PANEL DE RECLUTADOR / EMPRESA");
            System.out.println("1. Ver mis Ofertas Publicadas");
            System.out.println("2. Publicar Nueva Oferta");
            System.out.println("3. Evaluar Candidatos en Cola");
            System.out.println("0. Cerrar Sesión");
            System.out.println("-------------------------------------------------");

            opcion = pedirEntero("Opción: ");

            switch (opcion) {
                case 1:
                    System.out.println("Próximamente: Lista de ofertas...");
                    esperarEnter();
                    break;
                case 0:
                    sistema.cerrarSesion();
                    System.out.println("[ℹ️] Sesión cerrada.");
                    break;
                default: System.out.println("Opción en construcción o inválida.");
            }
        } while (opcion != 0);
    }

    private void ejecutarEditarMiPerfil() {
        imprimirEncabezado("EDITAR MI PERFIL");
        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva Profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Nueva Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Nuevo Resumen: ");
        String resumen = scanner.nextLine();

        sistema.editarPerfilActual(nombre, profesion, ciudad, resumen);
        esperarEnter();
    }

    private void ejecutarRegistro() {
        imprimirEncabezado("REGISTRO DE USUARIO");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenia = scanner.nextLine();

        String rol = "";
        while (true) {
            System.out.print("Seleccione su Rol (1: Profesional, 2: Reclutador): ");
            String opcionRol = scanner.nextLine();
            if (opcionRol.equals("1")) {
                rol = "Profesional"; break;
            }
            else if (opcionRol.equals("2")) {
                rol = "Reclutador"; break;
            }
            else { System.out.println("[⚠️] Por favor ingrese 1 o 2."); }
        }

        if (sistema.registrarUsuario(nombre, email, contrasenia, rol)) {
            System.out.println("[✅] Registro exitoso. ¡Ahora puedes iniciar sesión!");
        } else {
            System.out.println("[❌] Falló el registro.");
        }
        esperarEnter();
    }

/*
    private void ejecutarCargaDeDatos() {
        imprimirEncabezado("INICIANDO SIMULACIÓN DE CARGA DE DATOS");
        System.out.println("El sistema escribirá simulando a un usuario humano.\nSe probarán TODAS las funciones con 2 usuarios nuevos.\n");
        esperarEnter();

        imprimirEncabezado("FUNCIÓN 1: REGISTRAR NUEVO USUARIO (x2)");
        simularEntrada("Ingrese el nombre del usuario: ", "Marcos (Reclutador)");
        simularEntrada("Ingrese el email: ", "marcos@empresa.com");
        simularEntrada("Ingrese la contraseña: ", "1234");
        simularEntrada("Ingrese el rol (ej. PROFESIONAL, RECLUTADOR): ", "Reclutador");
        sistema.registrarUsuario("Marcos (Reclutador)", "marcos@empresa.com", "1234", "Reclutador");

        System.out.println();
        simularEntrada("Ingrese el nombre del usuario: ", "Sofia (Profesional)");
        simularEntrada("Ingrese el email: ", "sofia@mail.com");
        simularEntrada("Ingrese la contraseña: ", "1234");
        simularEntrada("Ingrese el rol (ej. PROFESIONAL, RECLUTADOR): ", "PROFESIONAL");
        sistema.registrarUsuario("Sofia (Profesional)", "sofia@mail.com", "1234", "Profesional");

        imprimirEncabezado("FUNCIÓN 2: INICIAR SESIÓN");
        simularEntrada("Ingrese correo para login: ", "sofia@mail.com");
        simularEntrada("Ingrese contraseña: ", "1234");
        sistema.iniciarSesion("sofia@mail.com", "1234");

        imprimirEncabezado("FUNCIÓN 3: EDITAR PERFIL");
        simularEntrada("Campo a editar (nombre, profesion, ciudad, resumen): ", "profesion");
        simularEntrada("Ingrese el nuevo valor: ", "Desarrolladora FullStack");

        imprimirEncabezado("FUNCIÓN 4: BUSCAR PERFIL DE USUARIO");
        simularEntrada("Ingrese el ID a buscar: ", "5");
        Usuario u1 = sistema.buscarUsuario(5);
        if (u1 != null) {
            System.out.println("\n>> PERFIL ENCONTRADO <<");
            System.out.println(u1.toString());
        }

        imprimirEncabezado("FUNCIÓN 5: DESHACER ÚLTIMO CAMBIO");
        simularEntrada("Ingrese el ID del usuario a deshacer cambio: ", "5");
        sistema.deshacerUltimoCambio();

        System.out.println("(Rehaciendo edición automáticamente para continuar...)");
        sistema.editarPerfilActual("", "profesion", "Desarrolladora FullStack","");

        imprimirEncabezado("FUNCIÓN 6: AGREGAR HABILIDAD AL PERFIL");
        simularEntrada("Ingrese el ID del usuario: ", "5");
        simularEntrada("Ingrese el nombre de la habilidad: ", "Angular");
        simularEntrada("Ingrese la categoría: ", "Frontend");
        // sistema.agregarHabilidadUsuario(5, "Angular", "Frontend");

        imprimirEncabezado("FUNCIÓN 7: ENVIAR SOLICITUD DE CONEXIÓN");
        simularEntrada("Ingrese su ID (Solicitante): ", "4");
        simularEntrada("Ingrese el ID a conectar (Receptor): ", "5");
        sistema.enviarSolicitudConexion(4, 5);

        imprimirEncabezado("FUNCIÓN 8: PROCESAR SOLICITUDES");
        simularEntrada("Ingrese su ID (Receptor): ", "5");
        simularEntrada("Ingrese el ID de quien le envió la solicitud: ", "4");
        simularEntrada("¿Desea aceptar la solicitud? (S/N): ", "S");
        sistema.procesarConexion(5, 4, true);

        imprimirEncabezado("FUNCIÓN 9: VER LISTA DE CONTACTOS");
        simularEntrada("Ingrese el ID del usuario: ", "4");
        int[] contactos = sistema.obtenerContactos(4);
        System.out.println("\nContactos del ID: 4");
        System.out.println("-------------------------------------------------");
        if (contactos != null) {
            for (int idContacto : contactos) {
                Usuario amigo = sistema.buscarUsuario(idContacto);
                String nombreAmigo = (amigo != null) ? amigo.getNombre() : "Usuario Desconocido";
                System.out.println(" • ID: " + idContacto + " -> " + nombreAmigo);
            }
        }
        System.out.println("-------------------------------------------------");

        imprimirEncabezado("FUNCIÓN 10: CALCULAR GRADOS DE SEPARACIÓN");
        simularEntrada("Ingrese el ID del usuario origen: ", "4");
        simularEntrada("Ingrese el ID del usuario destino: ", "5");
        int grados = sistema.calcularGradosDeSeparacion(4, 5);
        System.out.println("\n>> ANÁLISIS DE RED <<");
        System.out.println("-------------------------------------------------");
        if (grados > 0) {
            System.out.println("Son contactos directos (" + grados + " grado de separación).");
        }
        System.out.println("-------------------------------------------------");

        imprimirEncabezado("FUNCIÓN 11: CREAR OFERTA DE TRABAJO");
        simularEntrada("ID del reclutador: ", "4");
        simularEntrada("Título de la oferta: ", "Desarrollador Web SSR");
        simularEntrada("Descripción: ", "Se busca dev para proyecto bancario.");
        simularEntrada("Habilidades: ", "Angular, Java");
        sistema.crearOferta(4, "Desarrollador Web SSR", "Se busca dev para proyecto bancario.", "Angular, Java");

        imprimirEncabezado("FUNCIÓN 12: POSTULARSE A UNA OFERTA");
        simularEntrada("ID del usuario postulante: ", "5");
        simularEntrada("ID de la oferta: ", "2");
        sistema.postularse(5, 2);

        System.out.println("\n[✅ FIN DE LA DEMO] Todas las 12 funciones fueron probadas con 2 usuarios.");
        esperarEnter();
    }

 */

    private void ejecutarAgregarHabilidad() {
        imprimirEncabezado("CATÁLOGO JERÁRQUICO DE HABILIDADES");
        sistema.getArbolHabilidades().mostrarEstructura();

        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("1. Agregar una habilidad a mi perfil");
        System.out.println("2. Ver especialidades de una rama (Buscar por ID de categoría)");
        System.out.println("0. Volver");
        int opc = pedirEntero("Opción: ");

        if (opc == 1) {
            System.out.print("Escribe el nombre exacto de la tecnología (Ej: Java): ");
            String nombreHab = scanner.nextLine();
            sistema.agregarHabilidadAlPerfilActual(nombreHab);

        } else if (opc == 2) {
            int idRama = pedirEntero("Ingresa el [ID] numérico de la categoría a explorar (Ej: 2): ");
            sistema.getArbolHabilidades().mostrarSubEstructura(idRama);
        }
        esperarEnter();
    }

    private void ejecutarMiRedDeContactos() {
        int opcion;
        do {
            imprimirEncabezado("MÓDULO DE NETWORKING & CONTACTOS");
            System.out.println("1. Ver mis contactos actuales");
            System.out.println("2. Ver sugerencias de contactos recomendados");
            System.out.println("3. Conectar con un nuevo profesional (Por ID único)");
            System.out.println("4. Calcular grados de separación con otro usuario (BFS)");
            System.out.println("0. Volver al panel anterior");
            opcion = pedirEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    imprimirEncabezado("MIS CONTACTOS DIRECTOS");
                    int[] contactos = sistema.obtenerContactos(sistema.getUsuarioActual().getId());

                    if (contactos == null || contactos.length == 0) {
                        System.out.println("Aún no tienes contactos directos en tu red.");
                    } else {
                        System.out.println("Lista de profesionales en tu red:");
                        for (int idContacto : contactos) {
                            Usuario c = sistema.getArbolUsuarios().buscar(idContacto);
                            if (c != null) {
                                System.out.println("- [ID: " + c.getId() + "] " + c.getNombre() + " | Profesión: " + c.getProfesion());
                            }
                        }
                    }
                    esperarEnter();
                    break;

                case 2:
                    sistema.mostrarContactosRecomendados();
                    esperarEnter();
                    break;

                case 3:
                    imprimirEncabezado("CONECTAR CON UN PROFESIONAL");
                    int idConectar = pedirEntero("Ingresa el [ID] único del usuario que deseas agregar: ");
                    sistema.conectarConContacto(idConectar);
                    esperarEnter();
                    break;

                case 4:
                    imprimirEncabezado("CALCULAR GRADOS DE SEPARACIÓN (BFS)");
                    int idDistancia = pedirEntero("Ingresa el [ID] del profesional para medir la distancia: ");
                    sistema.mostrarGradosDeSeparacionCon(idDistancia);
                    esperarEnter();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("[⚠️] Opción inválida.");
                    esperarEnter();
            }
        } while (opcion != 0);
    }


    private void simularEntrada(String mensaje, String valorSimulado) {
        System.out.print(mensaje);
        try {
            Thread.sleep(400);
            System.out.println(valorSimulado);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int pedirEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("[❌ ERROR] Entrada inválida. Por favor, introduzca un número entero válido.");
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