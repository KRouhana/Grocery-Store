# GroceryStore Application Overview

This document provides a high-level summary of various aspects of the GroceryStore application, including its architecture, domain model, persistence layer, business logic, GUI, build configuration, user roles, assignment scheduling, reviews, testing strategy, equipment management, data validation, and configuration details.

---

## 1. Overall Project Structure & Architecture
- **Layered Design**: The application is organized into **Controller**, **Service**, **Model**, and **DAO** layers, promoting separation of concerns.
- **Entities** (e.g., `Customer.java`) reside in the Model layer, **business logic** (e.g., `createCustomer(...)`) in the Service layer, **database operations** in DAO repositories (e.g., `CustomerRepository.java`), and **request handling** in Controllers (e.g., `CustomerController.java`).
- **Flow**: A client request hits the Controller → Service applies business rules → DAO (repository) performs CRUD → Controller returns the response.

---

## 2. Database & Persistence Layer
- **PostgreSQL** is configured via `application.properties`, with JPA/Hibernate settings like `spring.jpa.hibernate.ddl-auto=update`.
- **DAO Interfaces** (`OrderRepository.java`, `ShoppableItemRepository.java`, etc.) extend `CrudRepository` or `JpaRepository`, mapping directly to JPA entities (e.g., `Order.java`, `ShoppableItem.java`).
- **Services** use these repositories for data access, isolating persistence details from business logic.

---

## 3. Gradle Build & Dependencies
- **Gradle Wrapper** ensures a consistent Gradle version across environments (`gradle-wrapper.properties`).
- **Root `build.gradle`**: May define tasks like `stage`, helpful for deployment (e.g., Heroku).
- **Spring Boot Plugins** and dependencies (e.g., `spring-boot-starter-web`, `spring-boot-starter-data-jpa`) facilitate web services and JPA. Testing libraries (`spring-boot-starter-test`, JUnit, Mockito) support robust test suites.
- **Procfile** (for Heroku) references the built JAR and is invoked via Gradle tasks.

---

## 4. Spring Boot Configuration & Entry Point
- **`GroceryStoreBackendApplication.java`**:
  - Annotated with `@SpringBootApplication`, combining auto-configuration, component scanning, and configuration settings.
  - The `main` method calls `SpringApplication.run(...)`, launching the Spring context.
  - **Component Scanning** automatically detects and registers Controllers, Services, and Repositories within the base package.

---

## 5. Domain Model Details
- **Entity Classes**: `@Entity` annotations map classes like `Customer`, `Order`, `ShoppableItem` to database tables.
- **Relationships**:
  - **One-to-Many** examples: A `Customer` can have multiple `Order`s.
  - **Many-to-Many** examples: An `Employee` might have multiple `DailySchedule`s, and vice versa.
- **Inheritance**: `Person.java` can be a base class for `Customer`, `Employee`, `Owner`, sharing common fields like `name`, `email`, etc.

---

## 6. Controllers & REST Endpoints
- **RESTful Mappings**: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` define endpoints (e.g., `/create_order`, `/delete_order`).
- **Request/Response Handling**:
  - `@RequestBody` or `@RequestParam` parse input;
  - `@ResponseBody` / `ResponseEntity` return data.
- **Delegation**: Controllers pass incoming data to Services for logic (e.g., placing an order) and return results (often as DTOs).

---

## 7. Services & Business Logic
- **Core Logic**: Classes like `CustomerService.java` or `OrderService.java` contain methods (`createCustomer(...)`, `placeOrder(...)`).
- **Validations & Rules**: For instance, ensuring a new customer doesn’t already exist, or confirming stock availability before creating an order.
- **DAO Interaction**: Services use repositories (DAO layer) for persistence operations, often via `@Autowired`.

---

## 8. Frontend Integration & GUI (Vue.js)
- **Components** (`BrowseItems.vue`, `Login.vue`, `OwnerMenu.vue`) handle user interactions, making API calls (e.g., via Axios) to backend endpoints.
- **Vue Router** (`router/index.js`) configures routes (`/browse`, `/login`, etc.) and can include navigation guards.
- **State Management**: Often uses Vuex for global state (e.g., storing logged-in user info), plus services in `src/services` to encapsulate API calls.

---

## 9. Testing Strategy (Unit & Integration Tests)
- **Unit Tests**: Typically in service and dao test classes, using JUnit + Mockito.
  - **Service tests** mock repositories to isolate logic.
  - **DAO tests** verify CRUD operations with in-memory or test database.
- **Integration Tests**: `@SpringBootTest` loads the full application context, ensuring multiple layers (Controller → Service → DAO → Database) work together.
- **Transactional tests** roll back changes after each run, keeping tests repeatable and isolated.

---

## 10. Security & Login Flow
- **Manual Authentication**: `LoginController` + `LoginService` validate credentials by checking repositories (Customer vs. Employee vs. Owner).
- **Role Determination**: Identifies user type by returned entity instance (Customer, Owner, etc.).
- **No Spring Security** in the provided info; the application uses custom logic for sessions/tokens or direct checks.

---

## 11. Configuration & Environment-Specific Details
- **Spring Profiles**: Possible separate config files (e.g., `application-dev.properties`, `application-test.properties`) for different environments.
- **Procfile**: For Heroku deployment, specifying how to run the app with Gradle.
- **Environment Variables**: Store database credentials (`SPRING_DATASOURCE_URL`, etc.) and port details (`PORT`), allowing flexible runtime settings.

---

## Overall Takeaway
The **GroceryStore** application showcases a well-structured, layered architecture (**Controller → Service → DAO → Model**) backed by **Spring Boot** and **Gradle**. The frontend (**Vue.js**) interacts through **REST endpoints**, and thorough **testing** ensures reliability. Authentication and environment-specific configurations are handled in a flexible, largely custom manner, making the application **scalable** and **maintainable**.




