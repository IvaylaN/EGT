package APITest;


import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class createUser {

    private static final String BASE_URI = "https://reqres.in";
    private static final String API_KEY = "reqres-free-v1";
    private static final String USERS_ENDPOINT = "/api/users";

    @BeforeClass
    public void setup() {
        baseURI = BASE_URI;
    }

    @Test
    public void createUserTest() {
        given()
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body("{\"name\":\"Test User\", \"job\":\"Automation Tester\"}")
        .when()
                .post(USERS_ENDPOINT)
        .then()
                .statusCode(201)
                .body("name", equalTo("Test User"))
                .body("job", equalTo("Automation Tester"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

}
