# Selenium + REST Assured + Cucumber + Excel Driven Automation Framework

## 1. Overview

This project is a hybrid **UI + API automation framework** built using:

-   **Selenium WebDriver** -- UI/browser automation
-   **REST Assured** -- REST API automation
-   **Cucumber** -- BDD feature files and executable specifications
-   **Java** -- Core programming language
-   **JUnit 5 / Cucumber JUnit Platform** -- Test execution
-   **Excel** -- External test-data management
-   **Allure** -- Test execution reporting
-   **Maven** -- Dependency management and build execution
-   **Page Object Model (POM)** -- UI page abstraction
-   **Environment-based execution** -- QA/UAT configuration
-   **Browser-based execution** -- Chrome/Edge/Firefox
-   **Hooks** -- Cucumber setup/teardown and execution lifecycle

The framework is designed to support both **UI and API test automation**
while keeping test scenarios, test data, configuration, page/API logic,
and execution/reporting responsibilities separated.

------------------------------------------------------------------------

## 2. Framework Goals

The framework is designed with the following objectives:

1.  Keep business scenarios readable through Cucumber feature files.
2.  Separate test data from automation code.
3.  Support multiple environments such as QA and UAT.
4.  Support multiple browsers such as Chrome, Edge, and Firefox.
5.  Reuse common Selenium driver and browser functionality.
6.  Reuse page objects instead of writing locators directly in step
    definitions.
7.  Support REST API automation through REST Assured.
8.  Generate execution results that can be viewed using Allure.
9.  Keep API, UI, test-data, configuration, and execution components
    modular.
10. Make the framework suitable for CI/CD execution in the future.

------------------------------------------------------------------------

# 3. Project Structure

The current project structure is approximately:

``` text
Framework_2026
│
├── .idea/
├── .mvn/
├── logger/
├── screenshots/
│
├── src/
│   │
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/
│   │   │   ├── context/
│   │   │   ├── driver/
│   │   │   ├── pages/
│   │   │   ├── testDataReader/
│   │   │   │   ├── ExcelDataReader
│   │   │   │   ├── FeatureFileGenerator
│   │   │   │   └── OrderTestData
│   │   │   └── utility/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       │
│       ├── java/
│       │   ├── hooks/
│       │   │   └── Hooks
│       │   ├── runners/
│       │   │   └── TestRunner
│       │   └── stepDefinitions/
│       │
│       └── resources/
│           ├── env/
│           ├── features/
│           │   ├── api_ui_order.feature
│           │   └── Purchase.feature
│           ├── featureTemplates/
│           │   └── Order_template.feature
│           └── testData/
│               ├── api/
│               └── ui/
│                   └── OrderTestData.xlsx
│
├── allure.properties
├── pom.xml
└── target/
```

------------------------------------------------------------------------

# 4. Responsibilities of Major Packages

## 4.1 `src/main/java/api`

This package contains the API automation implementation.

Typical responsibilities include:

-   API request creation
-   HTTP methods such as GET, POST, PUT, PATCH and DELETE
-   Request headers
-   Authentication
-   Request payload creation
-   Response validation
-   Status-code validation
-   Response-body validation
-   API utility methods
-   Reusable API clients/services

The API layer is kept separate from Cucumber step definitions so that
API operations can be reused across multiple scenarios.

### Example flow

``` text
Cucumber Scenario
       |
       v
Step Definition
       |
       v
API Service / API Client
       |
       v
REST Assured
       |
       v
Application API
       |
       v
Response
       |
       v
Assertions
```

------------------------------------------------------------------------

# 5. `context` Package

The `context` package is intended to maintain data/state that needs to
be shared between different steps or components during a scenario.

For example, an API response, order ID, token, created entity ID, or
other scenario-specific information can be stored in a shared context
and consumed by a later step.

This is particularly useful for hybrid API + UI flows.

### Example hybrid flow

``` text
API creates order
       |
       v
Order ID stored in Context
       |
       v
UI opens application
       |
       v
UI searches/validates order
       |
       v
Order ID retrieved from Context
       |
       v
UI validation
```

