# Selenium + REST Assured + Cucumber + Excel Automation Framework

## Overview

Hybrid automation framework for **UI + API testing** using:

- Selenium WebDriver
- REST Assured
- Cucumber BDD
- Java
- JUnit 5
- Excel test data
- Page Object Model
- Environment-based execution
- Cross-browser execution
- Allure reporting

---

## Project Architecture

```text
Framework_2026
│
├── src/main/java
│   ├── api/                 # REST Assured API layer
│   ├── context/             # Scenario/test data sharing
│   ├── driver/              # WebDriver / browser management
│   ├── pages/               # Selenium Page Objects
│   ├── testDataReader/      # Excel reader & feature generation
│   └── utility/             # Common utilities
│
├── src/test/java
│   ├── hooks/               # Before/After scenario handling
│   ├── runners/             # Cucumber/JUnit runner
│   └── stepDefinitions/     # Gherkin step implementations
│
├── src/test/resources
│   ├── env/                 # QA/UAT configuration
│   ├── features/            # Cucumber feature files
│   ├── featureTemplates/    # Feature templates
│   └── testData/            # Excel/API test data
│
├── screenshots/             # Failure screenshots
├── logger/                  # Logs
├── target/
├── pom.xml
└── allure.properties
```

---

# Architecture Flow

```text
                         Maven Command
                              |
                              v
                    Environment + Browser
                              |
                              v
                     Cucumber / JUnit
                              |
                              v
                       Feature Files
                              |
                              v
                      Step Definitions
                         /          \
                        /            \
                       v              v
                  UI Flow          API Flow
                     |                 |
                     v                 v
                Page Objects       API Layer
                     |                 |
                     v                 v
                 Selenium          REST Assured
                     |                 |
                     v                 v
                 Web App              API
                     \                 /
                      \               /
                       v             v
                         Assertions
                              |
                              v
                         Hooks
                              |
                              v
                       Allure Results
                              |
                              v
                       Allure Report
```

---

# Execution Flow

When you run:

```bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

the execution is:

```text
1. Maven starts
       |
2. clean removes previous target/
       |
3. Test phase starts
       |
4. env parameter is read
       |
5. browser parameter is read
       |
6. Environment configuration loaded
       |
7. Cucumber/JUnit runner starts
       |
8. Feature files are discovered
       |
9. @Before Hook executes
       |
10. Scenario executes
       |
       +---- UI --> Step Definition --> Page Object --> Selenium
       |
       +---- API --> Step Definition --> API Layer --> REST Assured
       |
11. Assertions execute
       |
12. @After Hook executes
       |
       +---- Pass --> Cleanup
       |
       +---- Fail --> Screenshot + Allure attachment
       |
13. Allure results generated
       |
       v
target/allure-results/
```

---

# UI Automation Flow

```text
Feature
   ↓
Step Definition
   ↓
Page Object
   ↓
WebDriver
   ↓
Browser
   ↓
Application
   ↓
Validation
```

Page Objects contain locators and UI actions. Step definitions should mainly orchestrate the test flow.

---

# API Automation Flow

```text
Feature
   ↓
Step Definition
   ↓
API Client / Service
   ↓
REST Assured
   ↓
REST API
   ↓
Response
   ↓
Assertions
```

API functionality should remain reusable and separate from Cucumber step definitions.

---

# Excel Data-Driven Flow

```text
OrderTestData.xlsx
        ↓
ExcelDataReader
        ↓
OrderTestData
        ↓
FeatureFileGenerator / Test Logic
        ↓
Cucumber Scenario
        ↓
UI / API Execution
```

Current UI test data:

```text
src/test/resources/testData/ui/OrderTestData.xlsx
```

---

# Environment & Browser Execution

Environment is passed using:

```text
-Denv=<environment>
```

Browser is passed using:

```text
-Dbrowser=<browser>
```

The same test code can therefore run against different combinations:

```text
QA   + Chrome
QA   + Firefox
UAT  + Edge
```

---

# Useful Commands

## QA + Chrome

```bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

## UAT + Edge

```bash
mvn clean test -Denv=uat -Dbrowser=edge
```

## QA + Firefox

```bash
mvn clean test -Denv=qa -Dbrowser=firefox
```

---

# Allure Reports

