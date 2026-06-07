package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {

    private By products = By.cssSelector(".card-body");

    private By productsText = By.cssSelector(".card-body b");

    private By cartBtn = By.cssSelector("[routerlink='/dashboard/cart']");

    private By spinner = By.cssSelector("#toast-container");

    public void addProductToCart(String productName) {

        waitUtil.waitForAllElementsVisible(products);

        List<WebElement> productList = driver.findElements(products);

        for (WebElement product : productList) {

            String name = product.findElement(By.cssSelector("b")).getText();

            if (name.trim().equalsIgnoreCase(productName)) {

                WebElement addToCartBtn = product.findElement(By.xpath(".//button[contains(.,'Add To Cart')]"));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);

                break;
            }
        }
    }

    public void clickCart() {
        waitUtil.waitForElementInvisible(spinner);
        waitUtil.waitForPageToLoad();
        waitUtil.waitForClick(cartBtn);
        driver.findElement(cartBtn).click();
    }
}