The exact objects stored in the context should remain scenario-specific
and should not be used as a global dumping ground.

------------------------------------------------------------------------

# 6. `driver` Package

The `driver` package is responsible for Selenium WebDriver management.

Typical responsibilities:

-   Browser initialization
-   Browser selection
-   Driver lifecycle
-   Driver cleanup
-   Thread-safe driver access where required
-   Browser-specific configuration
-   Navigation/session management

The browser is selected through the Maven property:

``` text
-Dbrowser=chrome
```

or:

``` text
-Dbrowser=edge
```

or:

``` text
-Dbrowser=firefox
```

This allows the same test suite to run against different browsers
without modifying the test code.

------------------------------------------------------------------------

# 7. `pages` Package -- Page Object Model

The `pages` package contains UI Page Object classes.

Each Page Object represents a page or a logical UI component.

A Page Object normally contains:

-   Locators
-   Page-specific actions
-   Page-specific waits
-   Page-specific validations
-   Reusable UI methods

The Cucumber step definition should call Page Object methods instead of
directly interacting with WebDriver.

### Recommended architecture

``` text
Feature File
     |
     v
Step Definition
     |
     v
Page Object
     |
     v
Selenium WebDriver
     |
     v
Web Application
```

### Example

Instead of writing:

``` java
driver.findElement(By.id("email")).sendKeys(username);
```

inside a step definition, prefer:

``` java
loginPage.enterUsername(username);
```

The locator and Selenium interaction remain inside the Page Object.

This makes the framework easier to maintain when UI locators change.

------------------------------------------------------------------------

# 8. `testDataReader` Package

This package handles external test-data processing.

Current components include:

### `ExcelDataReader`

Responsible for reading test data from Excel files.

Example source:

``` text
src/test/resources/testData/ui/OrderTestData.xlsx
```

Keeping test data outside the Java code provides better separation
between:

-   Test logic
-   Test data
-   Feature files

This also allows testers to modify input data without changing the
automation implementation.

------------------------------------------------------------------------

## 8.1 `OrderTestData`

This class can be used as a model/data object for order-related test
data.

A typical pattern is:

``` text
Excel Row
   |
   v
ExcelDataReader
   |
   v
OrderTestData object
   |
   v
Test / Feature Data
   |
   v
Step Definition
```

------------------------------------------------------------------------

## 8.2 `FeatureFileGenerator`

The framework contains a `FeatureFileGenerator` utility.

Its purpose is to support generation of Cucumber feature content from
external test data/templates when required.

The project contains:

``` text
src/test/resources/featureTemplates/Order_template.feature
```

and generated/used feature files under:

``` text
src/test/resources/features/
```

This approach can be useful when a large number of data-driven scenarios
need to be generated from structured test data.

------------------------------------------------------------------------

# 9. `utility` Package

The `utility` package contains common reusable framework utilities.

Typical examples include:

-   Wait utilities
-   Configuration utilities
-   Screenshot utilities
-   Date/time utilities
-   JSON utilities
-   File utilities
-   Common Selenium utilities
-   Common assertion/helper methods

A utility should contain reusable technical functionality rather than
application-specific business logic.

------------------------------------------------------------------------

# 10. Environment Configuration

The framework supports environment-based execution.

Environment files are maintained under:

``` text
src/test/resources/env/
```

The environment is selected using:

``` text
-Denv=<environment>
```

For example:

``` text
-Denv=QA
```

or:

``` text
-Denv=uat
```

The environment configuration should contain values that change between
environments, such as:

-   Application URL
-   API base URL
-   Username
-   Password
-   API configuration
-   Environment-specific settings

### Important

Environment-specific credentials should preferably be managed using
secure CI/CD secrets or environment variables rather than committing
real passwords to source control.

------------------------------------------------------------------------

# 11. Cucumber Feature Files

Feature files are located under:

``` text
src/test/resources/features/
```

Current feature files include:

``` text
api_ui_order.feature
Purchase.feature
```

