package pages;

import org.openqa.selenium.By;

public class ConfirmationPage extends BasePage {

    private By successMessage = By.cssSelector(".hero-primary");
    private By orderId = By.xpath("//label[@class='ng-star-inserted']");

    public String getOrderId() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(orderId);
        String orderIDtext= driver.findElement(orderId).getText();
        String orderIdReceived= orderIDtext.replace("|","").trim();
        return orderIdReceived;
    }

    public String getSuccessMessage() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(successMessage);
        return driver.findElement(successMessage).getText();
    }
}