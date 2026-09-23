package stepDefinitions.ui;

import context.TestContext;
import io.cucumber.java.en.When;
import pages.HomePage;

public class HomeStepDefinitions {
    HomePage homePage = new HomePage();

    private final TestContext testContext;

    public HomeStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("User adds product {string} to cart")
    public void user_adds_product_to_cart(String productName) {

        System.out.println("Product received from Cucumber: [" + productName + "]");

        productName = productName.trim();

        testContext.setProductName(productName);
        homePage.addProductToCart(productName);
    }

    @When("User navigates to cart page")
    public void user_navigates_to_cart_page() {

        homePage.clickCart();
    }
}
