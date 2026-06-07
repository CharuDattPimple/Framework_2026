package stepDefinitions.ui;



import io.cucumber.java.en.*;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.*;

public class CartStepDefinitions {

    CartPage cartPage = new CartPage();

    @Then("Product {string} should be displayed in cart")
    public void product_should_be_displayed_in_cart(String productName) {

        assertTrue(cartPage.verifyProductInCart(productName), "Product not found in cart");
    }

    @When("User clicks checkout button")
    public void user_clicks_checkout_button() {
        cartPage.clickCheckout();
    }
}
