**Evaluacion 3**

**Grupo C3**

Integrantes:

- Jose Luis Espinoza (Bludegar)
- Frederick Escobar
- Gonzalo Croft

# Sistema de Gestión de Prácticas — Informe y Guía de entrega

Este repositorio contiene la solución del proyecto de prácticas (back‑end Java + Thymeleaf). Este README está pensado para usarse como parte del informe final y contiene las instrucciones, evidencias a capturar y la guía para generar el diagrama de la base de datos.


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



