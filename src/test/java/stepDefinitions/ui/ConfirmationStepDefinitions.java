package stepDefinitions.ui;
import static org.junit.jupiter.api.Assertions.*;

import context.TestContext;
import io.cucumber.java.en.Then;
import pages.ConfirmationPage;

public class ConfirmationStepDefinitions {
  ConfirmationPage confirmationPage =
                new ConfirmationPage();

        public static String orderId = null;
    private final TestContext testContext;

    public ConfirmationStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }


    @Then("Order success message should be displayed")
        public void order_success_message_should_be_displayed() {
            orderId = confirmationPage.getOrderId();
            testContext.setOrderId(orderId);
            System.out.println("Fetched Order ID: "+orderId);
            assertEquals(confirmationPage.getSuccessMessage(), "THANKYOU FOR THE ORDER.");
        }

}
