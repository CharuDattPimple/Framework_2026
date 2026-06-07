package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage extends BasePage {

    public static Logger logger = LogManager.getLogger(CheckoutPage.class);

    private By country = By.cssSelector("[placeholder='Select Country']");

    private By countryOption = By.xpath("//button[contains(@class,'ta-item')]/span");

    private By placeOrderBtn = By.cssSelector(".action__submit");

    public void selectCountry(String countryName) {

        driver.findElement(country).sendKeys(countryName);

        waitUtil.waitForAllElementsVisible(countryOption);

        List<WebElement> dropdList = driver.findElements(countryOption);


        for (int i = 0; i < dropdList.size(); i++) {
            logger.info("Countries : " + dropdList.get(i).getText().trim());
            if (dropdList.get(i).getText().trim().equalsIgnoreCase(countryName)) {
                dropdList.get(i).click();
            }
        }
    }

    public void placeOrder() {

        driver.findElement(placeOrderBtn).click();
    }
}