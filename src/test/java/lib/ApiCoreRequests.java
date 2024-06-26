package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();

    }

    @Step("Make a GET-request with auth cookie only")
    public Response makeGetRequestwithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();

    }

    @Step("Make a GET-request with auth token only")
    public Response makeGetRequestwithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();

    }

    @Step("Make a POST-request")
    public Response makePostRequest(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request for creating user")
    public Response makePostRequestCreateUser(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a PUT-request to edit non auth user")
    public Response makePutRequestEditNoAuthUser(String url, Map<String, String> editData, String wrongToken, String wrongCookie) {
        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .header(new Header("x-csrf-token", wrongToken))
                .cookie("auth_sid", wrongCookie)
                .put(url)
                .andReturn();
    }

    @Step("Make a PUT-request to edit user")
    public Response makePutRequestEditUser(String url, Map<String, String> editData, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .put(url)
                .andReturn();

    }
    @Step("Make a PUT-request to edit user with invalid email")
    public Response makePutRequestEditInvalidEmail(String url, Map<String, String> editData, String token1, String cookie1) {
        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .header(new Header("x-csrf-token", token1))
                .cookie("auth_sid", cookie1)
                .put(url)
                .andReturn();

    }
    @Step("Make a GET-request for invalid email")
    public Response makeGetRequestInvalidEmail(String url, String token1, String cookie1) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token1))
                .cookie("auth_sid", cookie1)
                .get(url)
                .andReturn();

    }
    @Step("Make a GET-request for invalid firstName")
    public Response makeGetRequestInvalidFirstName(String url, String token2, String cookie2) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token2))
                .cookie("auth_sid", cookie2)
                .get(url)
                .andReturn();

    }
    @Step("Make a DELETE-request for authorized user")
    public Response makeDeleteRequest(String url, Map<String, String> deleteData, String token, String cookie ) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .body(deleteData)
                .delete(url)
                .andReturn();

    }
    @Step("Make a POST-request for creating user")
    public Response makePostRequestCreateUserForDelete(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }
    @Step("Make a POST-request for login user")
    public Response makePostRequestForDelete(String url, Map<String, String> authDeleteData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authDeleteData)
                .post(url)
                .andReturn();
    }

    @Step("Make a DELETE-request to delete user successfully")
    public Response makeDeleteRequestForSuccessDeleteUser(String url, Map<String, String> deleteData1, String tokenDel1, String cookieDel1) {
        return given()
                .filter(new AllureRestAssured())
                .body(deleteData1)
                .header(new Header("x-csrf-token", tokenDel1))
                .cookie("auth_sid", cookieDel1)
                .delete(url)
                .andReturn();

    }
    @Step("Make a GET-request for success delete user")
    public Response makeGetRequestForSuccessDeleteUser(String url, String tokenDel1, String cookieDel1) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", tokenDel1))
                .cookie("auth_sid", cookieDel1)
                .get(url)
                .andReturn();

    }
    @Step("Make a DELETE-request to delete user successfully")
    public Response makeDeleteRequestForDeleteAnotherUser(String url, String tokenDelAnother, String cookieDelAnother) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", tokenDelAnother))
                .cookie("auth_sid", cookieDelAnother)
                .delete(url)
                .andReturn();

    }
}
