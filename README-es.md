# Sistema de Gestión de Clientes

## Descripción
Este proyecto consiste en desarrollar un servicio RESTful para la gestión eficiente de clientes. La empresa enfrenta dificultades para agregar, actualizar, eliminar y consultar información de clientes de manera rápida y segura. La solución propuesta permite realizar operaciones CRUD sobre la lista de clientes a través de una interfaz uniforme y fácil de usar.

### Pasos para Ejecutar el Proyecto
1. **Clonar el repositorio:**
   ```sh
   git clone https://github.com/user/customer-management.git
   cd customer-management
   ```
2. **Construir el proyecto:**
   ```sh
   mvn clean install
   ```
3. Establece tus variables de entorno:
   ```sh
   export QUARKUS_PROFILE=dev
   export DATABASE_URL=jdbc:postgresql://localhost:5432/customer_management
   export DATABASE_USER=postgres....
   ```
4. **Ejecutar la aplicación:**
   ```sh
   mvn quarkus:dev
   ```

### o

1. **Clonar el repositorio:**
   ```sh
   git clone https://github.com/user/customer-management.git
   cd customer-management
   ```
2. **Asegúrate de tener Docker instalado en tu sistema.**
3. **Ejecuta el script de despliegue:**
   ```sh
   ./deploy.sh [dev|stg]
   ```
   donde [dev|stg] es el entorno al que deseas desplegar.
4. **Accede a la aplicación:**
   Abre tu navegador y navega a http://localhost:8080/customers
5. **Detener la aplicación:**
   ```sh
   docker-compose down
   ```

## Endpoints de la API
La API expone los siguientes endpoints para la gestión de clientes:

| Método  | Endpoint                         | Descripción |
|---------|----------------------------------|-------------|
| POST    | `/customers`                     | Crea un nuevo cliente. |
| GET     | `/customers`                     | Recupera todos los clientes. |
| GET     | `/customers?country={country}`   | Recupera clientes por país. |
| GET     | `/customers/{id}`                | Recupera un cliente por ID. |
| PUT     | `/customers/{id}`                | Actualiza un cliente existente. |
| DELETE  | `/customers/{id}`                | Elimina un cliente por ID. |

## Arquitectura del Proyecto y Decisiones de Diseño

Este proyecto sigue los principios de la Arquitectura Limpia (Clean Architecture), asegurando una clara separación de responsabilidades entre las capas de la aplicación. Se proporcionan decisiones clave de diseño y diagramas para visualizar la estructura del proyecto, las dependencias y las interacciones entre los componentes.