Feature files contain business-readable scenarios using Gherkin syntax.

Typical structure:

``` gherkin
Feature: Order processing

  Scenario: Create and verify an order
    Given ...
    When ...
    And ...
    Then ...
```

The feature file should describe **what** the user/business wants to
validate.

Implementation details should remain inside step definitions, Page
Objects, API classes, and utilities.

------------------------------------------------------------------------

# 12. Step Definitions

Step definitions are located under:

``` text
src/test/java/stepDefinitions/
```

They connect Gherkin steps to Java implementation.

The recommended responsibility of a step definition is orchestration
rather than low-level automation.

### Preferred flow

``` text
Gherkin Step
     |
     v
Step Definition
     |
     +------> Page Object
     |
     +------> API Client
     |
     +------> Test Data
     |
     +------> Context
```

Avoid putting large amounts of Selenium/API implementation directly
inside step-definition methods.

------------------------------------------------------------------------

# 13. Cucumber Hooks

Hooks are located under:

``` text
src/test/java/hooks/Hooks.java
```

Hooks are executed before and/or after scenarios.

Typical responsibilities include:

### Before scenario

-   Read configuration
-   Initialize WebDriver where required
-   Prepare scenario context
-   Initialize reporting information

### After scenario

-   Capture screenshot on failure
-   Attach screenshot to Allure
-   Close WebDriver
-   Perform cleanup
-   Store final scenario information

A typical lifecycle is:

``` text
@Before
   |
   v
Scenario
   |
   v
@After
   |
   +---- Pass --> Cleanup
   |
   +---- Fail --> Screenshot + Allure attachment + Cleanup
```

------------------------------------------------------------------------

# 14. Test Runner

The runner is located under:

``` text
src/test/java/runners/TestRunner.java
```

The Test Runner integrates:

-   Cucumber
-   JUnit
-   Feature files
-   Glue/step definitions
-   Plugins/reporting

Maven starts the test execution through the Maven Surefire/JUnit
execution mechanism and the Cucumber runner.

------------------------------------------------------------------------

# 15. Test Execution Parameters

The framework accepts two important runtime parameters.

## Environment

``` text
-Denv=QA
```

Possible values in the current setup include:

``` text
QA
uat
```

Use the exact values configured by the project's environment
configuration.

## Browser

``` text
-Dbrowser=chrome
```

Supported browsers in the current execution commands:

``` text
chrome
edge
firefox
```

This gives the framework a flexible execution model:

``` text
Environment + Browser
```

For example:

``` text
QA + Chrome
QA + Firefox
UAT + Edge
```

------------------------------------------------------------------------

# 16. Maven Execution Commands

## 16.1 QA -- Chrome

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

This performs a clean Maven build and executes the test suite against
the QA environment using Chrome.

------------------------------------------------------------------------

## 16.2 UAT -- Edge

``` bash
mvn clean test -Denv=uat -Dbrowser=edge
```

This executes the test suite against the UAT environment using Microsoft
Edge.

------------------------------------------------------------------------

## 16.3 QA -- Firefox

``` bash
mvn clean test -Denv=qa -Dbrowser=firefox
```

This executes the test suite against QA using Firefox.

------------------------------------------------------------------------

# 17. What Happens During `mvn clean test`

When the following command is executed:

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
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

------------------------------------------------------------------------

# 18. Detailed Execution Flow

## Step 1 -- Maven command

The user starts execution:

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

Maven receives:

``` text
env = QA
browser = chrome
```

------------------------------------------------------------------------

## Step 2 -- Clean target directory

The `clean` phase removes previous Maven build output.

This helps prevent old test artifacts from being mixed with the current
execution.

------------------------------------------------------------------------

## Step 3 -- Test phase starts

Maven starts the test lifecycle.

The configured test runner and Cucumber/JUnit integration are loaded.

------------------------------------------------------------------------

## Step 4 -- Environment is selected

The framework reads the value:

``` text
-Denv=QA
```

and loads the corresponding environment configuration.

