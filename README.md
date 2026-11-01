# Control de Estacionamiento

## Descripción

Sistema de control de estacionamiento desarrollado con Java y Spring Boot. Permite administrar espacios de estacionamiento y tickets de vehículos, controlar la disponibilidad de los espacios, registrar entradas y salidas y calcular los cobros correspondientes.

El sistema utiliza PostgreSQL para almacenar la información y expone operaciones mediante una API REST.

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

### Administración de espacios

- Consultar todos los espacios de estacionamiento.
- Consultar los espacios disponibles.
- Contar los espacios disponibles.
- Registrar nuevos espacios.
- Evitar el registro de códigos duplicados.
- Controlar la disponibilidad de cada espacio.

### Administración de tickets

- Consultar todos los tickets.
- Buscar tickets por ID.
- Consultar tickets según su estado.
- Contar tickets activos y finalizados.
- Registrar tickets con la placa y el espacio asignado.
- Actualizar tickets activos.
- Finalizar tickets y registrar la hora de salida.
- Calcular el importe a pagar según el tipo de vehículo y el tiempo de permanencia.
- Eliminar tickets mediante eliminación lógica, conservando los registros en la base de datos.

### Reglas de negocio

- No permitir la asignación de espacios ocupados.
- Verificar que el tipo de vehículo coincida con el tipo de espacio.
- No permitir modificar tickets finalizados.
- No permitir eliminar tickets activos.
- Liberar automáticamente el espacio al finalizar un ticket.
- Aplicar tarifas según el tipo de vehículo:
  - **MOTOCICLETA:** Q5.00 por hora.
  - **AUTO:** Q8.00 por hora.
  - **PICKUP:** Q12.00 por hora.
- Calcular el cobro por horas, redondeando las fracciones al siguiente número entero y cobrando como mínimo una hora.

### Indicadores

- Cantidad de espacios disponibles.
- Cantidad de tickets activos.
- Cantidad de tickets finalizados.
- Total recaudado durante el día.

## DTO, validaciones y manejo de errores

Se implementaron cuatro DTO para controlar la información que recibe y devuelve la API:

- **`TicketRequestDTO`:** recibe y valida los datos necesarios para registrar o actualizar tickets.
- **`EspacioRequestDTO`:** recibe y valida los datos necesarios para registrar o actualizar espacios.
- **`TicketResponseDTO`:** estructura la información de los tickets que devuelve la API.
- **`EspacioResponseDTO`:** estructura la información de los espacios que devuelve la API.

El uso de DTO permite controlar los datos enviados y recibidos, evitando exponer directamente las entidades en las operaciones de la API.

Entre las validaciones utilizadas se encuentran:

- **`@NotBlank`:** exige que los campos de texto obligatorios no estén vacíos.
- **`@NotNull`:** exige que se proporcione un valor.
- **`@Size`:** limita la longitud de los datos.
- **`@Pattern`:** restringe los caracteres y los valores permitidos.
- **`@Positive`:** requiere que el ID del espacio sea mayor que cero.
- **`@Valid`:** activa las validaciones definidas en los DTO desde los controladores.

También se implementó **`RecursoNoEncontradoException`** para manejar recursos inexistentes y **`GlobalExceptionHandler`**, mediante `@RestControllerAdvice`, para centralizar el manejo de errores.

Entre las respuestas HTTP utilizadas se encuentran:

- **`200 OK`:** consulta, actualización o finalización exitosa.
- **`201 Created`:** ticket registrado correctamente.
- **`204 No Content`:** eliminación exitosa.
- **`400 Bad Request`:** datos que no cumplen las validaciones.
- **`404 Not Found`:** ticket o espacio inexistente.
- **`409 Conflict`:** operación que contradice las reglas del sistema, como utilizar un espacio ocupado o incompatible.

## Arquitectura del proyecto

El proyecto utiliza una arquitectura por capas para organizar las responsabilidades:

- **Controller:** recibe las solicitudes HTTP y devuelve las respuestas de la API.
- **Service:** contiene la lógica de negocio y las reglas del sistema.
- **Repository:** permite interactuar con la base de datos mediante Spring Data JPA.
- **Entity:** representa las entidades y sus relaciones en la base de datos.
- **DTO:** controla los datos de entrada y salida de la API.
- **Exception:** contiene las excepciones personalizadas y el manejo global de errores.

## Base de datos

Se utiliza **PostgreSQL** para almacenar la información de los espacios y tickets.

Las entidades principales son:

- **`Espacio`:** almacena el código, tipo y disponibilidad de cada espacio.
- **`Ticket`:** almacena la placa, tipo de vehículo, horas de entrada y salida, estado, total y espacio asignado.

Cada ticket mantiene una relación con un espacio mediante una relación **`@ManyToOne`**.

## Pruebas

Las operaciones de la API se verifican mediante **Postman**, incluyendo:

- Registro y consulta de espacios.
- Consulta y conteo de espacios disponibles.
- Registro y consulta de tickets.
- Validación de datos incorrectos.
- Rechazo de espacios ocupados.
- Validación de compatibilidad entre vehículos y espacios.
- Actualización de tickets.
- Finalización y cálculo de tarifas.
- Liberación de espacios.
- Eliminación lógica de tickets.
- Consulta de indicadores y recaudación diaria.

## Cómo probar la API

La dirección base es:

`http://localhost:8080`

### Endpoints de espacios

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/espacios` | Consultar todos los espacios. |
| **GET** | `/api/espacios/disponibles` | Consultar espacios disponibles. |
| **GET** | `/api/espacios/disponibles/total` | Contar espacios disponibles. |
| **POST** | `/api/espacios` | Registrar un espacio. |

### Endpoints de tickets

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/tickets` | Consultar todos los tickets. |
| **GET** | `/api/tickets/{id}` | Consultar un ticket por su ID. |
| **GET** | `/api/tickets/estado/{estado}` | Consultar tickets por estado. |
| **GET** | `/api/tickets/estado/{estado}/total` | Contar tickets por estado. |
| **GET** | `/api/tickets/recaudacion/hoy` | Consultar la recaudación del día. |
| **POST** | `/api/tickets` | Registrar un ticket. |
| **PUT** | `/api/tickets/{id}` | Actualizar un ticket. |
| **PUT** | `/api/tickets/{id}/finalizar` | Finalizar un ticket. |
| **DELETE** | `/api/tickets/{id}` | Eliminar un ticket. |

Para las solicitudes **POST** y **PUT** se deben enviar los datos en formato JSON utilizando el encabezado:

`Content-Type: application/json`

Se recomienda ejecutar solicitudes válidas e inválidas para comprobar el funcionamiento de las validaciones, las reglas de negocio y el manejo de errores.