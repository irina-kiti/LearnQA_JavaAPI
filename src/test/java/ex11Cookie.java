import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ex11Cookie {
    @Test
    public void testForCookies(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
       Map<String, String> responseCookies = response.getCookies();
      String cookiesForAssert = responseCookies.toString();

        assertEquals("{HomeWork=hw_value}", cookiesForAssert, "Wrong Cookies");


    }
}