## Generate HTML report

Run after test execution:

```bash
allure generate target/allure-results -o target/allure-report --clean
```

Report location:

```text
target/allure-report/
```

## Open report directly

```bash
allure serve target/allure-results
```

---

# Complete Execution Example

```bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

Then:

```bash
allure generate target/allure-results -o target/allure-report --clean
```

Or directly:

```bash
allure serve target/allure-results
```

---

# Failure Handling

```text
Test Failure
     ↓
@After Hook
     ↓
Capture Screenshot
     ↓
Attach to Allure
     ↓
Driver Cleanup
```

Screenshots are maintained under:

```text
screenshots/
```

---

# Framework Responsibilities

| Layer | Responsibility |
|---|---|
| Feature | Business-readable scenarios |
| Step Definition | Connects Gherkin to code |
| Pages | UI interaction |
| API | REST API interaction |
| Driver | Browser management |
| Context | Shared scenario data |
| Excel Reader | Test-data handling |
| Hooks | Setup/cleanup/failure handling |
| Runner | Cucumber/JUnit execution |
| Env | Environment configuration |
| Allure | Test reporting |

---

# Quick Reference

```text
Features:
src/test/resources/features/

Step Definitions:
src/test/java/stepDefinitions/

Page Objects:
src/main/java/pages/

API:
src/main/java/api/

Excel Data:
src/test/resources/testData/

Environment:
src/test/resources/env/

Screenshots:
screenshots/

Allure Results:
target/allure-results/

Allure Report:
target/allure-report/
```

## Framework Execution Summary

```text
Maven
  ↓
Environment + Browser
  ↓
Cucumber / JUnit
  ↓
Feature
  ↓
Step Definition
  ↓
 ┌───────────────┐
 │               │
UI              API
↓                ↓
Page Object     REST Assured
↓                ↓
Selenium         API
 └───────┬───────┘
         ↓
     Assertions
         ↓
       Hooks
         ↓
 Allure Results
         ↓
   Allure Report
```

the high-level execution flow is:

``` text
Developer executes Maven command
             |
             v
        Maven starts
             |
             v
        clean phase
             |
             v
       target removed
             |
             v
        test phase
             |
             v
Read -Denv and -Dbrowser
             |
             v
Load environment configuration
             |
             v
Initialize Cucumber/JUnit
             |
             v
Discover feature files
             |
             v
Execute scenarios
             |
             v
@Before Hook
             |
             v
Step Definitions
      /              \
     v                v
   UI Flow          API Flow
     |                |
     v                v
Page Objects      REST Assured
     |                |
     v                v
 Selenium           API
     |                |
     +-------+--------+
             |
             v
       Assertions
             |
             v
        @After Hook
             |
       +-----+------+
       |            |
     Pass         Fail
       |            |
       |      Screenshot
       |      + Allure
       |       attachment
       |            |
       +-----+------+
             |
             v
     Allure Results
             |
             v
       target/allure-results
```
---------------------------------------------------------------------
Typical End-to-End Execution Architecture

The overall framework can be visualized as:

``` text
                         +----------------------+
                         |      Maven Command   |
                         | env + browser       |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         |    Test Runner       |
                         | Cucumber + JUnit     |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         |   Feature Files      |
                         | .feature             |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         |   Step Definitions   |
                         +----+------------+----+
                              |            |
                    UI Flow   |            |   API Flow
                              |            |
                              v            v
                    +---------+--+    +----+---------+
                    | Page Object |    | API Client   |
                    +------+------+    +------+--------+
                           |                  |
                           v                  v
                    +------+-----+      +-----+------+
                    | Selenium   |      | REST Assured|
                    +------+-----+      +-----+------+
                           |                  |
                           v                  v
                    +------+-----+      +-----+------+
                    | Web App    |      | REST API    |
                    +------------+      +-------------+
                              \            /
                               \          /
                                v        v
                              Assertions
                                   |
                                   v
                              Cucumber/JUnit
                                   |
                                   v
                               Allure Results
                                   |
                                   v
                         target/allure-results
                                   |
                     +-------------+-------------+
                     |                           |
                     v                           v
              allure generate              allure serve
                     |                           |
                     v                           v
          target/allure-report             Browser Report
```

------------------------------------------------------------------------