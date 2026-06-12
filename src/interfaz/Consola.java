package interfaz;

import estructurasTDA.ArbolUsuariosAVL;
import estructurasTDA.GrafoConexiones;
import entidades.Usuario;

public class Consola {

    private final ArbolUsuariosAVL arbolUsuarios;
    private final GrafoConexiones grafoConexiones;
    // Acá usamos el "nombre completo" en vez de solo Scanner
    private final java.util.Scanner scanner;

    public Consola() {
        this.arbolUsuarios = new ArbolUsuariosAVL();
        this.grafoConexiones = new GrafoConexiones();
        // Acá también usamos el "nombre completo"
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
                    menuGestionUsuarios();
                    break;
                case 2:
                    menuGestionContactos();
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

    private void mostrarMenuPrincipal() {
        System.out.println("=================================================");
        System.out.println("│          SISTEMA DE RED SOCIAL                │");
        System.out.println("=================================================");
        System.out.println("│  1. Gestión de Usuarios                       │");
        System.out.println("│  2. Gestión de Red y Contactos                │");
        System.out.println("│  0. Salir del Programa                        │");
        System.out.println("=================================================");
    }

    private void menuGestionUsuarios() {
        int subOpcion;
        do {
            imprimirEncabezado("MENÚ: GESTIÓN DE USUARIOS");
            System.out.println("1. Registrar nuevo Perfil de Usuario");
            System.out.println("2. Buscar Perfil por ID");
            System.out.println("3. Eliminar Perfil del Sistema");
            System.out.println("0. Volver al menú principal");
            System.out.println("-------------------------------------------------");

            subOpcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch (subOpcion) {
                case 1:
                    ejecutarRegistrarUsuario();
                    break;
                case 2:
                    ejecutarBuscarUsuario();
                    break;
                case 3:
                    ejecutarEliminarUsuario();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("[⚠️] Opción no válida.");
                    esperarEnter();
            }
        } while (subOpcion != 0);
    }

    private void menuGestionContactos() {
        int subOpcion;
        do {
            imprimirEncabezado("MENÚ: GESTIÓN DE RED Y CONTACTOS");
            System.out.println("1. Agregar enlace de Amistad (Contacto)");
            System.out.println("2. Ver lista de contactos de un Usuario");
            System.out.println("3. Calcular Grados de Separación");
            System.out.println("0. Volver al menú principal");
            System.out.println("-------------------------------------------------");

            subOpcion = pedirEntero("Seleccione una opción: ");
            System.out.println();

            switch (subOpcion) {
                case 1:
                    ejecutarAgregarContacto();
                    break;
                case 2:
                    ejecutarVerContactos();
                    break;
                case 3:
                    ejecutarCalcularGrados();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("[⚠️] Opción no válida.");
                    esperarEnter();
            }
        } while (subOpcion != 0);
    }

    private void ejecutarRegistrarUsuario() {
        while (true) {
            imprimirEncabezado("REGISTRAR NUEVO USUARIO");
            int id = pedirEntero("Ingrese el ID único (número entero): ");

            if (arbolUsuarios.existe(id)) {
                System.out.println("[❌ ERROR] Ya existe un usuario registrado con el ID " + id);
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            System.out.print("Ingrese el nombre del usuario: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el email: ");
            String email = scanner.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String contrasenia = scanner.nextLine();

            System.out.print("Ingrese el rol (ej. Admin, Standard): ");
            String rol = scanner.nextLine();

            Usuario nuevoUsuario = new Usuario(id, nombre, email, contrasenia, rol);

            arbolUsuarios.insertar(id, nuevoUsuario);
            grafoConexiones.agregarVertice(id);

            System.out.println("\n[✅ ÉXITO] Usuario '" + nombre + "' registrado correctamente en el sistema.");
            esperarEnter();
            return;
        }
    }

    private void ejecutarBuscarUsuario() {
        while (true) {
            imprimirEncabezado("BUSCAR PERFIL DE USUARIO");
            int id = pedirEntero("Ingrese el ID a buscar: ");

            Usuario usuario = arbolUsuarios.buscar(id);

            if (usuario != null) {
                System.out.println(">> PERFIL ENCONTRADO <<");
                System.out.println(usuario.toString());
                esperarEnter();
                return;
            } else {
                System.out.println("[❌] No se encontró ningún usuario con el ID " + id);
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
            }
        }
    }

    private void ejecutarEliminarUsuario() {
        while (true) {
            imprimirEncabezado("ELIMINAR USUARIO");
            int id = pedirEntero("Ingrese el ID del usuario a eliminar: ");

            if (!arbolUsuarios.existe(id)) {
                System.out.println("[❌] El usuario con ID " + id + " no existe en el sistema.");
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            grafoConexiones.eliminarVertice(id);
            arbolUsuarios.eliminar(id);

            System.out.println("[✅ ÉXITO] El usuario ha sido eliminado por completo de la red.");
            esperarEnter();
            return;
        }
    }

    private void ejecutarAgregarContacto() {
        while (true) {
            imprimirEncabezado("NUEVO ENLACE DE CONTACTO");
            int idOrigen = pedirEntero("Ingrese el ID del primer usuario: ");
            int idDestino = pedirEntero("Ingrese el ID del segundo usuario: ");

            if (!arbolUsuarios.existe(idOrigen) || !arbolUsuarios.existe(idDestino)) {
                System.out.println("[❌ ERROR] Uno o ambos IDs no existen en el sistema. Deben registrarse primero.");
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            if (idOrigen == idDestino) {
                System.out.println("[⚠️] No puedes agregarte a ti mismo como contacto.");
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            grafoConexiones.agregarArista(idOrigen, idDestino);
            System.out.println("[✅ ÉXITO] Conexión establecida entre el ID " + idOrigen + " y el ID " + idDestino);
            esperarEnter();
            return;
        }
    }

    private void ejecutarVerContactos() {
        while (true) {
            imprimirEncabezado("LISTA DE CONTACTOS");
            int id = pedirEntero("Ingrese el ID del usuario: ");

            if (!arbolUsuarios.existe(id)) {
                System.out.println("[❌] El usuario especificado no existe.");
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            Usuario usuarioActual = arbolUsuarios.buscar(id);
            int[] contactos = grafoConexiones.obtenerContactos(id);

            System.out.println("\nContactos de: " + usuarioActual.getNombre() + " (ID: " + id + ")");
            System.out.println("-------------------------------------------------");
            if (contactos.length == 0) {
                System.out.println("Este usuario aún no tiene contactos agregados.");
            } else {
                for (int idContacto : contactos) {
                    Usuario amigo = arbolUsuarios.buscar(idContacto);
                    String nombreAmigo = (amigo != null) ? amigo.getNombre() : "Usuario Desconocido";
                    System.out.println(" • ID: " + idContacto + " -> " + nombreAmigo);
                }
            }
            System.out.println("-------------------------------------------------");
            esperarEnter();
            return;
        }
    }

    private void ejecutarCalcularGrados() {
        while (true) {
            imprimirEncabezado("CALCULAR GRADOS DE SEPARACIÓN");
            int idOrigen = pedirEntero("Ingrese el ID del usuario origen: ");
            int idDestino = pedirEntero("Ingrese el ID del usuario destino: ");

            if (!arbolUsuarios.existe(idOrigen) || !arbolUsuarios.existe(idDestino)) {
                System.out.println("[❌ ERROR] Ambos usuarios deben existir en el sistema.");
                if (!deseaReintentar()) return;
                System.out.println("\n-------------------------------------------------");
                continue;
            }

            int grados = grafoConexiones.calcularGradosDeSeparacion(idOrigen, idDestino);

            System.out.println("\n>> ANÁLISIS DE RED <<");
            System.out.println("-------------------------------------------------");
            if (grados == 0) {
                System.out.println("Estás consultando el mismo usuario.");
            } else if (grados == -1) {
                System.out.println("No existe ninguna ruta o conexión entre estos usuarios.");
            } else if (grados == 1) {
                System.out.println("Son contactos directos (1 grado de separación).");
            } else {
                System.out.println("Están conectados a través de " + grados + " grados de separación.");
                System.out.println("(Se necesitan " + (grados - 1) + " intermediario/s para conectar los perfiles).");
            }
            System.out.println("-------------------------------------------------");
            esperarEnter();
            return;
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

    private boolean deseaReintentar() {
        while (true) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Volver a intentar");
            System.out.println("0. Volver al menú anterior");
            int opcion = pedirEntero("Opción: ");

            if (opcion == 1) {
                return true;
            } else if (opcion == 0) {
                return false;
            } else {
                System.out.println("[⚠️] Opción no válida. Ingrese 1 o 0.");
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