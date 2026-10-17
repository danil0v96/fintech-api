# API Fintech

API REST desarrollada con Java y Spring Boot para administrar usuarios y cuentas fintech.

El proyecto permite registrar personas físicas y jurídicas, crear cuentas asociadas a esos usuarios, consultar la información almacenada y realizar borrados lógicos o físicos de cuentas.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven Wrapper
- JUnit 5 y Mockito
- Swagger / OpenAPI

## Modelo del dominio

### Usuarios

`User` es una clase abstracta que contiene los datos comunes de los usuarios. Tiene dos implementaciones:

- `IndividualUser`: representa una persona física y contiene nombre, apellido y documento.
- `LegalEntityUser`: representa una persona jurídica y contiene razón social y CUIT.

La herencia se persiste mediante la estrategia `SINGLE_TABLE`. Todos los usuarios se guardan en la tabla `users` y la columna `user_type` permite distinguir entre `INDIVIDUAL` y `LEGAL_ENTITY`.

### Cuentas

Cada `Account` se relaciona con un `User`. Una cuenta se crea con:

- Saldo inicial igual a cero.
- Estado inicial `ACTIVE`.
- Un CBU y un alias únicos.

El borrado lógico conserva la cuenta y cambia su estado a `DEACTIVATED`. El borrado físico elimina definitivamente el registro.

## Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 21
- MySQL Server
- MySQL Workbench, opcional pero recomendado
- IntelliJ IDEA u otro IDE compatible con Maven

No es necesario instalar Maven porque el proyecto incluye Maven Wrapper.

## Configuración de MySQL

Crear la base de datos desde MySQL Workbench:

```sql
CREATE DATABASE fintech_api;
```

La conexión se encuentra configurada en `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fintech_api
    username: root
    password: ${DB_PASSWORD}
```

La contraseña no se guarda en el código. Debe configurarse mediante la variable de entorno `DB_PASSWORD`.

En macOS o Linux:

```bash
export DB_PASSWORD="tu_contraseña"
```

En PowerShell:

```powershell
$env:DB_PASSWORD="tu_contraseña"
```

En IntelliJ IDEA también puede agregarse desde **Run > Edit Configurations > Environment variables**.

Hibernate está configurado con `ddl-auto: update`, por lo que crea o actualiza las tablas al iniciar la aplicación.

## Ejecución

Desde la carpeta raíz del proyecto, ejecutar:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
mvnw.cmd spring-boot:run
```

También puede ejecutarse la clase `FintechApiApplication` desde IntelliJ IDEA.

La aplicación se inicia en:

```text
http://localhost:8080
```

## Documentación Swagger

Con la aplicación ejecutándose, la documentación interactiva se encuentra disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

## Endpoints

### Usuarios

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/usuarios` | Lista todos los usuarios |
| `GET` | `/usuarios?tipo=INDIVIDUAL` | Lista las personas físicas |
| `GET` | `/usuarios?tipo=LEGAL_ENTITY` | Lista las personas jurídicas |
| `GET` | `/usuarios/{id}` | Busca un usuario por ID |
| `POST` | `/usuarios/personas-fisicas` | Crea una persona física |
| `POST` | `/usuarios/personas-juridicas` | Crea una persona jurídica |

Ejemplo de persona física:

```json
{
  "name": "Ana",
  "lastName": "Pérez",
  "email": "ana@mail.com",
  "document": "30111222"
}
```

Ejemplo de persona jurídica:

```json
{
  "businessName": "Fintech SA",
  "email": "contacto@fintech.com",
  "cuit": "30712345678"
}
```

### Cuentas

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/cuentas` | Lista todas las cuentas |
| `GET` | `/cuentas/{id}` | Busca una cuenta por ID |
| `POST` | `/cuentas` | Crea una cuenta asociada a un usuario |
| `DELETE` | `/cuentas/{id}` | Realiza el borrado lógico y cambia el estado a `DEACTIVATED` |
| `DELETE` | `/cuentas/{id}/fisico` | Elimina definitivamente una cuenta |

Ejemplo de creación de cuenta:

```json
{
  "cbu": "1234567890123456789012",
  "alias": "ana.fintech",
  "userId": 1
}
```

## Manejo de errores

El proyecto incluye excepciones personalizadas para los casos en que no se encuentra un usuario o una cuenta:

- `UserNotFoundException`
- `AccountNotFoundException`

Los controladores responden con `404 Not Found` cuando el recurso solicitado no existe. Los DTO utilizan validaciones para rechazar datos incorrectos con `400 Bad Request`.

## Colección de Postman

La colección lista para importar se encuentra en:

```text
postman/Fintech API.postman_collection.json
```

Incluye todos los endpoints de usuarios y cuentas. Utiliza la variable de colección `baseUrl`, configurada con el valor `http://localhost:8080`.

## Tests

Los servicios se prueban de manera unitaria utilizando JUnit 5 y Mockito.

Para ejecutar los tests:

```bash
./mvnw test
```

Los tests cubren, entre otros casos:

- Consulta y creación de usuarios.
- Personas físicas y jurídicas.
- Filtrado de usuarios por tipo.
- Consulta y creación de cuentas.
- Usuario o cuenta inexistente.
- Borrado lógico de una cuenta.
- Borrado físico de una cuenta.

## Estructura del proyecto

```text
src/main/java/com/diplomatura/fintech
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
└── service
```

La aplicación está organizada por capas para separar las responsabilidades de presentación, lógica de negocio y persistencia.
