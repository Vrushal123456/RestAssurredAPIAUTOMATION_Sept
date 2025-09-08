Book API Test Automation – RestAssured Framework

Author: Vrushal Sagane

This repository provides an API testing framework built with RestAssured + TestNG, designed for testing the Book Store API. The focus is on reusability, maintainability, robust validations, and modern reporting with Allure.

🔧 How to Run the Tests
Prerequisites

Make sure you have the following installed:

Java 17+

Maven 3.8.4+

Git

Any IDE (IntelliJ IDEA, Eclipse, VS Code)

A running instance of the Book Store API (see the API project’s README.md)

Steps to Execute

Clone the repository:

git clone https://github.com/your-username/bookapi-tests.git
cd RestAssuredVrushal


Configure the environment:
Edit config.properties:

env=QA


Run the test suite:

mvn clean test


Generate Allure report:

allure serve allure-results

📊 Reporting

Allure is integrated for modern, interactive test reports.

Log4j2 ensures detailed runtime logging (console + files).

Allure reports are generated in the allure-results/ folder and can be visualized via allure serve.

✅ Validations Covered

The framework supports:

Header & body validation

Status code checks

JSON Schema validation (via ApiClient)

Positive and Negative test scenarios

Centralized assertions using WrappedAssert

🏗 Framework Design
Core Principles

1 Test Class = 1 API (e.g., GetAllBooksTest.java tests Get All Books API).

Request Chaining: login tokens, new users, and other generated data are reused across tests.

Reusable Builders: RequestBuilder supports specs with auth, without auth, with params, etc.

Centralized ApiClient: handles request execution, schema validation, and status checks.

POJOs for request/response serialization & deserialization.

EnvConfigResolver: dynamically loads environment-specific details (QA, DEV, PROD).

Logging

ConsoleColors → improves readability of console logs.

WrappedReportLogger → unified logging across console + report files.

Data-Driven Testing

Implemented with TestNG @DataProvider for multiple input sets.

🔄 Test Strategy
Positive Flow

Health Check

Create User

Login → Retrieve Token

Get All Books

Add New Book

Verify Book in List

Fetch Book by ID

Update Book

Validate Updated Book

Delete Book

Negative Flow

User creation with duplicate email

Login with wrong credentials

Access books with invalid/no token

Search for invalid book ID

Double deletion of same book

Invalid JSON payloads

Expired/incorrect token validations

📂 Project Structure
src/main/java/com/bookapi/
│
├── assertions/
│   └── WrappedAssert.java         # Custom assertions with TestNG + Log4j2
│
├── endPoints/
│   └── EndPoints.java             # API endpoint constants
│
├── logs/
│   ├── ConsoleColors.java         # Colored console output
│   └── WrappedReportLogger.java   # Logging wrapper
│
├── pojo/
│   ├── request/                   # Request POJOs
│   └── response/                  # Response POJOs
│
├── reportBuilder/
│   ├── ApiClient.java             # Executes HTTP calls & validates responses
│   └── RequestBuilder.java        # Common RestAssured request specs
│
└── utils/
    ├── ConfigurationManager.java  # Reads config.properties
    ├── DataGenerator.java         # Generates random data (email, IDs, etc.)
    └── EnvConfigResolver.java     # Handles environment configs

src/main/resources/
├── schemas/                       # JSON schemas for validation
└── log4j2.xml                     # Logging configuration

src/test/java/com/bookapi/testcases/
└── ...                            # Test classes per API

config.properties
pom.xml
testng.xml

⚠️ Known Issues

Invalid JSON (422) cannot be generated using POJOs → workaround: raw string request.

Wrong login credentials return 400 instead of 401.

Invalid token returns 403 instead of 401.

Duplicate Book ID request throws 500.

No User Deletion API → prevents test data cleanup.

API hosted via:

ssh -R 80:127.0.0.1:8000 serveo.net


and temporary URL added in config.properties.

Current GitHub Actions workflow runs on every push, but ideally should only trigger on merges to dev.

⚙️ CI/CD Integration

GitHub Actions pipeline (.yml) is included.

On push, tests execute via Maven.

Allure reports are generated inside allure-results/.

Improvement needed: restrict pipeline triggers to dev branch merges.

🧰 Tools & Libraries

Java 17

Maven 3.8.4+

RestAssured

TestNG

Log4j2

Allure Reporting

GitHub Actions