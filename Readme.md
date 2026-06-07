mvn clean test -Denv=QA -Dbrowser=chrome
mvn clean test -Denv=uat -Dbrowser=edge
mvn clean test -Denv=qa -Dbrowser=firefox


-Denv=qa
↓
ConfigReader
↓
qa.yml loaded
↓
URL + Credentials

-Dbrowser=chrome
↓
BrowserFactory
↓
ChromeDriver

DriverFactory
↓
ThreadLocal<WebDriver>

Hooks
↓
Launch URL
↓
Execute Test