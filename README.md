**Proyecto Sabor Gourmet**

**Grupo C3**

Integrantes:

- Jose Luis Espinoza (Bludegar)
- Frederick Escobar
- Gonzalo Croft

# Sistema de Gestión de Prácticas — Informe y Guía de entrega

Este repositorio contiene la solución del proyecto de prácticas (back‑end Java + Thymeleaf). Este README está pensado para usarse como parte del informe final y contiene las instrucciones, evidencias a capturar y la guía para generar el diagrama de la base de datos.

> Nota: las capturas de pantalla y el diagrama no se incluyen aquí (por limitaciones del repositorio). En las secciones correspondientes se indican qué capturas realizar y cómo generar el diagrama para pegarlos en el informe.

## 1. Resumen de la solución

- Aplicación web Spring Boot (Java 21) con Thymeleaf para la UI.
- Roles: `ESTUDIANTE`, `PROFESOR`.
- Funcionalidad principal:
  - Estudiantes: crear y ver sus prácticas (no pueden editar ni agregar detalles/horas).
  - Profesores: CRUD completo de prácticas; añadir/gestionar detalles (registro de horas), gestionar estudiantes, empresas y profesores supervisores.

## 2. Cómo ejecutar la aplicación (entorno local)

Requisitos:
- Java 21 (JDK 21)
- Maven (el proyecto incluye `mvnw` / `mvnw.cmd`)

Comandos (bash / Git Bash / WSL en Windows):

```bash
export JAVA_HOME="/c/Program Files/Java/jdk-21"
export PATH="$JAVA_HOME/bin:$PATH"
./mvnw -Dspring-boot.run.profiles=local -Dspring-boot.run.arguments="--server.port=8081" spring-boot:run
```

- La app arrancará en `http://localhost:8081` (si no cambias el puerto).
- H2 console (perfil `local`): `http://localhost:8081/h2-console` (si está habilitado). JDBC URL por defecto: `jdbc:h2:mem:localdb`.

### Usuarios de prueba (seed)

- Profesor: `maria@example.com` / `pass123` → redirige a `/profesor`
- Estudiante: `juan@example.com` / `pass123` → redirige a `/estudiante/{id}/practicas`

> Si las credenciales no funcionan, revisa en `src/main/java/ipss/parcticas/DataLoader.java` el código que crea usuarios de ejemplo.

## 3. Puntos que debe contener el informe (Checklist)

1. Captura(s) de la solución (pantallazos)
   - Login (pantalla de login)
   - Panel del profesor (`/profesor`) mostrando botones y lista (hacer captura con botones visibles)
   - Lista de prácticas (`/profesor/practicas`) con acciones `Ver`, `Editar`, `Eliminar`
   - Vista estudiante mostrando la lista de prácticas y formulario de creación (si corresponde)
   - Formulario de creación/edición de práctica (profesor y estudiante según corresponda)
   - Formulario de creación de detalle (solo profesor)
   - H2 console o captura del esquema (opcional)

2. Diagrama de la Base de Datos (ER)
   - Genera el diagrama siguiendo la sección siguiente y añade la imagen en el informe.

3. Funciones realizadas por cada integrante
   - Añade una tabla con el nombre del integrante y las tareas concretas realizadas (código, vistas, controladores, CSS, pruebas, documentación).

4. Pruebas de la funcionalidad
   - Capturas con casos de prueba: crear práctica (estudiante), ver práctica (estudiante), crear/editar/eliminar práctica (profesor), agregar detalle (profesor).
   - Anota los pasos ejecutados para cada captura (paso 1, paso 2, resultado esperado, resultado real).

## 4. Cómo generar el diagrama de la base de datos

Opción A — Usar DBeaver (recomendado, GUI):

1. Ejecuta la aplicación con el perfil `local` para que la base en memoria se cree (`jdbc:h2:mem:localdb`).
2. Abre DBeaver → Nueva Conexión → H2.
3. Conecta usando `jdbc:h2:tcp://localhost/~/localdb` o la URL que use tu entorno (si la memoria no es accesible vía TCP, puedes usar export SQL desde la app al disco o habilitar H2 con archivo en `application-local.properties`).
4. En DBeaver, botón derecho en el esquema → `ER Diagram` → exporta como PNG/SVG.

Opción B — Exportar esquema e importar en herramientas online:

1. Desde H2 Console o usando `schema-export` (o ejecutar `SCRIPT TO 'schema.sql'` en H2) exporta el DDL.
2. Usa una herramienta como dbdiagram.io o draw.io para crear el ER a partir del DDL.

Notas sobre la estructura:
- Entidades principales: `Usuario`, `Estudiante`, `ProfesorSupervisor`, `Empresa`, `Practica`, `DetallePractica`.
- Relaciones:
  - `Usuario` -> (1:1) `Estudiante` o `ProfesorSupervisor` (según `rol`).
  - `Practica` tiene relación con `Estudiante`, `Empresa`, `ProfesorSupervisor`.
  - `DetallePractica` referencia `Practica` y `Estudiante`.

Incluir en el informe un diagrama con estas tablas y las relaciones (PK/FK).

## 5. Funciones realizadas por cada integrante

| Integrante | Rol en el proyecto | Archivos / tareas principales |
|---|---|---|
| Frederick | Desarrollo backend | `controladores/*`, `servicios/*`, acceso a DB |
| Frederick / Jose Espinoza (bludegar) | Frontend / templates | `src/main/resources/templates/*`, `static/css/styles.css` |
| Jose Espinoza (bludegar) | Pruebas / seed / documentación | `DataLoader.java`, `README.md`, pruebas manuales |


## 7. Archivos fuente relevantes 

- Controladores: `src/main/java/ipss/parcticas/controladores/*` (autenticación, prácticas, detalles)
- Plantillas: `src/main/resources/templates/*` (vistas profesor/estudiante)
- Estilos: `src/main/resources/static/css/styles.css`
- Seed: `src/main/java/ipss/parcticas/DataLoader.java`



