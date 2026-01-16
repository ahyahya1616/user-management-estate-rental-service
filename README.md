# User Management Service

The **User Management Service** is a core microservice responsible for managing user profiles, and personalized preferences for an AI-powered estate rental platform.

It handles traditional user data alongside specialized features like MetaMask nonce management for decentralized authentication and user-specific parameters for an AI recommendation engine.

## 🚀 Key Features

- **User Profile Management**: CRUD operations for user profiles including identity and contact information.
- **AI Recommendation Preferences**: Stores and manages user-specific data (target rent, preferred property type, location preferences, etc.) used by the recommendation engine.
- **Microservice Ready**: Integrated with Spring Cloud Config and Actuator for centralized configuration and monitoring.

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.4.12
- **Language**: Java 17
- **Persistence**: Spring Data JPA
- **Database**: MySQL (Main) / H2 (In-memory/Test)
- **Mapping**: MapStruct
- **Utilities**: Lombok
- **DevOps**: Docker, Jenkins

## 🌐 Centralized Configuration

This service uses **Spring Cloud Config** for centralized management of its environment-specific settings.

- **Config Server**: [config-service-rental-estate](https://github.com/RealEstate-Rental-Project/config-service-rental-estate)
- **Config Repository**: [config-repo-estate-rental](https://github.com/RealEstate-Rental-Project/config-repo-estate-rental)
- **Configuration Files**:
    - `user-management-service.yml` (Base configuration)
    - `user-management-service-dev.yml` (Development profile)
    - `user-management-service-prod.yml` (Production profile)

## 📂 Project Structure

```text
src/main/java/ma/fstt/usermanagementservice/
├── controllers/   # REST API Endpoints (UserController)
├── services/      # Business Logic (UserService, NonceService)
├── entities/      # JPA Entities (User)
├── dto/           # Data Transfer Objects
├── repositories/  # Spring Data Repositories
├── mapper/        # Object Mapping logic
└── exception/     # Custom Exception Handling
```

## 🔌 API Endpoints

### User Management
- `GET /api/users`: Retrieve all users.
- `GET /api/users/wallet/{wallet}`: Find user by their Web3 wallet address.
- `GET /api/users/id/{id}`: Find user by their ID.
- `POST /api/users`: Create a new user.
- `PATCH /api/users/{id}`: Partially update user profile/preferences.
- `DELETE /api/users/{id}`: Remove a user.

### Web3 Nonce Management
- `POST /api/users/nonce`: Store a new nonce for a wallet.
- `GET /api/users/nonce`: Retrieve a valid nonce for a wallet.
- `DELETE /api/users/nonce`: Remove a nonce after use or expiration.

## ⚙️ How to Run

1. **Pre-requisites**: Ensure you have Java 17 and Maven installed.
2. **Config Server**: Ensure the [Config Server](https://github.com/RealEstate-Rental-Project/config-service-rental-estate) is running.
3. **Build**:
   ```bash
   mvn clean install
   ```
4. **Run with Profiles**:
   - **Development**:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.profiles=dev
     ```
   - **Production**:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.profiles=prod
     ```

---
*Developed as part of the Estate Rental Microservices Ecosystem.*
