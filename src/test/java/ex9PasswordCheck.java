import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ex9PasswordCheck {
    @Test
    public void testPasswCheck() {
        String[] pass = new String[] {"password", "123456", "12345678", "qwerty", "abc123", "monkey", "1234567", "letmein", "trustno1", "dragon", "baseball", "111111", "iloveyou", "master", "sunshine", "ashley", "bailey", "passw0rd", "shadow", "123123", "654321", "superman", "qazwsx", "michael", "Football", "welcome", "jesus", "ninja", "mustang", "password1", "password", "123456789", "adobe123", "admin", "1234567890", "photoshop", "1234", "12345", "princess", "azerty", "000000", "access", "696969", "batman", "1qaz2wsx", "login", "qwertyuiop", "solo", "starwars", "121212", "flower", "hottie", "loveme", "zaq1zaq1", "hello", "freedom", "whatever", "666666", "!@#$%^&*", "charlie", "aa123456", "donald", "qwerty123", "1q2w3e4r", "555555", "lovely", "7777777", "888888", "123qwe"};
        int i=0;
        for (i=0; i<pass.length; i++) {
            Map<String, String> authData = new HashMap<>();
            authData.put("login", "super_admin");
            authData.put("password", pass[i]);
            Response responseForCookie = RestAssured
                    .given()
                    .body(authData)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String auth_cookie = responseForCookie.getCookie("auth_cookie");
            Response responseForAuth = RestAssured
                    .given()
                    .queryParam("auth_cookie", auth_cookie)
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            String resultOfAuth = responseForAuth.asString();
            System.out.println(resultOfAuth);
           if (resultOfAuth == "You are authorized"){break;}
            System.out.println(pass[i]);
        }

    }

}