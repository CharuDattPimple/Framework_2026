package testDataReader;

public class OrderTestData {

    private final String testCase;
    private final String productName;
    private final String country;
    private final String runMode;

    public OrderTestData(
            String testCase,
            String productName,
            String country,
            String runMode) {

        this.testCase = testCase;
        this.productName = productName;
        this.country = country;
        this.runMode = runMode;
    }

    public String getTestCase() {
        return testCase;
    }

    public String getProductName() {
        return productName;
    }

    public String getCountry() {
        return country;
    }

    public String getRunMode() {
        return runMode;
    }
}