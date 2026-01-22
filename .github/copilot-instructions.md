<!--
Guidance for AI coding agents working on the `smart-gas-monitoring-system` repo.
Keep this file concise and actionable — reference real files and commands.
-->
# Copilot instructions — smart-gas-monitoring-system

Purpose: Help AI coding agents be immediately productive in this Spring Boot microservice.

- **Big picture**: This is a single Spring Boot service that exposes REST endpoints to ingest and query cylinder/gas sensor readings. The project follows a conventional layered structure under `src/main/java/com/lumitechsystems/smart_gas_monitoring_system`:
  - **`controller/`**: HTTP endpoints (e.g. `CylinderDataController.java`).
  - **`service/`**: Business logic (`CylinderDataService.java` and `RestController.java`).
  - **`repository/`**: Persistence interfaces (check `CylinderDataRepository.java`).
  - **`mapper/`**: DTO ↔ Entity conversions (see `CylinderDataMapper.java`).
  - **`entity/`** and **`dto/`**: Domain models and transport objects (`CylinderData.java`, `CylinderDataDTO.java`).
  - **`exception/`**: Centralized exception handling (`GlobalExceptionHandler.java`).

- **Common data flow to follow when changing the API**:
  1. Update or add a `DTO` in `dto/` (used by controllers).
  2. Update `CylinderDataMapper` to add mapping methods to/from the `Entity`.
  3. Modify `Controller` to accept/return DTOs.
  4. Implement service-layer logic in `service/`.
  5. Persist via interfaces in `repository/`.

- **Key files to inspect when implementing changes**:
  - `src/main/java/.../SmartGasMonitoringSystemApplication.java` — app entry point
  - `src/main/java/.../controller/CylinderDataController.java` — REST examples
  - `src/main/java/.../service/CylinderDataService.java` — business logic
  - `src/main/java/.../mapper/CylinderDataMapper.java` — mapping patterns
  - `src/main/resources/application.properties` — runtime configuration (DB, ports)
  - `Dockerfile` — multi-stage Docker build, exposes port `8080` and runs `app.jar`

- **Build / test / run commands** (use the included Maven wrapper):

```bash
# Run application in dev mode
./mvnw spring-boot:run

# Build (skips tests in Dockerfile build stage)
./mvnw clean package -DskipTests

# Run tests
./mvnw test

# Build docker image (uses multi-stage build in repository root)
docker build -t smart-gas-monitoring-system .
docker run -p 8080:8080 smart-gas-monitoring-system

# Run packaged JAR
java -jar target/*.jar
```

- **Repository & Docker conventions**:
  - The `Dockerfile` uses a Maven build stage that runs `./mvnw dependency:go-offline` and `./mvnw clean package -DskipTests` to produce `app.jar`. Use the wrapper (`./mvnw`) to keep builds reproducible.
  - App listens on port `8080` by default.
  - Java 17 (Temurin) is the target runtime.

- **Patterns & expectations for AI edits**:
  - Keep changes small and focused: when adding a new REST endpoint, add/modify `DTO`, `Mapper`, `Controller`, `Service`, and `Repository` in that order.
  - Follow existing naming: `CylinderData` (entity) vs `CylinderDataDTO` (transport). Reuse `CylinderDataMapper` rather than scattering mapping logic.
  - Centralized exception handling is implemented in `exception/GlobalExceptionHandler.java`. Throw domain exceptions in services and let the handler format responses.
  - Configuration values live in `application.properties` and should not be hard-coded into classes.

- **Commit / PR guidance**:
  - Use concise commit messages prefixed with scope: e.g. `feat(controller): add POST /cylinder-data` or `fix(mapper): handle null timestamps`.
  - When editing behavior that affects persistence or DTO shapes, include a short integration test (under `src/test/java/...`) that verifies the controller → service → repository path.

- **If uncertain**:
  - Inspect `CylinderDataController.java`, `CylinderDataService.java`, and `CylinderDataMapper.java` together to infer intended behavior and constraints.
  - Run the test suite (`./mvnw test`) and the app locally (`./mvnw spring-boot:run`) to validate changes quickly.

If anything in this file looks incomplete or you want other conventions included, ask for specifics and I'll update it.
