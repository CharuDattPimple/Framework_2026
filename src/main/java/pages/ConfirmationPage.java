package pages;

import org.openqa.selenium.By;

public class ConfirmationPage extends BasePage {

    private By successMessage = By.cssSelector(".hero-primary");

    public String getSuccessMessage() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(successMessage);
        return driver.findElement(successMessage).getText();
    }
}