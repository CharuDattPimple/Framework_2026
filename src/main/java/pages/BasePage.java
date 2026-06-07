package pages;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import utility.WaitUtil;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtil waitUtil;

    public BasePage() {
        driver = DriverFactory.getDriver();
        waitUtil = new WaitUtil(driver);

    }
}