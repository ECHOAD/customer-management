# Customer Management System

## Available languages
[Spanish](./README-es.md)

## Description
This project consists of developing a RESTful service for efficient customer management. The company faces difficulties in adding, updating, deleting, and querying customer information quickly and securely. The proposed solution enables CRUD operations on the customer list through a uniform and easy-to-use interface.


### Steps to Run the Project
1. **Clone the repository:**
   ```sh
   git clone https://github.com/user/customer-management.git
   cd customer-management
   ```
2. **Build the project:**
   ```sh
   mvn clean install
   ```
3. Set your environment variables:
   ```sh
   export QUARKUS_PROFILE=dev
   export DATABASE_URL=jdbc:postgresql://localhost:5432/customer_management
   export DATABASE_USER=postgres....
   ```
4. **Run the application:**
   ```sh
   mvn quarkus:dev
   ```

### or

1. **Clone the repository:**
   ```sh
   git clone https://github.com/user/customer-management.git
   cd customer-management
   ```
2. **Make sure you have Docker installed on your system.**
3. **Run the deployment script:**
   ```sh
   ./deploy.sh [dev|stg]
   ```

   where [dev|stg] is the environment you want to deploy to.
4. **Access the application:**
   Open your browser and navigate to http://localhost:8080/customers
5. **Stop the application:**
   ```sh
   docker-compose down
   ```

## API Endpoints
The API exposes the following endpoints for customer management:

| Method  | Endpoint                         | Description |
|---------|----------------------------------|-------------|
| POST    | `/customers`                     | Creates a new customer. |
| GET     | `/customers`                     | Retrieves all customers. |
| GET     | `/customers?country={country}`   | Retrieves customers by country. |
| GET     | `/customers/{id}`                | Retrieves a customer by ID. |
| PUT     | `/customers/{id}`                | Updates an existing customer. |
| DELETE  | `/customers/{id}`                | Deletes a customer by ID. |


## Project Architecture and Design Decisions

This project adheres to Clean Architecture principles, ensuring a clear separation of concerns across application layers. Key design decisions and diagrams are provided to visualize project structure, dependencies, and component interactions.

