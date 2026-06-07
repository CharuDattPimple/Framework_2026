package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {


    // Locators
    private By username = By.id("userEmail");
    private By password = By.id("userPassword");
    private By loginBtn = By.xpath("//input[@type='submit']");

    public void login(String user, String pass) {

        driver.findElement(username).sendKeys(user);

        driver.findElement(password).sendKeys(pass);

        waitUtil.waitForElementVisible(loginBtn);

        driver.findElement(loginBtn).click();

        waitUtil.waitForTitle("Let's Shop");
    }
}