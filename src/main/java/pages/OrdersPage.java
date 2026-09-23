package pages;

import org.openqa.selenium.By;

public class OrdersPage extends BasePage {
    private By yourorders = By.xpath("//h1[text()='Your Orders']");
    private By ordersBtn = By.cssSelector("[routerlink='/dashboard/myorders']");
//    private By vieworder =By.xpath("//th[text()='6aae379f2be7a4bc2b59fd74']/parent::tr/td[5]/button");
    private By orderid =By.xpath("//small[@class='col-title']/parent::div/div");
    private By nameOfProduct = By.xpath("//div[@class='artwork-card-info']/div[1]");

    private By viewOrder(String orderId) {

        return By.xpath("//th[text()='" + orderId + "']/parent::tr/td[5]/button");
    }

    public void clickViewOrder(String orderId) {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(yourorders);
        waitUtil.waitForElementVisible(viewOrder(orderId));
        driver.findElement(viewOrder(orderId)).click();
    }

    public void clickOrdersTab() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(ordersBtn);

        driver.findElement(ordersBtn).click();
        waitUtil.waitForClick(ordersBtn);
    }

    public String getOrderId() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(orderid);
        return driver.findElement(orderid).getText().trim();
    }

    public String getProductName() {
        waitUtil.waitForPageToLoad();
        waitUtil.waitForElementVisible(nameOfProduct);
        return driver.findElement(nameOfProduct).getText().trim();
    }


}