![Diseño de Arquitectura Limpia](https://kroki.io/mermaid/svg/eNpdkEtuwyAQhvc5xRwgUW9QycHOq42aptmhLKgzcZBsoAyoscThi8FJm7KAAf7_m0djhbnAoZxAXAVfK4f2LGqkI8xmz6G6Gk1IUOzWUygPbzSF6lqjcVKrGG-FMWgpwJwXxrSyFsPHMcP-A2CPpL2N7AAsZjpbQc762nmL2TJPFqaVE1IRzD1JhUTwqhtZByh5qbv481e8EurUDvzf9LBo9XeAim_xJIXTNuvLpL8zK-Wkk0MtC848Od3hg_AGLoUTUNSx6ihd3qV7jH3JCO-ziY3dxvEp0cJHTw47gmGejU1lBVhxpr1ytmetROWycZmMuzhFSS5lC7Dmw_kpaJxL9VAR010XQ4K4wbtHm7rY8Nv70_iWvZvk3eOXx8hPDKmaAC83fcba5OrHy3HyA2Gws8I)

1. **Capa de Aplicación (application)**
    - **Responsabilidad:**
        - Orquesta la lógica de negocio.
        - Contiene comandos y consultas, desacoplados de los detalles de persistencia de datos.
    - **Componentes:**
        - **Comandos:** Operaciones de escritura (por ejemplo, `CreateCustomerCommand`, `UpdateCustomerCommand`) con manejadores para su ejecución.
        - **Consultas:** Operaciones de lectura (por ejemplo, `GetCustomerQuery`) con manejadores para devolver datos.
        - **Interacción:** Se comunica con servicios de infraestructura para completar operaciones sin conocimiento directo de la base de datos.

2. **Capa de Dominio (domain)**
    - **Responsabilidad:**
        - Se enfoca en el modelo de negocio, definiendo objetos y reglas de negocio.
    - **Componentes:**
        - **Entidades:** Conceptos clave del negocio (por ejemplo, `Customer`).
        - **Lógica de Negocio:** Validaciones y comportamientos específicos de las entidades.

3. **Capa de Interfaces (interfaces)**
    - **Responsabilidad:**
        - Expone interfaces externas para la interacción con la aplicación.
    - **Componentes:**
        - **API REST:** Recursos de la API que mapean las solicitudes HTTP a operaciones de la aplicación (por ejemplo, `CustomerResource`).
        - **DTOs:** Objetos de Transferencia de Datos para la comunicación entre capas.
        - **Excepciones:** Excepciones específicas del dominio para una comunicación de errores consistente (por ejemplo, `CustomerCreationException`).

4. **Capa de Infraestructura (infrastructure)**
    - **Responsabilidad:**
        - Contiene detalles de implementación y dependencias de recursos externos.
    - **Componentes:**
        - **Repositorios:** Implementaciones de interfaces de persistencia de datos (por ejemplo, `CustomerPanacheRepository`).
        - **Servicios Externos:** Integraciones con APIs de terceros (por ejemplo, `CountryService`).
        - **Manejo de Excepciones:** Maneja excepciones relacionadas con la infraestructura.

5. **Capa de Mediador (mediator)**
    - **Responsabilidad:**
        - Facilita la comunicación de comandos/consultas sin acoplamiento directo.
    - **Componentes:**
        - **Mediador:** Orquesta la ejecución de comandos/consultas para una comunicación desacoplada del sistema.

6. **Capa de Filtros (filters)**
    - **Responsabilidad:**
        - Gestiona preocupaciones transversales como manejo global de excepciones y manipulación de respuestas HTTP.
    - **Componentes:**
        - **Filtros:** Gestión centralizada de registros y manejo de errores (por ejemplo, `CustomerExceptionMapper`, `LoggingFilter`).

## Visión General de los Componentes
![Visión General de los Componentes](https://kroki.io/mermaid/svg/eNpNjjEOwyAMRfecggvkCpFIgKhSu3S1MliUtEhpQA6RevwSzICn5y____0mjB9xf3Yij4TbnhytaN2xFGUEGePmLSYfdpYmUOGLvm4qW1bCI9Fp00mOVQ3GbzmpphjQP-vilVGVGR7u5TEFWjquFn0_iJFLC08Nz2wqrBrW_ENh0zB7TeOV9f4PL7U5rg)

## Flujo de una solicitud
![Flujo de una solicitud](https://kroki.io/mermaid/svg/eNqFkDFuwzAMRfeeghcosmvI4gzJ1tq5AOt8tARkSaWoFLl9FTsGEhtpNUiC-D_5nzK-C0KPnfCn8vBCdSVWk14SB6PGC4Ktn0u2OEBb5Fi0x1NBBz3LH_UWKWaxqJeVZMfGH5wn77hNYV632-V4R_vj8Y3aK0ye0i4ld65bJkcNe0-cksakwgZq4jBwOG3eC_RC-3r10Id2N-9DhpnA0SEYlHujH7EvWrCtDbXLDOmq2lRwxqarJYz0_06e6au3aKhHLv45_vh7jjqE01WaYqif-wsJbbUh)

## Características
- **Gestión completa de clientes**: Permite crear, actualizar, eliminar y recuperar clientes.
- **Validación de datos**: Implementa mecanismos de validación para garantizar la integridad y fiabilidad de los datos.
- **Documentación de la API**: Utiliza OpenAPI y Swagger UI para la documentación automática.
- **Persistencia eficiente**: Emplea Hibernate ORM con Panache para simplificar la interacción con la base de datos PostgreSQL.

## Requisitos Funcionales
1. **Crear un nuevo cliente.**
2. **Recuperar todos los clientes existentes.**
3. **Recuperar todos los clientes de un país específico.**
4. **Recuperar un cliente específico por su ID.**
5. **Actualizar un cliente existente**, permitiendo modificar su correo electrónico, dirección, teléfono y país.
6. **Eliminar un cliente por su ID.**

## Tecnologías Utilizadas
- **Quarkus**: Framework para el desarrollo de aplicaciones Java.
- **Lombok** ([Guía](https://projectlombok.org/)): Genera código repetitivo automáticamente.
- **Hibernate Validator** ([Guía](https://quarkus.io/guides/validation)): Valida las propiedades de los objetos y los parámetros de los métodos.
- **SmallRye OpenAPI** ([Guía](https://quarkus.io/guides/openapi-swaggerui)): Documenta las APIs REST utilizando OpenAPI y Swagger UI.
- **REST Jackson** ([Guía](https://quarkus.io/guides/rest#json-serialisation)): Soporte de serialización JSON para Quarkus REST.
- **Hibernate ORM con Panache** ([Guía](https://quarkus.io/guides/hibernate-orm-panache)): Simplifica la persistencia en Hibernate ORM.
- **Driver JDBC - PostgreSQL** ([Guía](https://quarkus.io/guides/datasource)): Conector JDBC para bases de datos PostgreSQL.

## Instalación y Configuración
### Requisitos Previos
- Java 17 o superior
- Maven 3.8+
- Docker (opcional, para base de datos PostgreSQL local)

## Documentación de la API
La documentación de la API está disponible en Swagger UI cuando la aplicación esté en ejecución:
```
http://localhost:8080/q/swagger-ui/
```

## Contribuciones
Las contribuciones son bienvenidas. Para contribuir, sigue estos pasos:
1. Haz un fork del repositorio.
2. Crea una nueva rama (`feature/nueva-característica`).
3. Realiza tus cambios y haz commit.
4. Envía un pull request.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.