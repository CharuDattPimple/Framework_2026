package stepDefinitions.ui;

import io.cucumber.java.en.When;
import pages.HomePage;

public class HomeStepDefinitions {
    HomePage homePage = new HomePage();

    @When("User adds product {string} to cart")
    public void user_adds_product_to_cart(String productName) {

        homePage.addProductToCart(productName);
    }

    @When("User navigates to cart page")
    public void user_navigates_to_cart_page() {

        homePage.clickCart();
    }
}
