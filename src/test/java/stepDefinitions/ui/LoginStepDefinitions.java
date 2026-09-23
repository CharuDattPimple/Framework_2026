package stepDefinitions.ui;

import io.cucumber.java.en.Given;
import pages.LoginPage;
import utility.ConfigReader;


public class LoginStepDefinitions {
    LoginPage loginPage = new LoginPage();


    @Given("User logs into application")
    public void user_logs_into_application() {

        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }
}
