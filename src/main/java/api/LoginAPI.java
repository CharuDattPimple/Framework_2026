package api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginAPI {

        private static final String BASE_URL =
                "https://rahulshettyacademy.com";

        public Response login(String username, String password) {

            String requestBody = """
                {
                    "userEmail": "%s",
                    "userPassword": "%s"
                }
                """.formatted(username, password);

            return given()
                    .baseUri(BASE_URL)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/api/ecom/auth/login");
        }
}