For example:

``` text
QA
```

can point to the QA application/API URLs and QA-specific configuration.

Changing the command to:

``` text
-Denv=uat
```

switches the execution configuration without requiring source-code
changes.

------------------------------------------------------------------------

# 19. Browser Selection Flow

The browser is controlled using:

``` text
-Dbrowser=chrome
```

The flow is:

``` text
Maven
  |
  v
System Property: browser
  |
  v
Driver Factory / Browser Factory
  |
  +---- chrome  --> ChromeDriver
  |
  +---- edge    --> EdgeDriver
  |
  +---- firefox --> FirefoxDriver
```

This enables the same feature and step-definition code to run on
multiple browsers.

------------------------------------------------------------------------

# 20. API Automation Flow

For API scenarios, the execution generally follows:

``` text
Feature File
     |
     v
API Step Definition
     |
     v
API Client / Service
     |
     v
REST Assured
     |
     v
HTTP Request
     |
     v
Application API
     |
     v
HTTP Response
     |
     v
Response Validation
     |
     v
Cucumber Assertion
     |
     v
Allure Result
```

Typical validations can include:

-   HTTP status code
-   Response body
-   JSON fields
-   Headers
-   Response time where applicable
-   Business validations

------------------------------------------------------------------------

# 21. UI Automation Flow

For UI scenarios:

``` text
Feature File
     |
     v
UI Step Definition
     |
     v
Page Object
     |
     v
WebDriver
     |
     v
Browser
     |
     v
Web Application
     |
     v
UI Validation
     |
     v
Allure Result
```

The Page Object layer keeps Selenium implementation separate from the
BDD layer.

------------------------------------------------------------------------

# 22. Hybrid API + UI Flow

One of the important capabilities of this framework is the ability to
combine API and UI testing.

Example:

``` text
Create order through API
          |
          v
Capture Order ID
          |
          v
Store Order ID in Context
          |
          v
Launch UI
          |
          v
Login
          |
          v
Search Order
          |
          v
Validate Order Details
```

This can reduce unnecessary UI setup and allows API-created data to be
validated through the application UI.

The reverse pattern can also be used:

``` text
Create data through UI
        |
        v
Capture ID
        |
        v
Validate data through API
```

------------------------------------------------------------------------

# 23. Excel Data-Driven Flow

For Excel-driven scenarios, the framework can follow:

``` text
OrderTestData.xlsx
        |
        v
ExcelDataReader
        |
        v
OrderTestData
        |
        v
FeatureFileGenerator / Test Logic
        |
        v
Cucumber Scenario
        |
        v
Step Definition
        |
        v
UI/API Execution
```

This approach allows multiple test-data combinations to be maintained
outside Java code.

Example:

``` text
Excel
------------------------------------------------
Username | Product       | Quantity | Country
------------------------------------------------
user1    | ZARA COAT 3   | 1        | India
user2    | ZARA COAT 3   | 2        | India
```

The actual columns should match the project's Excel reader
implementation.

------------------------------------------------------------------------

# 24. Feature Template Flow

The framework contains:

``` text
src/test/resources/featureTemplates/Order_template.feature
```

A template-based approach can be used when feature scenarios need to be
generated dynamically.

Conceptually:

``` text
Excel Test Data
       |
       v
FeatureFileGenerator
       |
       v
Order_template.feature
       |
       v
Generated Feature
       |
       v
Cucumber
```

This is useful for large data-driven suites where manually maintaining
hundreds of nearly identical Gherkin scenarios would create unnecessary
maintenance.

------------------------------------------------------------------------

# 25. Allure Reporting

Allure is used for test execution reporting.

The framework produces Allure result files under:

``` text
target/allure-results
```

These files contain execution information that Allure uses to build the
report.

------------------------------------------------------------------------

# 26. Generate Allure HTML Report

After test execution, generate the report using:

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

Explanation:

``` text
allure generate
```

Generates an HTML report from Allure result files.

``` text
target/allure-results
```

Source directory containing execution results.

