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

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test delete user which is forbidden to delete")
    @DisplayName("Test negative delete forbidden user")
    public void DeleteForbiddenUser(){
        //LOGIN USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");

        //DELETE USER
        Map<String, String> deleteData = new HashMap<>();
        deleteData.put("email", "vinkotov@example.com");
        deleteData.put("password", "1234");
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/2",
                        deleteData,
                        this.header,
                        this.cookie
                        );
        Assertions.assertResponseTextEquals(responseDeleteUser, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");
    }
    @Test
    @Description("This test successfully delete user")
    @DisplayName("Test positive delete user")
    public void DeleteUserSuccessfully(){
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests
                .makePostRequestCreateUserForDelete("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateUser.jsonPath().get("id");
        String userEmail = userData.get("email");
        String userPassword = userData.get("password");

        //LOGIN
        Map<String, String> authDeleteData = new HashMap<>();
        authDeleteData.put("email", userData.get("email"));
        authDeleteData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequestForDelete("https://playground.learnqa.ru/api/user/login", authDeleteData);
        String tokenDel1 = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookieDel1 = this.getCookie(responseGetAuth, "auth_sid");

        //DELETE
        Map<String, String> deleteData1 = new HashMap<>();
        deleteData1.put("email", userEmail);
        deleteData1.put("password", userPassword);

        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequestForSuccessDeleteUser("https://playground.learnqa.ru/api/user/" + userId,
                        deleteData1,
                        tokenDel1,
                        cookieDel1);

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequestForSuccessDeleteUser("https://playground.learnqa.ru/api/user/" + userId,
                        tokenDel1,
                        cookieDel1);

       Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }

    @Test
    @Description("This test delete User which is authorized as another user")
    @DisplayName("Test negative delete another user")
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

        String cookieDelAnother = this.getCookie(responseGetAuth, "auth_sid");
        String tokenDelAnother = this.getHeader(responseGetAuth, "x-csrf-token");

        //DELETE AS USER

        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequestForDeleteAnotherUser("https://playground.learnqa.ru/api/user/" + userId,
                        tokenDelAnother,
                        cookieDelAnother);
        Assertions.assertResponseTextEquals(responseDeleteUser, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");

    }

    }

