package stepDefinitions.ui;

import io.cucumber.java.en.When;
import pages.CheckoutPage;

public class CheckoutStepDefinitions {
  CheckoutPage checkoutPage = new CheckoutPage();

        @When("User enters country {string}")
        public void user_enters_country(String country) {

            checkoutPage.selectCountry(country);
        }

        @When("User places the order")
        public void user_places_the_order() {

            checkoutPage.placeOrder();
        }

}
