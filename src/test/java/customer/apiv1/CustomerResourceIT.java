package customer.apiv1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest

public class CustomerResourceIT {

    @Test
    void testCreateCustomer() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Adrian\", \"middleName\": \"\", \"firstSurname\": \"Estevez\", " +
                        "\"secondSurname\": \"Doe\", \"email\":" +
                        " \"adrian@example.com\", \"address\": \"Street 123\", \"phoneNumber\": \"1234567890\", " +
                        "\"country\": \"DO\"}")
                .when().post("/api/v1/customers")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("Adrian"))
                .body("demonym", equalTo("Dominican"));
    }

    @Test
    void testCreateCustomerWithEmptyBody() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when().post("/api/v1/customers")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetCustomers() {
        given()
                .when().get("/api/v1/customers")
                .then()
                .statusCode(200)
                .body("currentPage", equalTo(1))
                .body("hasNextPage", equalTo(false));
    }

    @Test
    void testGetCustomerById() {
        long newCustomerId = given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Customer\", \"middleName\": \"To\", \"firstSurname\": \"Be\", " +
                        "\"secondSurname\": \"Fetched\", \"email\":" +
                        " \"customer@tobe.com\", \"address\": \"Street 123\", \"phoneNumber\": \"0000000002\", " +
                        "\"country\": \"DO\"}")
                .when().post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getLong("id");


        given()
                .when().get("/api/v1/customers/"
                        .concat(String.valueOf(newCustomerId)))
                .then()
                .statusCode(200).
                body("id", equalTo((int) newCustomerId)).
                body("firstName", equalTo("Customer"))
                .body("demonym", equalTo("Dominican"));

    }



    @Test
    void testUpdateCustomer() {
        long newCustomerId = given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"John\", \"middleName\": \"\", \"firstSurname\": \"Doe\", " +
                        "\"secondSurname\": \"Doe\", \"email\":" +
                        " \"john@example.com\", \"address\": \"Street 123\", \"phoneNumber\": \"1234567890\", " +
                        "\"country\": \"DO\"}")
                .when().post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getLong("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("id", newCustomerId)
                .body("{ \"id\":" + newCustomerId + ", \"email\":\"john@example.com\", \"address\":\"Street 123\", \"phoneNumber\":\"1234567890\", " +
                        "\"country\":\"US\"}").when().put("/api/v1/customers/" + newCustomerId)
                .then()
                .statusCode(200)
                .body("country", equalTo("US"))
                .body("demonym", equalTo("American"));
    }

    @Test
    void testDeleteCustomer() {

        long newCustomerId = given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Customer\", \"middleName\": \"To\", \"firstSurname\": \"Be\", " +
                        "\"secondSurname\": \"Deleted\", \"email\":" +
                        " \"customer@deleted.com\", \"address\": \"Street 123\", \"phoneNumber\": \"1234567890\", " +
                        "\"country\": \"DO\"}")
                .when().post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getLong("id");


        given()
                .when().delete("/api/v1/customers/"
                        .concat(String.valueOf(newCustomerId)))
                .then()
                .statusCode(200);
    }
}
