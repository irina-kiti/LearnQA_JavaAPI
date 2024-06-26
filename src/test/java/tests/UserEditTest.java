package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    private Response id;

    @Test
    public void testEditJustCreatedTest() {
//GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        //EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        //GET
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        Assertions.asserJsonByName(responseUserData, "firstName", newName);

    }

    @Test
    @Description("This test edit User which is not authorized")
    @DisplayName("Test negative edit non authorized user")
    public void testEditNonAuthorizedUser() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateUser.jsonPath().get("id");

        //EDIT
        String newName = "Changed Name";
        String wrongToken = "nnn";
        String wrongCookie = "qqq";
        Map<String, String> editData = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();
        editData.put("firstName", newName);
        headers.put("x-csrf-token", wrongToken);
        cookies.put("auth_sid", wrongCookie);

        Response responseEditNoAuthUser = apiCoreRequests
                .makePutRequestEditNoAuthUser("https://playground.learnqa.ru/api/user/" + userId,
                        editData,
                        wrongToken,
                        wrongCookie);

        Assertions.assertResponseTextEquals(responseEditNoAuthUser, "{\"error\":\"Auth token not supplied\"}");
    }


    @Test
    @Description("This test edit User which is authorized as another user")
    @DisplayName("Test negative edit another user")
    public void testEditAnotherUser() {

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateUser.jsonPath().get("id");

        //LOGIN AS ANOTHER USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");

        //EDIT AS USER
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests
                .makePutRequestEditUser("https://playground.learnqa.ru/api/user/" + userId,
                        editData,
                        this.header,
                        this.cookie);

        //GET USER
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                        this.header,
                        this.cookie);

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }

    @Test
    @Description("This test edit authorized User with invalid email")
    @DisplayName("Test negative edit user")
    public void testEditUserInvalidEmail() {

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateUser.jsonPath().get("id");
        String userEmail = userData.get("email");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String token1 = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie1 = this.getCookie(responseGetAuth, "auth_sid");
        //EDIT
        String newFirstName = "test_example.com";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstname", newFirstName);
        Response responseEditUser = apiCoreRequests
                .makePutRequestEditInvalidEmail("https://playground.learnqa.ru/api/user/" + userId,
                        editData,
                        token1,
                        cookie1);

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequestInvalidFirstName("https://playground.learnqa.ru/api/user/" + userId,
                        token1,
                        cookie1);

        Assertions.asserJsonByName(responseUserData, "email", userEmail);
    }

    @Test
    @Description("This test edit authorized User with invalid firstname")
    @DisplayName("Test negative edit user")
    public void testEditUserInvalidFirstName() {

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests
                .makePostRequestCreateUser("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateUser.jsonPath().get("id");
        String userFirstName = userData.get("firstName");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String token2 = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie2 = this.getCookie(responseGetAuth, "auth_sid");
        //EDIT
        String newFirstName = "t";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstname", newFirstName);
        Response responseEditUser = apiCoreRequests
                .makePutRequestEditInvalidEmail("https://playground.learnqa.ru/api/user/" + userId,
                        editData,
                        token2,
                        cookie2);

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequestInvalidFirstName("https://playground.learnqa.ru/api/user/" + userId,
                        token2,
                        cookie2);

        Assertions.asserJsonByName(responseUserData, "firstName", userFirstName);
    }

}