``` text
-o target/allure-report
```

Output directory where the HTML report is generated.

``` text
--clean
```

Removes/cleans the existing generated report before creating the new
report.

The generated report will be available under:

``` text
target/allure-report
```

------------------------------------------------------------------------

# 27. Open Allure Report Directly

You can also use:

``` bash
allure serve target/allure-results
```

This starts a local Allure server and opens the generated report in the
browser.

The temporary report is generated from:

``` text
target/allure-results
```

This is convenient during local development because you do not have to
manually open the generated HTML directory.

------------------------------------------------------------------------

# 28. Recommended Complete Execution

A typical local execution is:

### Step 1 -- Run tests

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

### Step 2 -- Generate report

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

### Step 3 -- Open report

``` bash
allure serve target/allure-results
```

------------------------------------------------------------------------

# 29. Execution Matrix

  Environment   Browser   Command
  ------------- --------- ---------------------------------------------
  QA            Chrome    `mvn clean test -Denv=QA -Dbrowser=chrome`
  UAT           Edge      `mvn clean test -Denv=uat -Dbrowser=edge`
  QA            Firefox   `mvn clean test -Denv=qa -Dbrowser=firefox`

The framework can be extended to additional environments and browsers by
updating the relevant configuration/factory implementation.

------------------------------------------------------------------------

# 30. Failure Handling

When a scenario fails, the framework can capture diagnostic information
through the Cucumber Hooks and reporting integration.

A typical failure flow is:

``` text
Scenario fails
     |
     v
@After Hook
     |
     v
Capture Screenshot
     |
     v
Attach screenshot to Allure
     |
     v
Scenario marked Failed
     |
     v
WebDriver cleanup
```

Screenshots are maintained in the project's:

``` text
screenshots/
```

directory where applicable.

The Allure report can then be used to inspect the failed scenario and
its attachments.

------------------------------------------------------------------------

# 31. Logging

The project contains a:

``` text
logger/
```

directory and uses logging configuration through the framework's logging
setup.

Logging is useful for diagnosing:

-   Environment selection
-   Browser initialization
-   Test execution steps
-   API requests/responses where appropriate
-   Framework errors
-   Driver lifecycle
-   Data-processing failures

Avoid logging sensitive information such as:

-   Passwords
-   Access tokens
-   API keys
-   Personal information

------------------------------------------------------------------------

# 32. Separation of Responsibilities

A major design principle of this framework is separation of concerns.

  Component            Responsibility
  -------------------- ----------------------------------------
  Feature              Business-readable test scenario
  Step Definition      Connects Gherkin to framework actions
  Page Object          UI interaction
  API Client/Service   API interaction
  Driver               Browser lifecycle
  Context              Scenario-level shared data
  Excel Reader         Test-data reading
  Feature Generator    Data/template-based feature generation
  Utility              Common reusable functionality
  Hooks                Test lifecycle/setup/cleanup
  Runner               Test execution configuration
  Environment Config   Environment-specific values
  Allure               Reporting

This separation improves maintainability and allows individual layers to
evolve independently.

------------------------------------------------------------------------

# 33. Why Environment Parameters Are Useful

Without environment parameters, testers might have to modify URLs or
credentials in the source code before every execution.

With:

``` bash
-Denv=QA
```

or:

``` bash
-Denv=uat
```

the same test code can be executed against different environments.

Example:

``` text
Same Test
    |
    +---- QA configuration
    |
    +---- UAT configuration
```

This is especially useful for:

-   Local execution
-   Regression testing
-   Smoke testing
-   CI/CD pipelines
-   Cross-environment validation

------------------------------------------------------------------------

# 34. Why Browser Parameters Are Useful

The command:

``` bash
-Dbrowser=chrome
```

allows browser selection at runtime.

Instead of maintaining separate test implementations for:

``` text
Chrome Tests
Edge Tests
Firefox Tests
```

the same test suite can be reused.

Example:

``` text
Same Feature
     |
     +---- Chrome
     +---- Edge
     +---- Firefox
```

