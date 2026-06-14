# Nombre del proyecto: TPO_StarfNet_PrograII

**Integrantes del grupo:** Agostino Luciano, Ielpi Facundo, Luca Vilajosana Juan  
**Alternativa elegida:** Ecosistema de Red Social Profesional  
**Link del Repositorio:** https://github.com/lucho-ag/TPO_StarfNet_PrograII.git

---

## Estructuras de datos utilizadas

Para el desarrollo del sistema, hemos implementado las siguientes estructuras de datos abstractas (TDA) para asegurar un manejo eficiente de la información:

* **Árbol AVL:** Estructura de búsqueda auto-balanceada utilizada como base de datos principal para almacenar y gestionar los usuarios del sistema, garantizando tiempos de respuesta óptimos.
* **Árbol N-ario:** Estructura jerárquica empleada para organizar y categorizar el catálogo de habilidades técnicas disponibles.
* **Grafo no dirigido:** Utilizado para representar la red de contactos, donde cada usuario es un nodo y cada conexión es una arista, permitiendo mapear toda la red social.
* **Cola (FIFO):** Implementada para gestionar las solicitudes de conexión pendientes y el orden de llegada de los postulantes a las ofertas laborales.
* **Pila (LIFO):** Estructura utilizada para gestionar el historial de edición de perfiles, permitiendo deshacer cambios de forma eficiente.
* **Conjunto (Set):** Estructura fundamental para realizar operaciones matemáticas de intersección, permitiendo calcular los "amigos en común" y optimizar el motor de recomendaciones.

---

## Funcionalidades del Sistema

El programa abarca todo el ciclo de interacción de una red social laboral, dividiendo las acciones según el rol del usuario:

### Gestión de Cuentas
* **Registro de usuarios:** Creación de cuentas definiendo el rol (Reclutador o Profesional).
* **Autenticación:** Inicio y cierre de sesión seguro.
* **Baja del sistema:** Eliminación definitiva de la cuenta (baja lógica).

### Gestión de Perfil
* **Edición de datos:** Visualización y actualización de la información personal (nombre, ciudad, profesión, resumen).
* **Catálogo de habilidades:** Los profesionales pueden agregar conocimientos técnicos específicos a su perfil.
* **Deshacer cambios:** Opción para revertir la última modificación guardada en el perfil, restaurando la versión anterior mediante el historial temporal.

### Networking y Red de Contactos
* **Búsqueda global:** Localización de perfiles ingresando el nombre de usuario exacto.
* **Solicitudes de conexión:** Envío de invitaciones a otros usuarios de la plataforma.
* **Bandeja de entrada:** Procesamiento (aceptar o rechazar) las solicitudes de conexión pendientes.
* **Directorio:** Visualización del listado completo de contactos directos.
* **Motor de recomendaciones:** Sugiere nuevas personas para conectar analizando la cantidad de amigos en común. Si la red está vacía, recomienda usuarios que compartan la misma profesión.

### Bolsa de Trabajo
* **Creación de ofertas:** Opción exclusiva para los reclutadores, quienes pueden publicar anuncios con descripciones y habilidades requeridas.
* **Postulaciones:** Los profesionales pueden ver un listado de las ofertas activas publicadas por su red de contactos y enviar su postulación formal.

### Simulación Automática
* **Prueba de integración:** El sistema incluye un modo de "Prueba Automática" en el menú principal que ejecuta de forma secuencial un recorrido completo por todas las opciones. Esto simula la interacción real entre un reclutador y un profesional para verificar el correcto funcionamiento de todos los módulos sin necesidad de cargar los datos de forma manual.

---

## Contribuciones del equipo

* **Agostino Luciano:** Responsable de la arquitectura base del proyecto mediante la implementación de las estructuras de datos fundamentales como el Grafo de conexiones y la Cola de usuarios. Se encargó de la creación del Árbol AVL y Árbol N-ario para la gestión de usuarios y habilidades respectivamente, definió las interfaces críticas del sistema y realizó actualizaciones de los métodos de búsqueda, la lógica de conexiones, el método de intersección de conjuntos para los contactos recomendados y actualizó la reclutación y aceptación de ofertas laborales.
* **Ielpi Facundo:** Creación y configuración del archivo README del proyecto. Desarrolló la implementación técnica de la interfaz de la consola, asegurando la interacción fluida del usuario. Fue responsable de la configuración del archivo principal (main), la integración de la carga de datos del modelo, los cambios estructurales en la clase Usuario y la sincronización general del sistema mediante múltiples merges de ramas.
* **Luca Vilajosana Juan:** Responsable de la estructuración de las entidades del dominio como `Habilidad` y `PerfilEstado`. Implementó la clase `Usuario` y consolidó el `SistemaRedSocial`, junto con el desarrollo de la `PilaCambios` y sus nodos, asegurando la integridad de las estructuras de datos y su correcta integración con las interfaces del programa.

## Jerarquia del Proyecto

``` 
├── 📁 .idea/                
├── 📁 out/               
├── 📁 src/                  
│   ├── 📁 entidades/       
│   │   ├── 📄 Habilidad.java
│   │   ├── 📄 Oferta.java
│   │   ├── 📄 PerfilEstado.java
│   │   └── 📄 Usuario.java
│   │
│   ├── 📁 estructurasTDA/    
│   │   ├── 📄 ArbolHabilidades.java
│   │   ├── 📄 ArbolUsuariosAVL.java
│   │   ├── 📄 ColaUsuarios.java
│   │   ├── 📄 Conjunto.java
│   │   ├── 📄 GrafoConexiones.java
│   │   ├── 📄 IArbolHabilidades.java
│   │   ├── 📄 IArbolUsuariosAVL.java
│   │   ├── 📄 IColaUsuarios.java
│   │   ├── 📄 IGrafoConexiones.java
│   │   ├── 📄 NodoArbolHabilidades.java
│   │   ├── 📄 NodoAVL.java
│   │   ├── 📄 NodoCola.java
│   │   ├── 📄 NodoGrafo.java
│   │   ├── 📄 NodoListaEnlazada.java
│   │   ├── 📄 NodoPila.java
│   │   └── 📄 PilaCambios.java
│   │
│   └── 📁 interfaz/         
│       ├── 📄 Consola.java
│       ├── 📄 Main.java
│       ├── 📄 SistemaRedSocial.java
│       └── 📄 TesterAutomatizado.java
```
