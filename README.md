# Anime Catalog – Java Selenium & REST Assured Automation

Maven + Java 17 + TestNG framework using:
- Selenium WebDriver with Page Object Model (POM) foundation
- REST Assured for API testing
- TestNG test execution and assertions
- WebDriverManager for local browser driver setup

**No CI/CD configuration is included.**

## Important notes about the conversion

The supplied ZIP contains Playwright **API tests only**; it does not contain browser UI specs, page objects, frontend URLs, or UI selectors. Therefore:
- The existing API scenarios have been ported to REST Assured.
- Selenium, WebDriver setup, `BasePage`, and a POM package are included as the UI automation foundation.
- No speculative UI tests or locators have been invented. To complete real Selenium page objects, provide the Anime Catalog frontend URL and its page/element details.
- The Swagger URL supplied (`http://localhost:8080/swagger-ui/index.html`) is a local URL. It is documented as the API reference, but cannot be inspected from another machine/session. API routes below are ported from the Playwright API client and tests in your archive.

## Project structure

```text
anime-catalog-java-automation/
├── pom.xml
├── .env.example
├── README.md
└── src
    ├── main/java/com/animecatalog
    │   ├── config/Config.java
    │   ├── api/ApiClient.java
    │   ├── api/AnimeApi.java
    │   ├── api/GenresApi.java
    │   ├── api/AdminUsersApi.java
    │   ├── api/ProfileApi.java
    │   ├── models/AnimeRequest.java
    │   ├── models/GenreRequest.java
    │   ├── models/UserRequest.java
    │   └── ui
    │       ├── DriverFactory.java
    │       └── pages/BasePage.java
    └── test
        ├── java/com/animecatalog
        │   ├── api/AnimeApiTest.java
        │   ├── api/GenresApiTest.java
        │   ├── api/AdminUsersApiTest.java
        │   ├── api/ProfileApiTest.java
        │   └── ui/UiTestBase.java
        └── resources/testng.xml
```

## Prerequisites

- JDK 17+
- Maven 3.8+
- Running Anime Catalog Spring Boot API and its database
- Chrome or Firefox only if you add/run Selenium UI tests

## Configuration

The default API root is `http://localhost:8080`. This is intentionally **not** the Swagger UI path.

PowerShell:

```powershell
$env:API_BASE_URL="http://localhost:8080"
$env:API_ADMIN_USERNAME="superadmin"
$env:API_TIMEOUT_MS="15000"
```

Bash:

```bash
export API_BASE_URL=http://localhost:8080
export API_ADMIN_USERNAME=superadmin
export API_TIMEOUT_MS=15000
```

The API uses the `X-Username` request header for the acting user, matching the original Playwright fixtures. The configured administrator defaults to `superadmin`.

## Run tests

Run the complete TestNG suite (API tests by default):

```bash
mvn clean test
```

Run one API test class:

```bash
mvn -Dtest=AnimeApiTest test
mvn -Dtest=GenresApiTest test
mvn -Dtest=AdminUsersApiTest test
mvn -Dtest=ProfileApiTest test
```

## API routes ported from the supplied Playwright project

| Resource | Method | Route |
|---|---|---|
| Anime | GET, POST | `/api/anime` |
| Anime | PUT, DELETE | `/api/anime/{id}` |
| Genres | GET, POST | `/api/genres` |
| Genres | PUT, DELETE | `/api/genres/{id}` |
| Profile | GET | `/api/me` |
| Profile | PUT | `/api/me/password`, `/api/me/username` |
| Admin users | GET, POST | `/api/admin/users` |
| Admin users | PUT, DELETE | `/api/admin/users/{id}` |

The source project notes that `GET /api/anime/{id}` is not exposed by the backend. No such route is assumed here.

## Selenium POM foundation

`DriverFactory` supports Chrome and Firefox and honors `BROWSER` and `HEADLESS` environment variables. `BasePage` centralizes common Selenium waits and interactions.

The source archive contains no UI tests, so `UiTestBase` and the page-object foundation are intentionally not wired into the default TestNG suite. Once you provide the frontend URL and UI selectors, concrete pages (for example `AnimeListPage`, `AnimeDetailsPage`, and `LoginPage`) and UI tests can be added without mixing browser code into API tests.

## Safety / test data

- Anime, genre, and user CRUD tests create uniquely named records and attempt cleanup in `finally` blocks.
- Do not run mutation tests against production data.
- The original profile password and username mutation operations are not included in the default tests because they can alter the shared administrator account.
- The backend's genre IDs `[22, 27]` from the old anime test are not assumed to exist. The Java anime CRUD test creates a temporary genre first and uses its returned ID.
- The original Playwright README warns that an unfiltered anime list may expose a backend PostgreSQL `lower(bytea)` issue. If that backend defect remains, the list test may fail until the server query is corrected.
