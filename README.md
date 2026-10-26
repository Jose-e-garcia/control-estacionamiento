# Control de Estacionamiento

## Descripción

Sistema de control de estacionamiento desarrollado con Java y Spring Boot. Permite administrar espacios de estacionamiento y tickets de vehículos, verificando la disponibilidad de los espacios.

## Tecnologías utilizadas

- Java
- Spring Boot
- Maven
- PostgreSQL
- Spring Data JPA
- Spring Web
- Bean Validation
- Postman

## Funcionalidades

- Consultar los tickets de estacionamiento.
- Buscar tickets por ID.
- Registrar tickets con la placa del vehículo y el espacio asignado.
- Actualizar la información de los tickets.
- Eliminar tickets.
- Evitar la asignación de espacios que ya están ocupados.
- Validar los datos recibidos en las solicitudes.
- Manejar errores mediante excepciones personalizadas.
- Responder con códigos de estado HTTP según el resultado de cada operación.

## DTO, validaciones y manejo de errores

Se implementó `TicketRequestDTO` para recibir los datos necesarios al registrar o actualizar un ticket, sin permitir que el cliente envíe directamente el ID generado por el sistema.

El DTO contiene las siguientes validaciones:

- `@NotBlank`: exige que la placa no esté vacía.
- `@Size`: limita la longitud de la placa a 10 caracteres.
- `@Pattern`: permite letras, números, espacios y guiones en la placa.
- `@NotNull`: exige seleccionar un espacio.
- `@Positive`: requiere que el ID del espacio sea mayor que cero.

Las validaciones se ejecutan mediante `@Valid` en las solicitudes POST y PUT del controlador.

También se implementó `RecursoNoEncontradoException` para los recursos inexistentes y `GlobalExceptionHandler`, mediante `@RestControllerAdvice`, para centralizar el manejo de errores.

Entre las respuestas HTTP utilizadas se encuentran:

- `200 OK`: consulta o actualización exitosa.
- `201 Created`: ticket registrado correctamente.
- `204 No Content`: eliminación exitosa.
- `400 Bad Request`: datos que no cumplen las validaciones.
- `404 Not Found`: ticket o espacio inexistente.
- `409 Conflict`: intento de utilizar un espacio ocupado.

## Pruebas

Las operaciones de la API se verifican mediante Postman, incluyendo registros válidos, validaciones, consultas de tickets existentes e inexistentes y actualizaciones con datos inválidos.

## Cómo probar la API

Las operaciones pueden probarse utilizando Postman con la dirección base `http://localhost:8080`.

- **GET** `/api/tickets`: consultar todos los tickets.
- **GET** `/api/tickets/{id}`: consultar un ticket por su ID.
- **POST** `/api/tickets`: registrar un ticket.
- **PUT** `/api/tickets/{id}`: actualizar un ticket existente.
- **DELETE** `/api/tickets/{id}`: eliminar un ticket.

Para las solicitudes POST y PUT, se deben enviar los datos en formato JSON y utilizar el encabezado `Content-Type: application/json`.

Se recomienda probar tanto solicitudes válidas como datos incorrectos para comprobar las validaciones y el manejo de errores.