------------------------------------------------------------------------

# 35. Maven Project

The project uses Maven for dependency and build management.

The primary configuration file is:

``` text
pom.xml
```

The POM is responsible for managing dependencies/plugins required by the
framework.

Typical technology dependencies include:

-   Selenium
-   Cucumber
-   JUnit
-   REST Assured
-   Apache POI or equivalent Excel library
-   Allure
-   Logging framework
-   Maven plugins

The exact versions should be taken from the current `pom.xml`.

------------------------------------------------------------------------

# 36. Typical End-to-End Execution Architecture

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

# 37. Typical Developer Workflow

## Clone/checkout the project

After obtaining the project source code, open it as a Maven project in
IntelliJ IDEA or another Java IDE.

## Verify configuration

Check:

``` text
src/test/resources/env/
```

and confirm the required QA/UAT configuration exists.

## Verify test data

Check:

``` text
src/test/resources/testData/
```

and confirm the Excel data is available.

## Run a test

For QA Chrome:

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

## Check execution

Review:

``` text
target/
```

for Maven/test output and:

``` text
target/allure-results/
```

for Allure results.

## Generate report

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

## View report

``` bash
allure serve target/allure-results
```

------------------------------------------------------------------------

# 38. Adding a New UI Test

A typical process is:

1.  Add/modify a feature file under `src/test/resources/features/`.
2.  Add corresponding step definitions under
    `src/test/java/stepDefinitions/`.
3.  Create or update the relevant Page Object under
    `src/main/java/pages/`.
4.  Add required test data under `src/test/resources/testData/`.
5.  Reuse existing utilities/context where applicable.
6.  Execute the scenario.
7.  Review the Allure result.
8.  Add appropriate assertions and failure handling.

------------------------------------------------------------------------

# 39. Adding a New API Test

A typical process is:

1.  Add the API scenario to a feature file.
2.  Create/update the API step definitions.
3.  Implement reusable API calls in the `api` package.
4.  Add request/test data where required.
5.  Validate status code and response payload.
6.  Execute the scenario.
7.  Review the Allure report.

Recommended structure:

``` text
Feature
  |
  v
API Step Definition
  |
  v
API Service / Client
  |
  v
REST Assured
  |
  v
Response
  |
  v
Assertions
```

------------------------------------------------------------------------

# 40. Adding a New Environment

To add another environment:

1.  Add the environment configuration under:

``` text
src/test/resources/env/
```

2.  Define the required environment-specific properties.
3.  Ensure the configuration reader supports the environment name.
4.  Execute using:

``` bash
mvn clean test -Denv=<environment> -Dbrowser=chrome
```

Example:

``` bash
mvn clean test -Denv=staging -Dbrowser=chrome
```

Do not hard-code environment URLs inside Page Objects or Step
Definitions.

------------------------------------------------------------------------

# 41. Adding Another Browser

If another browser needs to be supported:

1.  Update the browser/driver factory.
2.  Add the corresponding driver setup.
3.  Ensure the browser name is correctly mapped.
4.  Execute:

``` bash
mvn clean test -Denv=QA -Dbrowser=<browser>
```

The feature files and step definitions should remain unchanged.

------------------------------------------------------------------------

# 42. Best Practices

### Feature files

-   Keep scenarios business-readable.
-   Avoid implementation details.
-   Use meaningful scenario names.
-   Avoid excessively long scenarios.

### Step definitions

-   Keep steps lightweight.
-   Delegate UI actions to Page Objects.
-   Delegate API actions to API services/clients.
-   Avoid duplicate code.

### Page Objects

-   Keep locators private.
-   Expose business-level actions.
-   Avoid assertions that belong to the test layer unless the framework
    deliberately follows a page-assertion pattern.
-   Use reusable waits.

### API layer

-   Create reusable API methods.
-   Centralize common request configuration.
-   Validate important response fields.
-   Avoid duplicating request-building logic.

### Test data

-   Keep test data external.
-   Avoid hard-coded credentials.
-   Use meaningful Excel column names.
-   Keep test data organized by functional area.

