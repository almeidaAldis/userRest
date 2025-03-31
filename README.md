# Microservicio para el manejo de usuario

Este proyecto es un microservicio desarrollado en **Spring Boot con Java 17**, que utiliza **MySQL** como base de datos y cuenta con **Swagger** para la documentación de la API.

## Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Java 17**
- **Maven**
- **MySQL Server**
- **IDE** (IntelliJ, Eclipse, VS Code, etc.)

## Configuración de la Base de Datos
Para conectar el microservicio a una base de datos MySQL, edita el archivo de configuración `application.properties` ubicado en:

```
src/main/resources/application.properties
```

Modifica las credenciales de la base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mss_client
spring.datasource.username=useraldis
spring.datasource.password=passAldis
```

> **Nota:** Asegúrate de reemplazar `tu_base_de_datos`, `tu_usuario` y `tu_contraseña` con los valores correctos de tu entorno.

## Ejecución del Proyecto
Para compilar y ejecutar el proyecto, usa los siguientes comandos:

```sh
# Compilar el proyecto
mvn clean install

# Ejecutar el microservicio
mvn spring-boot:run
```

Al iniciar el microservicio, las tablas se generarán automáticamente en la base de datos configurada.

## Inserción de Datos Iniciales
Ejecuta los siguientes **INSERT** en tu base de datos para el correcto funcionamiento del microservicio:

```sql
INSERT INTO user(id, created_at, user_name, password) VALUES (1, NOW(), 'admin', 'dnaqr7AnyCW9mrq3iyNAcOcCdS9iW3UuVeVbSOYH41g');

INSERT INTO param(id, created_at, description, name, value) VALUES
(1, NOW(), 'password Enc', 'ENCRYPTION_KEY', '8Ea6vY7HyX29qAiQ/ACBtDu8n6cRul3rePtC8qtkvK4='),
(2, NOW(), 'TOKEN_EXPIRATION_TIME_MINUTES', 'TOKEN_EXPIRATION_TIME_MINUTES', '300');
```

## Documentación con Swagger
Una vez que el microservicio esté en ejecución, puedes acceder a la documentación de la API en:

📌 **Swagger UI:** [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)

📌 **API Docs (JSON):** [http://localhost:8084/api-docs](http://localhost:8084/api-docs)

## Generación del Token de Acceso
Para obtener un token de acceso, usa las siguientes credenciales:

```
Usuario: admin
Contraseña: admin@123
```

Puedes autenticarte mediante una petición `POST` a `/auth/login` con el siguiente cuerpo JSON:

```json
{
  "username": "admin",
  "password": "admin@123"
}
```

Esto devolverá un **JWT Token** que puedes usar para acceder a los endpoints protegidos.

## Contacto y Soporte
Si tienes alguna duda o problema, contáctanos a través del equipo de desarrollo.

---
**© 2025 - Microservicio Spring Boot - Java 17**

