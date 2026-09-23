package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private By cartProducts = By.cssSelector(".cartSection h3");

    private By checkoutBtn = By.cssSelector(".totalRow button");

    public boolean verifyProductInCart(String productName) {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(cartProducts); // or create a visibility wait method

        List<WebElement> products = driver.findElements(cartProducts);


        for (WebElement product : products) {

            if (product.getText().trim().equalsIgnoreCase(productName)) {
                return true;
            }
        }

        return false;
    }

    public void clickCheckout() {

        driver.findElement(checkoutBtn).click();
    }
}