package api;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class OrderAPI {

        private static final String BASE_URL =
                "https://rahulshettyacademy.com";

        public Response createOrder(
                String token,
                String requestBody) {

            return given()
                    .baseUri(BASE_URL)
                    .contentType("application/json")
                    .header("Authorization", token)
                    .body(requestBody)
                    .when()
                    .post("/api/ecom/order/create-order");
        }


        public Response getOrderDetails(String token, String orderId) {

            return given()
                    .baseUri(BASE_URL)
                    .header("Authorization", token)
                    .pathParam("orderId", orderId)
                    .when()
                    .get("/api/ecom/order/get-orders-details?id={orderId}");
        }

}