### Configuration

-   Keep environment-specific configuration outside Java source code.
-   Never commit real secrets.
-   Prefer environment variables/secret managers in CI/CD.

### Reporting

-   Attach screenshots for UI failures.
-   Add useful API/request/response information where safe.
-   Do not expose passwords or tokens in reports.

------------------------------------------------------------------------

# 43. CI/CD Execution Concept

The framework can be integrated into a CI/CD pipeline.

A pipeline can execute:

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

followed by:

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

The resulting report can then be published as a CI/CD artifact.

A typical pipeline flow is:

``` text
Source Code Checkout
        |
        v
Install/Resolve Maven Dependencies
        |
        v
Run Automation Tests
        |
        v
Collect Allure Results
        |
        v
Generate Allure Report
        |
        v
Publish Report
```

------------------------------------------------------------------------

# 44. Troubleshooting

## Tests are not starting

Check:

-   Maven installation
-   Java version
-   `pom.xml`
-   Cucumber runner configuration
-   Feature file location
-   Step-definition package
-   JUnit/Cucumber dependencies

## Browser is not starting

Check:

-   Browser installation
-   Driver implementation
-   Browser parameter
-   Driver factory
-   Selenium version
-   Browser/driver compatibility

Example:

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

## Wrong environment is executed

Check:

``` text
-Denv=QA
```

versus:

``` text
-Denv=uat
```

Also verify the environment configuration and configuration-reader
implementation.

## Excel data is not read

Check:

-   Excel file path
-   File name
-   Sheet name
-   Column names
-   Test-data format
-   Excel reader implementation

Current UI test-data location:

``` text
src/test/resources/testData/ui/OrderTestData.xlsx
```

## Allure report is empty

First confirm that tests actually executed and that:

``` text
target/allure-results/
```

contains result files.

Then run:

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

or:

``` bash
allure serve target/allure-results
```

------------------------------------------------------------------------

# 45. Useful Commands

## Run QA on Chrome

``` bash
mvn clean test -Denv=QA -Dbrowser=chrome
```

## Run UAT on Edge

``` bash
mvn clean test -Denv=uat -Dbrowser=edge
```

## Run QA on Firefox

``` bash
mvn clean test -Denv=qa -Dbrowser=firefox
```

## Generate Allure report

``` bash
allure generate target/allure-results -o target/allure-report --clean
```

## Serve Allure report

``` bash
allure serve target/allure-results
```

------------------------------------------------------------------------

# 46. Quick Reference

``` text
Project
Framework_2026

Main Code
src/main/java/

    api/
    context/
    driver/
    pages/
    testDataReader/
    utility/

Test Code
src/test/java/

    hooks/
    runners/
    stepDefinitions/

Test Resources
src/test/resources/

    env/
    features/
    featureTemplates/
    testData/

Reports
target/allure-results/
target/allure-report/

Screenshots
screenshots/

Configuration
allure.properties
pom.xml
```

------------------------------------------------------------------------

# 47. Framework Design Summary

The framework follows a layered automation architecture:

``` text
                    TEST EXECUTION
                         |
                         v
                Cucumber + JUnit
                         |
                         v
                   Feature Files
                         |
                         v
                  Step Definitions
                    /           \
                   /             \
                  v               v
             UI Layer          API Layer
                |                 |
                v                 v
          Page Objects        API Services
                |                 |
                v                 v
            Selenium          REST Assured
                |                 |
                v                 v
            Web App             API
                   \             /
                    \           /
                     v         v
                       Context
                         |
                         v
                    Assertions
                         |
                         v
                   Allure Results
                         |
                         v
                   Allure Report
```

Supporting layers:

``` text
Environment Configuration
          +
Test Data / Excel
          +
Driver Management
          +
Utilities
          +
Hooks
          +
Logging
```

Together, these components provide a reusable automation framework for
**UI testing, API testing, BDD scenarios, Excel-driven test data,
cross-browser execution, environment-based execution, and Allure
reporting**.
