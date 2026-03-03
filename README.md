# 🚀 Credo API Test Automation Framework

A robust, production-grade **API test automation framework** built with **Java 21**, **REST Assured**, **WireMock**, and **TestNG**. Designed for testing RESTful `/users` endpoints with full stub isolation, dynamic data generation, rich Allure reporting, and persistent SQLite test result tracking.

---

## 📋 Table of Contents

- [Tech Stack](#-tech-stack)
- [Test Coverage](#-test-coverage)
- [Prerequisites](#-prerequisites)
- [Setup & Run](#-setup--run)
- [Allure Reports](#-allure-reports)
- [Database Tracking](#-database-tracking)
- [Key Design Decisions](#-key-design-decisions)

---

**Flow:**
1. **`@BeforeSuite`** — WireMock Docker container starts automatically
2. **`@BeforeClass`** — All WireMock stubs are registered (GET `/users` with various query params)
3. **`@Test`** — REST Assured sends HTTP requests → responses are deserialized → AssertJ validates
4. **`@AfterMethod`** — Each test result is persisted to SQLite via MyBatis
5. **`@AfterClass`** — WireMock stubs are reset
6. **`@AfterSuite`** — WireMock Docker container stops

---

## 🛠 Tech Stack

| Category | Technology | Version |
|---|---|---|
| **Language** | Java | 21 |
| **Build Tool** | Maven | 3.x |
| **Test Runner** | TestNG | 7.12.0 |
| **HTTP Client** | REST Assured | 5.5.0 |
| **API Mocking** | WireMock (Docker) | 2.35.0 |
| **Assertions** | AssertJ + Generated Assertions | 3.27.7 |
| **Reporting** | Allure | 2.29.1 |
| **Data Generation** | JavaFaker | 1.0.2 |
| **Serialization** | Jackson Databind | 2.21.1 |
| **ORM / DB** | MyBatis + SQLite | 3.5.19 / 3.51.2 |
| **Boilerplate** | Lombok | 1.18.42 |
| **Logging** | SLF4J + Logback | 2.0.17 / 1.5.32 |
| **AOP** | AspectJ Weaver | 1.9.25.1 |

---

## ✅ Test Coverage

### **18 Total Tests** — 4 Test Classes × 3+ Data Provider Entries Each

| Test Class | Scenario | Endpoint | Status | Count |
|---|---|---|---|---|
| **GetAllUsersTest** | GetAllUsers_200 | `GET /users` | `200` | 3 |
| | GetAllUsers_CheckFirstUser | `GET /users` | `200` | |
| | GetAllUsers_CheckSecondUser | `GET /users` | `200` | |
| **FilterByAgeTest** | FilterByAge_30 | `GET /users?age=30` | `200` | 3 |
| | FilterByAge_25 | `GET /users?age=25` | `200` | |
| | FilterByAge_45 | `GET /users?age=45` | `200` | |
| *(negative)* | InvalidAge_Negative | `GET /users?age=-1` | `400` | 3 |
| | InvalidAge_Zero | `GET /users?age=0` | `400` | |
| | InvalidAge_String | `GET /users?age=abc` | `400` | |
| **FilterByGenderTest** | FilterByGender_male | `GET /users?gender=male` | `200` | 3 |
| | FilterByGender_female | `GET /users?gender=female` | `200` | |
| | FilterByGender_male_second | `GET /users?gender=male` | `200` | |
| *(negative)* | InvalidGender_unknown | `GET /users?gender=unknown` | `422` | 3 |
| | InvalidGender_empty | `GET /users?gender=` | `422` | |
| | InvalidGender_numeric | `GET /users?gender=123` | `422` | |
| **ServerErrorTest** | InternalServerError_500 | `GET /users` | `500` | 3 |
| | InternalServerError_500_CheckMessage | `GET /users` | `500` | |
| | InternalServerError_500_CheckBody | `GET /users` | `500` | |

---

## 📌 Prerequisites

- **Java 21+**
- **Maven 3.8+**
- **Docker** (WireMock runs as a Docker container on port `8080`)

---

## ⚡ Setup & Run

### 1. Clone the repository

```bash
git clone <repository-url>
cd credoAPI
```

### 2. Run all tests

```bash
mvn clean test
```

This will automatically:
- Start a WireMock Docker container
- Register all API stubs
- Execute 18 tests across 4 test classes
- Save results to `test-results.db`
- Stop the WireMock container

### 3. Run from IDE

Open `TestNG.xml` in IntelliJ IDEA and click **Run**. Make sure Docker is running.

---

## 📊 Allure Reports

### Generate & open the report

```bash
mvn allure:serve
```

### What you'll see in Allure:

- **📝 Step-by-step execution** — Every API call, serialization, and deserialization is annotated with `@Step`
- **🌐 HTTP Request/Response** — Full request headers, body, and response captured via `allure-rest-assured`
- **✅ AssertJ assertions** — Integrated via `allure-assertj` for detailed assertion diffs
- **🏷 Features & Severity** — Tests are tagged with `@Feature` and `@Severity` for filtering

---

## 🗄 Database Tracking

Every test result is automatically persisted to **SQLite** (`test-results.db`) after each test method:

| Column | Type | Description |
|---|---|---|
| `id` | `INTEGER` | Auto-increment primary key |
| `test_name` | `TEXT` | Unique test identifier (method + scenario) |
| `status` | `TEXT` | `PASSED` or `FAILED` |
| `execution_time` | `DATETIME` | Timestamp of execution (`yyyy-MM-dd HH:mm:ss`) |

- New tests are **inserted**
- Re-runs **update** the existing row (upsert by `test_name`)

---

## 🧠 Key Design Decisions

### 🔹 WireMock in Docker
No external API dependency. WireMock runs locally as a Docker container — tests are **fully isolated** and **repeatable**.

### 🔹 Nested Constants (`ApiConstants`)
All configuration is centralized in `ApiConstants` using **inner static classes** for clean namespacing:
```java
ApiConstants.WireMock.BASE_URL
ApiConstants.Api.USERS_PATH
ApiConstants.Params.AGE
ApiConstants.Values.AGE_30
ApiConstants.Errors.ERROR_BAD_REQUEST
ApiConstants.Database.DB_URL
```

### 🔹 Factory Pattern for Test Data
`UserFactory` and `ErrorResponseFactory` create test data using **JavaFaker** for random fields and **fixed values** where consistency matters (e.g., age must match the filter parameter).

### 🔹 Auto-Generated AssertJ Assertions
The `assertj-assertions-generator-maven-plugin` generates **custom assertion classes** for `User` and `ErrorResponse` models at build time — enabling fluent checks like:
```java
assertThat(user).hasName("John").hasAge(30).hasGender("male");
assertThat(error).hasError("Bad Request").hasMessage("Invalid age parameter");
```

### 🔹 Data-Driven Testing
Every test uses **TestNG `@DataProvider`** with **3+ entries per provider**, covering positive cases, boundary values, and invalid inputs.

### 🔹 Allure Integration
Full traceability with `@Step`, `@Feature`, `@Description`, `@Severity`, plus automatic HTTP logging via `AllureRestAssured` filter.

---

## 📜 License

This project is for internal test automation purposes at **Credo**.
