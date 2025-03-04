# Customer Management System

## Description
This project consists of developing a RESTful service for efficient customer management. The company faces difficulties in adding, updating, deleting, and querying customer information quickly and securely. The proposed solution enables CRUD operations on the customer list through a uniform and easy-to-use interface.

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
3. **Run the application:**
   ```sh
   mvn quarkus:dev
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

