package stepDefinitions.ui;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Then;
import pages.ConfirmationPage;

public class ConfirmationStepDefinitions {
  ConfirmationPage confirmationPage =
                new ConfirmationPage();

        @Then("Order success message should be displayed")
        public void order_success_message_should_be_displayed() {

            assertEquals(confirmationPage.getSuccessMessage(), "THANKYOU FOR THE ORDER.");
        }

}
