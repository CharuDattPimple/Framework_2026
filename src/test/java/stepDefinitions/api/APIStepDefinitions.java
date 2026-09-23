package stepDefinitions.api;

import api.LoginAPI;
import api.OrderAPI;
import context.TestContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utility.ConfigReader;
import utility.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIStepDefinitions {


        private final LoginAPI loginAPI = new LoginAPI();
        private final OrderAPI orderAPI = new OrderAPI();

        private final TestContext testContext;

        public APIStepDefinitions(TestContext testContext) {
            this.testContext = testContext;
        }

    @Given("User is authenticated using API")
    public void user_is_authenticated_using_api() {

            String username = ConfigReader.getUsername();
            String password = ConfigReader.getPassword();

            Response response =
                    loginAPI.login(username, password);

            assertEquals(
                    200,
                    response.getStatusCode(),
                    "Login API failed");

            String token =
                    response.jsonPath()
                            .getString("token");

            assertNotNull(
                    token,
                    "Token was not generated");

            testContext.setToken(token);

            System.out.println("API Login successful");
        }

    @Given("User creates order using API for product {string} and country {string}")
    public void user_creates_order_using_api_for_product_and_country(
                String productName,
                String country) {

            testContext.setProductName(productName);
            testContext.setCountry(country);

            /*
             * For now we are using the product ID
             * captured from your actual request.
             *
             * Later we can make this dynamic by
             * calling the product API.
             */
            String productId =
                    "6960eac0c941646b7a8b3e68";

            testContext.setProductId(productId);

            String json =
                    JsonUtil.readJsonFile(
                            "src/test/resources/testData/api/createOrder.json");

            Map<String, String> values =
                    new HashMap<>();

            values.put("country", country);
            values.put("productId", productId);

            json =
                    JsonUtil.replacePlaceholders(
                            json,
                            values);

            System.out.println("Create Order Request:");
            System.out.println(json);

            Response response =
                    orderAPI.createOrder(
                            testContext.getToken(),
                            json);

            System.out.println("Create Order Response:");
            response.prettyPrint();

            assertEquals(
                    201,
                    response.getStatusCode(),
                    "Create Order API failed");

            /*
             * We need to inspect the actual response
             * to determine the exact JSONPath.
             */
            String orderId =
                    response.jsonPath()
                            .getString("orders[0]");

            assertNotNull(
                    orderId,
                    "Order ID was not generated");

            testContext.setOrderId(orderId);

            System.out.println(
                    "Created Order ID: " + orderId);
        }

    @Then("Order should be validated using API")
    public void order_should_be_validated_using_api() {

        String orderId = testContext.getOrderId();
        String token = testContext.getToken();

        Response response =
                orderAPI.getOrderDetails(token, orderId);

        System.out.println("API Status Code: "
                + response.getStatusCode());

        System.out.println("API Response:");
        response.prettyPrint();

        assertEquals(
                response.getStatusCode(),
                200,
                "Order API did not return 200"
        );



        String productName =
                response.jsonPath().getString("data.productName").toLowerCase();

        String country =
                response.jsonPath().getString("data.country");

        System.out.println("Product Name: " + productName);
        System.out.println("Country: " + country);

        assertEquals(
                productName,
                testContext.getProductName().toLowerCase(),
                "Product mismatch between UI and API"
        );

        assertEquals(
                country,
                testContext.getCountry(),
                "Country mismatch between UI and API"
        );
    }

}