![Clean Architecture Design](https://kroki.io/mermaid/svg/eNpdkEtuwyAQhvc5xRwgUW9QycHOq42aptmhLKgzcZBsoAyoscThi8FJm7KAAf7_m0djhbnAoZxAXAVfK4f2LGqkI8xmz6G6Gk1IUOzWUygPbzSF6lqjcVKrGG-FMWgpwJwXxrSyFsPHMcP-A2CPpL2N7AAsZjpbQc762nmL2TJPFqaVE1IRzD1JhUTwqhtZByh5qbv481e8EurUDvzf9LBo9XeAim_xJIXTNuvLpL8zK-Wkk0MtC848Od3hg_AGLoUTUNSx6ihd3qV7jH3JCO-ziY3dxvEp0cJHTw47gmGejU1lBVhxpr1ytmetROWycZmMuzhFSS5lC7Dmw_kpaJxL9VAR010XQ4K4wbtHm7rY8Nv70_iWvZvk3eOXx8hPDKmaAC83fcba5OrHy3HyA2Gws8I)



1. **Application Layer (application)**
   - **Responsibility:** 
     - Orchestrates business logic.
     - Contains commands and queries, decoupled from data persistence details.
   - **Components:**
     - **Commands:** Write operations (e.g., CreateCustomerCommand, UpdateCustomerCommand) with handlers for execution.
     - **Queries:** Read operations (e.g., GetCustomerQuery) with handlers to return data.
     - **Interaction:** Interfaces with infrastructure services to complete operations without direct database knowledge.

2. **Domain Layer (domain)**
   - **Responsibility:** 
     - Focuses on the business model, defining business objects and rules.
   - **Components:**
     - **Entities:** Core business concepts (e.g., Customer).
     - **Business Logic:** Validations and entity-specific behaviors.

3. **Interface Layer (interfaces)**
   - **Responsibility:** 
     - Exposes external interfaces for application interaction.
   - **Components:**
     - **API REST:** API resources mapping HTTP requests to application operations (e.g., CustomerResource).
     - **DTOs:** Data Transfer Objects for inter-layer communication.
     - **Exceptions:** Domain-specific exceptions for consistent error communication (e.g., CustomerCreationException).

4. **Infrastructure Layer (infrastructure)**
   - **Responsibility:** 
     - Contains implementation details and external resource dependencies.
   - **Components:**
     - **Repositories:** Data persistence interface implementations (e.g., CustomerPanacheRepository).
     - **External Services:** Third-party API integrations (e.g., CountryService).
     - **Exception Handling:** Manages infrastructure-related exceptions.

5. **Mediator Layer (mediator)**
   - **Responsibility:** 
     - Facilitates command/query communication without direct coupling.
   - **Components:**
     - **Mediator:** Orchestrates command/query execution for decoupled system communication.

6. **Filters Layer (filters)**
   - **Responsibility:** 
     - Manages cross-cutting concerns like global exception handling and HTTP response manipulation.
   - **Components:**
     - **Filters:** Centralized management of logging and error handling (e.g., CustomerExceptionMapper, LoggingFilter).


## Component Overview
![Component Overview](https://kroki.io/mermaid/svg/eNpNjjEOwyAMRfecggvkCpFIgKhSu3S1MliUtEhpQA6RevwSzICn5y____0mjB9xf3Yij4TbnhytaN2xFGUEGePmLSYfdpYmUOGLvm4qW1bCI9Fp00mOVQ3GbzmpphjQP-vilVGVGR7u5TEFWjquFn0_iJFLC08Nz2wqrBrW_ENh0zB7TeOV9f4PL7U5rg)

## Flow of a request
![Flow of a request](https://kroki.io/mermaid/svg/eNqFkDFuwzAMRfeeghcosmvI4gzJ1tq5AOt8tARkSaWoFLl9FTsGEhtpNUiC-D_5nzK-C0KPnfCn8vBCdSVWk14SB6PGC4Ktn0u2OEBb5Fi0x1NBBz3LH_UWKWaxqJeVZMfGH5wn77hNYV632-V4R_vj8Y3aK0ye0i4ld65bJkcNe0-cksakwgZq4jBwOG3eC_RC-3r10Id2N-9DhpnA0SEYlHujH7EvWrCtDbXLDOmq2lRwxqarJYz0_06e6au3aKhHLv45_vh7jjqE01WaYqif-wsJbbUh)


## Features
- **Complete customer management**: Allows creation, updating, deletion, and retrieval of customers.
- **Data validation**: Implements validation mechanisms to ensure data integrity and reliability.
- **API documentation**: Uses OpenAPI and Swagger UI for automatic documentation.
- **Efficient persistence**: Employs Hibernate ORM with Panache to simplify interaction with the PostgreSQL database.

## Functional Requirements
1. **Create a new customer.**
2. **Retrieve all existing customers.**
3. **Retrieve all customers belonging to a specific country.**
4. **Retrieve a specific customer by their ID.**
5. **Update an existing customer**, allowing modifications to their email, address, phone, and country.
6. **Delete a customer by their ID.**

## Technologies Used
- **Quarkus**: Framework for Java application development.
- **Lombok** ([Guide](https://projectlombok.org/)): Generates repetitive code automatically.
- **Hibernate Validator** ([Guide](https://quarkus.io/guides/validation)): Validates object properties and method parameters.
- **SmallRye OpenAPI** ([Guide](https://quarkus.io/guides/openapi-swaggerui)): Documents REST APIs using OpenAPI and Swagger UI.
- **REST Jackson** ([Guide](https://quarkus.io/guides/rest#json-serialisation)): JSON serialization support for Quarkus REST.
- **Hibernate ORM with Panache** ([Guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplifies persistence in Hibernate ORM.
- **JDBC Driver - PostgreSQL** ([Guide](https://quarkus.io/guides/datasource)): JDBC connector for PostgreSQL databases.

## Installation & Configuration
### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker (optional, for local PostgreSQL database)


## API Documentation
The API documentation is available in Swagger UI when the application is running:
```
http://localhost:8080/q/swagger-ui/
```

## Contributions
Contributions are welcome. To contribute, follow these steps:
1. Fork the repository.
2. Create a new branch (`feature/new-feature`).
3. Make your changes and commit them.
4. Submit a pull request.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

