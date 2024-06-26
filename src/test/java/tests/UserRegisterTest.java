package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");

    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");

    }
   /* @ParameterizedTest
    @ValueSource(strings = {
            "email":""
    })*/

    @Test
    @Description("This test create user with not valid email")
    @DisplayName("Negative create user")
    public void testCreateUserWithNotValidEmail() {
        String email = "vinkotovexample.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");

    }

    @Test
    @Description("This test create user with one symbol name")
    @DisplayName("Negative create user")


    public void testCreateUserWithShortName() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", DataGenerator.getRandomEmail());
        userData.put("password", "123456");
        userData.put("username", "l");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'username' field is too short");
    }
    @Test
    @Description("This test create user with 251 symbol name")
    @DisplayName("Negative create user")


    public void testCreateUserWhithLongtName() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", DataGenerator.getRandomEmail());
        userData.put("password", "123456");
        userData.put("username", "learnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqlearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'username' field is too long");
    }
}
